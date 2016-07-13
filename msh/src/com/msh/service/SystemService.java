package com.msh.service;

import com.msh.model.dto.SystemRoleDTO;
import com.msh.model.dto.SystemUserDTO;
import com.msh.model.entity.system.SystemRole;
import com.msh.model.entity.system.SystemUser;
import core.utils.Pagination;

/**
 * @author Tang Yong Di
 * @date 2016/3/21
 */
public interface SystemService {
    SystemUser getUserByAccount(String account);

    SystemRole getRoleByUserId(Integer uid) throws Exception;

    void updateLastLoginTime(String account);

    Pagination<SystemUserDTO> browseUserPage(SystemUserDTO systemUserDTO, Pagination<SystemUserDTO> pagination);

    Pagination<SystemRoleDTO> browseRolePage(SystemRoleDTO systemRoleDTO, Pagination<SystemRoleDTO> pagination);
}
