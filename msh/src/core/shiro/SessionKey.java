package core.shiro;

/**
 * 会话key
 *
 * @author Tang Yong Di
 * @date 2016/4/8
 */
public enum SessionKey {
    USER("登录用户信息");

    private String label;

    SessionKey(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return name();
    }
}
