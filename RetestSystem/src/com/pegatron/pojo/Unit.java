package com.pegatron.pojo;

import java.io.Serializable;

public class Unit implements Serializable{
	private static final long serialVersionUID = -8490501057716675392L;
	private String id;
	private String sn ;
	private String station_name;
	private String device_name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStation_name() {
		return station_name;
	}
	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	
	
}
