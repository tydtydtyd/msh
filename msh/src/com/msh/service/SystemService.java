package com.msh.service;

import com.msh.model.entity.system.SystemRole;
import com.msh.model.entity.system.SystemUser;

/**
 * @author Tang Yong Di
 * @date 2016/3/21
 */
public interface SystemService {
    SystemUser getUserByAccount(String account);

    SystemRole getRoleByUserId(Integer uid) throws Exception;

    void updateLastLoginTime(String account);
}
