package core.dao;

import com.msh.model.entity.Domain;
import core.utils.Pagination;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Tang Yong Di
 * @date 2016/3/6
 */
public interface Dao {

	<T extends Domain> Pagination<T> queryHQLForPage(String hql, Collection<Object> params, Pagination<T> pagination);

	<T extends Domain> Pagination<T> queryHQLForPage(String hql, Map<String, Object> params, Pagination<T> pagination);

	<T extends Domain> Pagination<T> querySQLForPage(String sql, Collection<Object> params, Pagination<T> pagination);

	<T extends Domain> Pagination<T> querySQLForClassPage(Class<T> entityClass, String sql, Collection<Object> params, Pagination<T> pagination);

	void save(Domain domain);

	void saveOrUpdate(Domain domain);

	<T extends Domain, E extends Serializable> T load(Class<T> clazz, E id);

	<T extends Domain, E extends Serializable> T get(Class<T> clazz, E id);

	<T extends Domain, E extends Serializable> boolean exists(Class<T> clazz, E id);

	<T extends Domain> List<T> findAll(Class<T> clazz);

	List<Object> find(String hql, Object... params);

	Object singleResult(String hql, Object... params);

	void update(String queryString, Object... params);

	void remove(Domain domain);

	<T extends Domain> void saveAll(Collection<T> domains);

	void removeAll(Collection list);

	<T extends Domain, E extends Serializable> List<T> findByIds(Class<T> clazz, List<E> ids);

	<T extends Domain, E extends Serializable> void updateOrder(Class<T> clazz, E id, Integer order);

	<T extends Domain, E extends Serializable> void updateOrderT(Class<T> clazz, E id, Integer order);

	<T extends Domain, E extends Serializable> void changeStatus(Class<T> clazz, List<E> ids, Enum status);

	<T extends Domain, E extends Serializable> void changeStatusT(Class<T> clazz, List<E> ids, Enum status);

	<T extends Domain, E extends Serializable> void remove(Class<T> clazz, List<E> ids);

	<T extends Domain, E extends Serializable> void remove(Class<T> clazz, E id);

	void flush();
}
