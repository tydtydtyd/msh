package com.msh.dao.impl;

import com.msh.dao.SystemDao;
import com.msh.model.entity.system.SystemUser;
import core.dao.DaoImpl;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Tang Yong Di
 * @date 2016/3/21
 */
@Repository
public class SystemDaoImpl extends DaoImpl implements SystemDao {
    @Override
    public SystemUser getUserByAccount(final String account) {
        return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<SystemUser>() {
            @Override
            public SystemUser doInHibernate(Session session) throws HibernateException, SQLException {
                String hql = "from SystemUser u where u.account=:account";
                Query query = session.createQuery(hql);
                query.setParameter("account", account);
                List list = query.list();
                return list.isEmpty() ? null : (SystemUser) list.get(0);
            }
        });
    }

    @Override
    public Object[] getRoleByUserId(final Integer uid) {
        return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Object[]>() {
            @Override
            public Object[] doInHibernate(Session session) throws HibernateException, SQLException {
                String sql = "select r.name, r.code, r.authority from system_role r " +
                        "left join system_user u on r.id=u.role_id where u.id=:uid";
                Query query = session.createSQLQuery(sql);
                query.setParameter("uid", uid);
                List list = query.list();
                return list.isEmpty() ? null : (Object[]) list.get(0);
            }
        });
    }

    @Override
    public SystemUser loadSystemUserByAccount(final String account) {
        return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<SystemUser>() {
            @Override
            public SystemUser doInHibernate(Session session) throws HibernateException, SQLException {
                String hql = "from SystemUser u where u.account=:account";
                Query query = session.createQuery(hql);
                query.setParameter("account", account);
                List list = query.list();
                return list.isEmpty() ? null : (SystemUser) list.get(0);
            }
        });
    }

    @Override
    public void updateSystemUser(SystemUser user) {
        getHibernateTemplate().update(user);
    }
}
