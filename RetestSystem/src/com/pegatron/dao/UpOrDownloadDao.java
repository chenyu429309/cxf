package com.pegatron.dao;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class UpOrDownloadDao extends BaseDao<T> {
	public void insertCacheDataList(String sql) {
		Transaction tran = (Transaction) getSession().beginTransaction();
		getSession().createSQLQuery(sql).executeUpdate();
		tran.commit();
	}

}
