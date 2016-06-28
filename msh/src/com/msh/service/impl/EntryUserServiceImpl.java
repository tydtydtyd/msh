package com.msh.service.impl;

import com.msh.dao.EntrySalaryDao;
import com.msh.dao.EntryUserDao;
import com.msh.model.dto.EntryUserDTO;
import com.msh.model.entity.entry.EntrySalary;
import com.msh.model.entity.entry.EntryUser;
import com.msh.service.EntryUserService;
import core.utils.JodaUtils;
import core.utils.Pagination;
import core.utils.ValidationUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tang Yong Di
 * @date 2016/3/5
 */
@Service
public class EntryUserServiceImpl implements EntryUserService {

	@Resource
	private EntryUserDao entryUserDao;
	@Resource
	private EntrySalaryDao entrySalaryDao;

	@Override
	public Pagination<EntryUserDTO> browsePage(EntryUserDTO entryUserDTO, Pagination<EntryUserDTO> pagination) {
		StringBuffer hql = new StringBuffer();
		List<Object> params = new ArrayList<>();
		hql.append("from EntryUser e where 1=1 ");
		if (ValidationUtils.isNotNullObject(entryUserDTO)) {
			if (ValidationUtils.isNotEmpty(entryUserDTO.getName())) {
				hql.append("and (e.name like ? or e.telephone like ? ) ");
				params.add("%" + entryUserDTO.getName() + "%");
				params.add("%" + entryUserDTO.getName() + "%");
			}
			if (ValidationUtils.isNotEmpty(entryUserDTO.getBeginDate())) {
				String beginTime = entryUserDTO.getBeginDate();
				LocalDate begin = JodaUtils.parseLocalDate(beginTime);
				hql.append("and e.joinDate >= ? ");
				params.add(begin);
			}
			if (ValidationUtils.isNotEmpty(entryUserDTO.getEndDate())) {
				String endTime = entryUserDTO.getEndDate();
				LocalDate end = JodaUtils.parseLocalDate(endTime);
				hql.append("and e.joinDate <= ? ");
				params.add(end);
			}
		}
		hql.append("order by e.id desc");
		Pagination<EntryUser> entryUserPagination = EntryUserDTO.toPageDomain(pagination);
		entryUserPagination = entryUserDao.queryHQLForPage(hql.toString(), params, entryUserPagination);
		if (!ValidationUtils.isNullObject(entryUserPagination)) {
			pagination = EntryUserDTO.toPages(entryUserPagination);
		}
		return pagination;
	}

	@Override
	public void deleteById(Integer id) {
		entryUserDao.remove(EntryUser.class, id);
	}

	@Override
	public EntryUserDTO findById(Integer id) {
		EntryUser entryUser = entryUserDao.load(EntryUser.class, id);
		return entryUser != null ? new EntryUserDTO(entryUser) : null;
	}

	@Override
	public boolean checkEntryUserExists(String name, String phone) {
		return entryUserDao.checkEntryUserExists(name, phone);
	}

	@Override
	public void saveOrUpdate(EntryUserDTO entryUserDTO) {
		EntryUser entryUser;
		if(entryUserDTO.getId() != null) {
			entryUser = entryUserDao.load(EntryUser.class, entryUserDTO.getId());
			entryUser.update(entryUserDTO);
		}else {
			entryUser = new EntryUser();
			entryUser.update(entryUserDTO);
		}
		entryUserDao.saveOrUpdate(entryUser);
		if(entryUserDTO.getQuickSalary() != null && entryUserDTO.getId() == null) {
			EntrySalary entrySalary = entrySalaryDao.findByUidAndDate(entryUser.id(), JodaUtils.parseLocalDate(entryUserDTO.getJoinDate()));
			if(entrySalary == null) {
				entrySalary = new EntrySalary();
				entrySalary.setCreationTime(JodaUtils.now());
			}
			entrySalary.setSalaryDate(JodaUtils.parseLocalDate(entryUserDTO.getJoinDate()));
			entrySalary.setSalary(entryUserDTO.getQuickSalary());
			entrySalary.setEntryUser(entryUser);
			entrySalary.setUpdateTime(JodaUtils.now());
			entrySalaryDao.saveOrUpdate(entrySalary);
		}
	}

	@Override
	public void checkAndDelete(Integer uid) throws Exception{
		boolean hasSalary = entrySalaryDao.checkUserHasSalary(uid);
		if(!hasSalary) {
			deleteById(uid);
		}else {
			throw new Exception("该入职人已经结过账，不能删除");
		}
	}
}
