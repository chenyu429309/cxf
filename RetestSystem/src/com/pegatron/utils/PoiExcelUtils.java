package com.pegatron.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class PoiExcelUtils {
	/**
	 * 
	 * @param wb
	 * @param sheetCount
	 *            sheet页下标,从0开始
	 * @param startReadLine
	 *            开始读取的行
	 * @param tailLine
	 *            去除最后读取的行
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static List<Map<String, Object>> readExcel(Workbook wb, int sheetCount, int startReadLine, int tailLine,
			int startReadcolumn, int tailColumn, String... args) throws InstantiationException, IllegalAccessException {
		Sheet sheet = wb.getSheetAt(sheetCount);
		Row row = null;
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
//		Boolean isMergeRegion = false;
		for (int i = startReadLine; i < sheet.getPhysicalNumberOfRows(); i++) {
			// 从指定起始行到指定结束行读取数据
			row = sheet.getRow(i);
			Map<String, Object> maps = new HashMap<String, Object>();// 利用map对象来存Excel的内容
			for (int c = startReadcolumn; c < args.length; c++) {
				// 这里可以从起始列开始读到制定的结束列，也可以根据类参数的个数来判断列数
				Object obj = new Object();
				Cell cell = row.getCell(c);
//				isMergeRegion = isMergedRegion(sheet, i, cell.getColumnIndex());// 判断是否有合并单元格
//				if (isMergeRegion) {// 如果有，则获取合并单元格的值
//					obj = getMergedRegionValue(sheet, i, cell.getColumnIndex());
//				} else {
					obj = getValue(cell);
//				}
				maps.put(args[c-startReadcolumn], obj);// 以map的形式将属性名与属性值存贮
			}
			mapList.add(maps);
			System.out.println(maps.toString());
		}
		return mapList;
	}

	// 只针对第一个excel
	public static List<Map<String, Object>> readRateDataExcel(Workbook workbook, int sheetCount, int startReadLine,
			int tailLine, int startReadcolumn, int tailColumn, String[] args) {
		Sheet sheet = workbook.getSheetAt(sheetCount);
		Object obj = new Object();
		Row row = null;
		Cell cell = null;
		Boolean isMergeRegion = false;
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (int i = startReadLine; i < tailLine + startReadLine; i++) {
			// 从指定起始行到指定结束行读取数据
			row = sheet.getRow(i);
			Map<String, Object> maps = null;// 利用map对象来存Excel的内容
			for (int _index = 0, count = 0; _index < (sheet.getRow(3).getPhysicalNumberOfCells() - tailColumn
					- startReadcolumn) / 6
					&& count < sheet.getRow(3).getPhysicalNumberOfCells() - tailColumn
							- startReadcolumn; _index++, count = count + 6) {
				maps = new HashMap<String, Object>();
				cell = sheet.getRow(2).getCell(7 + count);
				obj = getMergedRegionValue(sheet, 2, cell.getColumnIndex());
				maps.put("line", obj);
				// station
				cell = row.getCell(0);
				obj = getValue(cell);
				maps.put("station", obj);
				for (int c = 7 + count; c <= (10 + count); c++) {
					// line
					if (c == 7 + count) {
						cell = row.getCell(c);
						isMergeRegion = isMergedRegion(sheet, i, cell.getColumnIndex());// 判断是否有合并单元格
						if (isMergeRegion) {// 如果有，则获取合并单元格的值
							obj = getMergedRegionValue(sheet, i, cell.getColumnIndex());
						} else {
							obj = getValue(cell);
						}
						maps.put("input", obj);// 以map的形式将属性名与属性值存贮
					}
					if (c == 9 + count) {
						cell = row.getCell(c);
						isMergeRegion = isMergedRegion(sheet, i, cell.getColumnIndex());// 判断是否有合并单元格
						if (isMergeRegion) {// 如果有，则获取合并单元格的值
							obj = getMergedRegionValue(sheet, i, cell.getColumnIndex());
						} else {
							obj = getValue(cell);
						}
						maps.put("fail", obj);
					}
					if (c == 10 + count) {
						cell = row.getCell(c);
						isMergeRegion = isMergedRegion(sheet, i, cell.getColumnIndex());// 判断是否有合并单元格
						if (isMergeRegion) {// 如果有，则获取合并单元格的值
							obj = getMergedRegionValue(sheet, i, cell.getColumnIndex());
						} else {
							obj = getValue(cell);
						}
						maps.put("retest", obj);
					}
					// 这里可以从起始列开始读到制定的结束列，也可以根据类参数的个数来判断列数
				}
				System.out.println(maps.toString());
				mapList.add(maps);
			}
		}
		return mapList;
	}

	/**
	 * 获取合并单元格的值 当row和col在合并单元格范围内时，就会获取到合并单元格的值；
	 * 
	 * @param sheet
	 * @param row
	 * @param col
	 * @return
	 */
	public static Object getMergedRegionValue(Sheet sheet, int row, int col) {
		int mergedCount = sheet.getNumMergedRegions();// 获取合并单元格的数量
		for (int i = 0; i < mergedCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);// 获取合并单元格
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();
			if (row >= firstRow && row <= lastRow) {//
				if (col >= firstColumn && col <= lastColumn) {
					Row rows = sheet.getRow(firstRow);
					Cell cells = rows.getCell(firstColumn);
					return getValue(cells);
				}
			}
		}
		return null;
	}

	/**
	 * 获取单元格的值 有四种类型CELL_TYPE_STRING CELL_TYPE_BOOLEAN CELL_TYPE_FORMULA
	 * CELL_TYPE_NUMERIC
	 * 
	 * @param cells
	 * @return
	 */
	private static Object getValue(Cell cells) {
		if (cells == null) {
			return "";
		}
		if (cells.getCellType() == Cell.CELL_TYPE_STRING) {// 单元格的内容为String类型的
			return cells.getStringCellValue();
		} else if (cells.getCellType() == Cell.CELL_TYPE_BOOLEAN) {// 单元格的内容为Boolean类型的
			return cells.getBooleanCellValue();
		} else if (cells.getCellType() == Cell.CELL_TYPE_FORMULA) {// 单元格的内容为formula类型的
			return cells.getCellFormula();
		} else if (cells.getCellType() == Cell.CELL_TYPE_NUMERIC) {// 单元格的内容为numeric类型的
			if (cells.getCellStyle().getDataFormatString().indexOf("%") > -1) {
				return cells.getNumericCellValue() * 100;
			} else if (HSSFDateUtil.isCellDateFormatted(cells)) {
				return cells.getDateCellValue();
			}
			return cells.getNumericCellValue();
		} else if (cells.getCellType() == Cell.CELL_TYPE_BLANK) {// 单元格的内容为blank类型的
			return cells.getNumericCellValue();
		}
		return "";
	}

	/**
	 * 是否为合并单元格
	 * 
	 * @param sheet
	 * @param row
	 * @param col
	 * @return
	 */
	public static Boolean isMergedRegion(Sheet sheet, int row, int col) {
		int mergedCount = sheet.getNumMergedRegions();// 获取合并单元格的数量
		for (int i = 0; i < mergedCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);// 获取合并单元格
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();
			if (row >= firstRow && row <= lastRow) {//
				if (col >= firstColumn && col <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

}
