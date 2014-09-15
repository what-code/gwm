/**
 * AppServerFilter.java
 *
 * 功  能：web过滤器
 * 类  名：AppServerFilter
 *
 *   ver     变更日       公司          作者              变更内容
 * ────────────────────────────────────
 *   V1.00   '12-05-21     iZENEsoft		wiley.wang       初版
 *
 * Copyright (c) 2009 iZENEsoft Business Software corporation All Rights Reserved.
 * LICENSE INFORMATION
 */
package com.b5m.web.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * web过滤器
 * 
 * @author wiley.wang
 * @version V1.0 2012-05-21
 */
public class B5MFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String uri = request.getRequestURI();

		if (uri.endsWith(".jsp")) {
			try {
				response.setContentType(Constants.HTML_CONTENT_TYPE);
				B5MInterceptorAdapter b5mInterceptor = ContextUtils.getInstance().getBean(com.b5m.web.core.B5MInterceptorAdapter.class);
				if (b5mInterceptor != null) {
					boolean isCheck = b5mInterceptor.preHandle(request, response, null);
					if (!isCheck)
						return;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// // 从request里获取登陆状态
			// String isLogin = (String) req.getAttribute("user_session_login");
			// 获取路径
			String contentPath = ContextUtils.getInstance().getContextPath();
			String rootPath = request.getScheme() + "://" + request.getServerName()
					+ (request.getServerPort() != 80 ? ":" + request.getServerPort() : "") + contentPath;
			req.setAttribute("rootPath", rootPath);
			// 段誉需要（暂时留着）
			ContextUtils.getInstance().setCache("CONTEXT_ROOT_PATH", rootPath);
			// if (!requestURI.equals("/index.html")) {
			// if (isLogin == null || Constants.STATUS_OFF.equals(isLogin)) {
			// // 未登陆状态跳index页面
			// response.sendRedirect(request.getContextPath());
			// return;
			// }
			// }
		}

		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig cfg) throws ServletException {
	}

}
