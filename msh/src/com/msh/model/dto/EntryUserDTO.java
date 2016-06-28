package com.msh.model.dto;

import com.msh.model.entity.Enum.Gender;
import com.msh.model.entity.entry.EntryUser;
import core.utils.BaseForm;
import core.utils.JodaUtils;
import core.utils.Pagination;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tang Yong Di
 * @date 2016/3/5
 */
public class EntryUserDTO extends BaseForm {

	private Integer id;
	private String name;
	private Gender gender;
	private String telephone;
	private String creationTime;
	//默认日薪
	private BigDecimal quickSalary;
	private String joinDate;

	//查询用
	private String beginDate;
	private String endDate;
	//1=女 0=男
	private Integer genderCheck;

	public EntryUserDTO() {
	}

	public EntryUserDTO(EntryUser entryUser) {
		this.id = entryUser.getId();
		this.name = entryUser.getName();
		this.gender = entryUser.getGender();
		this.telephone = entryUser.getTelephone();
		this.creationTime = JodaUtils.dateTimeAllToString(entryUser.getCreationTime());
		this.joinDate = JodaUtils.localDateToString(entryUser.getJoinDate());

		this.genderCheck = entryUser.getGender().ordinal();
	}

	public static Pagination<EntryUserDTO> toPages(Pagination<EntryUser> entryUserPagination) {
		Pagination<EntryUserDTO> pagination = new Pagination<>(entryUserPagination.getTotalCount(), entryUserPagination.getCurrentPage(),
				entryUserPagination.getPageSize(), entryUserPagination.getSumPage(), entryUserPagination.isQueryAll(), entryUserPagination.getSortName(),
				entryUserPagination.getSortType());
		List<EntryUser> entryUsers = entryUserPagination.getList();
		List<EntryUserDTO> entryUserDTOs = new ArrayList<>();
		for (EntryUser entryUser : entryUsers) {
			EntryUserDTO entryUserDTO = new EntryUserDTO(entryUser);
			entryUserDTOs.add(entryUserDTO);
		}
		pagination.setList(entryUserDTOs);
		return pagination;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Integer getGenderCheck() {
		return genderCheck;
	}

	public void setGenderCheck(Integer genderCheck) {
		this.genderCheck = genderCheck;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public BigDecimal getQuickSalary() {
		return quickSalary;
	}

	public void setQuickSalary(BigDecimal quickSalary) {
		this.quickSalary = quickSalary;
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

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
}
