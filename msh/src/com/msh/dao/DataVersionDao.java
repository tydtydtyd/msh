package com.msh.dao;

import core.dao.Dao;

/**
 * @author Tang Yong Di
 * @date 2015/9/29
 */
public interface DataVersionDao extends Dao{
    void sqlUpdate(String... sql);
}
