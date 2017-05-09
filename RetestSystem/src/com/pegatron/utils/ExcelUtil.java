package com.pegatron.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExcelUtil {
	public static Workbook fillExcelDataWithTemplate(List<Object[]> dateList,
			String templateFileName) throws IOException {
		InputStream inp = ExcelUtil.class.getResourceAsStream(templateFileName);
		XSSFWorkbook wb=new XSSFWorkbook(inp);
		Sheet sheet = wb.getSheetAt(0);
//		int rowIndex=1;
		String[] head={"project","route","line","station","day","s_day","e_day","input","retest","fail"};
		if(dateList.size()>0&&dateList!=null){
			for(int i=0;i<dateList.size();i++){
				Object[] obj=dateList.get(i);
//				JSONObject json=JSONObject.fromObject(obj);
				Row rows=sheet.createRow(i+1);
				for(int j=0;j<head.length;j++){
					Cell cell=rows.createCell(j);
					cell.setCellValue(obj[j].toString());
				}
			}
		}
		return wb;
	}

	public static Workbook fillExcelDataWithTemplateByShift(List<Object[]> dateList, String templateFileName) throws Exception {
		InputStream inp = ExcelUtil.class.getResourceAsStream(templateFileName);
		XSSFWorkbook wb=new XSSFWorkbook(inp);
		Sheet sheet = wb.getSheetAt(0);
		String[] head={"project","route","shift","line","station","day","s_day","e_day","input","retest","fail"};
		if(dateList.size()>0&&dateList!=null){
			for(int i=0;i<dateList.size();i++){
				Object[] obj=dateList.get(i);
				Row rows=sheet.createRow(i+1);
				for(int j=0;j<head.length;j++){
					Cell cell=rows.createCell(j);
					cell.setCellValue(obj[j].toString());
				}
			}
		}
		return wb;
	}

	public static Workbook fillExcelDataWithTemplateByHour(List<Object[]> dateList, String templateFileName) throws Exception {
		InputStream inp = ExcelUtil.class.getResourceAsStream(templateFileName);
		XSSFWorkbook wb=new XSSFWorkbook(inp);
		Sheet sheet = wb.getSheetAt(0);
		String[] head={"project","route","shift","line","station","day","s_day","e_day","input","retest","fail"};
		if(dateList.size()>0&&dateList!=null){
			for(int i=0;i<dateList.size();i++){
				Object[] obj=dateList.get(i);
				Row rows=sheet.createRow(i+1);
				for(int j=0;j<head.length;j++){
					Cell cell=rows.createCell(j);
					cell.setCellValue(obj[j].toString());
				}
			}
		}
		return wb;
	}
}
