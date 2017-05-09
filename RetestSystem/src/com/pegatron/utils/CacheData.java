package com.pegatron.utils;

public class CacheData {
	private String id;
	private String sn;
	private String station;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public CacheData(String id, String sn, String station) {
		super();
		this.id = id;
		this.sn = sn;
		this.station = station;
	}
	public CacheData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
