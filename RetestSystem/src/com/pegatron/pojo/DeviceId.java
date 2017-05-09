package com.pegatron.pojo;

import java.io.Serializable;

public class DeviceId implements Serializable{
	private static final long serialVersionUID = 1L;
	private String deviceId;
	private String smallStation;
	private String project;
	
	public DeviceId(String deviceId, String smallStation, String project) {
		super();
		this.deviceId = deviceId;
		this.smallStation = smallStation;
		this.project = project;
	}
	public DeviceId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getSmallStation() {
		return smallStation;
	}
	public void setSmallStation(String smallStation) {
		this.smallStation = smallStation;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
}
