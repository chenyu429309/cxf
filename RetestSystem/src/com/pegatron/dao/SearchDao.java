package com.pegatron.dao;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.pegatron.pojo.RateData;
@Repository
public class SearchDao extends BaseDao<T>{

	public List<RateData> findRateDataListBySql(String sql) {
		Transaction tran = (Transaction) getSession().beginTransaction();
		@SuppressWarnings("unchecked")
		List<RateData> list =getSession().createSQLQuery(sql).addEntity(RateData.class).list();
		tran.commit();
		return list;
	}
}
