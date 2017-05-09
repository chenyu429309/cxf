package com.pegatron.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.pegatron.pojo.Permission;


public class PermissionUtils {

	/**
	 * 遍历部门树，把所有的部门遍历出来放到同一个集合中返回，并且其中所有部门的名称都修改了，以表示层次。
	 * 
	 * @param topList
	 * @return
	 */
	public static List<Permission> getAllPermission(Collection<Permission> topList) {
		List<Permission> list = new ArrayList<Permission>();
		walkRoleTreeList(topList, "┣", list);
		return list;
	}
	/**
	 * 遍历部门树，把遍历出的部门信息放到指定的集合中
	 * 
	 * @param topList
	 */
	private static void walkRoleTreeList(Collection<Permission> topList, String prefix, List<Permission> list) {
		if(topList!=null){
		for (Permission top : topList) {
			// 顶点
			Permission copy = new Permission(); // 使用副本，因为原对象在Session中
			copy.setP_id(top.getP_id());
			copy.setName(prefix + top.getName());
			list.add(copy); // 把副本添加到同一个集合中
			// 子树
			walkRoleTreeList(top.getChildren(), "　" + prefix, list); // 使用全角的空格
		}
		}
	}
}
