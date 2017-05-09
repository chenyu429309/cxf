package com.pegatron.utils;

public class ShiftSelectUtils {
		public static void setShiftSelectUtil(StringBuffer sb,Integer searchMark,String shift){
			switch(searchMark){
			case 0: 
				StringUtil.HqlUtils(sb, "shift", "HOUR", "!=");
				break;
			case 1: 
				if (!"ALL".equals(shift)) {
					StringUtil.HqlUtils(sb, "shift", shift, "=");
				} else {
					StringUtil.HqlUtils(sb, "shift", "HOUR", "!=");
				}
				break;
			case 2: 
				break;
			case 3: 
				break;
			default: break;
			
			}
		}
}
