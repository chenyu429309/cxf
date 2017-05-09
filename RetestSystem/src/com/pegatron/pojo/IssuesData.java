package com.pegatron.pojo;

import java.util.Date;

public class IssuesData {

	//标记为失败issue
	public final static String TYPE_FAIL="FAIL_ISSUE";
	//标记为复测issue
	public  final static String TYPE_RETEST="RETEST_ISSUE";
	public final static String SHIFT_D="D";
	public final static String SHIFT_N="N";
	public final static String SHIFT_HOUR="HOUR";
	private String id;//主键
	private String project;//机种
	private String route;//节点
	private String shift;
	private String line;//线体
	private String station;//站位
	private String unit;//对应小站位 one-to-one
	private Date day;//具体时间
	private Date s_day;//开始时间
	private Date e_day;//结束时间
	private String failureSymptoms;//原因
	private String type;//原因类型
	
	public IssuesData(String id, String project, String route, String shift, String line, String station, String unit,
			Date day, Date s_day, Date e_day, String failureSymptoms, String type) {
		super();
		this.id = id;
		this.project = project;
		this.route = route;
		this.shift = shift;
		this.line = line;
		this.station = station;
		this.unit = unit;
		this.day = day;
		this.s_day = s_day;
		this.e_day = e_day;
		this.failureSymptoms = failureSymptoms;
		this.type = type;
	}
	public IssuesData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	public Date getS_day() {
		return s_day;
	}
	public void setS_day(Date s_day) {
		this.s_day = s_day;
	}
	public Date getE_day() {
		return e_day;
	}
	public void setE_day(Date e_day) {
		this.e_day = e_day;
	}
	public String getFailureSymptoms() {
		return failureSymptoms;
	}
	public void setFailureSymptoms(String failureSymptoms) {
		this.failureSymptoms = failureSymptoms;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
