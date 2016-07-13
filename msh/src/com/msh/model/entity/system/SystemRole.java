package com.msh.model.entity.system;

import com.msh.model.entity.AbstractDomain;
import org.joda.time.DateTime;

/**
 * @author Tang Yong Di
 * @date 2016/3/21
 */
public class SystemRole extends AbstractDomain {
    private Integer id;
    private String name;
    private String code;
    private String authority;
    private DateTime updateTime;

    public SystemRole() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getAuthority() {
        return authority;
    }

    public DateTime getUpdateTime() {
        return updateTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setUpdateTime(DateTime updateTime) {
        this.updateTime = updateTime;
    }
}
