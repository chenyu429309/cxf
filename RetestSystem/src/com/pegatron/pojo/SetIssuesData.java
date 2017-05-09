package com.pegatron.pojo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.pegatron.utils.CacheData;

public class SetIssuesData {
	private List<Object[]> setList=new ArrayList<Object[]>();
	private List<IssuesData> list=new ArrayList<IssuesData>();
	public List<Object[]> getSetList() {
		return setList;
	}
	public void setSetList(ArrayList<Object[]> setList) {
		this.setList = setList;
	}
	public List<IssuesData> getList() {
		return list;
	}
	public void setList(List<IssuesData> list) {
		this.list = list;
	}
	
}
