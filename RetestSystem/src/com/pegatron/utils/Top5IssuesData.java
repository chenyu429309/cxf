package com.pegatron.utils;

import java.util.List;

public class Top5IssuesData {
	private List<Object[]> list;
	private int count;
	public Top5IssuesData(List<Object[]> list, int count) {
		super();
		this.list = list;
		this.count = count;
	}
	public Top5IssuesData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public List<Object[]> getList() {
		return list;
	}
	public void setList(List<Object[]> list) {
		this.list = list;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
