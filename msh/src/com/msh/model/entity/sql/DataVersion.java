package com.msh.model.entity.sql;


import com.msh.model.entity.AbstractDomain;

/**
 * 数据版本实体类
 * @author Tang Yong Di
 * @date 2015/9/29
 */
public class DataVersion extends AbstractDomain {

    public static final String SERVER_VERSION_KEY = "server_version";

    //数据名称
    private String name;

    //版本号
    private Integer version;

    public String getName() {
        return name;
    }

    public Integer getVersion() {
        return version;
    }
}
