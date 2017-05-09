package com.pegatron.pojo;

import java.io.Serializable;
import java.util.Date;

public class RateData implements Serializable{
	public final static String SHIFT_D="D";
	public final static String SHIFT_N="N";
	public final static String SHIFT_HOUR="HOUR";
	private static final long serialVersionUID = 4519355638454869945L;
	private String id;
	private String project;
	private String route;
	private String line;
	private String station;
	private Date day;
	private String shift;
	private Date s_day;
	private Date e_day;
	private Double input;
	private Double retest;
	private Double fail;
	
	public RateData() {
		super();
	}
	public RateData(String id, String project, String route, String line, String station, Date day, String shift,
			Date s_day, Date e_day, Double input, Double retest, Double fail) {
		super();
		this.id = id;
		this.project = project;
		this.route = route;
		this.line = line;
		this.station = station;
		this.day = day;
		this.shift = shift;
		this.s_day = s_day;
		this.e_day = e_day;
		this.input = input;
		this.retest = retest;
		this.fail = fail;
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
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
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
	public Double getInput() {
		return input;
	}
	public void setInput(Double input) {
		this.input = input;
	}
	public Double getRetest() {
		return retest;
	}
	public void setRetest(Double retest) {
		this.retest = retest;
	}
	public Double getFail() {
		return fail;
	}
	public void setFail(Double fail) {
		this.fail = fail;
	}
	
	
}
