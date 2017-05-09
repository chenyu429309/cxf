package com.pegatron.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pegatron.dao.UpOrDownloadDao;
import com.pegatron.pojo.DeviceId;
import com.pegatron.pojo.IssuesData;
import com.pegatron.pojo.RateData;
import com.pegatron.pojo.SetIssuesData;
import com.pegatron.utils.CacheData;
import com.pegatron.utils.PoiExcelUtils;
import com.pegatron.utils.PropertiesConfig;
import com.pegatron.utils.StringUtil;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


@Service
@SuppressWarnings("unused")
public class UpOrDownloadService {
	private Logger logger=Logger.getLogger(UpOrDownloadService.class);
	@Autowired
	private UpOrDownloadDao upOrDownloadDao;
	/**
	 * 上传数据
	 * @param multipartfile
	 * @param request
	 * @throws Exception
	 */
	public void readExcel(MultipartFile multipartfile, HttpServletRequest request) throws Exception {
		Workbook workbook = null;
		Properties pros = PropertiesConfig.getProperties("config.properties");
		String name = multipartfile.getOriginalFilename();// 获取文件名
		InputStream input = multipartfile.getInputStream();// 文件流
		// 读取route
		int rou_index = name.indexOf("_");// 获取route的位置
		String route = name.substring(0, rou_index);
		String endFile = name.substring(name.indexOf(".") + 1);
		if ("xls".equals(endFile)) {
			workbook = new HSSFWorkbook(input);
		} else if ("xlsx".equals(endFile)) {
			workbook = new XSSFWorkbook(input);
		}
		logger.info("上传的文件名："+name);
		// 读取机种
		String projectCell = PoiExcelUtils.getMergedRegionValue(workbook.getSheetAt(0), 0, 0).toString().trim();
		int _indexProject = projectCell.indexOf(" ");
		String project = projectCell.substring(0, _indexProject);
		logger.info("上传的机种名："+name);
		// 读取cut_time
		String cut_time = PoiExcelUtils.getMergedRegionValue(workbook.getSheetAt(0), 1, 6).toString().trim();
		int _first = cut_time.indexOf(":");
		int _second = cut_time.indexOf("~");
		String _sDate = cut_time.substring(_first + 1, _second).replaceAll("/", "-");
		String _eDate = cut_time.substring(_second + 1).replaceAll("/", "-");
		String _date_ = "";
		// shift是D/N/Hour
		String shift = "";
		if (pros.getProperty("_sDate").equals(_sDate.substring(11))
				&& pros.getProperty("_eDate").equals(_eDate.substring(11))) {
			shift = RateData.SHIFT_D;
			_date_ = _sDate.substring(0, _sDate.indexOf(" ")).replaceAll("/", "-");
		} else if (pros.getProperty("_sDate").equals(_eDate.substring(11))
				&& pros.getProperty("_eDate").equals(_sDate.substring(11))) {
			shift = RateData.SHIFT_N;
			_date_ = _eDate.substring(0, _sDate.indexOf(" ")).replaceAll("/", "-");
		} else {
			if (pros.getProperty("_sDate").equals(_sDate.substring(11)))
				_date_ = _eDate.substring(0, _eDate.indexOf(" ")).replaceAll("/", "-");
			else if (pros.getProperty("_eDate").equals(_sDate.substring(11)))
				_date_ = _sDate.substring(0, _sDate.indexOf(" ")).replaceAll("/", "-");

			shift = RateData.SHIFT_HOUR;
		}
		// 读取第一个Excel
		String[] args0 = { "line", "station", "input", "fail", "retest" };
		// 第一个是workbook，第二个是第几行，第三个是多少行，第四个是从第几列开始读，第五个是从少读几列
		List<Map<String, Object>> rateDataMaps = PoiExcelUtils.readRateDataExcel(workbook, 0, 4, 10, 7, 0, args0);
		System.out.println(rateDataMaps);
		// 读取第三个Excel
		String[] args2 = { "Station", "FailureSymptoms", "Unit", "Line" };
		List<Map<String, Object>> failureSymptomsMaps = PoiExcelUtils.readExcel(workbook, 2, 1, 0, 0, 3, args2);
		System.out.println(failureSymptomsMaps);
		// 读取第四个Excel
		String[] args3 = { "Station", "FailureSymptoms", "Unit", "Line" };
		List<Map<String, Object>> retestSymptomsMaps = PoiExcelUtils.readExcel(workbook, 3, 1, 0, 0, 8, args3);
		System.out.println(retestSymptomsMaps);
		Set<RateData> rateDataList = getRateDataByExcel(project, route, _sDate, _eDate, _date_, rateDataMaps, shift);
		SetIssuesData failureobj = getIssuesDataByExcel(project, route, _sDate, _eDate, _date_, failureSymptomsMaps,
				IssuesData.TYPE_FAIL, shift);
		SetIssuesData retestobj = getIssuesDataByExcel(project, route, _sDate, _eDate, _date_, retestSymptomsMaps,
				IssuesData.TYPE_RETEST, shift);
		// 轉換成對應的類
		List<IssuesData> list = new ArrayList<IssuesData>();
		list.addAll(list);
		upOrDownloadDao.saveOrUpdateList(failureobj.getList());
		upOrDownloadDao.saveOrUpdateList(retestobj.getList());
		upOrDownloadDao.saveOrUpdateList(rateDataList);
		// 將獲取到的sn放到配置文件中
		List<Object[]> set = new ArrayList<Object[]>();
		set.addAll(failureobj.getSetList());
		set.addAll(retestobj.getSetList());
		saveRateData(set);
	}
/**
 * 将查询到的数据放进shiftDataTable和towHourDataTable中
 * 便于工具访问查找对应的device
 * 检查传进来的set与shiftDataTable的数据进行比较，发现不重复的放进
 	到towHourDataTable中
 * @param set
 */
	private void saveRateData(List<Object[]> set) {
		// 1 现在数据库中查找缓存数据，再将本次的存入到数据库
		String sql = "SELECT * FROM shiftDataTable";
		List<Object[]> sets = (List<Object[]>) upOrDownloadDao.findObjectListBySql(sql);
		if(sets.size()>0){
			set.removeAll(sets);
		}
		Iterator<Object[]> iterator = set.iterator();
		while (iterator.hasNext()) {
			Object[] s = iterator.next();
			String  sql1="SELECT * FROM shiftDataTable where id='"+s[0] +"'";
			String  sql2="SELECT * FROM towHourDataTable where id='"+s[0] +"'";
			List<Object[]> sets1= (List<Object[]>) upOrDownloadDao.findObjectListBySql(sql1);
			List<Object[]> sets2= (List<Object[]>) upOrDownloadDao.findObjectListBySql(sql2);
			if(sets1.size()==0){
			String inserttowHoursql= "insert into shiftDataTable(id,sn,station) values('" + s[0] + "','" +s[1] + "','"
					+ s[2] + "')";
			upOrDownloadDao.insertCacheDataList(inserttowHoursql);
			}
			if(sets1.size()==0){
			String insertshiftsql= "insert into towHourDataTable(id,sn,station) values('" + s[0] + "','" + s[1] + "','"
					+ s[2] + "')";
			upOrDownloadDao.insertCacheDataList(insertshiftsql);
			}
		}

	}

	/**
	 * 将读取的map转换成对应的RateData数据
	 * 这里可以用jsonObject来替代
	 * @param rateDataMaps
	 * @param project
	 * @param route
	 * @param shift
	 * @param _sDate
	 * @param _date_
	 * @param _eDate
	 * @return
	 * @throws Exception
	 */
	private List<RateData> getRateDateList(List<Map<String, Object>> rateDataMaps, String project, String route,
			String shift, String _sDate, String _date_, String _eDate) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<RateData> list = new ArrayList<RateData>();
		for (Map<String, Object> map : rateDataMaps) {
			String line = map.get("line").toString();
			String station = map.get("station").toString();
			double input = Double.parseDouble(map.get("input").toString());
			double retest = Double.parseDouble(map.get("retest").toString());
			double fail = Double.parseDouble(map.get("fail").toString());
			String id = Base64.encode((project + route + shift + _eDate + line + station).getBytes());
			RateData rateData = new RateData(id, project, route, line, station, sdf.parse(_date_), shift,
					sdf1.parse(_sDate), sdf.parse(_date_), input, retest, fail);
			if (rateData != null) {
				list.add(rateData);
			}
		}
		return list;
	}

	// 将捞来的数据变成对象 第二 三 个Excel
	private SetIssuesData getIssuesDataByExcel(String project, String route, String _sDate, String _eDate,
			String _date_, List<Map<String, Object>> failureSymptomsMaps, String typeFail, String shift)
					throws Exception {
		List<IssuesData> rateData = new ArrayList<IssuesData>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		ArrayList<Object[]> set = new ArrayList<Object[]>();
		for (Map<String, Object> map : failureSymptomsMaps) {
			String id = Base64.encode(
					(project + route + shift + map.get("Unit").toString() + map.get("FailureSymptoms").toString()
							+ map.get("Line").toString() + map.get("Station").toString()).getBytes());
			IssuesData issuesdata = new IssuesData(id, project, route, shift, map.get("Line").toString(),
					map.get("Station").toString(), map.get("Unit").toString(), sdf.parse(_date_), sdf1.parse(_sDate),
					sdf1.parse(_eDate), map.get("FailureSymptoms").toString(), typeFail);
			rateData.add(issuesdata);
			Object[] obj=new Object[]{Base64.encode((map.get("Unit").toString() + map.get("Station").toString()).getBytes()),map.get("Unit").toString(), map.get("Station").toString()};
			set.add(obj);
		}
		SetIssuesData obj = new SetIssuesData();
		obj.setList(rateData);
		obj.setSetList(set);
		return obj;
	}

	// 将set保存到文件中
	private void saveSnToFile(Set<String> set) throws Exception {
		FileSystemView fsv = FileSystemView.getFileSystemView();
		File home = fsv.getHomeDirectory();
		String filePath = home.getAbsolutePath() + "/Desktop/AutoQCR/config/grp.txt";
		String filePath1 = home.getAbsolutePath() + "/Desktop/AutoQCR/config/out1.txt";
		Set<String> snSet = getSnSet(home.getAbsolutePath());
		if (snSet != null) {
			set.removeAll(snSet);
		}
		// 保存最新的
		saveFile(set, filePath);
		// 可以在以前的起出基礎上，繼續添加的
		saveFile1(set, filePath1);
	}

	// 不可以连续保存文件的读取工具
	private void saveFile(Set<String> set, String filePath) {
		File outFile = new File(filePath);// 写出的CSV文件
		outFile.getParentFile().mkdirs();
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
			Iterator<String> ite = set.iterator();
			while (ite.hasNext()) {
				String str = (String) ite.next();
				String[] map = str.split("-");
				writer.write(map[0] + "," + map[1]);
				writer.newLine();
			}
			writer.flush();
			writer.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			System.out.println("没找到文件！");
		} catch (IOException ex) {
			System.out.println("读写文件出错！");
		}
	}

	// 可以连续保存文件的读取工具
	private void saveFile1(Set<String> set, String filePath) {
		File outFile = new File(filePath);// 写出的CSV文件
		outFile.getParentFile().mkdirs();
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(outFile, true));
			Iterator<String> ite = set.iterator();
			int len = set.size();
			int index_ = 1;
			while (ite.hasNext()) {
				String str = (String) ite.next();
				String[] map = str.split("-");
				writer.write(map[0] + "," + map[1]);
				if (index_ < len) {
					writer.newLine();
					index_++;
				}
			}
			writer.flush();
			writer.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			System.out.println("没找到文件！");
		} catch (IOException ex) {
			System.out.println("读写文件出错！");
		}
	}

	// 读取配置文件的properties的sn内容
	private Set<String> getSnSet(String homepath) throws Exception {
		Set<String> set = new HashSet<String>();
		Properties pros = PropertiesConfig.getProperties("config.properties");
		String str = homepath + "/Desktop/AutoQCR/config/" + pros.get("SnFileName").toString();
		File inFile = new File(str);
		if (inFile.exists()) {
			BufferedReader reader = new BufferedReader(new FileReader(inFile));
			String s = "";
			try {
				while ((s = reader.readLine()) != null) {
					String[] args = s.toString().split(",");
					String sss = args[0] + "-" + args[1];
					set.add(sss);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			set = null;
		}
		return set;
	}

	// 将捞来的数据变成对象 第一个Excel
	private Set<RateData> getRateDataByExcel(String project, String route, String _sDate, String _eDate, String _date_,
			List<Map<String, Object>> rateDataMaps, String shift) throws Exception {
		Set<RateData> rateData = new HashSet<RateData>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (Map<String, Object> map : rateDataMaps) {
			String id = Base64.encode(
					(project + route + _eDate + map.get("line").toString() + map.get("station").toString()).getBytes());
//			String id=UUID.randomUUID().toString();
			System.out.println(id);
			RateData rd = new RateData(id, project, route, map.get("line").toString(), map.get("station").toString(),
					sdf.parse(_date_), shift, sdf1.parse(_sDate), sdf1.parse(_eDate),
					Double.parseDouble(map.get("input").toString()), Double.parseDouble(map.get("retest").toString()),
					Double.parseDouble(map.get("fail").toString()));
			if (map.get("line").toString().startsWith("AL") && ((map.get("station").toString().contains("DFU NAND Init")
					|| map.get("station").toString().contains("SOC")
					|| map.get("station").toString().contains("FCT")))) {
				rateData.add(rd);
			}
			if (map.get("line").toString().startsWith("AB") && ((map.get("station").toString().contains("CELL S1")
					|| map.get("station").toString().contains("CCT")
					|| map.get("station").toString().contains("CELL S2")
					|| map.get("station").toString().contains("WiFi/BT W1")
					|| map.get("station").toString().contains("WIFI/BT W2")
					|| map.get("station").toString().contains("SCOND")
					|| map.get("station").toString().contains("SMT-QT")))) {
				rateData.add(rd);
			}
		}
		return rateData;
	}

	// 维护deviceId 和小站位之间的关系
	public void readDeviceExcel(MultipartFile multipartfile) throws Exception {
		Workbook workbook = null;
		String name = multipartfile.getOriginalFilename();// 获取文件名
		InputStream input = multipartfile.getInputStream();// 文件流
		// 读取route
		String endFile = name.substring(name.indexOf(".") + 1);
		if ("xls".equals(endFile)) {
			workbook = new HSSFWorkbook(input);
		} else if ("xlsx".equals(endFile)) {
			workbook = new XSSFWorkbook(input);
		}
		String[] args0 = { "project", "smallStation", "deviceId" };
		// 第一个是workbook，第二个是第几行，第三个是多少行，第四个是从第几列开始读，第五个是从少读几列
		List<Map<String, Object>> deviceDataMaps = PoiExcelUtils.readExcel(workbook, 0, 1, 0, 0, 0, args0);
		List<DeviceId> deviceList = saveDeviceId(deviceDataMaps);
		upOrDownloadDao.saveOrUpdateList(deviceList);

	}

	private List<DeviceId> saveDeviceId(List<Map<String, Object>> deviceDataMaps) {
		List<DeviceId> deviceList = new ArrayList<DeviceId>();
		for (Map<String, Object> map : deviceDataMaps) {
			DeviceId device = new DeviceId(map.get("deviceId").toString(), map.get("smallStation").toString(),
					map.get("project").toString());
			deviceList.add(device);
		}
		return deviceList;
	}

//	public void uploadFlieToExcel(String project, String line, String day, String e_day, String s_day) {
//		StringBuffer sb=new StringBuffer("select * from retestsystem_ratedata_tb where 1=1");
//		StringUtil.HqlUtils(sb, "project", project, "=");
//		List<>
//	}

}
