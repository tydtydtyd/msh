package com.msh.model.dto;

import com.msh.model.entity.system.SystemRole;
import core.utils.BaseForm;
import core.utils.JodaUtils;
import core.utils.Pagination;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tyd
 * @date 2016/7/13
 */
public class SystemRoleDTO extends BaseForm{
    private Integer id;
    private String name;
    private String code;
    private String authority;
    private String updateTime;

    public SystemRoleDTO() {
    }

    public SystemRoleDTO(SystemRole systemRole) {
        this.id = systemRole.id();
        this.name = systemRole.getName();
        this.code = systemRole.getCode();
        this.authority = systemRole.getAuthority();
        DateTime updateTime = systemRole.getUpdateTime();
        if(updateTime != null) {
            this.updateTime = JodaUtils.dateTimeAllToString(updateTime);
        }
    }

    public static Pagination<SystemRoleDTO> toPages(Pagination<SystemRole> systemRolePagination) {
        Pagination<SystemRoleDTO> pagination = new Pagination<>(systemRolePagination.getTotalCount(), systemRolePagination.getCurrentPage(),
                systemRolePagination.getPageSize(), systemRolePagination.getSumPage(), systemRolePagination.isQueryAll(), systemRolePagination.getSortName(),
                systemRolePagination.getSortType());
        List<SystemRole> systemRoleList = systemRolePagination.getList();
        List<SystemRoleDTO> systemRoleDTOs = new ArrayList<>();
        for (SystemRole systemRole : systemRoleList) {
            SystemRoleDTO systemRoleDTO = new SystemRoleDTO(systemRole);
            systemRoleDTOs.add(systemRoleDTO);
        }
        pagination.setList(systemRoleDTOs);
        return pagination;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
