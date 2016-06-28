package com.msh.dao;

import com.msh.model.entity.entry.EntryUser;
import core.dao.Dao;

import java.util.List;

/**
 * @author Tang Yong Di
 * @date 2016/3/5
 */
public interface EntryUserDao extends Dao {

	boolean checkEntryUserExists(String name, String phone);
}
