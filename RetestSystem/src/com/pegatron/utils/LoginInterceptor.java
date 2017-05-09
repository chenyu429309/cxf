package com.pegatron.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pegatron.pojo.User;


public class LoginInterceptor extends HandlerInterceptorAdapter {
	private Logger logger=Logger.getLogger(LoginInterceptor.class);
	private static final String[] NOT_IGNORE_URL ={"/goPermissionManager_tables_jsp","/goRoleManager_tables_jsp","/goUserManager_tables_jsp","/goDownload_tables_jsp"};
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String requesturl=request.getRequestURI();
		User currentUser=(User) request.getSession().getAttribute("CURRENT_USER");
		if(currentUser!=null){
			if(requesturl.contains("/loginOut")){
				logger.info(currentUser.getChinaName()+"下线了；IP地址为："+request.getRemoteAddr());
			}
			if(requesturl.contains("/login")){
				logger.info(currentUser.getChinaName()+"上线了；IP地址为："+request.getRemoteAddr());
			}
			return true;
		}
		else{
			//URL
			for(String str:NOT_IGNORE_URL){
				if(requesturl.contains(str)){
					logger.info("你没有权限，请登陆！");
					request.getRequestDispatcher("/errorpage/400.jsp").forward(request, response);
					return false;
				}
			}
				return true;
		}
//		System.out.println("[LoginInterceptor] preHandle");
//		System.out.println(request.getContextPath());
//		System.out.println(request.getRequestURI());
//		System.out.println(request.getLocalPort());
//		System.out.println(request.getRealPath("/"));
//		System.out.println(request.getLocalAddr());
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("[LoginInterceptor] postHandle");
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("[LoginInterceptor] afterCompletion");
		super.afterCompletion(request, response, handler, ex);
	}
}
