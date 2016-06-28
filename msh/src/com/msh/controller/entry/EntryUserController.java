package com.msh.controller.entry;

import com.msh.model.dto.EntryUserDTO;
import com.msh.service.EntryUserService;
import core.excel.ExcelView;
import core.shiro.SecurityHolder;
import core.utils.JodaUtils;
import core.utils.Pagination;
import core.utils.ValidationUtils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tang Yong Di
 * @date 2016/3/5
 */
@Controller
@RequestMapping("/entry/user")
public class EntryUserController {

    @Resource
    private EntryUserService entryUserService;

    private Pagination<EntryUserDTO> pagination;
    private Pagination<EntryUserDTO> paginationUser;
    private Pagination<EntryUserDTO> paginationExcel;

    public EntryUserController() {
        this.pagination = new Pagination<>();
        this.paginationUser = new Pagination<>();
        this.paginationExcel = new Pagination<>();
    }

    @RequestMapping("/list")
    public ModelAndView list(EntryUserDTO entryUserDTO) {
        Map<String, Object> model = new HashMap<>();
        pagination.setCurrentPage(entryUserDTO.getCurrentPage());
        pagination.setPageSize(pagination.getPageSize());
        pagination.setQueryAll(false);
        pagination = entryUserService.browsePage(entryUserDTO, pagination);
        model.put("pagination", pagination);
        model.put("entryUserDTO", entryUserDTO);
        return new ModelAndView("/entry/entry_user_list", model);
    }

    @RequestMapping("/goSaveOrUpdate")
    public ModelAndView goSaveOrUpdate(HttpServletRequest request) throws Exception {
        Map<String, Object> model = new HashMap<>();
        Integer id = ServletRequestUtils.getIntParameter(request, "id");
        EntryUserDTO entryUserDTO;
        if (id != null) {
            entryUserDTO = entryUserService.findById(id);
        } else {
            entryUserDTO = new EntryUserDTO();
        }
        model.put("entryUserDTO", entryUserDTO);
        return new ModelAndView("/entry/entry_user_edit", model);
    }

    @ResponseBody
    @RequestMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(EntryUserDTO entryUserDTO, HttpServletRequest request) throws Exception {
        Map<String, Object> model = new HashMap<>();
        Map<String, Object> errors = new HashMap<>();
        String name = entryUserDTO.getName();
        if (ValidationUtils.isEmpty(name)) {
            errors.put("name", "请填写姓名");
        } else {
            String phone = entryUserDTO.getTelephone();
            if (ValidationUtils.isEmpty(phone)) {
                errors.put("telephone", "请填写手机号码");
            } else {
                boolean exists = entryUserService.checkEntryUserExists(name, phone);
                if (exists) {
                    if (entryUserDTO.getId() == null) {
                        errors.put("name", "该入职人已存在");
                    } else {
                        EntryUserDTO euDTO = entryUserService.findById(entryUserDTO.getId());
                        if (!name.equals(euDTO.getName())) {
                            errors.put("name", "该入职人已存在");
                        }
                    }
                }
            }
        }
        String joinDate = entryUserDTO.getJoinDate();
        if (ValidationUtils.isEmpty(joinDate)) {
            errors.put("joinDate", "请选择入职日期");
        }
        if (ValidationUtils.isNotNullMap(errors)) {
            model.put("success", false);
            model.put("errors", JSONObject.fromObject(errors));
        } else {
            entryUserService.saveOrUpdate(entryUserDTO);
            model.put("success", true);
        }
        return new ModelAndView(new MappingJacksonJsonView(), model);
    }

    @ResponseBody
    @RequestMapping("/delete")
    public ModelAndView delete(EntryUserDTO entryUserDTO) {
        Map<String, Object> model = new HashMap<>();
        try {
            Integer id = entryUserDTO.getId();
            entryUserService.checkAndDelete(id);
            model.put("success", true);
        } catch (Exception e) {
            model.put("success", false);
            model.put("message", e.getMessage());
        }
        return new ModelAndView(new MappingJacksonJsonView(), model);
    }

    @ResponseBody
    @RequestMapping("/getUserList")
    public ModelAndView getUserList(EntryUserDTO entryUserDTO) {
        Map<String, Object> model = new HashMap<>();
        try {
            paginationUser.setCurrentPage(1);
            paginationUser.setPageSize(10);
            paginationUser.setQueryAll(false);
            paginationUser = entryUserService.browsePage(entryUserDTO, paginationUser);
            model.put("users", paginationUser.getList());
        } catch (Exception e) {
            model.put("success", false);
            model.put("message", e.getMessage());
        }
        return new ModelAndView(new MappingJacksonJsonView(), model);
    }

    @RequestMapping("/excel")
    public ModelAndView excel(EntryUserDTO entryUserDTO) throws Exception {
        Map<String, Object> model = new HashMap<>();
        paginationExcel.setQueryAll(true);
        paginationExcel = entryUserService.browsePage(entryUserDTO, paginationExcel);
        model.put("exportUser", SecurityHolder.getUsername());
        model.put("exportTime", JodaUtils.dateTimeNow());
        model.put("entryUser", paginationExcel.getList());
        return new ModelAndView(new ExcelView("entry_user"), model);
    }
}
