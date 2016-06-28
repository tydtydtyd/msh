package com.msh.model.entity.entry;

import com.msh.model.dto.EntrySalaryDTO;
import com.msh.model.entity.AbstractDomain;
import core.utils.JodaUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.math.BigDecimal;

/**
 * @author Tang Yong Di
 * @date 2016/3/6
 */
public class EntrySalary extends AbstractDomain {

	private EntryUser entryUser;
	private DateTime creationTime = JodaUtils.now();
	private DateTime updateTime;
	private BigDecimal salary;
	private LocalDate salaryDate;

	public EntrySalary() {
	}

	public void update(EntrySalaryDTO entrySalaryDTO) {
		EntryUser entryUser = new EntryUser(entrySalaryDTO.getEntryUserId());
		this.entryUser = entryUser;
		this.updateTime = JodaUtils.now();
		this.salary = entrySalaryDTO.getSalary();
		this.salaryDate = JodaUtils.parseLocalDate(entrySalaryDTO.getSalaryDate());
	}

	public EntryUser getEntryUser() {
		return entryUser;
	}

	public DateTime getCreationTime() {
		return creationTime;
	}

	public DateTime getUpdateTime() {
		return updateTime;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public LocalDate getSalaryDate() {
		return salaryDate;
	}

	public void setEntryUser(EntryUser entryUser) {
		this.entryUser = entryUser;
	}

	public void setCreationTime(DateTime creationTime) {
		this.creationTime = creationTime;
	}

	public void setUpdateTime(DateTime updateTime) {
		this.updateTime = updateTime;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public void setSalaryDate(LocalDate salaryDate) {
		this.salaryDate = salaryDate;
	}
}
