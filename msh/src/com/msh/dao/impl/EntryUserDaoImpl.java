package com.msh.dao.impl;

import com.msh.dao.EntryUserDao;
import core.dao.DaoImpl;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

/**
 * @author Tang Yong Di
 * @date 2016/3/5
 */
@Repository
public class EntryUserDaoImpl extends DaoImpl implements EntryUserDao {

	@Override
	public boolean checkEntryUserExists(final String name, final String phone) {
		return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "select count(*) from EntryUser e where e.name=:name and e.telephone=:phone";
				Query query = session.createQuery(hql);
				query.setParameter("name", name);
				query.setParameter("phone", phone);
				return ((Number) query.uniqueResult()).intValue();
			}
		}) > 0;
	}
}
