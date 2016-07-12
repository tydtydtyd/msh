package com.msh.service.impl;

import com.msh.dao.EntrySalaryDao;
import com.msh.model.dto.EntrySalaryDTO;
import com.msh.model.entity.entry.EntrySalary;
import com.msh.service.EntrySalaryService;
import core.utils.JodaUtils;
import core.utils.Pagination;
import core.utils.ValidationUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tang Yong Di
 * @date 2016/3/6
 */
@Service
public class EntrySalaryServiceImpl implements EntrySalaryService {

	@Resource
	private EntrySalaryDao entrySalaryDao;

	@Override
	public Pagination<EntrySalaryDTO> browsePage(EntrySalaryDTO entrySalaryDTO, Pagination<EntrySalaryDTO> pagination) {
		String hql = "from EntrySalary e order by e.id desc";
		Pagination<EntrySalary> entrySalaryPagination = EntrySalaryDTO.toPageDomain(pagination);
		entrySalaryPagination = entrySalaryDao.queryHQLForPage(hql, new ArrayList<Object>(), entrySalaryPagination);
		if (!ValidationUtils.isNullObject(entrySalaryPagination)) {
			pagination = EntrySalaryDTO.toPages(entrySalaryPagination);
		}
		return pagination;
	}

	@Override
	public EntrySalaryDTO findById(Integer id) {
		EntrySalary entrySalary = entrySalaryDao.load(EntrySalary.class, id);
		return entrySalary == null ? null : new EntrySalaryDTO(entrySalary);
	}

	@Override
	public boolean checkTodayAgainExists(Integer entryUserId) {
		return entrySalaryDao.checkTodayAgainExists(entryUserId);
	}

	@Override
	public void saveOrUpdate(EntrySalaryDTO entrySalaryDTO) {
		EntrySalary entrySalary;
		if (entrySalaryDTO.getId() != null) {
			entrySalary = entrySalaryDao.load(EntrySalary.class, entrySalaryDTO.getId());
			entrySalary.update(entrySalaryDTO);
		} else {
			entrySalary = new EntrySalary();
			entrySalary.update(entrySalaryDTO);
		}
		entrySalaryDao.saveOrUpdate(entrySalary);
	}

	@Override
	public Pagination<EntrySalaryDTO> browsePageForDay(EntrySalaryDTO entrySalaryDTO, Pagination<EntrySalaryDTO> pagination) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<>();
		sql.append("select date_format(e.salary_date, '%Y-%m-%d') date, sum(e.salary) from entry_salary e where 1=1 ");
		if (ValidationUtils.isNotEmpty(entrySalaryDTO.getBeginDate())) {
			String beginTime = entrySalaryDTO.getBeginDate() + " 00:00:00";
			sql.append("and e.salary_date >= ? ");
			params.add(beginTime);
		}
		if (ValidationUtils.isNotEmpty(entrySalaryDTO.getEndDate())) {
			String endTime = entrySalaryDTO.getEndDate() + " 23:59:59";
			sql.append("and e.salary_date <= ? ");
			params.add(endTime);
		}
		sql.append("group by date order by date desc");
		Pagination<EntrySalary> entrySalaryPagination = EntrySalaryDTO.toPageDomain(pagination);
		entrySalaryPagination = entrySalaryDao.querySQLForPage(sql.toString(), params, entrySalaryPagination);
		if (!ValidationUtils.isNullObject(entrySalaryPagination)) {
			pagination = EntrySalaryDTO.toSqlPages(entrySalaryPagination);
		}
		return pagination;
	}

	@Override
	public Pagination<EntrySalaryDTO> browsePageForMonth(EntrySalaryDTO entrySalaryDTO, Pagination<EntrySalaryDTO> pagination) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<>();
		sql.append("select date_format(e.salary_date, '%Y-%m') date, sum(e.salary) from entry_salary e where 1=1 ");
		if (ValidationUtils.isNotEmpty(entrySalaryDTO.getBeginDate())) {
			String beginTime = entrySalaryDTO.getBeginDate() + "-01 00:00:00";
			sql.append("and e.salary_date >= ? ");
			params.add(beginTime);
		}
		if (ValidationUtils.isNotEmpty(entrySalaryDTO.getEndDate())) {
			DateTime endTime = JodaUtils.parseTimeDateTime(entrySalaryDTO.getEndDate() + "-01 23:59:59");
			DateTime end = JodaUtils.getMonthLast(endTime);
			sql.append("and e.salary_date <= ? ");
			params.add(JodaUtils.dateTimeAllToString(end));
		}
		sql.append("group by date order by date desc");
		Pagination<EntrySalary> entrySalaryPagination = EntrySalaryDTO.toPageDomain(pagination);
		entrySalaryPagination = entrySalaryDao.querySQLForPage(sql.toString(), params, entrySalaryPagination);
		if (!ValidationUtils.isNullObject(entrySalaryPagination)) {
			pagination = EntrySalaryDTO.toSqlPages(entrySalaryPagination);
		}
		return pagination;
	}

	@Override
	public EntrySalaryDTO getAllSalaryForDay(EntrySalaryDTO entrySalaryDTO) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<>();
		sql.append("select sum(e.salary) from entry_salary e where 1=1 ");
		if (ValidationUtils.isNotEmpty(entrySalaryDTO.getBeginDate())) {
			String beginTime = entrySalaryDTO.getBeginDate() + " 00:00:00";
			sql.append("and e.salary_date >= ? ");
			params.add(beginTime);
		}
		if (ValidationUtils.isNotEmpty(entrySalaryDTO.getEndDate())) {
			String endTime = entrySalaryDTO.getEndDate() + " 23:59:59";
			sql.append("and e.salary_date <= ? ");
			params.add(endTime);
		}
		BigDecimal sumSalary = entrySalaryDao.getAllSalaryForDay(sql.toString(), params);
		entrySalaryDTO.setSumSalary(sumSalary);
		return entrySalaryDTO;
	}

	@Override
	public EntrySalaryDTO getAllSalaryForMonth(EntrySalaryDTO entrySalaryDTO) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<>();
		sql.append("select sum(e.salary) from entry_salary e where 1=1 ");
		if (ValidationUtils.isNotEmpty(entrySalaryDTO.getBeginDate())) {
			String beginTime = entrySalaryDTO.getBeginDate() + "-01 00:00:00";
			sql.append("and e.salary_date >= ? ");
			params.add(beginTime);
		}
		if (ValidationUtils.isNotEmpty(entrySalaryDTO.getEndDate())) {
			DateTime endTime = JodaUtils.parseTimeDateTime(entrySalaryDTO.getEndDate() + "-01 23:59:59");
			DateTime end = JodaUtils.getMonthLast(endTime);
			sql.append("and e.salary_date <= ? ");
			params.add(JodaUtils.dateTimeAllToString(end));
		}
		BigDecimal sumSalary = entrySalaryDao.getAllSalaryForDay(sql.toString(), params);
		entrySalaryDTO.setSumSalary(sumSalary);
		return entrySalaryDTO;
	}

	@Override
	public boolean checkDateAgainExists(Integer entryUserId, LocalDate salaryDate) {
		return entrySalaryDao.checkDateAgainExists(entryUserId, salaryDate);
	}
}
