package core.cache;

/**
 * @author Tang Yong Di
 * @date 2016/4/7
 */
public enum EhcacheKey {
    AUTH("授权类缓存");

    private String label;

    EhcacheKey(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return name();
    }
}
