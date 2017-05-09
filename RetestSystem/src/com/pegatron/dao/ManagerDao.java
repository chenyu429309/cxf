package com.pegatron.dao;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.pegatron.pojo.Permission;
@Repository
@SuppressWarnings("unchecked")
public class ManagerDao extends BaseDao<T>{

//	@Autowired
//	private SessionFactory sessionFactory;
//	private Session getSession(){
//		return sessionFactory.getCurrentSession();
//	}

	public List<Permission> getPermissionListBySql(String sql, int i) {
		Transaction tran =getSession().beginTransaction();
		SQLQuery list= getSession().createSQLQuery(sql).addEntity(Permission.class);
		tran.commit();
		return (List<Permission>) list;
	}

}
