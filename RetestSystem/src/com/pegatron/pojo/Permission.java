package com.pegatron.pojo;

import java.io.Serializable;
import java.util.List;

public class Permission implements Serializable{
	private static final long serialVersionUID = 1L;
	private String p_id;
	private String name;
	private String urlPatten;
	private String descripation;
	private Permission parent;
	private List<Permission> children;
	private List<Role> roles;
	private Integer depth;
	
	public Permission() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Permission(String p_id, String name, String urlPatten, String descripation, Permission parent,
			Integer depth) {
		super();
		this.p_id = p_id;
		this.name = name;
		this.urlPatten = urlPatten;
		this.descripation = descripation;
		this.parent = parent;
		this.depth = depth;
	}

	public Permission(String p_id, String name, String urlPatten, String descripation, Integer depth) {
		super();
		this.p_id = p_id;
		this.name = name;
		this.urlPatten = urlPatten;
		this.descripation = descripation;
		this.depth = depth;
	}
	public String getP_id() {
		return p_id;
	}
	public void setP_id(String p_id) {
		this.p_id = p_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrlPatten() {
		return urlPatten;
	}
	public void setUrlPatten(String urlPatten) {
		this.urlPatten = urlPatten;
	}
	public String getDescripation() {
		return descripation;
	}
	public void setDescripation(String descripation) {
		this.descripation = descripation;
	}
	public Permission getParent() {
		return parent;
	}
	public void setParent(Permission parent) {
		this.parent = parent;
	}
	public List<Permission> getChildren() {
		return children;
	}
	public void setChildren(List<Permission> children) {
		this.children = children;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public Integer getDepth() {
		return depth;
	}
	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	
}
