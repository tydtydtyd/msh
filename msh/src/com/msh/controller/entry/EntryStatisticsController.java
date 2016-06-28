package com.msh.controller.entry;

import com.msh.model.dto.EntrySalaryDTO;
import com.msh.service.EntrySalaryService;
import core.utils.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tang Yong Di
 * @date 2016/3/9
 */
@Controller
@RequestMapping("/entry/statistics")
public class EntryStatisticsController {

	@Resource
	private EntrySalaryService entrySalaryService;

	private Pagination<EntrySalaryDTO> paginationDay;
	private Pagination<EntrySalaryDTO> paginationMonth;

	public EntryStatisticsController() {
		this.paginationDay = new Pagination<>();
		this.paginationMonth = new Pagination<>();
	}

	@RequestMapping("/salaryForDay")
	public ModelAndView salaryForDay(EntrySalaryDTO entrySalaryDTO){
		Map<String, Object> model = new HashMap<>();
		paginationDay.setCurrentPage(entrySalaryDTO.getCurrentPage());
		paginationDay.setPageSize(paginationDay.getPageSize());
		paginationDay.setQueryAll(false);
		paginationDay = entrySalaryService.browsePageForDay(entrySalaryDTO, paginationDay);
		entrySalaryDTO = entrySalaryService.getAllSalaryForDay(entrySalaryDTO);
		model.put("pagination", paginationDay);
		model.put("entrySalaryDTO", entrySalaryDTO);
		return new ModelAndView("/entry/salary_day_list", model);
	}

	@RequestMapping("/salaryForMonth")
	public ModelAndView salaryForMonth(EntrySalaryDTO entrySalaryDTO) {
		Map<String, Object> model = new HashMap<>();
		paginationMonth.setCurrentPage(entrySalaryDTO.getCurrentPage());
		paginationMonth.setPageSize(paginationMonth.getPageSize());
		paginationMonth.setQueryAll(false);
		paginationMonth = entrySalaryService.browsePageForMonth(entrySalaryDTO, paginationMonth);
		entrySalaryDTO = entrySalaryService.getAllSalaryForMonth(entrySalaryDTO);
		model.put("pagination", paginationMonth);
		model.put("entrySalaryDTO", entrySalaryDTO);
		return new ModelAndView("/entry/salary_month_list", model);
	}
}
