package com.msh.dao.impl;

import com.msh.dao.DataVersionDao;
import com.msh.model.entity.sql.DataVersion;
import core.dao.DaoImpl;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Tang Yong Di
 * @date 2015/9/29
 */
@Repository
public class DataVersionDaoImpl extends DaoImpl implements DataVersionDao {

    @Override
    public void sqlUpdate(final String... sql) {
		getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List<String> sqlList = Arrays.asList(sql);
				for(String exSql : sqlList) {
					SQLQuery query = session.createSQLQuery(exSql);
					query.executeUpdate();
				}
				return null;
			}
		});
    }
}
