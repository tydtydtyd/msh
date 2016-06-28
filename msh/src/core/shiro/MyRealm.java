package core.shiro;

import com.msh.model.entity.system.SystemRole;
import com.msh.model.entity.system.SystemUser;
import com.msh.service.SystemService;
import core.cache.EhcacheKey;
import core.cache.EhcacheUtils;
import core.utils.ValidationUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 自定义的指定Shiro验证用户登录的类
 *
 * @author Tang Yong Di
 * @date 2016/3/4
 */
public class MyRealm extends AuthorizingRealm {

    private final static String AUTH = EhcacheKey.AUTH.getValue();

    @Resource
    private SystemService systemService;

    /**
     * 为当前登录的Subject授予角色和权限
     * 经测试:本例中该方法的调用时机为需授权资源被访问时
     * 经测试:并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache
     * 个人感觉若使用了Spring3.1开始提供的ConcurrentMapCache支持,则可灵活决定是否启用AuthorizationCache
     * 比如说这里从数据库获取权限信息时,先去访问Spring3.1提供的缓存,而不使用Shiro提供的AuthorizationCache
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        try {
            Object cacheObject = EhcacheUtils.getCacheObject(AUTH);
            if (cacheObject == null) {
                //获取当前登录的用户名,等价于(String)principals.fromRealm(this.getName()).iterator().next()
                String account = (String) super.getAvailablePrincipal(principals);
                List<String> roleList = new ArrayList<>();
                List<String> permissionList = new ArrayList<>();
                //从数据库中获取当前登录用户的详细信息
                SystemUser user = systemService.getUserByAccount(account);
                if (null != user) {
                    SystemRole role = systemService.getRoleByUserId(user.id());
                    if (role != null) {
                        roleList.add(role.getCode());
                        String authority = role.getAuthority();
                        if (ValidationUtils.isNotEmpty(authority)) {
                            if (authority.equals("ALL")) {
                                List<ShiroPermission> allPermission = ShiroPermission.getAllPermission();
                                for (ShiroPermission shiroPermission : allPermission) {
                                    permissionList.add(shiroPermission.getValue());
                                }
                            } else {
                                String[] authorities = authority.split(",");
                                Collections.addAll(permissionList, authorities);
                            }
                        }
                    }
                } else {
                    throw new AuthorizationException();
                }
                //为当前用户设置角色和权限
                SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
                simpleAuthorInfo.addRoles(roleList);
                simpleAuthorInfo.addStringPermissions(permissionList);
                //权限放入内存
                EhcacheUtils.putCacheObject(AUTH, simpleAuthorInfo);
                //若该方法什么都不做直接返回null的话,就会导致任何用户访问/admin/listUser.jsp时都会自动跳转到unauthorizedUrl指定的地址
                //详见applicationContext.xml中的<bean id="shiroFilter">的配置
                return simpleAuthorInfo;
            } else {
                return (SimpleAuthorizationInfo) cacheObject;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthorizationException();
        }
    }


    /**
     * 验证当前登录的Subject
     * 经测试:本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        //获取基于用户名和密码的令牌
        //实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的
        //两个token的引用都是一样的,本例中是org.apache.shiro.authc.UsernamePasswordToken@33799a1e
            UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
            //System.out.println("验证当前Subject时获取到token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
            SystemUser user = systemService.getUserByAccount(token.getUsername());
            if (null != user) {
                AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getAccount(), user.getPassword(), user.getUsername());
                this.setSession(SessionKey.USER.getValue(), user);
                return authcInfo;
            } else {
                return null;
            }

        //此处无需比对,比对的逻辑Shiro会做,我们只需返回一个和令牌相关的正确的验证信息
        //说白了就是第一个参数填登录用户名,第二个参数填合法的登录密码(可以是从数据库中取到的,本例中为了演示就硬编码了)
        //这样一来,在随后的登录页面上就只有这里指定的用户和密码才能通过验证
        /*if ("jadyer".equals(token.getUsername())) {
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo("jadyer", "jadyer", this.getName());
            this.setSession("currentUser", "jadyer");
            return authcInfo;
        } else if ("玄玉".equals(token.getUsername())) {
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo("玄玉", "xuanyu", this.getName());
            this.setSession("currentUser", "玄玉");
            return authcInfo;
        }*/
        //没有返回登录用户名对应的SimpleAuthenticationInfo对象时,就会在LoginController中抛出UnknownAccountException异常
        //return null;
    }


    /**
     * 将一些数据放到ShiroSession中,以便于其它地方使用
     * 比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到
     */
    private void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
            session.setAttribute(key, value);
        }
    }
}