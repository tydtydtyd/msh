package com.msh.dao;

import com.msh.model.entity.entry.EntrySalary;
import core.dao.Dao;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Tang Yong Di
 * @date 2016/3/6
 */
public interface EntrySalaryDao extends Dao {
	boolean checkTodayAgainExists(Integer entryUserId);

	BigDecimal getAllSalaryForDay(String sql, List<Object> params);

	boolean checkDateAgainExists(Integer entryUserId, LocalDate salaryDate);

	EntrySalary findByUidAndDate(Integer uid, LocalDate salaryDate);

	boolean checkUserHasSalary(Integer uid);
}
