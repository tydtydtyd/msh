package com.msh.model.entity.entry;

import com.msh.dao.EntryUserDao;
import com.msh.model.dto.EntryUserDTO;
import com.msh.model.entity.AbstractDomain;
import com.msh.model.entity.Enum.Gender;
import core.spring.instance.InstanceFactory;
import core.utils.JodaUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @author Tang Yong Di
 * @date 2016/3/5
 */
public class EntryUser extends AbstractDomain {
	private String name;
	private Gender gender;
	private String telephone;
	private DateTime creationTime = JodaUtils.now();
	//默认日薪
	private LocalDate joinDate;

	public EntryUser() {
	}

	public EntryUser(Integer id) {
		super(id);
	}

	public void update(EntryUserDTO entryUserDTO) {
		this.name = entryUserDTO.getName();
		this.gender = entryUserDTO.getGenderCheck() != null && entryUserDTO.getGenderCheck().compareTo(1) == 0 ? Gender.MAN : Gender.WOMAN;
		this.telephone = entryUserDTO.getTelephone();
		this.joinDate = JodaUtils.parseLocalDate(entryUserDTO.getJoinDate());
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Gender getGender() {
		return gender;
	}

	public String getTelephone() {
		return telephone;
	}

	public DateTime getCreationTime() {
		return creationTime;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}
}
