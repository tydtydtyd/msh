package com.msh.controller.entry;

import com.msh.model.dto.EntrySalaryDTO;
import com.msh.service.EntrySalaryService;
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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tang Yong Di
 * @date 2016/3/6
 */
@Controller
@RequestMapping("/entry/salary")
public class EntrySalaryController {

	@Resource
	private EntrySalaryService entrySalaryService;

	private Pagination<EntrySalaryDTO> pagination;

	public EntrySalaryController() {
		this.pagination = new Pagination<>();
	}

	@RequestMapping("/list")
	public ModelAndView list(EntrySalaryDTO entrySalaryDTO) {
		Map<String, Object> model = new HashMap<>();
		pagination.setCurrentPage(entrySalaryDTO.getCurrentPage());
		pagination.setPageSize(pagination.getPageSize());
		pagination.setQueryAll(false);
		pagination = entrySalaryService.browsePage(entrySalaryDTO, pagination);
		model.put("pagination", pagination);
		model.put("entrySalaryDTO", entrySalaryDTO);
		return new ModelAndView("/entry/entry_salary_list", model);
	}

	@RequestMapping("/goSaveOrUpdate")
	public ModelAndView goSaveOrUpdate(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<>();
		Integer id = ServletRequestUtils.getIntParameter(request, "id");
		EntrySalaryDTO entrySalaryDTO;
		if (id != null) {
			entrySalaryDTO = entrySalaryService.findById(id);
		} else {
			entrySalaryDTO = new EntrySalaryDTO();
		}
		model.put("entrySalaryDTO", entrySalaryDTO);
		return new ModelAndView("/entry/entry_salary_edit", model);
	}

	@ResponseBody
	@RequestMapping("/saveOrUpdate")
	public ModelAndView saveOrUpdate(EntrySalaryDTO entrySalaryDTO, HttpServletRequest request) throws Exception{
		Map<String, Object> model = new HashMap<>();
		Map<String, Object> errors = new HashMap<>();
		Integer entryUserId = entrySalaryDTO.getEntryUserId();
		String salaryDate = entrySalaryDTO.getSalaryDate();
		if(ValidationUtils.isEmpty(salaryDate)) {
			errors.put("salaryDate", "请选择结账日期");
		}else {
			if (entryUserId == null) {
				errors.put("entryUserName", "请选择入职人员结账");
			} else {
				boolean exists = entrySalaryService.checkDateAgainExists(entryUserId, JodaUtils.parseLocalDate(salaryDate));
				if (exists) {
					if(entrySalaryDTO.getId() == null){
						errors.put("entryUserName", salaryDate + "已经为该入职人结账");
					}else {
						EntrySalaryDTO esDTO = entrySalaryService.findById(entrySalaryDTO.getId());
						if(entryUserId.compareTo(esDTO.getEntryUserId()) != 0) {
							errors.put("entryUserName", salaryDate + "已经为该入职人结账");
						}
					}
				}
			}
		}
		BigDecimal salary = entrySalaryDTO.getSalary();
		if(salary == null) {
			errors.put("salary", "请填写结账金额");
		}
		if (ValidationUtils.isNotNullMap(errors)) {
			model.put("success", false);
			model.put("errors", JSONObject.fromObject(errors));
		} else {
			entrySalaryService.saveOrUpdate(entrySalaryDTO);
			model.put("success", true);
		}
		return new ModelAndView(new MappingJacksonJsonView(), model);
	}
}
