package com.pegatron.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
@SuppressWarnings({ "unchecked", "hiding" })
public class BaseDao<T> {
	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	// ================= 增删改查
	// 保存实体(增)
	public <T> void save(T t) throws Exception {
		Transaction tran = getSession().beginTransaction();
		getSession().save(t);
		tran.commit();
	}

	// 保存或更新实体
	public <T> void saveOrUpdate(T t) throws Exception {
		Transaction tran = (Transaction) getSession().beginTransaction();
		getSession().saveOrUpdate(t);
		tran.commit();
	}

	// 保存或更新实体
	public <T> void saveOrUpdateList(Collection<T> list) throws Exception {
		Transaction tran = (Transaction) getSession().beginTransaction();

		for (T t : list) {
			getSession().flush();
			getSession().clear();
			getSession().saveOrUpdate(t);
		}
		tran.commit();
	}

	// 更新实体
	public <T> void update(T t) throws Exception {
		Transaction tran = (Transaction) getSession().beginTransaction();
		getSession().update(t);
		tran.commit();
	}

	// 保存list
	public <T> void saveByList(List<T> list) throws Exception {
		Transaction tran = (Transaction) getSession().beginTransaction();
		for (T t : list) {
			getSession().save(t);
		}
		tran.commit();
	}

	// 立即加载实体类，
	public <T> T get(Class<T> clazz, Serializable id) throws Exception {
		Transaction tran = (Transaction) getSession().beginTransaction();
		T t = (T) getSession().get(clazz, id);
		tran.commit();
		return t;
	}

	// 延迟加载实体类
	public <T> T load(Class<T> clazz, Serializable id) throws Exception {
		Transaction tran = (Transaction) getSession().beginTransaction();
		T t = (T) getSession().load(clazz, id);
		tran.commit();
		return t;
	}

	// 删除实体
	public <T> void delete(T t) throws Exception {
		Transaction tran = (Transaction) getSession().beginTransaction();
		getSession().delete(t);
		tran.commit();
	}

	// 保存list
	public <T> void deleteByList(List<T> list) throws Exception {
		Transaction tran = (Transaction) getSession().beginTransaction();
		for (T t : list) {
			getSession().delete(t);
		}
		tran.commit();
	}

	// ================根据hql操作数据
	// 根据各种参数查询实体类Hql语句
	public <T> T searchObjectByHql(String hql, Object... args) throws Exception {
		Transaction tran = (Transaction) getSession().beginTransaction();
		T t = (T) setParameter(getSession().createQuery(hql), args);
		tran.commit();
		return t;
	}

	// 根据各种参数来更新删除插入实体类Hql语句
	public <T> void updateOrDeleteOrInsertObjectByHql(String hql, Object... args) throws Exception {
		Transaction tran = (Transaction) getSession().beginTransaction();
		setParameter(getSession().createQuery(hql), args);
		tran.commit();
	}

	// =====================涉及到limit
	// 查找第一个实体类
	public <T> T getFirstObject(String hql, Object... args) throws Exception {
		Transaction tran = (Transaction) getSession().beginTransaction();
		T t = setParameter(getSession().createQuery(hql), args).list() != null
				? (T) setParameter(getSession().createQuery(hql), args).list().get(0) : null;
		tran.commit();
		return t;
	}

	// 查询多个数据List
	public <T> List<T> getObjectList(String hql, Object... args) throws Exception {
		Transaction tran = (Transaction) getSession().beginTransaction();
		List<T> list = setParameter(getSession().createQuery(hql), args).list();
		tran.commit();
		return list;
	}

	// 分页查询操作
	public <T> List<T> getObjectLimitList(String hql, int page, int size, Object... args) throws Exception {
		Transaction tran = (Transaction) getSession().beginTransaction();
		List<T> list = setParameter(getSession().createQuery(hql), args).setMaxResults(size)
				.setFirstResult((page - 1) * size).list();
		tran.commit();
		return list;
	}
	public Collection<Object[]> findObjectListBySql(String sql) {
		Transaction tran = (Transaction) getSession().beginTransaction();
		Collection<Object[]> list =getSession().createSQLQuery(sql).list();
		tran.commit();
		return list;
	}
	public List<String> findStringListBySql(String sql) {
		Transaction tran = (Transaction) getSession().beginTransaction();
		List<String> list =getSession().createSQLQuery(sql).list();
		tran.commit();
		return list;
	}


	// 查询记录数
	public long getCountLimit(String hql, Object... args) throws Exception {
		Transaction tran = (Transaction) getSession().beginTransaction();
		long count = (long) setParameter(getSession().createQuery(hql), args).uniqueResult();
		tran.commit();
		return count;
	}
	
	// 查询记录数
		public long getCountLimit(String hql) throws Exception {
			Transaction tran = (Transaction) getSession().beginTransaction();
			long count=getSession().createSQLQuery(hql).uniqueResult()==null?0:Long.parseLong(getSession().createSQLQuery(hql).uniqueResult().toString());
			tran.commit();
			return count;
		}

	public Query setParameter(Query query, Object... args) {
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		return query;
	}
}
