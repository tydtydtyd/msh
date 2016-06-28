package com.msh.service.impl;

import com.msh.dao.DataVersionDao;
import com.msh.service.DataVersionService;
import core.spring.instance.InstanceFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Tang Yong Di
 * @date 2015/9/29
 */
@Service
public class DataVersionServiceImpl implements DataVersionService {

	@Resource
    private DataVersionDao dataVersionDao;

    private static JdbcTemplate jdbcTemplate;

    private static JdbcTemplate getJdbcTemplate(){
        if(jdbcTemplate == null){
            jdbcTemplate = InstanceFactory.getInstance(JdbcTemplate.class);
        }
        return jdbcTemplate;
    }

    @Override
    public int queryForInt(String queryCountSql) {
		return getJdbcTemplate().queryForObject(queryCountSql, Integer.class);
    }

    @Override
    public void sqlUpdate(String... sql){
        dataVersionDao.sqlUpdate(sql);
    }
}
