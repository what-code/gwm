/*
 * [文 件 名]:B5MInitServlet.java
 * [创 建 人]:Wiley
 * [创建时间]:2012-10-9
 * [编　　码]:UTF-8
 * [版　　权]:Copyright © 2012 B5Msoft Co,Ltd. 
 */

package com.b5m.web.core;

import java.util.*;
import java.io.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * [简要描述]:初始化 [详细描述]:初始化
 * 
 * @author [Wiley]
 * @email [wiley.wang@b5m.com]
 * @version [版本号,2012-10-9]
 * @see [B5MInitServlet]
 * @package [com.b5m.plugin]
 * @since [comb5mpluginserver]
 */
public class B5MInitServlet extends HttpServlet {

	private static final long serialVersionUID = -599037181821679325L;
	public static Logger logger = Logger.getLogger(B5MInitServlet.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			super.init(config);
			WebApplicationContext webCtx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext(),
					"org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher");
			ContextUtils.getInstance()._setContextPath(config.getServletContext().getContextPath());
			ContextUtils.getInstance()._setApplicationContext(webCtx);
			ContextUtils.getInstance()._setIntercepter((HandlerInterceptorAdapter) webCtx.getBean("b5mInterceptor"));
			Properties prop = new Properties();
			InputStream in = new FileInputStream(config.getServletContext().getRealPath("/") + "WEB-INF/config.properties");
			prop.load(in);
			ContextUtils.getInstance().setMemcachedOpen(prop.getProperty("memcached.open"));
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(0);
		}
	}
}
