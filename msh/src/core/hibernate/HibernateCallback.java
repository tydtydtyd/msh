package core.hibernate;

import core.utils.StringUtils;

/**
 * @author Tang Yong Di
 * @date 2016/3/5
 */
public abstract class HibernateCallback<T> implements org.springframework.orm.hibernate3.HibernateCallback<T> {
    private String name;

    protected HibernateCallback() {
    }

    protected HibernateCallback(String name) {
        this.name = name;
    }

    protected HibernateCallback(String tag, String method) {
        this.name = tag + "." + method;
    }

    protected HibernateCallback(String tag, String method, Object... params) {
        this.name = tag + "." + method + "(" + StringUtils.join(params, ",") + ")";
    }

    public String getName() {
        return name;
    }
}
