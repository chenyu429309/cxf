package com.pegatron.utils;

import java.util.List;

/**
 * 
 * @author Felix 工具类 只是为了存放图表参数（全部参数，可以根据需要把值填充到对应的list中）
 */
public class DayRateList {
	private List<String> horizontalDatas;
	private List<Integer> inputs;
	private List<String> rrdatatargets;
	private List<String> rrdatas;
	private List<String> yrdatatargets;
	private List<String> yrdatas;
	private String rrDataContent;
	private String yrDataContent;
	public DayRateList(List<String> horizontalDatas, List<Integer> inputs, List<String> rrdatatargets,
			List<String> rrdatas, List<String> yrdatatargets, List<String> yrdatas, String rrDataContent,
			String yrDataContent) {
		super();
		this.horizontalDatas = horizontalDatas;
		this.inputs = inputs;
		this.rrdatatargets = rrdatatargets;
		this.rrdatas = rrdatas;
		this.yrdatatargets = yrdatatargets;
		this.yrdatas = yrdatas;
		this.rrDataContent = rrDataContent;
		this.yrDataContent = yrDataContent;
	}
	public DayRateList() {
		super();
		// TODO Auto-generated constructor stub
	}
	public List<String> getHorizontalDatas() {
		return horizontalDatas;
	}
	public void setHorizontalDatas(List<String> horizontalDatas) {
		this.horizontalDatas = horizontalDatas;
	}
	public List<Integer> getInputs() {
		return inputs;
	}
	public void setInputs(List<Integer> inputs) {
		this.inputs = inputs;
	}
	public List<String> getRrdatatargets() {
		return rrdatatargets;
	}
	public void setRrdatatargets(List<String> rrdatatargets) {
		this.rrdatatargets = rrdatatargets;
	}
	public List<String> getRrdatas() {
		return rrdatas;
	}
	public void setRrdatas(List<String> rrdatas) {
		this.rrdatas = rrdatas;
	}
	public List<String> getYrdatatargets() {
		return yrdatatargets;
	}
	public void setYrdatatargets(List<String> yrdatatargets) {
		this.yrdatatargets = yrdatatargets;
	}
	public List<String> getYrdatas() {
		return yrdatas;
	}
	public void setYrdatas(List<String> yrdatas) {
		this.yrdatas = yrdatas;
	}
	public String getRrDataContent() {
		return rrDataContent;
	}
	public void setRrDataContent(String rrDataContent) {
		this.rrDataContent = rrDataContent;
	}
	public String getYrDataContent() {
		return yrDataContent;
	}
	public void setYrDataContent(String yrDataContent) {
		this.yrDataContent = yrDataContent;
	}

	

}
