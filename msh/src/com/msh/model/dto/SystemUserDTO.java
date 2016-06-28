package com.msh.model.dto;

import com.msh.model.entity.Enum.Gender;
import com.msh.model.entity.Enum.Status;
import com.msh.model.entity.system.SystemRole;
import com.msh.model.entity.system.SystemUser;
import core.utils.BaseForm;
import core.utils.JodaUtils;

/**
 * @author Tang Yong Di
 * @date 2016/4/8
 */
public class SystemUserDTO extends BaseForm {

    private Integer id;
    private String username;
    private Gender gender;
    private String phone;
    private String account;
    private String lastLoginTime;
    private Status status;
    private Integer roleId;

    public SystemUserDTO() {
    }

    public SystemUserDTO(SystemUser user) {
        this.id = user.id();
        this.username = user.getUsername();
        this.gender = user.getGender();
        this.phone = user.getPhone();
        this.account = user.getAccount();
        this.lastLoginTime = JodaUtils.dateTimeAllToString(user.getLastLoginTime());
        this.status = user.getStatus();

        SystemRole role = user.getRole();
        if(role != null) {
            this.roleId = role.id();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
