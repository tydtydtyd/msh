package com.msh.service.impl;

import com.msh.dao.SystemDao;
import com.msh.model.entity.system.SystemRole;
import com.msh.model.entity.system.SystemUser;
import com.msh.service.SystemService;
import core.utils.JodaUtils;
import core.utils.ValidationUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Tang Yong Di
 * @date 2016/3/21
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Resource
    private SystemDao systemDao;

    @Override
    public SystemUser getUserByAccount(String account) {
        return systemDao.getUserByAccount(account);
    }

    @Override
    public SystemRole getRoleByUserId(Integer uid) throws Exception {
        Object[] role = systemDao.getRoleByUserId(uid);
        SystemRole systemRole = null;
        if (ValidationUtils.isNotNullObject(role)) {
            systemRole = new SystemRole();
            String name = (String) role[0];
            String code = (String) role[1];
            String authority = (String) role[2];
            systemRole.setName(name);
            systemRole.setCode(code);
            systemRole.setAuthority(authority);
        }
        return systemRole;
    }

    @Override
    public void updateLastLoginTime(String account) {
        SystemUser user = systemDao.loadSystemUserByAccount(account);
        user.setLastLoginTime(JodaUtils.now());
        systemDao.updateSystemUser(user);
    }
}
