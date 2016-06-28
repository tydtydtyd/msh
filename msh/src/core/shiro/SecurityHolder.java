package core.shiro;

import com.msh.model.dto.SystemUserDTO;
import com.msh.model.entity.system.SystemUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * @author Tang Yong Di
 * @date 2016/4/8
 */
public final class SecurityHolder {

    private static Subject currentUser;

    private static Subject getCurrentUser() {
        if (currentUser == null) currentUser = SecurityUtils.getSubject();
        return currentUser;
    }

    public static SystemUserDTO getSystemUserDTO() {
        Object attribute = getUserSession();
        if (attribute != null) {
            SystemUser systemUser = (SystemUser) attribute;
            return new SystemUserDTO(systemUser);
        }
        return null;
    }

    public static String getUsername() {
        Object attribute = getUserSession();
        if (attribute != null) {
            SystemUser systemUser = (SystemUser) attribute;
            return systemUser.getUsername();
        }
        return "";
    }

    public static Integer getUserId() {
        Object attribute = getUserSession();
        if (attribute != null) {
            SystemUser systemUser = (SystemUser) attribute;
            return systemUser.id();
        }
        return null;
    }

    private static Object getUserSession() {
        Session session = getCurrentUser().getSession();
        return session.getAttribute(SessionKey.USER.getValue());
    }
}
