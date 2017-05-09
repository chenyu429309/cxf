package com.pegatron.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

public class ResponseUtil {

	/**
	 * 
	 *  下載文件
	 * @param response 請求
	 * @param filepath 文件路徑
	 * @param outfileName 下載到什麼位置
	 * @param delete 刪除文件
	 * @throws IOException io異常
	 */
	public static void downloadfile(HttpServletResponse response,String filepath,String outfileName, Boolean delete) throws IOException {
		FileInputStream f = new FileInputStream(filepath);
		byte[] fb = new byte[f.available()];//讀取文件f中沒有讀的字節，定義好要用的內存
		f.read(fb); //開始讀文件fb
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(outfileName.getBytes("gb2312"),
						"iso8859-1"));
		ByteArrayInputStream bais = new ByteArrayInputStream(fb);
		//邊讀邊寫
		int b;
		while ((b = bais.read()) != -1) {
			response.getOutputStream().write(b);
		}
		
		response.getOutputStream().flush();//讓輸出瀏刷新，將數據輸出
		f.close();
		if(delete){
			File exeFile = new File(filepath);
			exeFile.delete();
		}
	}
	/**
	 * 將數據傳到視圖層
	 * @param resp 應答
	 * @param data 要返回的數據
	 */
	public final static void responseData(HttpServletResponse resp,String data){
		if(null!=data){
			try {
				resp.setCharacterEncoding("UTF-8");//定義編碼方式為"UTF-8"
				PrintWriter out;//定義一個打印輸出流out
				out=resp.getWriter();//初始化一個打印輸出流out
				out.write(data);//將數據data打印到輸出劉中
				out.flush();//
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
		public static void write(HttpServletResponse response,Object o)throws Exception{
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.print(o.toString());
			out.flush();
			out.close();
		}
	
		
		public static void export(HttpServletResponse response,Workbook wb,String fileName)throws Exception{
			response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("utf-8"),"iso8859-1"));
			response.setContentType("application/ynd.ms-excel;charset=UTF-8");
			OutputStream out=response.getOutputStream();
			wb.write(out);
			out.flush();
			out.close();
		}
		/**
		 * 解碼
		 * @param insString
		 * @return
		 * @throws UnsupportedEncodingException
		 */
		public final static String zhEncodeToDecode(String insString)
	            throws UnsupportedEncodingException {
	    String outString;
	    insString = java.net.URLEncoder.encode(insString, "ISO-8859-1");
	    outString = java.net.URLDecoder.decode(insString, "utf-8");
	    return outString;
	}
}
