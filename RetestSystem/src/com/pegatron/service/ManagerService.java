package com.pegatron.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pegatron.dao.ManagerDao;
import com.pegatron.pojo.Permission;
import com.pegatron.pojo.Role;
import com.pegatron.pojo.User;
import com.pegatron.utils.StringUtil;

@Service
public class ManagerService {
	@Autowired
	private ManagerDao managerDao;

	public List<User> getAllUserListTable(int currePage, int size) throws Exception {
		String hql = "From User";
		return managerDao.getObjectLimitList(hql, currePage, size);
	}

	public long getCount(String hql, Object... args) throws Exception {
		return managerDao.getCountLimit(hql, args);
	}

	public List<User> getAllRoleListTable(int currePage, int size) throws Exception {
		String hql = "From Role";
		return managerDao.getObjectLimitList(hql, currePage, size);
	}

	public List<Permission> getAllPermissionListTable(int currePage, int size) throws Exception {
		String hql = "From Permission";
		return managerDao.getObjectLimitList(hql, currePage, size);
	}

	public List<Permission> getRolePermissionListTable(String roleid) throws Exception {
		Role role = getRoleListTable(roleid);
		List<Permission> plist = role.getPermissions();
		return plist;
	}

	private Role getRoleListTable(String roleid) throws Exception {
		String hql = "From Role where r_id=?";
		return managerDao.getFirstObject(hql, roleid);
	}

	public String saveNewRole(String role_name, String role_descrition) throws Exception {
		Role role = new Role(Math.abs(role_name.hashCode()) + "", role_name, role_descrition);
		String msg = "";
		try {
			managerDao.saveOrUpdate(role);
			msg = "保存成功";
		} catch (Exception e) {
			msg = e.getMessage().toUpperCase();
		}
		return msg;
	}

	public List<Permission> newPermisssion() throws Exception {
//		String sql = "SELECT * FROM tetsdatabase_wang.RETESTSYSTEM_PERMISSION_TB where PARENT_ID=null;";
//		List<Permission> plist = managerDao.getPermissionListBySql(sql,1);
		String hql="From Permission where parent is null";
		return managerDao.getObjectList(hql);
	}

	public String saveNewPermisssion(String permission_name, String permission_urlPatten, Integer permission_depth,
			String permission_descrition, String permissionParent) {
		Permission parent=new Permission();
		String data="";
		//如果上级权限id不为空
		if(permissionParent!=null&&permissionParent!=""){
			String hql="From Permission where p_id=?";
			try {
				parent=(Permission)managerDao.getFirstObject(hql, permissionParent);
				Permission permission=new Permission(permission_name.hashCode()+"",permission_name,
						permission_urlPatten,permission_descrition,parent,permission_depth);
				parent.getChildren().add(permission);
				managerDao.saveOrUpdate(permission);
				managerDao.saveOrUpdate(parent);
				data="保存成功";
			} catch (Exception e) {
				data=e.getMessage().toUpperCase();
				e.printStackTrace();
			}
		}else{
			Permission permission=new Permission(permission_name.hashCode()+"",permission_name,
					permission_urlPatten,permission_descrition,permission_depth);
			try {
				managerDao.saveOrUpdate(permission);
				data="保存成功";
			} catch (Exception e) {
				data=e.getMessage().toUpperCase();
				e.printStackTrace();
			}
		}
		return data;
	}

	public User checkLoginByWorkIdAndPassword(String workId, String newPassword) throws Exception {
		StringBuffer sb=new StringBuffer("From User where 1=1");
		StringUtil.HqlUtils(sb, "workId", workId,"==");
		StringUtil.HqlUtils(sb, "password", newPassword,"==");
		User currentUser=managerDao.getFirstObject(sb.toString());
		return currentUser;
	}
}
