package com.msh.controller.system;

import com.msh.model.dto.SystemUserDTO;
import com.msh.service.SystemService;
import core.utils.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tang Yong Di
 * @date 2016/3/21
 */
@Controller
@RequestMapping("/system/user")
public class SystemUserController {

    @Resource
    private SystemService systemService;

    private Pagination<SystemUserDTO> pagination;

    public SystemUserController() {
        this.pagination = new Pagination<>();
    }

    @RequestMapping("/list")
    public ModelAndView list(SystemUserDTO systemUserDTO) throws Exception {
        Map<String, Object> model = new HashMap<>();
        try{
            pagination.setCurrentPage(systemUserDTO.getCurrentPage());
            pagination.setPageSize(pagination.getPageSize());
            pagination.setQueryAll(false);
            pagination = systemService.browseUserPage(systemUserDTO, pagination);
            model.put("pagination", pagination);
            model.put("systemUserDTO", systemUserDTO);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("/system/system_user", model);
    }
}
