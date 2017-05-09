package com.pegatron.pojo;

import java.io.Serializable;
import java.util.List;

public class Department implements Serializable{
	private static final long serialVersionUID = 1L;
	private String departmentId;
	private String shortName;
	private String fullName;
	private Department parent;
	private List<Department> children;
	private Integer depth;
	private User user;
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Department getParent() {
		return parent;
	}
	public void setParent(Department parent) {
		this.parent = parent;
	}
	public List<Department> getChildren() {
		return children;
	}
	public void setChildren(List<Department> children) {
		this.children = children;
	}
	public Integer getDepth() {
		return depth;
	}
	public void setDepth(Integer depth) {
		this.depth = depth;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
