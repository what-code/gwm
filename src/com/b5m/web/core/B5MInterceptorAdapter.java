package com.b5m.web.core;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.b5m.gwm.utils.CookieUtils;
import com.b5m.gwm.utils.GwmUtils;
import com.b5m.gwm.utils.XMemCachedUtil;

public class B5MInterceptorAdapter extends HandlerInterceptorAdapter {

	public static Logger logger = Logger.getLogger(B5MInterceptorAdapter.class);

	// 利用正则映射到不需要拦截的路径
	private static List<String> excludeMappingURLs = new ArrayList<String>();

	// 登陆的session 存储的频道ID
	static String USER_SESSION_CHANNEL_ID = "user_session_channel_id";
	// 登陆的session 存储的用户邮箱
	static String USER_SESSION_CHANNEL_MAIL = "user_session_email";
	// 登陆的session 存储的频道名称
	static String USER_SESSION_CHANNEL_NAME = "user_session_name";
	// 登陆的session 存储的审核状态
	static String USER_SESSION_CHECK_STATUS = "user_session_check_status";
	// 登陆的session 存储的邮箱状态
	static String USER_SESSION_MAIL_STATUS = "user_session_mail_status";
	// 登陆的session 存储的封号状态
	static String USER_SESSION_CLOSE_STATUS = "user_session_close_status";
	// 登陆的session 存储的群组id
	static String USER_SESSION_GROUP_IDS = "user_session_group_ids";
	// 登陆的session 存储的登陆状态
	static String USER_SESSION_FLAG = "user_session_login";
	// 登陆的session 存储的用户头像
	static String USER_SESSION_AVATAR = "user_session_avatar";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		ContextUtils.getInstance()._setServlet(request, response);
		if (handler != null && handler instanceof AbstractBaseController) {
			// 注入全部的request和response对象
			((AbstractBaseController) handler)._setServlet(request, response);
		}

		String url = request.getRequestURL().toString();
		// 排除那些不需要拦截url
		for (String excludeMappingURL : excludeMappingURLs) {
			if (url.contains(excludeMappingURL)) {
				return true;
			}
		}

		// cookie
		Cookie[] cks = request.getCookies();
		// HttpSession session = request.getSession();
		// 先后从cookie，chnId和 isLogin 判断 用户 是否登录
		if (null != cks && cks.length > 0) {
			String chnId = CookieUtils.getCooKieValue(Constants.USER_CHANNEL_ID_COOKIE, cks);
			String email = CookieUtils.getCooKieValue(Constants.USER_MAIL_COOKIE, cks);
			String checkStatus = CookieUtils.getCooKieValue(Constants.USER_CHECK_STATUS_COOKIE, cks);
			String mailStatus = CookieUtils.getCooKieValue(Constants.USER_MAIL_STATUS_COOKIE, cks);
			String closeStatus = CookieUtils.getCooKieValue(Constants.USER_CLOSE_STATUS_COOKIE, cks);
			String groupIds = CookieUtils.getCooKieValue(Constants.USER_GROUP_IDS_COOKIE, cks);
			String chnName = CookieUtils.getCooKieValue(Constants.USER_CHANNEL_NAME_COOKIE, cks);
			String avatar = CookieUtils.getCooKieValue(Constants.USER_CHANNEL_AVATAR_COOKIE, cks);
			String isLogin = CookieUtils.getCooKieValue(Constants.USER_CHANNEL_ISLOGIN, cks);

			try {
				if (null != chnId && isLogin != null) {
					if (isLogin.equals(Constants.STATUS_OK)) {
						request.setAttribute(USER_SESSION_CHANNEL_ID, chnId);
						request.setAttribute(USER_SESSION_CHANNEL_MAIL, URLDecoder.decode(email, "utf-8"));
						request.setAttribute(USER_SESSION_CHECK_STATUS, checkStatus);
						request.setAttribute(USER_SESSION_MAIL_STATUS, mailStatus);
						request.setAttribute(USER_SESSION_CLOSE_STATUS, closeStatus);
						request.setAttribute(USER_SESSION_GROUP_IDS, groupIds);
						request.setAttribute(USER_SESSION_CHANNEL_NAME, URLDecoder.decode(chnName, "utf-8"));
						request.setAttribute(USER_SESSION_AVATAR, avatar);
						request.setAttribute(USER_SESSION_FLAG, isLogin);
						logger.info("is set session :chnId----" + chnId + "--email:" + URLDecoder.decode(email, "utf-8") + "--chnName:"
								+ URLDecoder.decode(chnName, "utf-8") + "--avatar:" + avatar + "--isLogin:" + isLogin);
					} else {
						request.setAttribute(USER_SESSION_FLAG, Constants.STATUS_OFF);
					}
				} else {
					request.setAttribute(USER_SESSION_FLAG, Constants.STATUS_OFF);
				}
			} catch (Exception e) {
				logger.info("--------异常---------");
			}
		}

		/*
		 * 以后可扩展权限 String uri=request.getRequestURI(); if(request.getContextPath().length()>0) uri=uri.substring(request.getContextPath().length());
		 * //不希望拦截的URl在此过滤，直接返回即可 if(uri.endsWith("/login.do")) return true;
		 */
		return true;

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (null == modelAndView)
			return;
		// String contentPath = ContextUtils.getInstance().getContextPath();
		// String rootPath = request.getScheme() + "://" + request.getServerName()
		// + (request.getServerPort() != 80 ? ":" + request.getServerPort() : "") + contentPath;
		//
		// modelAndView.addObject("rootPath", rootPath);
		String today = "";
		Object version = XMemCachedUtil.getInstance().getCache("v_version");
		if(version != null){
			today = version.toString();
		}else{
			today = GwmUtils.getFormatDate0();
		}
		request.setAttribute("version", today);
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}

	public void setExcludeMappingURLs(List<String> excludeMappingURLs) {
		B5MInterceptorAdapter.excludeMappingURLs = excludeMappingURLs;
	}
}
