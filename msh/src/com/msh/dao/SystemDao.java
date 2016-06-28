package com.msh.dao;

import com.msh.model.entity.system.SystemUser;
import core.dao.Dao;

/**
 * @author Tang Yong Di
 * @date 2016/3/21
 */
public interface SystemDao extends Dao {
    SystemUser getUserByAccount(String account);

    Object[] getRoleByUserId(Integer uid);

    SystemUser loadSystemUserByAccount(String account);

    void updateSystemUser(SystemUser user);
}
