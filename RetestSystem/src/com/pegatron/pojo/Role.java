package com.pegatron.pojo;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable{
	private static final long serialVersionUID = 1L;
	private String r_id;
	private String name;
	private String descripation;
	private List<User> users;
	private List<Permission> permissions;
	
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Role(String r_id, String name, String descripation) {
		super();
		this.r_id = r_id;
		this.name = name;
		this.descripation = descripation;
	}
	public String getR_id() {
		return r_id;
	}
	public void setR_id(String r_id) {
		this.r_id = r_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescripation() {
		return descripation;
	}
	public void setDescripation(String descripation) {
		this.descripation = descripation;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public List<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	
}
