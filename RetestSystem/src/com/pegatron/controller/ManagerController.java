package com.pegatron.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pegatron.pojo.CurrentUserInfo;
import com.pegatron.pojo.Permission;
import com.pegatron.pojo.Role;
import com.pegatron.pojo.User;
import com.pegatron.service.ManagerService;
import com.pegatron.service.SearchService;
import com.pegatron.utils.PageBean;
import com.pegatron.utils.ResponseUtil;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
@Controller
@RequestMapping(value = "manager")
public class ManagerController {
	private Logger logger=Logger.getLogger(ManagerController.class);
	@Autowired
	private ManagerService managerService;
	@Autowired
	private SearchService searchService;

	// ==========登陆模块
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, //
			HttpServletResponse response, //
			@RequestParam("workId") String workId,//
			@RequestParam("password") String password) throws Exception {//
			HttpSession session=request.getSession();
			String newPassword=Base64.encode(password.getBytes());
			User currentUser=managerService.checkLoginByWorkIdAndPassword(workId,newPassword);
			if(currentUser!=null){
				session.setAttribute("CURRENT_USER", currentUser);
			}
			return "redirect:home.jsp";
	}
	// ==========登出模块
	@RequestMapping(value = "loginOut", method = RequestMethod.POST)
	public String loginOut(HttpServletRequest request, //
			HttpServletResponse response) throws Exception {//
			HttpSession session=request.getSession();
			session.invalidate();
			return "redirect:home.jsp";
	}
	// ==========获取当前用户名模块
		@RequestMapping(value = "getCurrentUser", method = RequestMethod.POST)
		@ResponseBody
		public CurrentUserInfo getCurrentUser(HttpServletRequest request, //
				HttpServletResponse response) throws Exception {//
				HttpSession session=request.getSession();
				User currentUser=(User) session.getAttribute("CURRENT_USER");
				CurrentUserInfo currentUserInfo=null;
				if(currentUser==null){
					currentUserInfo=new CurrentUserInfo(false, "欢迎你：游客");
				}
				else
					currentUserInfo=new CurrentUserInfo(true, "欢迎你："+currentUser.getChinaName());
				return currentUserInfo;
		}
	
	// ==========跳转模块
	// 跳转到day_tables.jsp
	//要获取选择框的数组
	//机种，节点，站位，线体
	@RequestMapping(value = "goDay_tables_jsp", method = { RequestMethod.POST, RequestMethod.GET })
	public String goDay_tables_jsp(HttpServletRequest request) throws Exception {
		List<String> pList=searchService.getProjectList();
		List<String> rList=searchService.getRoute();
		List<String> lList=searchService.getLine();
		List<String> sList=searchService.getStation();
		request.setAttribute("pList", pList);
		request.setAttribute("rList", rList);
		request.setAttribute("lList", lList);
		request.setAttribute("sList", sList);
		logger.info("上线IP："+request.getRemoteAddr());
		return "SearchManager/day_tables";
	}
	// 跳转到shift_tables.jsp
	//要获取选择框的数组
	//机种，节点，站位，线体，班别
	@RequestMapping(value = "goShift_tables_jsp", method = { RequestMethod.POST, RequestMethod.GET })
	public String goShift_tables_jsp(HttpServletRequest request) throws Exception {
		List<String> pList=searchService.getProjectList();
		List<String> rList=searchService.getRoute();
		List<String> lList=searchService.getLine();
		List<String> sList=searchService.getStation();
		request.setAttribute("pList", pList);
		request.setAttribute("rList", rList);
		request.setAttribute("lList", lList);
		request.setAttribute("sList", sList);
		return "SearchManager/shift_tables";
	}

	// 跳转到hour_tables.jsp
	//要获取选择框的数组
	//机种，节点，站位，线体，班别
	@RequestMapping(value = "goHour_tables_jsp", method = { RequestMethod.POST, RequestMethod.GET })
	public String goHour_tables_jsp(HttpServletRequest request) throws Exception {
		List<String> pList=searchService.getProjectList();
		List<String> rList=searchService.getRoute();
		List<String> lList=searchService.getLine();
		List<String> sList=searchService.getStation();
		request.setAttribute("pList", pList);
		request.setAttribute("rList", rList);
		request.setAttribute("lList", lList);
		request.setAttribute("sList", sList);
		return "SearchManager/hour_tables";
	}

	// 跳转到day_lines.jsp
	@RequestMapping(value = "goDayLinesJsp", method = { RequestMethod.POST, RequestMethod.GET })
	public String goDayLinesJsp() throws Exception {
		return "SearchManager/day_lines";
	}

	// 跳转到day_stations.jsp
	@RequestMapping(value = "goDayStationsJsp", method = { RequestMethod.POST, RequestMethod.GET })
	public String goDayStationsJsp() throws Exception {
		return "SearchManager/day_stations";
	}

	// 跳转到day_Issues.jsp
	@RequestMapping(value = "goDayIssuesJsp", method = { RequestMethod.POST, RequestMethod.GET })
	public String goDayIssuesJsp() throws Exception {
		return "SearchManager/day_Issues";
	}

	// 跳转到userManager_tables.jsp
	@RequestMapping(value = "goUserManager_tables_jsp", method = { RequestMethod.POST, RequestMethod.GET })
	public String goUserManager_tables_jsp(HttpServletRequest request,
			@RequestParam(value = "page", required = false) String page) throws Exception {
		int currePage = 1;
		if (page != null||page=="") {
			currePage = Integer.parseInt(page);
		}
		List<User> userList = managerService.getAllUserListTable(currePage, 10);
		String hql="select count(workId) From User";
		long recordCount = managerService.getCount(hql);
		PageBean<User> pageList = new PageBean<User>(currePage, 10, (int) recordCount, userList);
		request.setAttribute("pageList", pageList);
		return "Manager/userManager_tables";
	}
	// 跳转到roleManager_tables.jsp
	@RequestMapping(value = "goRoleManager_tables_jsp", method = { RequestMethod.POST, RequestMethod.GET })
	public String goRoleManager_tables_jsp(HttpServletRequest request,
			@RequestParam(value = "page", required = false) String page) throws Exception {
		int currePage = 1;
		if (page != null||page=="") {
			currePage = Integer.parseInt(page);
		}
		List<User> userList = managerService.getAllRoleListTable(currePage, 10);
		String hql="select count(name) From Role";
		long recordCount = managerService.getCount(hql);
		PageBean<Role> pageList = new PageBean<Role>(currePage, 10, (int) recordCount, userList);
		request.setAttribute("pageList", pageList);
		return "Manager/roleManager_tables";
	}

	// 跳转到permissionManager_tables.jsp
	@RequestMapping(value = "goPermissionManager_tables_jsp", method = { RequestMethod.POST, RequestMethod.GET })
	public String goPermissionManager_tables_jsp(HttpServletRequest request,
			@RequestParam(value = "page", required = false) String page) throws Exception {
		int currePage = 1;
		if (page != null||page=="") {
			currePage = Integer.parseInt(page);
		}
		List<Permission> permissionList = managerService.getAllPermissionListTable(currePage, 10);
		String hql="select count(name) From Permission";
		long recordCount = managerService.getCount(hql);
		PageBean<Permission> pageList = new PageBean<Permission>(currePage, 10, (int) recordCount, permissionList);
		request.setAttribute("pageList", pageList);
		return "Manager/permissionManager_tables";
	}
	// 跳转到permissionManager_tables.jsp
	@RequestMapping(value = "goPermissionList_tables_jsp", method = { RequestMethod.POST, RequestMethod.GET })
	public String goPermissionList_tables_jsp(HttpServletRequest request,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value="roleid") String roleid) throws Exception {
		int currePage = 1;
		if (page != null||page=="") {
			currePage = Integer.parseInt(page);
		}
		List<Permission> pList = managerService.getRolePermissionListTable(roleid);
		List<Permission> permissionList =pList.subList((currePage-1)*10, 10);
		int recordCount=pList.size();
		PageBean<Permission> pageList = new PageBean<Permission>(currePage, 10, (int) recordCount, permissionList);
		request.setAttribute("pageList", pageList);
		return "Manager/permissionManager_tables";
	}

	// 跳转到download_tables.jsp
	@RequestMapping(value = "goDownload_tables_jsp", method = { RequestMethod.POST, RequestMethod.GET })
	public String goDownload_tables_jsp() throws Exception {
		return "OtherManager/download_tables";
	}

	// ==========注册模块
	// ==========修改密码模块
	// ==========添加用户模块
	// ==========添加角色模块
	//saveNewRole
	// 添加角色
		@RequestMapping(value = "saveNewRole", method = { RequestMethod.POST, RequestMethod.GET })
		public  void saveNewRole(
				@RequestParam (value="role_name")String role_name,
				@RequestParam (value="role_descrition")String role_descrition,
				HttpServletResponse response) throws Exception {
			String msg=managerService.saveNewRole(role_name,role_descrition);
			ResponseUtil.responseData(response, msg);
//			return msg;
			
		}
	// ==========添加权限模块
	//showPermissionList 添加权限
		@RequestMapping(value = "showPermissionList", method = { RequestMethod.POST, RequestMethod.GET })
		public  void showPermissionList(
				@RequestParam (value="role_name")String role_name,
				@RequestParam (value="role_descrition")String role_descrition,
				HttpServletResponse response) throws Exception {
			String msg=managerService.saveNewRole(role_name,role_descrition);
			ResponseUtil.responseData(response, msg);
//			return msg;
			
		}
	// ==========设置权限模块
		//newPermisssion添加权限模块
		@RequestMapping(value = "newPermisssion", method = { RequestMethod.POST, RequestMethod.GET })
		public  void newPermisssion(
				HttpServletResponse response) throws Exception {
			List<Permission> plist=managerService.newPermisssion();
			JsonConfig cfg=new JsonConfig();
			cfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		JSONArray jarray=JSONArray.fromObject(plist, cfg);
			ResponseUtil.responseData(response, jarray.toString());
//			return msg;
		}
		//保存权限
		@RequestMapping(value = "saveNewPermisssion", method = { RequestMethod.POST, RequestMethod.GET })
		public void saveNewPermisssion(HttpServletResponse response,
				@RequestParam (value="permission_name" )String permission_name,
				@RequestParam (value="permission_urlPatten") String permission_urlPatten,
				@RequestParam (value="permission_depth") Integer permission_depth,
				@RequestParam (value="permission_descrition" )String permission_descrition,
				@RequestParam (value="permissionParent" )String permissionParent)throws Exception{
			String data=managerService.saveNewPermisssion(permission_name,permission_urlPatten,permission_depth,permission_descrition,permissionParent);
			ResponseUtil.responseData(response, data);
		}
	// ==========设置角色模块
}