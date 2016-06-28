package com.msh.dao.impl;

import com.msh.dao.EntrySalaryDao;
import com.msh.model.entity.entry.EntrySalary;
import core.dao.DaoImpl;
import core.utils.ValidationUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.joda.time.LocalDate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Tang Yong Di
 * @date 2016/3/6
 */
@Repository
public class EntrySalaryDaoImpl extends DaoImpl implements EntrySalaryDao {
	@Override
	public boolean checkTodayAgainExists(final Integer entryUserId) {
		return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				String sql = "select count(*) from entry_salary e where e.user_id=:uId and date(e.creation_time) = curdate();";
				SQLQuery query = session.createSQLQuery(sql);
				query.setParameter("uId", entryUserId);
				return ((Number) query.uniqueResult()).intValue();
			}
		}) > 0;
	}

	@Override
	public BigDecimal getAllSalaryForDay(final String sql, final List<Object> params) {
		return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<BigDecimal>() {
			@Override
			public BigDecimal doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				int parameterIndex = 0;
				if (params != null && params.size() > 0) {
					for (Object obj : params) {
						query.setParameter(parameterIndex++, obj);
					}
				}


				String val = String.valueOf(query.uniqueResult());
				return ValidationUtils.isEmpty(val) ? BigDecimal.ZERO : new BigDecimal(val);
			}
		});
	}

	@Override
	public boolean checkDateAgainExists(final Integer entryUserId, final LocalDate salaryDate) {
		return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "select count(*) from EntrySalary e where e.entryUser.id=:uId and e.salaryDate=:salaryDate";
				Query query = session.createQuery(hql);
				query.setParameter("uId", entryUserId);
				query.setParameter("salaryDate", salaryDate);
				return ((Number) query.uniqueResult()).intValue();
			}
		}) > 0;
	}

	@Override
	public EntrySalary findByUidAndDate(final Integer uid, final LocalDate salaryDate) {
		return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<EntrySalary>() {
			@Override
			public EntrySalary doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from EntrySalary e where e.entryUser.id=:uid and e.salaryDate=:salaryDate";
				Query query = session.createQuery(hql);
				query.setParameter("uid", uid);
				query.setParameter("salaryDate", salaryDate);
				List list = query.list();
				return list.isEmpty() ? null : (EntrySalary) list.get(0);
			}
		});
	}

	@Override
	public boolean checkUserHasSalary(final Integer uid) {
		return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "select count(*) from EntrySalary e where e.entryUser.id=:uid";
				Query query = session.createQuery(hql);
				query.setParameter("uid", uid);
				return ((Number) query.uniqueResult()).intValue();
			}
		}) > 0;
	}
}
