package com.msh.controller;

import com.msh.service.SystemService;
import core.cache.EhcacheKey;
import core.cache.EhcacheUtils;
import core.utils.MD5Utils;
import core.utils.ValidationUtils;
import core.web.WebAttributeKey;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tang Yong Di
 * @date 2016/3/3
 */
@Controller
@RequestMapping("/system/login")
public class LoginController {

    @Resource
    private SystemService systemService;

    @RequestMapping("/toLogin")
    public ModelAndView toLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String account = ServletRequestUtils.getRequiredStringParameter(request, "account");
        String password = ServletRequestUtils.getRequiredStringParameter(request, "password");

        String pwd = ValidationUtils.isEmpty(password) ? "" : MD5Utils.md5(password);
        UsernamePasswordToken token = new UsernamePasswordToken(account, pwd);
        token.setRememberMe(true);
        //System.out.println("为了验证登录用户而封装的token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
        //获取当前的Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            currentUser.login(token);
        } catch (UnknownAccountException uae) {
            request.setAttribute("message_login", "未知账户");
        } catch (IncorrectCredentialsException ice) {
            request.setAttribute("message_login", "密码不正确");
        } catch (LockedAccountException lae) {
            request.setAttribute("message_login", "账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            request.setAttribute("message_login", "用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            ae.printStackTrace();
            request.setAttribute("message_login", "用户名或密码不正确");
        }
        //验证是否登录成功
        if (currentUser.isAuthenticated()) {
            systemService.updateLastLoginTime(account);
            return new ModelAndView("redirect:/system/login/index");
        } else {
            token.clear();
            return new ModelAndView("redirect:/system/login/logout");
        }
    }

    @RequestMapping("/index")
    public ModelAndView index() throws Exception {
        return new ModelAndView("/system/index");
    }

    /**
     * 用户登出
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        SecurityUtils.getSubject().logout();
        EhcacheUtils.removeCacheObject(EhcacheKey.AUTH.getValue());
        request.removeAttribute(WebAttributeKey.USERNAME);
        return "/index";
    }

}
