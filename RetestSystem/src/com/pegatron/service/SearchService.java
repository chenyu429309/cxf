package com.pegatron.service;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pegatron.dao.SearchDao;
import com.pegatron.utils.DayRateList;
import com.pegatron.utils.PageBean;
import com.pegatron.utils.PageList;
import com.pegatron.utils.PropertiesConfig;
import com.pegatron.utils.ShiftSelectUtils;
import com.pegatron.utils.StringUtil;
import com.pegatron.utils.Top5IssuesData;

import net.sf.json.JSONObject;

@Service
public class SearchService {
	// 找出以天为单位的sql 可以查到project，route，day，input，fail，retest，rrdata，
	@Autowired
	private SearchDao searchDao;

	// ==========登陆模块
	public String login(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}
	// ==========登出模块
	// ==========跳转模块
	// ==========注册模块
	// ==========修改密码模块
	// ==========添加用户模块
	// ==========添加角色模块
	// ==========添加权限模块
	// ==========设置权限模块
	// ==========设置角色模块
	/**
	 * 工具方法：为了生成图表参数的信息，首先需要一个共5个参数 目标值的取值 需有个标志
	 * @param RateData
	 * @param pros
	 * @return
	 * @throws Exception
	 */
	public DayRateList getDayRateList(List<Object[]> RateData, Properties pros, Integer SearchMark) throws Exception {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		if (RateData.size() > 0) {
			// 横轴
			List<String> horizontalDatas = new ArrayList<String>();
			// 投入
			List<Integer> inputs = new ArrayList<Integer>();
			// 复测率
			List<String> rrdatas = new ArrayList<String>();
			// 良率
			List<String> yrdatas = new ArrayList<String>();
			// 复测率目标
			List<String> rrdatatargets = new ArrayList<String>();
			// 良率目标
			List<String> yrdatatargets = new ArrayList<String>();
			for (Object[] obj : RateData) {
				horizontalDatas.add(obj[0].toString());
				inputs.add((int) Float.parseFloat(obj[1].toString()));
				rrdatas.add(decimalFormat.format(Float.parseFloat(obj[2].toString()) * 100));
				yrdatas.add(decimalFormat.format(Float.parseFloat(obj[3].toString()) * 100));
				// 这里判断是否为小时的
				if (SearchMark == 0) {
					rrdatatargets.add(pros.getProperty("rrtargetRate"));
					yrdatatargets.add(pros.getProperty("yrtargetRate"));
				} else {
					rrdatatargets.add(pros.getProperty("rrtargetRatetotal"));
					yrdatatargets.add(pros.getProperty("yrtargetRatetotal"));
				}
			}
			return new DayRateList(horizontalDatas, inputs, rrdatatargets, rrdatas, yrdatatargets, yrdatas,
					pros.getProperty("rrDataContent"), pros.getProperty("yrDataContent"));
		}
		return null;
	}

	/**
	 * 写一个收集表数据的列表 一个object[] 写一个表头，把所有的数据变成String类型就行了 前端页面的table显示
	 * 犹豫不确定性，所以用反射机制来将对应的数据将对应的属性赋值，有些 值在List
	 * <Object[]>中是没有的，所以需手动配置，-----------------通用性差，需改善 SearchMark主要是为标记查询条件
	 * 
	 * @param RateData
	 * @param pros
	 * @param page
	 * @return
	 * @throws Exception
	 */
	// 工具类的获取方法，这里还需要判断 目标的取值，看是总的还是分的。
	public List<PageList> getTableRateList(String sql, List<String> heads, Integer SearchMark) throws Exception {
		List<Object[]> RateData = (List<Object[]>) searchDao.findObjectListBySql(sql);
		DecimalFormat dec = new DecimalFormat("0.00");
		List<PageList> pList = new ArrayList<PageList>();
		for (Object[] obj : RateData) {
			PageList pageList = new PageList();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			Class<?> clazz = loader.loadClass("com.pegatron.utils.PageList");// 获取字节文件
			pageList = (PageList) clazz.newInstance();// 实例化
			Properties pros = PropertiesConfig.getProperties("config.properties");
			if (SearchMark<2) {
				pageList.setRrdatatarget(pros.getProperty("rrtargetRate"));
				pageList.setYrdatatarget(pros.getProperty("yrtargetRate"));
			} else {
				pageList.setRrdatatarget(pros.getProperty("rrtargetRatetotal"));
				pageList.setYrdatatarget(pros.getProperty("yrtargetRatetotal"));
			}
			for (int i = 0; i < heads.size(); i++) {
				if ("rrdata".equals(heads.get(i)) || "yrdata".equals(heads.get(i))) {
					Field f = clazz.getDeclaredField(heads.get(i));// 获取属性
					f.setAccessible(true);
					float data = Float.parseFloat(obj[i].toString()) * 100;
					f.set(pageList, dec.format(data));// 赋值
				}else{
					Field f = clazz.getDeclaredField(heads.get(i));// 获取属性
					f.setAccessible(true);
					f.set(pageList, obj[i].toString());// 赋值
				}
			}
			pageList.setRrDiffer(
					dec.format(Float.parseFloat(pageList.getRrdatatarget()) - Float.parseFloat(pageList.getRrdata())));
			pageList.setYrDiffer(
					dec.format(Float.parseFloat(pageList.getYrdatatarget()) - Float.parseFloat(pageList.getYrdata())));
			
			//加%
			pageList.setRrdata(pageList.getRrdata()+"%");
			pageList.setRrdatatarget(pageList.getRrdatatarget()+"%");
			pageList.setRrDiffer(pageList.getRrDiffer()+"%");
			pageList.setYrdata(pageList.getYrdata()+"%");
			pageList.setYrdatatarget(pageList.getYrdatatarget()+"%");
			pageList.setYrDiffer(pageList.getYrDiffer()+"%");
			pList.add(pageList);
		}
		return pList;
	}

	// -=================获取选择框的列表================
	public List<String> getProjectList() throws Exception {
		String sql = "select distinct project From RateData";
		return searchDao.getObjectList(sql);
	}

	public List<String> getRoute() throws Exception {
		String sql = "select distinct route From RateData";
		return searchDao.getObjectList(sql);
	}

	public List<String> getLine() throws Exception {
		String sql = "select distinct replace(tb.line, 'AL', 'AB') line From RETESTSYSTEM_RATEDATA_TB tb group by tb.line";
		List<String> list = searchDao.findStringListBySql(sql);
		return list;
	}

	public List<String> getStation() throws Exception {
		String sql = "select distinct station From RateData";
		return searchDao.getObjectList(sql);
	}

	public List<String> getShift() throws Exception {
		String sql = "select distinct shift From RateData";
		return searchDao.getObjectList(sql);
	}

	// bug
	public String getTableData(String project, String route, String line, String station, Date startTime, Date endTime,
			String shift, int currentPage, Integer SearchMark) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		// 有四种情况
		StringBuffer tableSb = new StringBuffer();
		List<String> heads = StringUtil.getHeads();
		List<String> tableHeads = StringUtil.getTableHeads();
		// 1 line and station not select
		if ("".equals(line) && "".equals(station)) {
			// 为班别
			heads.remove("line");
			heads.remove("station");
			tableHeads.remove("line");
			tableHeads.remove("station");
			if (SearchMark == 1) {
				// 生成table的sql and tb.day='2017-01-06' and shift="D"
				tableSb.append(StringUtil.shift_not_select_all_table);
				// 生成图表的sql and tb.day='2017-01-06' and shift="D" group by
				// `tb`.`project` , `tb`.`route` , `tb`.`day` , `tb`.`shift`
				StringUtil.HqlUtils(tableSb, "project", project, "=");
				StringUtil.HqlUtils(tableSb, "route", route, "=");
				StringUtil.HqlUtils(tableSb, "day", sdf.format(startTime), ">");
				StringUtil.HqlUtils(tableSb, "day", sdf.format(endTime), "<");
				if (!"ALL".equals(shift)) {
					StringUtil.HqlUtils(tableSb, "shift", shift, "=");
				} else {
					StringUtil.HqlUtils(tableSb, "shift", "HOUR", "!=");
				}
				// 因为在生成charts的sql语句中用到了concat函数 ，所以需要用group by
				// charsSb.append(" group by `tb`.`project` , `tb`.`route` ,
				// `tb`.`day` , `tb`.`shift`");
			} // day
			else if (SearchMark == 2) {
				heads.remove("shift");
				tableHeads.remove("shift");
				tableSb.append(StringUtil.day_not_select_all_table);
				StringUtil.HqlUtils(tableSb, "project", project, "=");
				StringUtil.HqlUtils(tableSb, "route", route, "=");
				StringUtil.HqlUtils(tableSb, "day", sdf.format(startTime), ">");
				StringUtil.HqlUtils(tableSb, "day", sdf.format(endTime), "<");
			}
			// HOUR
			else if (SearchMark == 0) {
				shift = "HOUR";
				heads = StringUtil.getHourHeads();
				tableHeads = StringUtil.getHourTableHeads();
				heads.remove("line");
				heads.remove("station");
				tableHeads.remove("line");
				tableHeads.remove("station");
				tableSb.append(StringUtil.hour_not_select_all_table);
				StringUtil.HqlUtils(tableSb, "project", project, "=");
				StringUtil.HqlUtils(tableSb, "route", route, "=");
				StringUtil.HqlUtils(tableSb, "e_day", sdf1.format(startTime), ">");
				StringUtil.HqlUtils(tableSb, "e_day", sdf1.format(endTime), "<");
				StringUtil.HqlUtils(tableSb, "shift", "HOUR", "=");
			}
		}
		// 2 line select
		if (!"".equals(line) && "".equals(station)) {
			// 为班别
			heads.remove("station");
			tableHeads.remove("station");
			if (SearchMark == 1) {
				// 生成table的sql
				tableSb.append(StringUtil.shift_select_line_table);
				// 生成图表的sql
				StringUtil.HqlUtils(tableSb, "project", project, "=");
				StringUtil.HqlUtils(tableSb, "route", route, "=");
				StringUtil.HqlUtils(tableSb, "day", sdf.format(startTime), ">");
				StringUtil.HqlUtils(tableSb, "day", sdf.format(endTime), "<");
				if (!"ALL".equals(shift)) {
					StringUtil.HqlUtils(tableSb, "shift", shift, "=");
				} else {
					StringUtil.HqlUtils(tableSb, "shift", "HOUR", "!=");
				}
				StringUtil.HqlUtils(tableSb, "line", line, "=");
			} else if (SearchMark == 2) {
				heads.remove("shift");
				tableHeads.remove("shift");
				tableSb.append(StringUtil.day_select_line_table);
				StringUtil.HqlUtils(tableSb, "project", project, "=");
				StringUtil.HqlUtils(tableSb, "route", route, "=");
				StringUtil.HqlUtils(tableSb, "day", sdf.format(startTime), ">");
				StringUtil.HqlUtils(tableSb, "day", sdf.format(endTime), "<");
				StringUtil.HqlUtils(tableSb, "line", line, "=");
			} else if (SearchMark == 0) {
				shift = "HOUR";
				heads = StringUtil.getHourHeads();
				tableHeads = StringUtil.getHourTableHeads();
				heads.remove("station");
				tableHeads.remove("station");
				tableSb.append(StringUtil.hour_select_line_table);
				StringUtil.HqlUtils(tableSb, "project", project, "=");
				StringUtil.HqlUtils(tableSb, "route", route, "=");
				StringUtil.HqlUtils(tableSb, "e_day", sdf1.format(startTime), ">");
				StringUtil.HqlUtils(tableSb, "e_day", sdf1.format(endTime), "<");
				StringUtil.HqlUtils(tableSb, "shift", "HOUR", "=");
				StringUtil.HqlUtils(tableSb, "line", line, "=");
			}
		}
		// 3 station select
		if ("".equals(line) && !"".equals(station)) {
			// 为班别
			heads.remove("line");
			tableHeads.remove("line");
			if (SearchMark == 1) {
				// 生成table的sql
				tableSb.append(StringUtil.shift_select_station_table);
				// 生成图表的sql
				StringUtil.HqlUtils(tableSb, "project", project, "=");
				StringUtil.HqlUtils(tableSb, "route", route, "=");
				StringUtil.HqlUtils(tableSb, "day", sdf.format(startTime), ">");
				StringUtil.HqlUtils(tableSb, "day", sdf.format(endTime), "<");
				if (!"ALL".equals(shift)) {
					StringUtil.HqlUtils(tableSb, "shift", shift, "=");
				} else {
					StringUtil.HqlUtils(tableSb, "shift", "HOUR", "!=");
				}
				StringUtil.HqlUtils(tableSb, "station", station, "=");
			} else if (SearchMark == 2) {
				heads.remove("shift");
				tableHeads.remove("shift");
				tableSb.append(StringUtil.day_select_station_table);
				StringUtil.HqlUtils(tableSb, "project", project, "=");
				StringUtil.HqlUtils(tableSb, "route", route, "=");
				StringUtil.HqlUtils(tableSb, "day", sdf.format(startTime), ">");
				StringUtil.HqlUtils(tableSb, "day", sdf.format(endTime), "<");
				StringUtil.HqlUtils(tableSb, "station", station, "=");
			} else if (SearchMark == 0) {
				shift = "HOUR";
				heads = StringUtil.getHourHeads();
				tableHeads = StringUtil.getHourTableHeads();
				heads.remove("line");
				tableHeads.remove("line");
				tableSb.append(StringUtil.hour_select_station_table);
				StringUtil.HqlUtils(tableSb, "project", project, "=");
				StringUtil.HqlUtils(tableSb, "route", route, "=");
				StringUtil.HqlUtils(tableSb, "e_day", sdf1.format(startTime), ">");
				StringUtil.HqlUtils(tableSb, "e_day", sdf1.format(endTime), "<");
				StringUtil.HqlUtils(tableSb, "shift", "HOUR", "=");
				StringUtil.HqlUtils(tableSb, "station", station, "=");
			}
		}
		// 4 line and station select
		if (!"".equals(line) && !"".equals(station)) {
			// 为班别
			if (SearchMark == 1) {
				// 生成table的sql
				tableSb.append(StringUtil.shift_select_all_table);
				StringUtil.HqlUtils(tableSb, "project", project, "=");
				StringUtil.HqlUtils(tableSb, "route", route, "=");
				StringUtil.HqlUtils(tableSb, "day", sdf.format(startTime), ">");
				StringUtil.HqlUtils(tableSb, "day", sdf.format(endTime), "<");
				// 生成图表的sql
				if (!"ALL".equals(shift)) {
					StringUtil.HqlUtils(tableSb, "tb.shift", shift, "=");
				} else {
					StringUtil.HqlUtils(tableSb, "tb.shift", "HOUR", "!=");
				}
				StringUtil.HqlUtils(tableSb, "line", line, "=");
				StringUtil.HqlUtils(tableSb, "station", station, "=");
			} else if (SearchMark == 2) {
				heads.remove("shift");
				tableHeads.remove("shift");
				tableSb.append(StringUtil.day_select_all_table);
				StringUtil.HqlUtils(tableSb, "tb.project", project, "=");
				StringUtil.HqlUtils(tableSb, "tb.route", route, "=");
				StringUtil.HqlUtils(tableSb, "tb.day", sdf.format(startTime), ">");
				StringUtil.HqlUtils(tableSb, "tb.day", sdf.format(endTime), "<");
				StringUtil.HqlUtils(tableSb, "tb.line", line, "=");
				StringUtil.HqlUtils(tableSb, "tb.station", station, "=");
			} else if (SearchMark == 0) {
				shift = "HOUR";
				heads = StringUtil.getHourHeads();
				tableHeads = StringUtil.getHourTableHeads();
				tableSb.append(StringUtil.hour_select_all_table);
				StringUtil.HqlUtils(tableSb, "tb.project", project, "=");
				StringUtil.HqlUtils(tableSb, "tb.route", route, "=");
				StringUtil.HqlUtils(tableSb, "tb.e_day", sdf1.format(startTime), ">");
				StringUtil.HqlUtils(tableSb, "tb.e_day", sdf1.format(endTime), "<");
				StringUtil.HqlUtils(tableSb, "tb.shift", "HOUR", "=");
				StringUtil.HqlUtils(tableSb, "tb.line", line, "=");
				StringUtil.HqlUtils(tableSb, "tb.station", station, "=");
			}
		}
		return getTablesDataJsonObject(tableSb, heads, tableHeads, SearchMark, currentPage);
	}

	public String getTablesDataJsonObject(StringBuffer tableSb, List<String> heads, List<String> tableHeads,
			Integer SearchMark, int currentPage) throws Exception {
		// 表格数据
		List<PageList> pList = new ArrayList<PageList>();
		pList = getTableRateList(tableSb.toString(), heads, SearchMark);
		JSONObject obj = new JSONObject();
		// 对表各数据进行分页操作
		PageBean<PageList> pageList = null;
		if (pList.size() > 0 && (currentPage - 1) * 10 <= pList.size()) {
			int size = pList.size() - currentPage * 10 < 0 ? pList.size() - (currentPage - 1) * 10 : 10;
			pageList = new PageBean<PageList>(currentPage, 10, pList.size(),
					pList.subList((currentPage - 1) * 10, (currentPage - 1) * 10 + size));
		}
		obj.put("pageList", pageList);
		obj.put("heads", tableHeads);
		return obj.toString();
	}

	/**
	 * 图表生成方法。只需写出对应的sql就可以获取到相应的图表 主页方面
	 * 
	 * @param project
	 * @param route
	 * @param line
	 * @param station
	 * @param startTime
	 * @param endTime
	 * @param shift
	 * @param currentPage
	 * @param SearchMark
	 * @return
	 * @throws Exception
	 */
	public String getHomeRate(String project, String route, String line, String station, Date startTime, Date endTime,
			String shift, Integer SearchMark) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		// 有四种情况
		StringBuffer charsSb = new StringBuffer();
		// 1 line and station not select
		if ("".equals(line) && "".equals(station)) {
			// 为班别
			if (SearchMark == 1) {
				// 生成图表的sql and tb.day='2017-01-06' and shift="D" group by
				// `tb`.`project` , `tb`.`route` , `tb`.`day` , `tb`.`shift`
				charsSb.append(StringUtil.shift_not_select_all);
				StringUtil.HqlUtils(charsSb, "project", project, "=");
				StringUtil.HqlUtils(charsSb, "route", route, "=");
				StringUtil.HqlUtils(charsSb, "day", sdf.format(startTime), ">");
				StringUtil.HqlUtils(charsSb, "day", sdf.format(endTime), "<");
				if (!"ALL".equals(shift)) {
					StringUtil.HqlUtils(charsSb, "shift", shift, "=");
				} else {
					StringUtil.HqlUtils(charsSb, "shift", "HOUR", "!=");
				}
				// 因为在生成charts的sql语句中用到了concat函数 ，所以需要用group by
				// charsSb.append(" group by `tb`.`project` , `tb`.`route` ,
				// `tb`.`day` , `tb`.`shift`");
			} // day
			else if (SearchMark == 2) {
				charsSb.append(StringUtil.day_not_select_all);
				StringUtil.HqlUtils(charsSb, "project", project, "=");
				StringUtil.HqlUtils(charsSb, "route", route, "=");
				StringUtil.HqlUtils(charsSb, "day", sdf.format(startTime), ">");
				StringUtil.HqlUtils(charsSb, "day", sdf.format(endTime), "<");
			}
			// HOUR
			else if (SearchMark == 0) {
				shift = "HOUR";
				charsSb.append(StringUtil.hour_not_select_all);
				StringUtil.HqlUtils(charsSb, "project", project, "=");
				StringUtil.HqlUtils(charsSb, "route", route, "=");
				StringUtil.HqlUtils(charsSb, "e_day", sdf1.format(startTime), ">");
				StringUtil.HqlUtils(charsSb, "e_day", sdf1.format(endTime), "<");
				StringUtil.HqlUtils(charsSb, "shift", "HOUR", "=");
			}
		}
		// 2 line select
		if (!"".equals(line) && "".equals(station)) {
			// 为班别
			if (SearchMark == 1) {
				// 生成图表的sql
				charsSb.append(StringUtil.shift_select_line);
				StringUtil.HqlUtils(charsSb, "project", project, "=");
				StringUtil.HqlUtils(charsSb, "route", route, "=");
				StringUtil.HqlUtils(charsSb, "day", sdf.format(startTime), ">");
				StringUtil.HqlUtils(charsSb, "day", sdf.format(endTime), "<");
				if (!"ALL".equals(shift)) {
					StringUtil.HqlUtils(charsSb, "shift", shift, "=");
				} else {
					StringUtil.HqlUtils(charsSb, "shift", "HOUR", "!=");
				}
				StringUtil.HqlUtils(charsSb, "line", line, "=");
				// charsSb.append(" group by tb.day,tb.shift");
			} else if (SearchMark == 2) {
				charsSb.append(StringUtil.day_select_line);
				StringUtil.HqlUtils(charsSb, "project", project, "=");
				StringUtil.HqlUtils(charsSb, "route", route, "=");
				StringUtil.HqlUtils(charsSb, "day", sdf.format(startTime), ">");
				StringUtil.HqlUtils(charsSb, "day", sdf.format(endTime), "<");
				StringUtil.HqlUtils(charsSb, "line", line, "=");
			} else if (SearchMark == 0) {
				shift = "HOUR";
				charsSb.append(StringUtil.hour_select_line);
				StringUtil.HqlUtils(charsSb, "project", project, "=");
				StringUtil.HqlUtils(charsSb, "route", route, "=");
				StringUtil.HqlUtils(charsSb, "e_day", sdf1.format(startTime), ">");
				StringUtil.HqlUtils(charsSb, "e_day", sdf1.format(endTime), "<");
				StringUtil.HqlUtils(charsSb, "shift", "HOUR", "=");
				StringUtil.HqlUtils(charsSb, "line", line, "=");
			}
		}
		// 3 station select
		if ("".equals(line) && !"".equals(station)) {
			// 为班别
			if (SearchMark == 1) {
				charsSb.append(StringUtil.shift_select_station);
				StringUtil.HqlUtils(charsSb, "project", project, "=");
				StringUtil.HqlUtils(charsSb, "route", route, "=");
				StringUtil.HqlUtils(charsSb, "day", sdf.format(startTime), ">");
				StringUtil.HqlUtils(charsSb, "day", sdf.format(endTime), "<");
				if (!"ALL".equals(shift)) {
					StringUtil.HqlUtils(charsSb, "shift", shift, "=");
				} else {
					StringUtil.HqlUtils(charsSb, "shift", "HOUR", "!=");
				}
				StringUtil.HqlUtils(charsSb, "station", station, "=");
			} else if (SearchMark == 2) {
				charsSb.append(StringUtil.day_select_station);
				StringUtil.HqlUtils(charsSb, "project", project, "=");
				StringUtil.HqlUtils(charsSb, "route", route, "=");
				StringUtil.HqlUtils(charsSb, "day", sdf.format(startTime), ">");
				StringUtil.HqlUtils(charsSb, "day", sdf.format(endTime), "<");
				StringUtil.HqlUtils(charsSb, "station", station, "=");
			} else if (SearchMark == 0) {
				shift = "HOUR";
				charsSb.append(StringUtil.hour_select_station);
				StringUtil.HqlUtils(charsSb, "project", project, "=");
				StringUtil.HqlUtils(charsSb, "route", route, "=");
				StringUtil.HqlUtils(charsSb, "e_day", sdf1.format(startTime), ">");
				StringUtil.HqlUtils(charsSb, "e_day", sdf1.format(endTime), "<");
				StringUtil.HqlUtils(charsSb, "shift", "HOUR", "=");
				StringUtil.HqlUtils(charsSb, "station", station, "=");
			}
		}
		// 4 line and station select
		if (!"".equals(line) && !"".equals(station)) {
			// 为班别
			if (SearchMark == 1) {
				charsSb.append(StringUtil.shift_select_all);
				StringUtil.HqlUtils(charsSb, "tb.project", project, "=");
				StringUtil.HqlUtils(charsSb, "tb.route", route, "=");
				StringUtil.HqlUtils(charsSb, "tb.day", sdf.format(startTime), ">");
				StringUtil.HqlUtils(charsSb, "tb.day", sdf.format(endTime), "<");
				if (!"ALL".equals(shift)) {
					StringUtil.HqlUtils(charsSb, "tb.shift", shift, "=");
				} else {
					StringUtil.HqlUtils(charsSb, "tb.shift", "HOUR", "!=");
				}
				StringUtil.HqlUtils(charsSb, "tb.line", line, "=");
				StringUtil.HqlUtils(charsSb, "tb.station", station, "=");
			} else if (SearchMark == 2) {
				charsSb.append(StringUtil.day_select_all);
				StringUtil.HqlUtils(charsSb, "tb.project", project, "=");
				StringUtil.HqlUtils(charsSb, "tb.route", route, "=");
				StringUtil.HqlUtils(charsSb, "tb.day", sdf.format(startTime), ">");
				StringUtil.HqlUtils(charsSb, "tb.day", sdf.format(endTime), "<");
				StringUtil.HqlUtils(charsSb, "tb.line", line, "=");
				StringUtil.HqlUtils(charsSb, "tb.station", station, "=");
			} else if (SearchMark == 0) {
				shift = "HOUR";
				charsSb.append(StringUtil.hour_select_all);
				StringUtil.HqlUtils(charsSb, "tb.project", project, "=");
				StringUtil.HqlUtils(charsSb, "tb.route", route, "=");
				StringUtil.HqlUtils(charsSb, "tb.e_day", sdf1.format(startTime), ">");
				StringUtil.HqlUtils(charsSb, "tb.e_day", sdf1.format(endTime), "<");
				StringUtil.HqlUtils(charsSb, "tb.shift", "HOUR", "=");
				StringUtil.HqlUtils(charsSb, "tb.line", line, "=");
				StringUtil.HqlUtils(charsSb, "station", station, "=");
			}
		}
		return generatindProgram(charsSb.toString(), SearchMark);
	}

	/**
	 * 图表数据生成器
	 * 
	 * @param charsSb
	 * @param tableSb
	 * @param heads
	 * @param tableHeads
	 * @param currentPage
	 * @param SearchMark
	 * @return
	 * @throws Exception
	 */
	private String generatindProgram(String charsSb, Integer SearchMark) throws Exception {
		// 图表数据
		DayRateList dayRateList = new DayRateList();
		// 读取配置文件
		Properties pros = PropertiesConfig.getProperties("config.properties");
		// 生成图表数据
		dayRateList = getDayRateList((List<Object[]>) searchDao.findObjectListBySql(charsSb.toString()), pros,
				SearchMark);
		JSONObject obj = new JSONObject();
		obj.put("dayRateList", dayRateList);
		return obj.toString();
	}

	/**
	 * 在line这个选择中，存在传来的横轴不一样的情况，、 1
	 * 当station为nul时，证明页面是从首页传来的，即传来的horizontalData需要转换 2 当station部位null时
	 * 证明是从station页面转来的，即传来的horizontalData不需要转换 根据上面的情况，只有station为空时才需要对应的算法
	 * 
	 * @param project
	 * @param route
	 * @param station
	 * @param horizontalData
	 * @param SearchMark
	 * @param currentPage
	 * @return
	 * @throws Exception
	 */
	public String getLineTableChartsDatas(String project, String route, String line, String station, String day,
			String shift, String endTime, Integer SearchMark) throws Exception {
		// 有四种情况
		// 图表数据
		// 表格数据
		StringBuffer charsSb = new StringBuffer();
		// 小时
		if (SearchMark == 0) {
			if (station == "" || station == null) {
				// 图表数据
				charsSb.append(StringUtil.linePage_no_station_charts_shiftANDhour);
				StringUtil.HqlUtils(charsSb, "project", project, "=");
				StringUtil.HqlUtils(charsSb, "route", route, "=");
				StringUtil.HqlUtils(charsSb, "e_day", endTime, "=");
				StringUtil.HqlUtils(charsSb, "shift", shift, "=");
				// charsSb.append(" order by tb.line");
			} else {
				// 图表数据
				charsSb.append(StringUtil.linePage_station_charts_shiftANDhour);
				StringUtil.HqlUtils(charsSb, "project", project, "=");
				StringUtil.HqlUtils(charsSb, "route", route, "=");
				StringUtil.HqlUtils(charsSb, "e_day", endTime, "=");// 这里的horizontalData需要进行转换一下
				StringUtil.HqlUtils(charsSb, "shift", shift, "=");
				StringUtil.HqlUtils(charsSb, "station", station, "=");// 返回当存在station的时候
			}
		} // 天及以上级别
		else if (SearchMark == 2) {
			if (station == "" || station == null) {
				// 图表数据
				charsSb.append(StringUtil.linePage_no_station_charts_charts_DAY);
				StringUtil.HqlUtils(charsSb, "project", project, "=");
				StringUtil.HqlUtils(charsSb, "route", route, "=");
				StringUtil.HqlUtils(charsSb, "day", day, "=");
			} else {
				// 图表数据
				charsSb.append(StringUtil.linePage_station_charts_DAY);
				StringUtil.HqlUtils(charsSb, "project", project, "=");
				StringUtil.HqlUtils(charsSb, "route", route, "=");
				StringUtil.HqlUtils(charsSb, "day", day, "=");
				StringUtil.HqlUtils(charsSb, "station", station, "=");
			}
		}
		// 班别
		else if (SearchMark == 1) {
			if (station == "" || station == null) {
				charsSb.append(StringUtil.linePage_no_station_charts_shiftANDhour);
				StringUtil.HqlUtils(charsSb, "project", project, "=");
				StringUtil.HqlUtils(charsSb, "route", route, "=");
				StringUtil.HqlUtils(charsSb, "day", day, "=");
				if (!"ALL".equals(shift)) {
					StringUtil.HqlUtils(charsSb, "shift", shift, "=");
				} else {
					StringUtil.HqlUtils(charsSb, "shift", "HOUR", "!=");
				}
			} else {
				// 图表数据
				charsSb.append(StringUtil.linePage_station_charts_shiftANDhour);
				StringUtil.HqlUtils(charsSb, "project", project, "=");
				StringUtil.HqlUtils(charsSb, "route", route, "=");
				StringUtil.HqlUtils(charsSb, "day", day, "=");
				if (!"ALL".equals(shift)) {
					StringUtil.HqlUtils(charsSb, "shift", shift, "=");
				} else {
					StringUtil.HqlUtils(charsSb, "shift", "HOUR", "!=");
				}
				StringUtil.HqlUtils(charsSb, "station", station, "=");
			}
		}
		return generatindProgram(charsSb.toString(), SearchMark);
	}

	public String getTableDataByLineTables(String project, String route, String line, String station, String day,
			String shift, String endTime, Integer SearchMark, int currentPage) throws Exception {
		// 有四种情况
		// 图表数据
		// 表格数据
		StringBuffer tableSb = new StringBuffer();
		List<String> heads = StringUtil.getHeads();// 需要获取的表格数据
		List<String> tableHeads = StringUtil.getTableHeads();// 返回前端的表头数据
		// 小时
		if (SearchMark == 0) {
			heads = StringUtil.getHourHeads();
			tableHeads = StringUtil.getHourTableHeads();
			heads.remove("day");
			tableHeads.remove("day");
			if (station == "" || station == null) {
				heads.remove("station");
				tableHeads.remove("station");
				// 表格数据
				tableSb.append(StringUtil.linePage_no_station_hour_table);
				StringUtil.HqlUtils(tableSb, "project", project, "=");
				StringUtil.HqlUtils(tableSb, "route", route, "=");
				StringUtil.HqlUtils(tableSb, "e_day", endTime, "=");
				StringUtil.HqlUtils(tableSb, "shift", "HOUR", "=");
			} else {
				// 表格数据
				tableSb.append(StringUtil.linePage_station_hour_table);
				StringUtil.HqlUtils(tableSb, "project", project, "=");
				StringUtil.HqlUtils(tableSb, "route", route, "=");
				StringUtil.HqlUtils(tableSb, "e_day", endTime, "=");// 这里的horizontalData需要进行转换一下
				StringUtil.HqlUtils(tableSb, "shift", shift, "=");
				StringUtil.HqlUtils(tableSb, "station", station, "=");// 返回当存在station的时候
			}
		} // 天及以上级别
		else if (SearchMark == 2) {
			heads.remove("shift");
			tableHeads.remove("shift");
			if (station == "" || station == null) {
				heads.remove("station");
				tableHeads.remove("station");
				// 表格数据
				tableSb.append(StringUtil.linePage_no_station_day_table);
				StringUtil.HqlUtils(tableSb, "project", project, "=");
				StringUtil.HqlUtils(tableSb, "route", route, "=");
				StringUtil.HqlUtils(tableSb, "day", day, "=");
			} else {
				// 表格数据
				tableSb.append(StringUtil.linePage_station_day_table);
				StringUtil.HqlUtils(tableSb, "project", project, "=");
				StringUtil.HqlUtils(tableSb, "route", route, "=");
				StringUtil.HqlUtils(tableSb, "day", day, "=");
				StringUtil.HqlUtils(tableSb, "station", station, "=");
			}
		}
		// 班别
		else if (SearchMark == 1) {
			if (station == "" || station == null) {
				heads.remove("station");
				tableHeads.remove("station");
				// 表各数据
				tableSb.append(StringUtil.linePage_no_station_shift_table);
				StringUtil.HqlUtils(tableSb, "project", project, "=");
				StringUtil.HqlUtils(tableSb, "route", route, "=");
				StringUtil.HqlUtils(tableSb, "day", day, "=");
				if (!"ALL".equals(shift)) {
					StringUtil.HqlUtils(tableSb, "shift", shift, "=");
				} else {
					StringUtil.HqlUtils(tableSb, "shift", "HOUR", "!=");
				}
			} else {
				// 表格数据
				tableSb.append(StringUtil.linePage_station_shift_table);
				StringUtil.HqlUtils(tableSb, "project", project, "=");
				StringUtil.HqlUtils(tableSb, "route", route, "=");
				StringUtil.HqlUtils(tableSb, "day", day, "=");
				if (!"ALL".equals(shift)) {
					StringUtil.HqlUtils(tableSb, "shift", shift, "=");
				} else {
					StringUtil.HqlUtils(tableSb, "shift", "HOUR", "!=");
				}
				StringUtil.HqlUtils(tableSb, "station", station, "=");
			}
		}
		return getTablesDataJsonObject(tableSb, heads, tableHeads, SearchMark, currentPage);
	}

	public String getStationPageInitialChartsANDtables(String project, String route, String line, String station,
			String day, String shift, String endTime, Integer SearchMark) throws Exception {
		// 有四种情况
		// 图表数据
		// 表格数据
		StringBuffer charsSb = new StringBuffer();
		// 小时
		if (SearchMark == 0) {
			if (line == "" || line == null) {
				// 图表数据
				charsSb.append(StringUtil.stationPage_no_line_charts_shiftANDhour);
				StringUtil.HqlUtils(charsSb, "project", project, "=");
				StringUtil.HqlUtils(charsSb, "route", route, "=");
				StringUtil.HqlUtils(charsSb, "e_day", endTime, "=");
				StringUtil.HqlUtils(charsSb, "shift", shift, "=");
				// charsSb.append(" order by tb.line");

			} else {
				// 图表数据
				charsSb.append(StringUtil.stationPage_line_charts_shiftANDhour);
				StringUtil.HqlUtils(charsSb, "project", project, "=");
				StringUtil.HqlUtils(charsSb, "route", route, "=");
				StringUtil.HqlUtils(charsSb, "e_day", endTime, "=");// 这里的horizontalData需要进行转换一下
				StringUtil.HqlUtils(charsSb, "shift", shift, "=");
				StringUtil.HqlUtils(charsSb, "line", line, "=");// 返回当存在line的时候
			}
		} // 天及以上级别
		else if (SearchMark == 2) {
			if (line == "" || line == null) {
				// 图表数据
				charsSb.append(StringUtil.stationPage_no_line_charts_DAY);
				StringUtil.HqlUtils(charsSb, "project", project, "=");
				StringUtil.HqlUtils(charsSb, "route", route, "=");
				StringUtil.HqlUtils(charsSb, "day", day, "=");
			} else {
				// 图表数据
				charsSb.append(StringUtil.stationPage_line_charts_DAY);
				StringUtil.HqlUtils(charsSb, "project", project, "=");
				StringUtil.HqlUtils(charsSb, "route", route, "=");
				StringUtil.HqlUtils(charsSb, "day", day, "=");
				StringUtil.HqlUtils(charsSb, "line", line, "=");
			}
		}
		// 班别
		else if (SearchMark == 1) {
			if (line == "" || line == null) {
				charsSb.append(StringUtil.stationPage_no_line_charts_shiftANDhour);
				StringUtil.HqlUtils(charsSb, "project", project, "=");
				StringUtil.HqlUtils(charsSb, "route", route, "=");
				StringUtil.HqlUtils(charsSb, "day", day, "=");
				StringUtil.HqlUtils(charsSb, "shift", shift, "=");
			} else {
				// 图表数据
				charsSb.append(StringUtil.stationPage_line_charts_shiftANDhour);
				StringUtil.HqlUtils(charsSb, "project", project, "=");
				StringUtil.HqlUtils(charsSb, "route", route, "=");
				StringUtil.HqlUtils(charsSb, "day", day, "=");
				StringUtil.HqlUtils(charsSb, "shift", shift, "=");
				StringUtil.HqlUtils(charsSb, "line", line, "=");
			}
		}
		return generatindProgram(charsSb.toString(), SearchMark);
	}

	public String getStationPageInitialTables(String project, String route, String line, String station, String day,
			String shift, String endTime, Integer SearchMark, int currentPage) throws Exception {
		// 有四种情况
		// 图表数据
		// 表格数据
		StringBuffer tableSb = new StringBuffer();
		List<String> heads = StringUtil.getHeads();// 需要获取的表格数据
		List<String> tableHeads = StringUtil.getTableHeads();// 返回前端的表头数据
		// 小时
		if (SearchMark == 0) {
			heads = StringUtil.getHourHeads();
			tableHeads = StringUtil.getHourTableHeads();
			heads.remove("day");
			tableHeads.remove("day");
			if (line == "" || line == null) {
				heads.remove("line");
				tableHeads.remove("line");
				// 表格数据
				tableSb.append(StringUtil.stationPage_no_line_hour_table);
				StringUtil.HqlUtils(tableSb, "project", project, "=");
				StringUtil.HqlUtils(tableSb, "route", route, "=");
				StringUtil.HqlUtils(tableSb, "e_day", endTime, "=");
				StringUtil.HqlUtils(tableSb, "shift", "HOUR", "=");
			} else {
				// 表格数据
				tableSb.append(StringUtil.stationPage_line_hour_table);
				StringUtil.HqlUtils(tableSb, "project", project, "=");
				StringUtil.HqlUtils(tableSb, "route", route, "=");
				StringUtil.HqlUtils(tableSb, "e_day", endTime, "=");// 这里的horizontalData需要进行转换一下
				StringUtil.HqlUtils(tableSb, "shift", shift, "=");
				StringUtil.HqlUtils(tableSb, "line", line, "=");// 返回当存在line的时候
			}
		} // 天及以上级别
		else if (SearchMark == 2) {
			heads.remove("shift");
			tableHeads.remove("shift");
			if (line == "" || line == null) {
				heads.remove("line");
				tableHeads.remove("line");
				// 表格数据
				tableSb.append(StringUtil.stationPage_no_line_day_table);
				StringUtil.HqlUtils(tableSb, "project", project, "=");
				StringUtil.HqlUtils(tableSb, "route", route, "=");
				StringUtil.HqlUtils(tableSb, "day", day, "=");
			} else {
				// 表格数据
				tableSb.append(StringUtil.stationPage_line_day_table);
				StringUtil.HqlUtils(tableSb, "project", project, "=");
				StringUtil.HqlUtils(tableSb, "route", route, "=");
				StringUtil.HqlUtils(tableSb, "day", day, "=");
				StringUtil.HqlUtils(tableSb, "line", line, "=");
			}
		}
		// 班别
		else if (SearchMark == 1) {
			if (line == "" || line == null) {
				heads.remove("line");
				tableHeads.remove("line");
				// 表各数据
				tableSb.append(StringUtil.stationPage_no_line_shift_table);
				StringUtil.HqlUtils(tableSb, "project", project, "=");
				StringUtil.HqlUtils(tableSb, "route", route, "=");
				StringUtil.HqlUtils(tableSb, "day", day, "=");
				StringUtil.HqlUtils(tableSb, "shift", shift, "=");
			} else {
				// 表格数据
				tableSb.append(StringUtil.stationPage_line_shift_table);
				StringUtil.HqlUtils(tableSb, "project", project, "=");
				StringUtil.HqlUtils(tableSb, "route", route, "=");
				StringUtil.HqlUtils(tableSb, "day", day, "=");
				StringUtil.HqlUtils(tableSb, "shift", shift, "=");
				StringUtil.HqlUtils(tableSb, "line", line, "=");
			}
		}
		return getTablesDataJsonObject(tableSb, heads, tableHeads, SearchMark, currentPage);
	}

	// =========================TOP页面需要查询的数据
	/**
	 * 
	 * @param project
	 * @param route
	 * @param line
	 * @param station
	 * @param day
	 * @param shift
	 * @param endTime
	 * @param searchMark
	 * @return
	 * @throws Exception
	 */
	// 查询TOP5和机种站位的信息<s>
	public String IssuePageInitialQuery(String project, String route, String line, String station, String day,
			String shift, String endTime, Integer searchMark) throws Exception {
		Top5IssuesData concentrateByRetest = findTopDataMethod(project, route, line, station, day, shift, endTime,
				searchMark, "RETEST_ISSUE", StringUtil.issue_lineAndStation_sql_head_shiftAndHour,
				"GROUP BY stationId ORDER BY COUNT(*) desc LIMIT 0 , 5",
				StringUtil.issue_lineAndStation_sql_head_shiftAndHour_count,true);
		Top5IssuesData concentrateByFail = findTopDataMethod(project, route, line, station, day, shift, endTime,
				searchMark, "FAIL_ISSUE", StringUtil.issue_lineAndStation_sql_head_shiftAndHour,
				"GROUP BY stationId ORDER BY COUNT(*) desc LIMIT 0 , 5",
				StringUtil.issue_lineAndStation_sql_head_shiftAndHour_count,true);
		Top5IssuesData topIssueByFail = findTopDataMethod(project, route, line, station, day, //
				shift, endTime, searchMark, "FAIL_ISSUE", //
				StringUtil.top5issue_lineAndStation_sql_head_shiftAndHour, //
				"  GROUP BY failureSymptoms ORDER BY COUNT(*) desc LIMIT 0 , 5",
				StringUtil.top5issue_lineAndStation_sql_head_shiftAndHour_count,false);
		Top5IssuesData topIssueByRetest = findTopDataMethod(project, route, line, station, day, //
				shift, endTime, searchMark, "RETEST_ISSUE", //
				StringUtil.top5issue_lineAndStation_sql_head_shiftAndHour, //
				"  GROUP BY failureSymptoms ORDER BY COUNT(*) desc LIMIT 0 , 5",
				StringUtil.top5issue_lineAndStation_sql_head_shiftAndHour_count,false);
		JSONObject obj = new JSONObject();
		obj.put("concentrateByRetest", concentrateByRetest);
		obj.put("concentrateByFail", concentrateByFail);
		obj.put("topIssueByFail", topIssueByFail);
		obj.put("topIssueByRetest", topIssueByRetest);
		return obj.toString();
	}
	private Top5IssuesData findTopDataMethod(String project, String route, String line, String station, String day,
			String shift, String endTime, Integer searchMark, String topType, String tableHead, String tabletail,
			String tableCountHead,Boolean flag) throws Exception {
		//获取配置文件
		Properties pros = PropertiesConfig.getProperties("config.properties");
		String alList=pros.getProperty("AL_Line_Station_Data");
		String[] alArray=alList.split(",");
		Boolean alFlag=false;
		for(int i=0;i<alArray.length;i++){
			alFlag=alFlag||(StringUtil.EqualsUtils(alArray[i],station));
		}
		// 查询数据的StringBuffer
		StringBuffer sb = new StringBuffer();
		// 查询数量的StringBuffer
		StringBuffer countsqlday = new StringBuffer();
		// 三种情况的差距其实并不大，只是选择的条件的不同才会导致数据的不同。
		sb.append(tableHead);
		StringUtil.HqlUtils(sb, "tb.project", project, "=");
		StringUtil.HqlUtils(sb, "tb.route", route, "=");
		if(flag){
			if(alFlag){
				StringUtil.HqlUtils(sb, "tb.line", line.replace("AB", "AL"), "=");
			}
			else{
				StringUtil.HqlUtils(sb, "tb.line", line, "=");
			}
		}
		else{
			StringUtil.HqlUtils(sb, "tb.line", line, "=");
		}
		StringUtil.HqlUtils(sb, "tb.station", station, "=");
		// setShiftSelectUtil用来识别shift
		ShiftSelectUtils.setShiftSelectUtil(sb, searchMark, shift);
		StringUtil.HqlUtils(sb, "tb.day", day, "=");
		StringUtil.HqlUtils(sb, "tb.e_day", endTime, "=");
		StringUtil.HqlUtils(sb, "tb.type", topType, "=");
		sb.append(tabletail);
		//
		countsqlday.append(tableCountHead);
		StringUtil.HqlUtils(countsqlday, "tb.project", project, "=");
		StringUtil.HqlUtils(countsqlday, "tb.route", route, "=");
		if(flag){
			if(alFlag){
				StringUtil.HqlUtils(countsqlday, "tb.line", line.replace("AB", "AL"), "=");
			}
			else{
				StringUtil.HqlUtils(countsqlday, "tb.line", line, "=");
			}
		}
		else{
			StringUtil.HqlUtils(countsqlday, "tb.line", line, "=");
		}
		StringUtil.HqlUtils(countsqlday, "tb.station", station, "=");
		ShiftSelectUtils.setShiftSelectUtil(countsqlday, searchMark, shift);
		StringUtil.HqlUtils(countsqlday, "tb.e_day", endTime, "=");
		StringUtil.HqlUtils(countsqlday, "tb.type", topType, "=");
		return new Top5IssuesData(((List<Object[]>) searchDao.findObjectListBySql(sb.toString())),
				(int) searchDao.getCountLimit(countsqlday.toString()));
	}
	public List<Object[]> getRateDataList(String project, String route, String line, String station,
			String startTime, String endTime) {
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT project,route,line,station,day,s_day,e_day,input,retest,fail FROM tetsdatabase_wang.retestsystem_ratedata_tb where 1=1 ");
		StringUtil.HqlUtils(sb, "project", project, "=");
		StringUtil.HqlUtils(sb, "route", route, "=");
		StringUtil.HqlUtils(sb, "line", line, "=");
		StringUtil.HqlUtils(sb, "station", station, "=");
		StringUtil.HqlUtils(sb, "day", startTime, ">=");
		StringUtil.HqlUtils(sb, "day",  endTime, "<=");
		sb.append(" group by day");
		return (List<Object[]>) searchDao.findObjectListBySql(sb.toString());
	}
	public List<Object[]> downLoadByShift(String project, String route, String shift, String line, String station,
			String startTime, String endTime) {
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT project,route,shift,line,station,day,s_day,e_day,input,retest,fail FROM tetsdatabase_wang.retestsystem_ratedata_tb where 1=1 ");
		StringUtil.HqlUtils(sb, "project", project, "=");
		StringUtil.HqlUtils(sb, "route", route, "=");
		if("ALL".equals(shift)){
			StringUtil.HqlUtils(sb, "shift", "Hour", "!=");
		}else
			StringUtil.HqlUtils(sb, "shift",shift, "=");
		StringUtil.HqlUtils(sb, "line", line, "=");
		StringUtil.HqlUtils(sb, "station", station, "=");
		StringUtil.HqlUtils(sb, "day", startTime, ">=");
		StringUtil.HqlUtils(sb, "day",  endTime, "<=");
		return (List<Object[]>) searchDao.findObjectListBySql(sb.toString());
	}
	public List<Object[]> downLoadByHour(String project, String route, String line, String station, String startTime,
			String endTime) {
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT project,route,shift,line,station,day,s_day,e_day,input,retest,fail FROM tetsdatabase_wang.retestsystem_ratedata_tb where 1=1 ");
		StringUtil.HqlUtils(sb, "project", project, "=");
		StringUtil.HqlUtils(sb, "route", route, "=");
		StringUtil.HqlUtils(sb, "line", line, "=");
		StringUtil.HqlUtils(sb, "shift", line, "Hour");
		StringUtil.HqlUtils(sb, "station", station, "=");
		StringUtil.HqlUtils(sb, "startTime", startTime, ">=");
		StringUtil.HqlUtils(sb, "startTime",  endTime, "<=");
		return (List<Object[]>) searchDao.findObjectListBySql(sb.toString());
	}
}
