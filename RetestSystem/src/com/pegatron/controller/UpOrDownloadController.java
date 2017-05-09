package com.pegatron.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pegatron.service.UpOrDownloadService;
import com.pegatron.utils.ResponseUtil;

@Controller
@RequestMapping(value = "upOrDownload")
public class UpOrDownloadController {
	private Logger logger=Logger.getLogger(UpOrDownloadController.class);
	@Autowired
	private UpOrDownloadService upOrDownloadService;
/**
 * 上传QA数据
 * @param request
 * @param resp
 * @param file
 */
	@RequestMapping(value = "uploadRateData")
	public void uploadRateData(HttpServletRequest request,HttpServletResponse resp, @RequestParam(value = "file") MultipartFile file) {
		// System.out.println(file.get(0).getOriginalFilename());
		// Map<String,String> map=new HashMap<String,String>();
		// map.put("name", file.getOriginalFilename());
		long startdate=System.currentTimeMillis();
		logger.info("上传QA数据的IP："+request.getRemoteAddr());
		String data = "";
		try {
			upOrDownloadService.readExcel(file,request);
			data = "success";
			logger.info("上传QA数据--耗时："+(System.currentTimeMillis()-startdate)/60000+"分钟");
		} catch (Exception e) {
			data = e.getMessage();
		}
		ResponseUtil.responseData(resp, data);

	}
	// devicefile
/**
 * 上传device与机种和stationId之间的关系
 * @param request
 * @param resp
 * @param file
 */
	@RequestMapping(value = "devicefileuploadRateData")
	public void devicefileuploadRateData(HttpServletRequest request,HttpServletResponse resp,
			@RequestParam(value = "file") MultipartFile file) {
		// System.out.println(file.get(0).getOriginalFilename());
		// Map<String,String> map=new HashMap<String,String>();
		// map.put("name", file.getOriginalFilename());
		long startdate=System.currentTimeMillis();
		logger.info("上传device与机种和stationId之间的关系的IP："+request.getRemoteAddr());
		String data = "";
		try {
			upOrDownloadService.readDeviceExcel(file);
			data = "success";
			logger.info("上传device与机种和stationId之间的关系数据--耗时："+(System.currentTimeMillis()-startdate)/60000+"分钟");
		} catch (Exception e) {
			data = e.getMessage();
		}
		ResponseUtil.responseData(resp, data);

	}
	//下载模块
//	@RequestMapping(value = "exportRateData")
//	public void exportRateData(HttpServletRequest request,HttpServletResponse resp,
//			@RequestParam("project") String project,
//			@RequestParam("line") String line,
//			@RequestParam("day") String day,
//			@RequestParam("shift") String shift,
//			@RequestParam("e_day") String e_day,
//			@RequestParam("s_day") String s_day
//			) {
//	
//		String data = "";
//		try {
//			upOrDownloadService.uploadFlieToExcel(project,line,day,e_day,s_day);
//			data = "success";
//		} catch (Exception e) {
//			data = e.getMessage();
//		}
//		ResponseUtil.responseData(resp, data);
//
//	}
	
	
	
}
