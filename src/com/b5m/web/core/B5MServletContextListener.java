package com.b5m.web.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 第一时间把getServletContext的信息共享出来
 * 
 * @author Larry Lang
 * @email larry.lang@b5m.com
 * @date Jan 14, 2013
 * 
 */
public class B5MServletContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ContextUtils.getInstance()._setContextPath(event.getServletContext().getContextPath());
		ContextUtils.getInstance()._setRootPath(event.getServletContext().getRealPath("/"));
	}

}
