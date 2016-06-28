package com.msh.service;

import com.msh.model.dto.EntryUserDTO;
import com.msh.model.entity.entry.EntryUser;
import core.utils.Pagination;

/**
 * @author Tang Yong Di
 * @date 2016/3/5
 */
public interface EntryUserService {
	Pagination<EntryUserDTO> browsePage(EntryUserDTO entryUserDTO, Pagination<EntryUserDTO> pagination);

	void deleteById(Integer id);

	EntryUserDTO findById(Integer id);

	boolean checkEntryUserExists(String name, String phone);

	void saveOrUpdate(EntryUserDTO entryUserDTO);

	void checkAndDelete(Integer uid) throws Exception;
}
