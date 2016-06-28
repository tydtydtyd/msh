package com.msh.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Tang Yong Di
 * @date 2016/4/7
 */
@Controller
@RequestMapping("/system/role")
public class SystemRoleController {

    @RequestMapping("/list")
    public ModelAndView list() {
        return new ModelAndView();
    }
}
