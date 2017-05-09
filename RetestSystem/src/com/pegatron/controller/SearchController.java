package com.pegatron.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pegatron.service.SearchService;
import com.pegatron.utils.ExcelUtil;
import com.pegatron.utils.ResponseUtil;

@Controller
@RequestMapping(value = "search")
public class SearchController {
	private Logger logger=Logger.getLogger(SearchController.class);
	@Autowired
	private SearchService searchService;
	/**
	 * 这个版本只有四个页面，1 首页， 2 line页面 ， 3 station页面 ， 4 issues 页面 SeachMark: 0 HOUR 1
	 * shift 2 day 3 week 4 month 5 year
	 */

	// =============查询方式===============
	/**
	 * 通用页面 byday byshift byhour 都可以使用 甚至以后的byMonth byYear都可以使用，只需要
	 * 存放不同的chartsql 和tablesql 即可。 getRateByDay只是一个方面，命名不规范，需改正
	 * -----》（getSelectRateData）
	 * 
	 * @param request
	 * @param response
	 * @param project
	 * @param route
	 * @param line
	 * @param station
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "getRateByDay")
	public void getRateByDay(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("project") String project, @RequestParam("route") String route,
			@RequestParam("line") String line, @RequestParam("station") String station,
			@RequestParam(value = "startTime", required = false) Date startTime,
			@RequestParam(value = "endTime", required = false) Date endTime,
			@RequestParam(value = "shift", required = false) String shift,
			@RequestParam(value = "SearchMark", required = false) Integer SearchMark) throws Exception {
		String data = searchService.getHomeRate(project, route, line, station, startTime, endTime, shift, SearchMark);
		ResponseUtil.responseData(response, data);
	}

	/**
	 * 获取表格的数据（byDay，byShift，ByHour）
	 * @param request
	 * @param response
	 * @param project
	 * @param route
	 * @param line
	 * @param station
	 * @param startTime
	 * @param endTime
	 * @param shift
	 * @param SearchMark
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "getTableData")
	public void getTableData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "project", required = false) String project,
			@RequestParam(value = "route", required = false) String route,
			@RequestParam(value = "line", required = false) String line,
			@RequestParam(value = "station", required = false) String station,
			@RequestParam(value = "startTime", required = false) Date startTime,
			@RequestParam(value = "endTime", required = false) Date endTime,
			@RequestParam(value = "shift", required = false) String shift,
			@RequestParam(value = "SearchMark", required = false) Integer SearchMark,
			@RequestParam(value = "page", required = false) String page) throws Exception {
		int currentPage = 1;
		if (page != null) {
			currentPage = Integer.parseInt(page);
		}
		String data = searchService.getTableData(project, route, line, station, startTime, endTime, shift, currentPage,
				SearchMark);
		ResponseUtil.responseData(response, data);
	}

	// ==========从起始页面到其他的页面==================
	/**
	 * 前往issues页面
	 * 
	 * @param request
	 * @param response
	 * @param project
	 * @param route
	 * @param line
	 * @param station
	 * @param day
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "dayToIssuesPages")
	public String dayToIssuesPages(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "SearchManager/day_Issues";
	}
/**
 * 前往到站位的页面
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	@RequestMapping(value = "dayToStationPages")
	public String dayToStationPages(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "SearchManager/day_stations";
	}

	/**
	 * 前往line页面
	 * 
	 * @param request
	 * @param response
	 * @param project
	 * @param route
	 * @param line
	 * @param station
	 * @param day
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "dayToLinePages")
	public String dayToLinePages(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "SearchManager/day_lines";
	}

	/**
	 * 到了线体，站位，issues页面时，最大的问题是，如何取决于横轴--->小时（2016-12-12 12:00:00）
	 * 班别：（2016-12-12/D） 天（2016-12-12） 月（2016／多少周）年（2016）
	 * 需要一个标记来标记他们是什么选择方式，来决定是什么情况，从而来分解横轴与查询条件
	 */
	// =====================线体页面相关方法==================
	/**
	 * 线体页面加载数据方法 除了正常的几个条件外，还有有两个判断条件，1 shift==hour
	 * 来判断getDayRateList（）和getTableRateList（）的取值 2
	 * 是否是从Station页面传过来的，是则加上station的判断条件getLinePageInitial（）
	 * 
	 * @param request
	 * @param response
	 * @param project
	 * @param route
	 * @param station
	 * @param day
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "linePageInitialChartsAndtable")
	public void linePageInitial(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("project") String project, // 机种
			@RequestParam("route") String route, // 节点
			// @RequestParam("horizontalData") String horizontalData, //横坐标的值
			// 不需要班别的原因是：可以根据SearchMark来判断shift的值
			@RequestParam(value = "station", required = false) String station, // 站位
			@RequestParam(value = "line", required = false) String line, // 站位
			//
			@RequestParam(value = "day", required = false) String day, // 当为班别和天的时候会用到
			@RequestParam(value = "shift", required = false) String shift, // 班别
			@RequestParam(value = "endTime", required = false) String endTime, // 当为小时时需要用到
			//
			@RequestParam(value = "page", required = false) String page, // 页码
			@RequestParam(value = "SearchMark", required = false) Integer SearchMark// 查询标志
	) throws Exception {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		String data = searchService.getLineTableChartsDatas(project, route, line, station, day, shift, endTime,
				SearchMark);
		ResponseUtil.responseData(response, data);
	}
	/**
	 * 获取line页面的表格
	 * @param request
	 * @param response
	 * @param project
	 * @param route
	 * @param line
	 * @param station
	 * @param day
	 * @param shift
	 * @param endTime
	 * @param page
	 * @param SearchMark
	 * @throws Exception
	 */
	// getTableDataByLineTables
	@RequestMapping(value = "getTableDataByLineTables")
	public void getTableDataByLineTables(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("project") String project, // 机种
			@RequestParam("route") String route, // 节点
			// 不需要班别的原因是：可以根据SearchMark来判断shift的值
			@RequestParam(value = "line", required = false) String line, // 站位
			@RequestParam(value = "station", required = false) String station, // 站位
			@RequestParam(value = "day", required = false) String day, // 当为班别和天的时候会用到
			@RequestParam(value = "shift", required = false) String shift, // 班别
			@RequestParam(value = "endTime", required = false) String endTime, // 当为小时时需要用到
			@RequestParam(value = "page", required = false) String page, // 页码
			@RequestParam(value = "SearchMark", required = false) Integer SearchMark// 查询标志
	) throws Exception {
		int currentPage = 1;
		if (page != null) {
			currentPage = Integer.parseInt(page);
		}
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		String data = searchService.getTableDataByLineTables(project, route, line, station, day, shift, endTime,
				SearchMark, currentPage);
		ResponseUtil.responseData(response, data);
	}
/**
 * 获取station页面的图表数据
 * @param request
 * @param response
 * @param project
 * @param route
 * @param line
 * @param station
 * @param day
 * @param shift
 * @param endTime
 * @param page
 * @param SearchMark
 * @throws Exception
 */
	@RequestMapping(value = "stationPageInitialChartsANDtables")
	public void stationPageInitialChartsANDtables(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("project") String project, // 机种
			@RequestParam("route") String route, // 节点
			// 不需要班别的原因是：可以根据SearchMark来判断shift的值
			@RequestParam(value = "line", required = false) String line, // 站位
			@RequestParam(value = "station", required = false) String station, // 站位
			//
			@RequestParam(value = "day", required = false) String day, // 当为班别和天的时候会用到
			@RequestParam(value = "shift", required = false) String shift, // 班别
			@RequestParam(value = "endTime", required = false) String endTime, // 当为小时时需要用到
			//
			@RequestParam(value = "page", required = false) String page, // 页码
			@RequestParam(value = "SearchMark", required = false) Integer SearchMark// 查询标志
	) throws Exception {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		String data = searchService.getStationPageInitialChartsANDtables(project, route, line, station, day, shift,
				endTime, SearchMark);
		ResponseUtil.responseData(response, data);
	}
/**
 * 获取station页面的表格数据
 * @param request
 * @param response
 * @param project
 * @param route
 * @param line
 * @param station
 * @param day
 * @param shift
 * @param endTime
 * @param page
 * @param SearchMark
 * @throws Exception
 */
	// getTableDataByLineTables
	@RequestMapping(value = "stationPageInitialTables")
	public void stationPageInitialTables(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("project") String project, // 机种
			@RequestParam("route") String route, // 节点
			// 不需要班别的原因是：可以根据SearchMark来判断shift的值
			@RequestParam(value = "line", required = false) String line, // 站位
			@RequestParam(value = "station", required = false) String station, // 站位
			@RequestParam(value = "day", required = false) String day, // 当为班别和天的时候会用到
			@RequestParam(value = "shift", required = false) String shift, // 班别
			@RequestParam(value = "endTime", required = false) String endTime, // 当为小时时需要用到
			@RequestParam(value = "page", required = false) String page, // 页码
			@RequestParam(value = "SearchMark", required = false) Integer SearchMark// 查询标志
	) throws Exception {
		int currentPage = 1;
		if (page != null) {
			currentPage = Integer.parseInt(page);
		}
		String data = searchService.getStationPageInitialTables(project, route, line, station, day, shift, endTime,
				SearchMark, currentPage);
		ResponseUtil.responseData(response, data);
	}

	/**
	 * 获取issuePageIntialQuery页面的stationId加对应的count数据
	 * 加上station和line对应的所有stationId的个数
	 * @param request
	 * @param response
	 * @param project
	 * @param route
	 * @param line
	 * @param station
	 * @param day
	 * @param shift
	 * @param endTime
	 * @param SearchMark
	 * @throws Exception
	 *             共有四组数据：1 集中站位（fail和retest）2 top（fail和retest） 需要的数据有project
	 *             ，route，line，station，shift，endTime，SearchMark
	 */
	@RequestMapping(value = "IssuePageInitialQuery",method=RequestMethod.POST)
	public void IssuePageInitialQuery(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("project") String project, // 机种
			@RequestParam("route") String route, // 节点
			// 不需要班别的原因是：可以根据SearchMark来判断shift的值
			@RequestParam(value = "line", required = false) String line, // 站位
			@RequestParam(value = "station", required = false) String station, // 站位
			@RequestParam(value = "day", required = false) String day, // 当为班别和天的时候会用到
			@RequestParam(value = "shift", required = false) String shift, // 班别
			@RequestParam(value = "endTime", required = false) String endTime, // 当为小时时需要用到
			@RequestParam(value = "SearchMark", required = false) Integer SearchMark// 查询标志
	) throws Exception {
		
		String data = searchService.IssuePageInitialQuery(project, route, line, station, day, shift, endTime,
				SearchMark);
		ResponseUtil.responseData(response, data);
	}
	/**
	 * Byday页面对应的下载
	 * @param request
	 * @param reponse
	 * @param project
	 * @param route
	 * @param line
	 * @param station
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 */
	@RequestMapping(value = "/downLoadByDay")
	public void downLoadByDay(HttpServletRequest request, HttpServletResponse reponse,
			@RequestParam("project") String project, // 机种
			@RequestParam("route") String route, // 节点
			@RequestParam(value = "line", required = false) String line, // 站位
			@RequestParam(value = "station", required = false) String station, // 站位
			@RequestParam(value = "startTime", required = false) String startTime, // 当为小时时需要用到
			@RequestParam(value = "endTime", required = false) String endTime // 当为小时时需要用到
			) throws Exception {
		logger.info("下载QA数据的IP："+request.getRemoteAddr());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		List<Object[]> dateList=searchService.getRateDataList(project,route, line, station, startTime, endTime);
		try {
			Workbook wb = ExcelUtil.fillExcelDataWithTemplate(dateList, "/com/pegatron/template/ratedate.xlsx");
			ResponseUtil.export(reponse, wb, "RateData" + sdf.format(new Date()) + ".xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ByShift页面对应的下载
	 * @param request
	 * @param reponse
	 * @param project
	 * @param route
	 * @param shift
	 * @param line
	 * @param station
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 */
		@RequestMapping(value = "/downLoadByShift")
		public void downLoadByShift(HttpServletRequest request, HttpServletResponse reponse,
				@RequestParam("project") String project, // 机种
				@RequestParam("route") String route, // 节点
				@RequestParam(value = "shift", required = false) String shift, // 节点
				@RequestParam(value = "line", required = false) String line, // 站位
				@RequestParam(value = "station", required = false) String station, // 站位
				@RequestParam(value = "startTime", required = false) String startTime, // 当为小时时需要用到
				@RequestParam(value = "endTime", required = false) String endTime // 当为小时时需要用到
				) throws Exception {
			logger.info("下载QA数据的IP："+request.getRemoteAddr());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			List<Object[]> dateList=searchService.downLoadByShift(project,route, shift,line, station, startTime, endTime);
			try {
				Workbook wb = ExcelUtil.fillExcelDataWithTemplateByShift(dateList, "/com/pegatron/template/ratedateByShift.xlsx");
				ResponseUtil.export(reponse, wb, "RateDataByShift" + sdf.format(new Date()) + ".xlsx");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * ByHour页面对应的下载
		 * @param request
		 * @param reponse
		 * @param project
		 * @param route
		 * @param line
		 * @param station
		 * @param startTime
		 * @param endTime
		 * @throws Exception
		 */
		@RequestMapping(value = "/downLoadByHour")
		public void downLoadByHour(HttpServletRequest request, HttpServletResponse reponse,
				@RequestParam("project") String project, // 机种
				@RequestParam("route") String route, // 节点
				@RequestParam(value = "line", required = false) String line, // 站位
				@RequestParam(value = "station", required = false) String station, // 站位
				@RequestParam(value = "startTime", required = false) String startTime, // 当为小时时需要用到
				@RequestParam(value = "endTime", required = false) String endTime // 当为小时时需要用到
				) throws Exception {
			logger.info("下载QA数据的IP："+request.getRemoteAddr());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			List<Object[]> dateList=searchService.downLoadByHour(project,route,line, station, startTime, endTime);
			try {
				Workbook wb = ExcelUtil.fillExcelDataWithTemplateByHour(dateList, "/com/pegatron/template/ratedate.xlsx");
				ResponseUtil.export(reponse, wb, "RateDataByHour" + sdf.format(new Date()) + ".xlsx");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
