package com.pegatron.pojo;

import java.io.Serializable;
import java.sql.Blob;
import java.util.List;


@SuppressWarnings("unused")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private  final char SEX_MAN='男';
	private  final char SEX_WOMEN='女';
	private String workId;
	private String password;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String ChinaName;
	private String EnglishName;
	private char sex;
	private String email;
	private boolean living;
	private String telphone;
	private String shortPhone;
	private Blob img;
	private String url;
	private Department department;
	private List<Role> roles;
	public String getWorkId() {
		return workId;
	}
	public void setWorkId(String workId) {
		this.workId = workId;
	}
	public String getChinaName() {
		return ChinaName;
	}
	public void setChinaName(String chinaName) {
		ChinaName = chinaName;
	}
	public String getEnglishName() {
		return EnglishName;
	}
	public void setEnglishName(String englishName) {
		EnglishName = englishName;
	}
	
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isLiving() {
		return living;
	}
	public void setLiving(boolean living) {
		this.living = living;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getShortPhone() {
		return shortPhone;
	}
	public void setShortPhone(String shortPhone) {
		this.shortPhone = shortPhone;
	}
	public Blob getImg() {
		return img;
	}
	public void setImg(Blob img) {
		this.img = img;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public boolean isAdmin(String name,String password){
		if("admin".equals(name)&&"admin".equals(password))
			return true;
		else
			return false;
	}
	public boolean isHasePermission(User user,String url){
		for(Role role:user.getRoles()){
			for(Permission permission:role.getPermissions()){
				if(url.equals(permission.getUrlPatten())){
					return true;
				}
			}
		}
		return false;
	}
}
