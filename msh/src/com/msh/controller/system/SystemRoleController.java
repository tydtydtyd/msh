package com.msh.controller.system;

import com.msh.model.dto.SystemRoleDTO;
import com.msh.service.SystemService;
import core.utils.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tang Yong Di
 * @date 2016/4/7
 */
@Controller
@RequestMapping("/system/role")
public class SystemRoleController {

    @Resource
    private SystemService systemService;

    private Pagination<SystemRoleDTO> paginationRole;

    public SystemRoleController() {
        this.paginationRole = new Pagination<>();
    }

    @RequestMapping("/list")
    public ModelAndView list() {
        return new ModelAndView();
    }

    @ResponseBody
    @RequestMapping("/getRoleList")
    public ModelAndView getRoleList(SystemRoleDTO systemRoleDTO){
        Map<String, Object> model = new HashMap<>();
        try{
            paginationRole.setCurrentPage(1);
            paginationRole.setPageSize(10);
            paginationRole = systemService.browseRolePage(systemRoleDTO, paginationRole);
            model.put("roles", paginationRole.getList());
        }catch (Exception e) {
            model.put("success", false);
            model.put("message", e.getMessage());
        }
        return new ModelAndView(new MappingJacksonJsonView(), model);
    }
}
