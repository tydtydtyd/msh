package com.msh.service;

import com.msh.model.dto.EntrySalaryDTO;
import core.utils.Pagination;
import org.joda.time.LocalDate;

/**
 * @author Tang Yong Di
 * @date 2016/3/6
 */
public interface EntrySalaryService {
	Pagination<EntrySalaryDTO> browsePage(EntrySalaryDTO entrySalaryDTO, Pagination<EntrySalaryDTO> pagination);

	EntrySalaryDTO findById(Integer id);

	boolean checkTodayAgainExists(Integer entryUserId);

	void saveOrUpdate(EntrySalaryDTO entrySalaryDTO);

	Pagination<EntrySalaryDTO> browsePageForDay(EntrySalaryDTO entrySalaryDTO, Pagination<EntrySalaryDTO> pagination);

	Pagination<EntrySalaryDTO> browsePageForMonth(EntrySalaryDTO entrySalaryDTO, Pagination<EntrySalaryDTO> pagination);

	EntrySalaryDTO getAllSalaryForDay(EntrySalaryDTO entrySalaryDTO);

	EntrySalaryDTO getAllSalaryForMonth(EntrySalaryDTO entrySalaryDTO);

	boolean checkDateAgainExists(Integer entryUserId, LocalDate salaryDate);
}
