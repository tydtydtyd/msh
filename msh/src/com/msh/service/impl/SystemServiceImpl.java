package com.msh.service.impl;

import com.msh.dao.SystemDao;
import com.msh.model.dto.SystemUserDTO;
import com.msh.model.entity.system.SystemRole;
import com.msh.model.entity.system.SystemUser;
import com.msh.service.SystemService;
import core.utils.JodaUtils;
import core.utils.Pagination;
import core.utils.ValidationUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public Pagination<SystemUserDTO> browseUserPage(SystemUserDTO systemUserDTO, Pagination<SystemUserDTO> pagination) {
        StringBuffer hql = new StringBuffer();
        List<Object> params = new ArrayList<>();
        hql.append("from SystemUser s where 1=1 ");
        if(ValidationUtils.isNotNullObject(systemUserDTO)) {
            if(ValidationUtils.isNotEmpty(systemUserDTO.getUsername())) {
                hql.append("and (s.username=? ").append("or s.account=?) ");
                params.add(systemUserDTO.getUsername());
                params.add(systemUserDTO.getPhone());
            }
            if(systemUserDTO.getRoleId() != null) {
                hql.append("and s.role.id=? ");
                params.add(systemUserDTO.getRoleId());
            }
        }
        hql.append("order by s.id desc");
        Pagination<SystemUser> systemUserPagination = SystemUserDTO.toPageDomain(pagination);
        systemUserPagination = systemDao.queryHQLForPage(hql.toString(), new ArrayList<Object>(), systemUserPagination);
        if (!ValidationUtils.isNullObject(systemUserPagination)) {
            pagination = SystemUserDTO.toPages(systemUserPagination);
        }
        return pagination;
    }
}
