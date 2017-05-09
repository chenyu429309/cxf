package com.pegatron.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StringUtil {
	// 班别
	public static final String shift_select_head = "SELECT CONCAT(date_format(tb.day,'%Y-%m-%d'), '/', tb.shift), tb.input, tb.rrData, tb.yrData FROM ";
	// 某班别 选中station时的sql （and project = 'Kirin' and route = 'KR10' and station
	// = 'CCT';）
	public static final String shift_select_station = shift_select_head + " shift_hour_base_bystation tb where 1=1";
	// 某班别什么都不选时的sql (and tb.day='2017-01-06' and shift="D" group by
	// `tb`.`project` , `tb`.`route` , `tb`.`day` , `tb`.`shift`)
	public static final String shift_not_select_all = shift_select_head + " `shift_hour_base_byDay` `tb` where 1=1";
	// 某班别 选中line时的sql (and line = 'AB05' ;)
	public static final String shift_select_line = shift_select_head + " shift_hour_base_byline tb where 1=1 ";
	// 某班别 选中line时的sql (and day='2017-01-06' and line='AB05'and station="CCT")
	public static final String shift_select_all = shift_select_head + " baseratedata tb where 1=1 ";

	// 某班别 选中station时的sql （and project = 'Kirin' and route = 'KR10' and station
	// = 'CCT';）
	public static final String shift_select_station_table = "SELECT tb.project,tb.route,tb.day,tb.shift,"
			+ "tb.station,tb.input,tb.fail,tb.retest,tb.rrData, tb.yrData FROM shift_hour_base_bystation tb where 1=1";
	// 某班别什么都不选时的sql (and tb.day='2017-01-06' and shift="D") 没有聚合函数就不需要group by
	public static final String shift_not_select_all_table = "select tb.project,tb.route,tb.day,tb.shift,"
			+ "tb.input,tb.fail,tb.retest,tb.rrData, tb.yrData from `shift_hour_base_byDay` `tb` where 1=1";
	// 某班别 选中line时的sql (and line = 'AB05' ;)
	public static final String shift_select_line_table = "SELECT tb.project,tb.route,tb.day,tb.shift,"
			+ "tb.line,tb.input,tb.fail,tb.retest,tb.rrData, tb.yrData  FROM shift_hour_base_byline tb where 1=1 ";
	// 某班别 都选时的sql (and day='2017-01-06' and line='AB05'and station="CCT")
	public static final String shift_select_all_table = "SELECT tb.project,tb.route,tb.day,tb.shift,"
			+ "tb.line,tb.station,tb.input,tb.fail,tb.retest,tb.rrData, tb.yrData  FROM baseratedata tb where 1=1 ";

	// day方面的sql
	public static final String day_select_head = "SELECT tb.day, tb.input, tb.rrData, tb.yrData FROM ";
	// 某班别 选中station时的sql （and project = 'Kirin' and route = 'KR10' and station
	// = 'CCT';）
	public static final String day_select_station = day_select_head + " day_base_bystation tb where 1=1";
	// 某班别什么都不选时的sql (and tb.day='2017-01-06' and shift="D" group by
	// `tb`.`project` , `tb`.`route` , `tb`.`day` , `tb`.`shift`)
	public static final String day_not_select_all = day_select_head + " `day_base_ByDay` `tb` where 1=1";
	// 某班别 选中line时的sql (and line = 'AB05' ;)
	public static final String day_select_line = day_select_head + " day_base_byline tb where 1=1 ";
	// 某班别 选中line时的sql (and day='2017-01-06' and line='AB05'and station="CCT")
	public static final String day_select_all = day_select_head + " baseratedatabyday tb where 1=1 ";

	// 某班别 选中station时的sql （and project = 'Kirin' and route = 'KR10' and station
	// = 'CCT';）
	public static final String day_select_station_table = "SELECT tb.project,tb.route,tb.day,tb.station,"
			+ "tb.input,tb.fail,tb.retest,tb.rrData, tb.yrData FROM day_base_bystation tb where 1=1";
	// 某班别什么都不选时的sql (and tb.day='2017-01-06' and shift="D") 没有聚合函数就不需要group by
	public static final String day_not_select_all_table = "select tb.project,tb.route,tb.day,tb.input,tb.fail,"
			+ "tb.retest,tb.rrData, tb.yrData from `day_base_ByDay` `tb` where 1=1";
	// 某班别 选中line时的sql (and line = 'AB05' ;)
	public static final String day_select_line_table = "SELECT tb.project,tb.route,tb.day,tb.line,tb.input,"
			+ "tb.fail,tb.retest,tb.rrData, tb.yrData  FROM day_base_byline tb where 1=1 ";
	// 某班别 都选时的sql (and day='2017-01-06' and line='AB05'and station="CCT")
	public static final String day_select_all_table = "SELECT tb.project,tb.route,tb.day,tb.line,tb.station,"
			+ "tb.input,tb.fail,tb.retest,tb.rrData, tb.yrData  FROM baseratedatabyday tb where 1=1 ";

	// hour方面的sql

	public static final String hour_select_head = "SELECT tb.e_day, tb.input, tb.rrData, tb.yrData FROM ";
	// 某班别 选中station时的sql （and project = 'Kirin' and route = 'KR10' and station
	// = 'CCT';）
	public static final String hour_select_station = hour_select_head + " shift_hour_base_bystation tb where 1=1";
	// 某班别什么都不选时的sql (and tb.day='2017-01-06' and shift="D" group by
	// `tb`.`project` , `tb`.`route` , `tb`.`day` , `tb`.`shift`)
	public static final String hour_not_select_all = hour_select_head + " `shift_hour_base_byDay` `tb` where 1=1";
	// 某班别 选中line时的sql (and line = 'AB05' ;)
	public static final String hour_select_line = hour_select_head + " shift_hour_base_byline tb where 1=1 ";
	// 某班别 选中line时的sql (and day='2017-01-06' and line='AB05'and station="CCT")
	public static final String hour_select_all = hour_select_head + " baseratedata tb where 1=1 ";

	// 某班别 选中station时的sql （and project = 'Kirin' and route = 'KR10' and station
	// = 'CCT';）
	public static final String hour_select_station_table = "SELECT tb.project,tb.route,tb.day,tb.e_day,"
			+ "tb.station,tb.input,tb.fail,tb.retest,tb.rrData, tb.yrData FROM shift_hour_base_bystation tb where 1=1";
	// 某班别什么都不选时的sql (and tb.day='2017-01-06' and shift="D") 没有聚合函数就不需要group by
	public static final String hour_not_select_all_table = "select tb.project,tb.route,tb.day,tb.e_day,"
			+ "tb.input,tb.fail,tb.retest,tb.rrData, tb.yrData from `shift_hour_base_byDay` `tb` where 1=1";
	// 某班别 选中line时的sql (and line = 'AB05' ;)
	public static final String hour_select_line_table = "SELECT tb.project,tb.route,tb.day,tb.e_day,"
			+ "tb.line,tb.input,tb.fail,tb.retest,tb.rrData, tb.yrData  FROM shift_hour_base_byline tb where 1=1 ";
	// 某班别 都选时的sql (and day='2017-01-06' and line='AB05'and station="CCT")
	public static final String hour_select_all_table = "SELECT tb.project,tb.route,tb.day,tb.e_day,"
			+ "tb.line,tb.station,tb.input,tb.fail,tb.retest,tb.rrData, tb.yrData  FROM baseratedata tb where 1=1 ";

	
	
	// 在line页面的时候，存在的参数有：project，route，day，shift，e_day，input，fail，retest，rrData，yrData
	public static final String sql_select_charts_head = "SELECT line, input, rrData, yrData FROM ";
//	public static final String sql_lineStation_select_charts_head = "SELECT station, input, rrData, yrData FROM ";
	// 当shift，day，hour ，站位部位空时都可以用这个
	public static final String linePage_station_charts_shiftANDhour = sql_select_charts_head + 
			"baseratedata where 1=1 ";
	// 当shift，day，hour ，站位部位空时都可以用这个
	public static final String linePage_station_charts_DAY = sql_select_charts_head + 
			"baseratedatabyday where 1=1 ";
	// 2
	// 一个班别一个站位的所有线体(project，route，day，shift，e_day,station,input，fail，retest，rrData，yrData)
	// groupbyline
	public static final String linePage_no_station_charts_shiftANDhour  = sql_select_charts_head +
			"shift_hour_base_byline where 1=1 ";
//	public static final String linePage_no_station_line_charts_shiftANDhour  = sql_select_lineStation_charts_head +
//			"shift_hour_base_byline where 1=1 ";
	// groupbyline
	public static final String linePage_no_station_charts_charts_DAY  = sql_select_charts_head +
			"day_base_byline where 1=1 ";

	// shift 相关的
	// 1一个班别的所有线体（project，route，day，shift，e_day，input，fail，retest，rrData，yrData）
	public static final String linePage_station_shift_table = "select project,route,day,shift,line,station,input,fail,retest,rrData,yrData from baseratedata where 1=1 ";
	public static final String linePage_station_day_table = "select project,route,day,line,station,input,fail,retest,rrData,yrData from baseratedatabyday where 1=1 ";
	public static final String linePage_station_hour_table = "select project,route,e_day,line,station,input,fail,retest,rrData,yrData from baseratedata where 1=1 ";
	//
	// shift 相关的
	// 1一个班别的所有线体（project，route，day，shift，e_day，input，fail，retest，rrData，yrData）
	public static final String linePage_no_station_shift_table = "select project,route,day,shift,line,input,fail,retest,rrData,yrData from shift_hour_base_byline where 1=1 ";
	public static final String linePage_no_station_day_table = "select project,route,day,line,input,fail,retest,rrData,yrData from day_base_byline where 1=1 ";
	public static final String linePage_no_station_hour_table = "select project,route,e_day,line,input,fail,retest,rrData,yrData from shift_hour_base_byline where 1=1 ";

	
	// 在line页面的时候，存在的参数有：project，route，day，shift，e_day，input，fail，retest，rrData，yrData
	public static final String sql_select_station_charts_head = "SELECT station, input, rrData, yrData FROM ";
	// 当shift，day，hour ，站位部位空时都可以用这个
	public static final String stationPage_line_charts_shiftANDhour = sql_select_station_charts_head + 
			"baseratedata where 1=1 ";
	// 当shift，day，hour ，站位部位空时都可以用这个
	public static final String stationPage_line_charts_DAY = sql_select_station_charts_head + 
			"baseratedatabyday where 1=1 ";
	// 2
	// 一个班别一个站位的所有线体(project，route，day，shift，e_day,station,input，fail，retest，rrData，yrData)
	// groupbyline
	public static final String stationPage_no_line_charts_shiftANDhour  = sql_select_station_charts_head +
			" shift_hour_base_bystation where 1=1 ";
	// groupbyline
	public static final String stationPage_no_line_charts_DAY  = sql_select_station_charts_head +
			" day_base_bystation where 1=1 ";
	
	// shift 相关的
	// 1一个班别的所有线体（project，route，day，shift，e_day，input，fail，retest，rrData，yrData）
	public static final String stationPage_line_shift_table = "select project,route,day,shift,line,station,input,fail,retest,rrData,yrData from baseratedata where 1=1 ";
	public static final String stationPage_line_day_table = "select project,route,day,line,station,input,fail,retest,rrData,yrData from baseratedatabyday where 1=1 ";
	public static final String stationPage_line_hour_table = "select project,route,e_day,line,station,input,fail,retest,rrData,yrData from baseratedata where 1=1 ";
	//
	// shift 相关的
	// 1一个班别的所有线体（project，route，day，shift，e_day，input，fail，retest，rrData，yrData）
	public static final String stationPage_no_line_shift_table = "select project,route,day,shift,station,input,fail,retest,rrData,yrData from shift_hour_base_bystation where 1=1 ";
	public static final String stationPage_no_line_day_table = "select project,route,day,station,input,fail,retest,rrData,yrData from day_base_bystation where 1=1 ";
	public static final String stationPage_no_line_hour_table = "select project,route,e_day,station,input,fail,retest,rrData,yrData from shift_hour_base_bystation where 1=1 ";
	
	//issues 相关的sql
	public static String issue_lineAndStation_sql_head_day="SELECT stationId,count(*) FROM top5stationidbyday where 1=1";
	//change
	public static String issue_lineAndStation_sql_head_shiftAndHour="SELECT tb.stationId,count(*) FROM top5stationidbyshiftandstation tb where 1=1";
	//生成图表
	public static String top5issue_lineAndStation_sql_head_day="SELECT failureSymptoms,count(*) FROM retestsystem_issuesdata_tb where 1=1";
	//change
	public static String top5issue_lineAndStation_sql_head_shiftAndHour="SELECT tb.failureSymptoms,count(*) FROM (SELECT project,unit,replace(line,'AL','AB') line,station,FAILURESYMPTOMS,route,SHIFT,type,day,s_day,e_day FROM tetsdatabase_wang.retestsystem_issuesdata_tb) tb where 1=1";
	//数量
	public static String top5issue_lineAndStation_sql_head_day_count="SELECT  ifnull(count(*),0)  FROM topissuesbyday where 1=1";
	
	//change
	public static String top5issue_lineAndStation_sql_head_shiftAndHour_count="SELECT  ifnull(count(*),0)  FROM (SELECT project,unit,replace(line,'AL','AB') line,station,FAILURESYMPTOMS,route,SHIFT,type,day,s_day,e_day FROM tetsdatabase_wang.retestsystem_issuesdata_tb) tb where 1=1";
	//change
	public static String issue_lineAndStation_sql_head_shiftAndHour_count="SELECT  ifnull(count(*),0)  FROM top5stationidbyshiftandstation tb where 1=1";
	
	
	public static void HqlUtils(StringBuffer sb, String params, Object obj, String symbol) {
		if (obj != null && obj != "") {
			sb.append(" and ").append(params).append(symbol).append("'" + obj + "' ");
		}
	}
	public static Boolean EqualsUtils(String params, String station) {
		return params.equals(station);
	}

	/**
	 * Heads
	 * 
	 * @return
	 */
	public static List<String> getHeads() {
		List<String> heads = new ArrayList<String>();
		heads.add("project");
		heads.add("route");
		heads.add("day");
		heads.add("shift");
		heads.add("line");
		heads.add("station");
		heads.add("input");
		heads.add("fail");
		heads.add("retest");
		heads.add("rrdata");
		heads.add("yrdata");
		return heads;
	}

	public static List<String> getTableHeads() {
		List<String> tableHeads = new ArrayList<String>();
		tableHeads.add("project");
		tableHeads.add("route");
		tableHeads.add("day");
		tableHeads.add("shift");
		tableHeads.add("line");
		tableHeads.add("station");
		tableHeads.add("input");
		tableHeads.add("fail");
		tableHeads.add("retest");
		tableHeads.add("rrdata");
		tableHeads.add("rrdatatarget");
		tableHeads.add("rrDiffer");
		tableHeads.add("yrdata");
		tableHeads.add("yrdatatarget");
		tableHeads.add("yrDiffer");
		return tableHeads;
	}
	public static List<String> getHourHeads() {
		List<String> heads = new ArrayList<String>();
		heads.add("project");
		heads.add("route");
		heads.add("day");
		heads.add("e_day");
		heads.add("line");
		heads.add("station");
		heads.add("input");
		heads.add("fail");
		heads.add("retest");
		heads.add("rrdata");
		heads.add("yrdata");
		return heads;
	}

	
	public static List<String> getHourTableHeads() {
		List<String> tableHeads = new ArrayList<String>();
		tableHeads.add("project");
		tableHeads.add("route");
		tableHeads.add("day");
		tableHeads.add("e_day");
		tableHeads.add("line");
		tableHeads.add("station");
		tableHeads.add("input");
		tableHeads.add("fail");
		tableHeads.add("retest");
		tableHeads.add("rrdata");
		tableHeads.add("rrdatatarget");
		tableHeads.add("rrDiffer");
		tableHeads.add("yrdata");
		tableHeads.add("yrdatatarget");
		tableHeads.add("yrDiffer");
		return tableHeads;
	}


	/**
	 * MD5加密
	 * 
	 * @param String
	 *            str
	 * @return 加密後的字符串
	 */
	public final static String getMD5(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
				sb.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			} else {
				sb.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}
		byteArray = null;
		return sb.toString().toUpperCase();
	}

	/**
	 * 获取验证码
	 * 
	 * @return
	 * @author Kaiser8_Li
	 */
	public final static String getSecurityCode() {
		return (int) ((Math.random() * 9 + 1) * 100000) + "";
	}

	/**
	 * 去除字符中的特殊字符
	 * 
	 * @author Kaiser8_Li
	 */
	public final static String StringToJSON(String str) {
		str = str.replace(">", "&gt;");
		str = str.replace("<", "&lt;");
		str = str.replace(" ", "&nbsp;");
		str = str.replace("\"", "&quot;");
		str = str.replace("\'", "&#39;");
		str = str.replace("\\", "\\\\");// 对斜线的转义
		str = str.replace("\n", "\\n");
		str = str.replace("\r", "\\r");
		str = str.replace("\t", "\\t");
		return str;
	}

	/**
	 * 傳回級聯框json
	 * 
	 * @param left:已有
	 * @param right:未有
	 * @return
	 * @author Kaiser8_Li
	 */
	public final static String getSelectJSON(String left, String right) {
		StringBuilder result = new StringBuilder("{\"left\":").append(left).append(",\"right\":").append(right)
				.append("}");
		return result.toString();
	}

	/**
	 * 解碼
	 * 
	 * @param insString
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public final static String zhEncodeToDecode(String insString) throws UnsupportedEncodingException {
		String outString;
		insString = java.net.URLEncoder.encode(insString, "ISO-8859-1");
		outString = java.net.URLDecoder.decode(insString, "utf-8");
		return outString;
	}

	/**
	 * 獲取周數 W1552 格式
	 * 
	 * @author Kaiser8_Li
	 */
	public final static String getWeekOfYear(Date month) {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.setFirstDayOfWeek(Calendar.MONDAY); // 以周1为首日
		c.setTimeInMillis(month.getTime());// 当前时间
		int week = c.get(Calendar.WEEK_OF_YEAR);
		int year = c.get(Calendar.YEAR);
		c.add(Calendar.DATE, -7);
		if (week < c.get(Calendar.WEEK_OF_YEAR)) {
			year++;
		}
		int result = (year % 100) * 100 + week;
		return "W" + result;
	}

	/**
	 * 取得本周週一時間字符串
	 * 
	 * @author Kaiser8_Li
	 */
	public final static String getMondayOfThisWeek(Date day) {
		return getDayOfWeek(day, 0, Calendar.MONDAY);
	}

	/**
	 * 取得本周最後一天時間字符串
	 * 
	 * @author Kaiser8_Li
	 */
	public final static String getSundayOfThisWeek(Date day) {
		return getDayOfWeek(day, 0, Calendar.SUNDAY);
	}

	/**
	 * 取得上周週一時間字符串
	 * 
	 * @author Kaiser8_Li
	 */
	public final static String getMondayOfLastWeek(Date day) {
		return getDayOfWeek(day, -1, Calendar.MONDAY);
	}

	/**
	 * 取得下周週一時間字符串
	 * 
	 * @author Kaiser8_Li
	 */
	public static String getMondayOfNextWeek(Date day) {
		return getDayOfWeek(day, 1, Calendar.MONDAY);
	}

	/**
	 * 取得當前日期前一個月時間字符串
	 * 
	 * @author Kaiser8_Li
	 */
	public static String getDateOfLastMonth(Date day) {
		return getDayOfWeek(day, -4, Calendar.MONDAY);
	}

	/**
	 * @param day
	 * @param i：周别
	 * @param w：周几
	 * @return
	 */
	private final static String getDayOfWeek(Date day, int i, int w) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.setFirstDayOfWeek(Calendar.MONDAY); // 以周1为首日
		c.setTimeInMillis(day.getTime());// 当前时间
		c.add(Calendar.WEEK_OF_YEAR, i);
		c.set(Calendar.DAY_OF_WEEK, w);
		return sdf.format(c.getTime());
	}

	// public void setValue(String key, String path, String value, T t) {
	// ClassLoader loader = Thread.currentThread().getContextClassLoader();
	// Class clazz = null;
	// try {
	// clazz = loader.loadClass(path);// 获取字节文件
	// t = (T) clazz.newInstance();// 实例化
	// Field f = clazz.getDeclaredField(key);// 获取属性
	// f.setAccessible(true);
	// f.set(t, value);// 赋值
	// } catch (ClassNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (InstantiationException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (NoSuchFieldException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (SecurityException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// public static void setValue(Class clazz,String name,String value){
	// try {
	// Method m = clazz.getMethod("get" + name);
	// m.invoke(clazz, value);
	// } catch (IllegalAccessException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IllegalArgumentException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (InvocationTargetException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (NoSuchMethodException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (SecurityException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }
	//
	// public static void putParam(String key, String value) {
	// map.put(key, value);
	// }
	//
	// public static String getParam(String key) {
	// return map.get(key);
	// }

}
