package com.msh.service.impl;

import com.msh.dao.SystemDao;
import com.msh.model.dto.SystemRoleDTO;
import com.msh.model.dto.SystemUserDTO;
import com.msh.model.entity.system.SystemRole;
import com.msh.model.entity.system.SystemUser;
import com.msh.service.SystemService;
import core.utils.JodaUtils;
import core.utils.Pagination;
import core.utils.ValidationUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
                hql.append("and (s.username like ? ").append("or s.account like ?) ");
                params.add("%" + systemUserDTO.getUsername() + "%");
                params.add("%" + systemUserDTO.getUsername() + "%");
            }
            if(systemUserDTO.getRoleId() != null) {
                hql.append("and s.role.id=? ");
                params.add(systemUserDTO.getRoleId());
            }
        }
        hql.append("order by s.id desc");
        Pagination<SystemUser> systemUserPagination = SystemUserDTO.toPageDomain(pagination);
        systemUserPagination = systemDao.queryHQLForPage(hql.toString(), params, systemUserPagination);
        if (!ValidationUtils.isNullObject(systemUserPagination)) {
            pagination = SystemUserDTO.toPages(systemUserPagination);
        }
        return pagination;
    }

    @Override
    public Pagination<SystemRoleDTO> browseRolePage(SystemRoleDTO systemRoleDTO, Pagination<SystemRoleDTO> pagination) {
        StringBuffer hql = new StringBuffer();
        List<Object> params = new ArrayList<>();
        hql.append("from SystemRole r where 1=1 ");
        if(ValidationUtils.isNotNullObject(systemRoleDTO)) {
            if(ValidationUtils.isNotEmpty(systemRoleDTO.getName())) {
                hql.append("and r.name like ? ");
                params.add("%" + systemRoleDTO.getName() + "%");
            }
        }
        hql.append("order by r.id desc");
        Pagination<SystemRole> systemRolePagination = SystemRoleDTO.toPageDomain(pagination);
        systemRolePagination = systemDao.queryHQLForPage(hql.toString(), params, systemRolePagination);
        if (!ValidationUtils.isNullObject(systemRolePagination)) {
            pagination = SystemRoleDTO.toPages(systemRolePagination);
        }
        return pagination;
    }
}
