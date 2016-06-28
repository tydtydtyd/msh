package com.msh.service;


/**
 * @author Tang Yong Di
 * @date 2015/9/29
 */
public interface DataVersionService{
    int queryForInt(String queryCountSql);

    void sqlUpdate(String... sql);
}
