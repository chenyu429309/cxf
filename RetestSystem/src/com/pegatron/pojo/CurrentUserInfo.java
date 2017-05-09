package com.pegatron.pojo;

public class CurrentUserInfo {
	private boolean status;
	private String info;
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public CurrentUserInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CurrentUserInfo(boolean status, String info) {
		super();
		this.status = status;
		this.info = info;
	}
	
	
}
