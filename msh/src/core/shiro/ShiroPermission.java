package core.shiro;

import java.util.Arrays;
import java.util.List;

/**
 * shiro权限
 *
 * @author Tang Yong Di
 * @date 2016/3/18
 */
public enum ShiroPermission {

    INDEX(null, "首页"),

    SYSTEM_MANAGE(null, "系统管理"),
    SYSTEM_USER_MANAGE(SYSTEM_MANAGE, "用户管理"),
    SYSTEM_ROLE_MANAGE(SYSTEM_MANAGE, "角色管理"),

    ENTRY_MANAGE(null, "人员管理"),
    ENTRY_USER_MANAGE(ENTRY_MANAGE, "入职人管理"),
    ENTRY_SALARY(ENTRY_MANAGE, "结账录入"),

    REPORT(null, "报表"),
    ENTRY_SALARY_FOR_DAY(REPORT, "入职工资统计报表"),;

    //父权限
    private ShiroPermission parentPermission;
    //权限说明
    private String label;

    private ShiroPermission(ShiroPermission parentPermission, String label) {
        this.parentPermission = parentPermission;
        this.label = label;
    }

    public static List<ShiroPermission> getAllPermission() {
        return Arrays.asList(ShiroPermission.values());
    }

    public ShiroPermission getParentPermission() {
        return parentPermission;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return name();
    }

    public ShiroPermission getRoot() {
        return this.getParentPermission() == null ? this : this.getParentPermission();
    }
}
