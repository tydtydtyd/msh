package core.dao;

import com.msh.model.entity.Domain;
import core.utils.JodaUtils;
import core.utils.PageHelper;
import core.utils.Pagination;
import core.utils.ValidationUtils;
import org.hibernate.*;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import javax.annotation.Resource;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Tang Yong Di
 * @date 2016/3/6
 */
public class DaoImpl extends HibernateDaoSupport implements Dao {

	@Resource
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}

	/**
	 * hql分页查询
	 *
	 * @param hql
	 * @param params
	 * @param pagination
	 * @param <T>
	 * @return
	 */
	@Override
	public <T extends Domain> Pagination<T> queryHQLForPage(final String hql,
															final Collection<Object> params, Pagination<T> pagination) {
		Session session = this.getSessionFactory().getCurrentSession();
		Integer count = 0;
		// 返回查询分页记录数HQL
		String subHql = interceptive(hql);
		if (subHql != null) {
			Query query = session.createQuery(subHql);
			setParamForQuery(query, params);
			Object result = query.uniqueResult();
			if (result != null)
				count = Integer.parseInt(result.toString());
		}
		// Query query = session.createQuery(hql).setCacheable(true);
		Query query = session.createQuery(hql);
		if (!pagination.isQueryAll()) {
			Integer start = (pagination.getCurrentPage() - 1) * pagination.getPageSize();
			start = start < 0 ? 0 : start;
			query.setFirstResult(start);
			query.setMaxResults(pagination.getPageSize());
		} else {
			pagination.setPageSize(count);
		}
		setParamForQuery(query, params);
		List<T> list = query.list();
		Pagination<T> pagination1 = pagination == null ? new Pagination<T>().setList(list).setTotalCount(
				count) : pagination.setList(list).setTotalCount(count).setCurrentPage(
				pagination.getCurrentPage()).setSumPage(
				PageHelper.getPageCount(count, pagination.getPageSize()));
		return pagination1;
	}

	/**
	 * hql分页查询 params为map（设定参数为 :** 不是 ？）
	 *
	 * @param hql
	 * @param params     key如果是以list开头的 就当做设定的in(**)  query.setParameterList("listObject", params.get(listObject));
	 * @param pagination
	 * @param <T>
	 * @return
	 */
	@Override
	public <T extends Domain> Pagination<T> queryHQLForPage(final String hql,
															final Map<String, Object> params, Pagination<T> pagination) {
		Session session = this.getSessionFactory().getCurrentSession();
		Integer count = 0;
		// 返回查询分页记录数HQL
		String subHql = interceptive(hql);
		if (subHql != null) {
			Query query = session.createQuery(subHql);
			setParamForQuery(query, params);
			Object result = query.uniqueResult();
			if (result != null)
				count = Integer.parseInt(result.toString());
		}
		// Query query = session.createQuery(hql).setCacheable(true);
		Query query = session.createQuery(hql);
		if (!pagination.isQueryAll()) {
			Integer start = (pagination.getCurrentPage() - 1) * pagination.getPageSize();
			start = start < 0 ? 0 : start;
			query.setFirstResult(start);
			query.setMaxResults(pagination.getPageSize());
		} else {
			pagination.setPageSize(count);
		}
		setParamForQuery(query, params);
		List<T> list = query.list();
		Pagination<T> pagination1 = pagination == null ? new Pagination<T>().setList(list).setTotalCount(
				count) : pagination.setList(list).setTotalCount(count).setCurrentPage(
				pagination.getCurrentPage()).setSumPage(
				PageHelper.getPageCount(count, pagination.getPageSize()));
		return pagination1;
	}

	/**
	 * sql分页查询
	 */
	@Override
	public <T extends Domain> Pagination<T> querySQLForPage(final String sql, final Collection<Object> params,
															Pagination<T> pagination) {
		Session session = this.getSessionFactory().getCurrentSession();
		Integer count = 0;
		// 返回查询分页记录数HQL
		String subSql = interceptiveSql(sql);
		if (subSql != null) {
			SQLQuery query = session.createSQLQuery(subSql);
			setParamForQuery(query, params);
			Object result = query.uniqueResult();
			if (result != null) {
				count = Integer.parseInt(result.toString());
			}
		}
		String pageSql = "";
		if (!pagination.isQueryAll()) {
			Integer start = (pagination.getCurrentPage() - 1) * pagination.getPageSize();
			start = start < 0 ? 0 : start;
			pageSql = addLimit(sql, start, pagination.getPageSize());
		} else {
			pageSql = sql;
			pagination.setPageSize(count);
		}
		SQLQuery query = session.createSQLQuery(pageSql);
		setParamForQuery(query, params);
		List<Object[]> list = query.list();
		return pagination == null ? new Pagination<T>().setSqlList(list).setTotalCount(
				count) : pagination.setSqlList(list).setTotalCount(count).setCurrentPage(
				pagination.getCurrentPage()).setSumPage(
				PageHelper.getPageCount(count, pagination.getPageSize()));
	}

	/**
	 * sql分页查询
	 *
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @param pagination
	 * @param <T>
	 * @return
	 */
	@Override
	public <T extends Domain> Pagination<T> querySQLForClassPage(Class<T> entityClass,
																 final String sql, final Collection<Object> params,
																 Pagination<T> pagination) {
		Session session = this.getSessionFactory().getCurrentSession();
		Integer count = 0;
		// 返回查询分页记录数HQL
		String subSql = interceptiveSql(sql);
		if (subSql != null) {
			SQLQuery query = session.createSQLQuery(subSql);
			setParamForQuery(query, params);
			Object result = query.uniqueResult();
			if (result != null) {
				count = Integer.parseInt(result.toString());
			}
		}
		String pageSql = "";
		if (!pagination.isQueryAll()) {
			Integer start = (pagination.getCurrentPage() - 1) * pagination.getPageSize();
			start = start < 0 ? 0 : start;
			pageSql = addLimit(sql, start, pagination.getPageSize());
		} else {
			pageSql = sql;
			pagination.setPageSize(count);
		}
		SQLQuery query = session.createSQLQuery(pageSql).addEntity(entityClass);
		setParamForQuery(query, params);
		List<T> list = query.list();
		return pagination == null ? new Pagination<T>().setList(list).setTotalCount(
				count) : pagination.setList(list).setTotalCount(count).setCurrentPage(
				pagination.getCurrentPage()).setSumPage(
				PageHelper.getPageCount(count, pagination.getPageSize()));
	}

	@Override
	public void save(Domain domain) {
		saveOrUpdate(domain);
	}

	@Override
	public void saveOrUpdate(Domain domain) {
		getHibernateTemplate().saveOrUpdate(domain);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Domain, E extends Serializable> T load(Class<T> clazz, E id) {
		List list = getHibernateTemplate().find("from " + clazz.getName() + " c where c.id=?", id);
		return list.isEmpty() ? null : (T) list.get(0);
		//return getHibernateTemplate().load(clazz, id);
	}

	@Override
	public <T extends Domain, E extends Serializable> T get(Class<T> clazz, E id) {
		return getHibernateTemplate().get(clazz, id);
	}

	@Override
	public <T extends Domain, E extends Serializable> boolean exists(Class<T> clazz, E id) {
		List list = getHibernateTemplate().find("select c.id from " + clazz.getSimpleName()
				+ " as c where c.id=?", id);
		return null != list && list.size() > 0;
	}

	@Override
	public <T extends Domain> List<T> findAll(Class<T> clazz) {
		return getHibernateTemplate().loadAll(clazz);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object> find(String hql, Object... params) {
		return getHibernateTemplate().find(hql, params);
	}

	@Override
	public Object singleResult(final String hql, final Object... params) {
		return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				for (int i = 0; i < params.length; i++) {
					query = query.setParameter(i, params[i]);
				}
				return query.uniqueResult();
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public void update(final String hql, final Object... params) {
		getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				for (int i = 0; i < params.length; i++) {
					query = query.setParameter(i, params[i]);
				}
				return query.executeUpdate();
			}
		});

	}

	@Override
	public void remove(Domain domain) {
		getHibernateTemplate().delete(domain);
	}

	@Override
	public <T extends Domain> void saveAll(Collection<T> domains) {
		getHibernateTemplate().saveOrUpdateAll(domains);
	}

	@Override
	public void removeAll(Collection list) {
		getHibernateTemplate().deleteAll(list);
	}

	/**
	 * 通过一系列的id去查询对应的一系列Domain
	 *
	 * @param clazz 查询的Domain类
	 * @param ids   id集合
	 * @param <T>   对应的类
	 * @return 一系列Domain
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Domain, E extends Serializable> List<T> findByIds(Class<T> clazz, final List<E> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyList();
		}
		final String hql = "from " + clazz.getName() + " t  where t.id  in (:ids)";
		return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List<T>>() {
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql).setParameterList("ids", ids);
				return query.list();
			}
		});
	}

	@Override
	public <T extends Domain, E extends Serializable> void updateOrder(Class<T> clazz, final E id, final Integer order) {
		final String hql = "update " + clazz.getName() + " t set t.order=:order where t.id=:id";
		getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setParameter("order", order);
				query.setParameter("id", id);
				return query.executeUpdate();
			}
		});
	}

	@Override
	public <T extends Domain, E extends Serializable> void updateOrderT(Class<T> clazz, final E id, final Integer order) {
		final String hql = "update " + clazz.getName() + " t set t.order=:order,t.updateTime=:updateTime where t.id=:id";
		getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setParameter("order", order);
				query.setParameter("updateTime", JodaUtils.bigIntegerNow());
				query.setParameter("id", id);
				return query.executeUpdate();
			}
		});
	}

	@Override
	public <T extends Domain, E extends Serializable> void changeStatus(Class<T> clazz, final List<E> ids, final Enum status) {
		if (ids.isEmpty()) {
			return;
		}
		final String hql = "update " + clazz.getName() + " t set t.status=:status where t.id in (:ids)";
		getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setParameter("status", status);
				query.setParameterList("ids", ids);
				return query.executeUpdate();
			}
		});
	}

	@Override
	public <T extends Domain, E extends Serializable> void changeStatusT(Class<T> clazz, final List<E> ids, final Enum status) {
		if (ids.isEmpty()) {
			return;
		}
		final String hql = "update " + clazz.getName() + " t set t.status=:status,t.updateTime=:updateTime where t.id in (:ids)";
		getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setParameter("status", status);
				query.setParameter("updateTime", JodaUtils.bigIntegerNow());
				query.setParameterList("ids", ids);
				return query.executeUpdate();
			}
		});
	}

	@Override
	public <T extends Domain, E extends Serializable> void remove(Class<T> clazz, final List<E> ids) {
		if (ids.isEmpty()) {
			return;
		}
		final String hql = "delete from " + clazz.getName() + " t where t.id in (:ids)";
		getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Object>() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setParameterList("ids", ids);
				return query.executeUpdate();
			}
		});
	}

	@Override
	public <T extends Domain, E extends Serializable> void remove(Class<T> clazz, E id) {
		getHibernateTemplate().bulkUpdate("delete from " + clazz.getSimpleName() + " c where c.id=?", id);
	}

	public void flushAndClear() {
		getHibernateTemplate().flush();
		getHibernateTemplate().clear();
	}

	protected List find(final String hql, final Integer startIndex, final Integer pageCount, final Object... args) {
		return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<List>() {
			@Override
			public List doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				for (int i = 0; i < args.length; i++) {
					query.setParameter(i, args[i]);
				}
				return query.setFirstResult(startIndex)
						.setMaxResults(pageCount)
						.list();
			}
		});
	}

	/**
	 * @param queryString a HQL query
	 */
	public Integer findIntegerValue(String queryString, Object... params) {
		List list = getHibernateTemplate().find(queryString, params);
		if (null != list && list.size() == 1) {
			return (Integer) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * @param queryString a HQL query
	 */
	public Long findLongValue(String queryString, Object... params) {
		List list = getHibernateTemplate().find(queryString, params);
		if (null != list && list.size() == 1) {
			return (Long) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * @param queryString a HQL query
	 */
	public String findStringValue(String queryString, Object... params) {
		List list = getHibernateTemplate().find(queryString, params);
		if (null != list && list.size() == 1) {
			return (String) list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void flush() {
		getHibernateTemplate().flush();
	}



	private String addLimit(final String sql, Integer start, int pageSize) {
		return sql + " limit " + start + "," + pageSize;
	}

	/**
	 * 设置查询语句中? 的参数
	 *
	 * @param query
	 * @param params
	 * @return
	 */
	private Query setParamForQuery(Query query,
								   final Collection<Object> params) {
		int parameterIndex = 0;
		if (params != null && params.size() > 0) {
			for (Object obj : params) {
				query.setParameter(parameterIndex++, obj);
			}
		}
		return query;
	}

	/**
	 * 设置查询语句中 :String 的参数
	 *
	 * @param query
	 * @param params
	 * @return
	 */
	private Query setParamForQuery(Query query,
								   final Map<String, Object> params) {
		if (ValidationUtils.isNotNullMap(params)) {
			for (String key : params.keySet()) {
				if (key.startsWith("list")) {
					query.setParameterList(key, (Collection) params.get(key));
				} else {
					query.setParameter(key, params.get(key));
				}
			}
		}
		return query;
	}

	/**
	 * @param hql 处理HQL
	 * @return
	 */
	private String interceptive(final String hql) {
		String temp = hql.toLowerCase();
		int beginPos = temp.indexOf("from");
		if (temp.indexOf("from") > -1) {
			if (temp.substring(0, beginPos).indexOf("distinct") > -1) {
				return "select count("
						+ temp.substring(temp.substring(0, beginPos).indexOf(
						"distinct"), beginPos - 1) + ") "
						+ hql.substring(beginPos);
			}
			return "select count(*) " + hql.substring(beginPos);
		}
		return null;
	}

	/**
	 * @param hql 处理HQL
	 * @return
	 */
	private String interceptiveSum(final String hql, String sumColumn) {
		String temp = hql.toLowerCase();
		int beginPos = temp.indexOf("from");
		if (temp.indexOf("from") > -1) {
			return "select sum(" + sumColumn + ") " + hql.substring(beginPos);
		}
		return null;
	}

	/**
	 * @param sql 处理SQL
	 * @return
	 */
	private String interceptiveSql(final String sql) {
		String temp = sql.toLowerCase();
		int beginPos = temp.indexOf("from");
		if (temp.indexOf("from") > -1) {
			if (temp.substring(0, beginPos).indexOf("distinct") > -1) {
				return "select count("
						+ temp.substring(temp.substring(0, beginPos).indexOf(
						"distinct"), beginPos - 1) + ") "
						+ sql.substring(beginPos);
			}
			if (beginPos > 9) {
				return "select count(*) from(" + sql + ") c";
			}
			return "select count(*) " + sql.substring(beginPos);
		}
		return null;
	}
}
