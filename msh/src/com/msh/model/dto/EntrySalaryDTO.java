package com.msh.model.dto;

import com.msh.model.entity.entry.EntrySalary;
import com.msh.model.entity.entry.EntryUser;
import com.msh.service.EntryUserService;
import core.utils.BaseForm;
import core.utils.JodaUtils;
import core.utils.Pagination;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tang Yong Di
 * @date 2016/3/6
 */
public class EntrySalaryDTO extends BaseForm {

	private Integer id;
	private Integer entryUserId;
	private String creationTime;
	private String updateTime;
	private BigDecimal salary;
	private String salaryDate;

	private String entryUserName;
	private String telephone;

	//查询用
	private String beginDate;
	private String endDate;
	//统计用
	private String dayOrMonth;
	private BigDecimal dayOrMonthOfSalary;
	private BigDecimal sumSalary;

	public EntrySalaryDTO() {
	}

	public EntrySalaryDTO(EntrySalary entrySalary) {
		this.id = entrySalary.id();
		EntryUser entryUser = entrySalary.getEntryUser();
		if(entryUser != null) {
			Integer uid = entryUser.getId();
			this.entryUserId = uid;
			this.entryUserName = entryUser.getName();
			this.telephone = entryUser.getTelephone();
		}
		this.creationTime = JodaUtils.dateTimeAllToString(entrySalary.getCreationTime());
		this.updateTime = JodaUtils.dateTimeAllToString(entrySalary.getUpdateTime());
		this.salary = entrySalary.getSalary();
		this.salaryDate = JodaUtils.localDateToString(entrySalary.getSalaryDate());
	}

	public EntrySalaryDTO(Object[] obj, boolean isSql) {
		this.dayOrMonth = String.valueOf(obj[0]);
		this.dayOrMonthOfSalary = new BigDecimal(String.valueOf(obj[1]));
	}

	public static Pagination<EntrySalaryDTO> toPages(Pagination<EntrySalary> entrySalaryPagination) {
		Pagination<EntrySalaryDTO> pagination = getEntrySalaryDTOPagination(entrySalaryPagination);
		List<EntrySalary> entrySalaries = entrySalaryPagination.getList();
		List<EntrySalaryDTO> entrySalaryDTOs = new ArrayList<>();
		for (EntrySalary entrySalary : entrySalaries) {
			EntrySalaryDTO entrySalaryDTO = new EntrySalaryDTO(entrySalary);
			entrySalaryDTOs.add(entrySalaryDTO);
		}
		pagination.setList(entrySalaryDTOs);
		return pagination;
	}

	public static Pagination<EntrySalaryDTO> toSqlPages(Pagination<EntrySalary> entrySalaryPagination) {
		Pagination<EntrySalaryDTO> pagination = getEntrySalaryDTOPagination(entrySalaryPagination);
		List<Object[]> entrySalaries = entrySalaryPagination.getSqlList();
		List<EntrySalaryDTO> entrySalaryDTOs = new ArrayList<>();
		for (Object[] entrySalary : entrySalaries) {
			EntrySalaryDTO entrySalaryDTO = new EntrySalaryDTO(entrySalary, true);
			entrySalaryDTOs.add(entrySalaryDTO);
		}
		pagination.setList(entrySalaryDTOs);
		return pagination;
	}

	private static Pagination<EntrySalaryDTO> getEntrySalaryDTOPagination(Pagination<EntrySalary> entrySalaryPagination) {
		return new Pagination<>(entrySalaryPagination.getTotalCount(), entrySalaryPagination.getCurrentPage(),
				entrySalaryPagination.getPageSize(), entrySalaryPagination.getSumPage(), entrySalaryPagination.isQueryAll(), entrySalaryPagination.getSortName(),
				entrySalaryPagination.getSortType());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEntryUserId() {
		return entryUserId;
	}

	public void setEntryUserId(Integer entryUserId) {
		this.entryUserId = entryUserId;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public String getEntryUserName() {
		return entryUserName;
	}

	public void setEntryUserName(String entryUserName) {
		this.entryUserName = entryUserName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDayOrMonth() {
		return dayOrMonth;
	}

	public void setDayOrMonth(String dayOrMonth) {
		this.dayOrMonth = dayOrMonth;
	}

	public BigDecimal getSumSalary() {
		return sumSalary;
	}

	public void setSumSalary(BigDecimal sumSalary) {
		this.sumSalary = sumSalary;
	}

	public BigDecimal getDayOrMonthOfSalary() {
		return dayOrMonthOfSalary;
	}

	public void setDayOrMonthOfSalary(BigDecimal dayOrMonthOfSalary) {
		this.dayOrMonthOfSalary = dayOrMonthOfSalary;
	}

	public String getSalaryDate() {
		return salaryDate;
	}

	public void setSalaryDate(String salaryDate) {
		this.salaryDate = salaryDate;
	}
}
