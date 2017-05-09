package com.pegatron.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Test {
	private final static String getDayOfWeek(Date day,int i,int w){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar c=Calendar.getInstance(Locale.CHINA);
		c.setFirstDayOfWeek(Calendar.MONDAY); //以周1为首日
		c.setTimeInMillis(day.getTime());//当前时间
		c.add(Calendar.WEEK_OF_YEAR, i);
		c.set(Calendar.DAY_OF_WEEK, w);
		return sdf.format(c.getTime());
	}
	public static void main(String[] args) throws ParseException {
	System.out.println(getDayOfWeek(new Date(),1,3));
//		List<Permission> list=new ArrayList<Permission>(); 
//		List<Permission> list1=new ArrayList<Permission>(); 
//		Permission p=new Permission();
//		Permission p1=new Permission();
//		Permission p2=new Permission();
//		Permission p3=new Permission();
//		Permission p4=new Permission();
//		p.setP_id("123");
//		p.setName("123");
//		p1.setP_id("1323");
//		p1.setName("1323");
//		p2.setP_id("1333");
//		p2.setName("1333");
//		p3.setP_id("14443");
//		p3.setName("14443");
//		p4.setP_id("15553");
//		p4.setName("15553");
//		list1.add(p4);
//		list1.add(p3);
//		list1.add(p2);
//		p.setChildren(list1);
//		p1.setChildren(list1);
//		list.add(p);
//		list.add(p1);
//		List<Permission> m=PermissionUtils.getAllPermission(list);
//		for(Permission ppp:m){
//			System.out.println(ppp);
//		}
//		
//		System.out.println();
		
//		System.out.println(1/0.0);
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");	
//		Date d1=sdf.parse("2016-12-12");
//		Date d2=sdf.parse("2016-12-5");
//		Calendar d=Calendar.getInstance();
//		Calendar dd=Calendar.getInstance();
//		
//	d.setTime(d2);
//	d.add(d.DAY_OF_YEAR+10, Calendar.DAY_OF_YEAR);;
//	dd.setTime(d1);
//	System.out.println("d:"+d.getTime());
//	System.out.println(d.getTime());
//	System.out.println(d1.getTime());
//	System.out.println(dd.DAY_OF_YEAR);
//	System.out.println(d.DAY_OF_YEAR-dd.DAY_OF_YEAR);
	
//		System.out.println(d2.getTime()-d1.getTime());
//		System.out.println(1000*60*60*24*7);
//		Double a=100.0;
//		System.out.println(a.intValue());
//		double s=1.1;
//		int c=(int) s;
//		System.out.println(c);
	}
}
