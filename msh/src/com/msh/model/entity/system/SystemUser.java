package com.msh.model.entity.system;

import com.msh.model.entity.AbstractDomain;
import com.msh.model.entity.Enum.Gender;
import com.msh.model.entity.Enum.Status;
import org.joda.time.DateTime;

/**
 * @author Tang Yong Di
 * @date 2016/3/21
 */
public class SystemUser extends AbstractDomain {

    private String username;
    private Gender gender;
    private String phone;
    private String account;
    private String password;
    private DateTime lastLoginTime;
    private Status status;
    private SystemRole role;

    public SystemUser() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(DateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public SystemRole getRole() {
        return role;
    }

    public void setRole(SystemRole role) {
        this.role = role;
    }
}
