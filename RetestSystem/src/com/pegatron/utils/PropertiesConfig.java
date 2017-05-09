package com.pegatron.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.pegatron.service.UpOrDownloadService;

public class PropertiesConfig {
	private static Properties pros = new Properties();

	public static Properties getProperties(String fileName) {
		// 读取配置文件
		// 获取配置文件流
		InputStream in = UpOrDownloadService.class.getClassLoader().getResourceAsStream(fileName);
		try {
			// 加载文件流
			pros.load(in);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return pros;
	}
	// public static String getConfig(String str){
	// return getProperties().get(str).toString();
	// }
}
