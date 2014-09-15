package com.b5m.gwm.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookies 的工具类
 * 
 */
public class CookieUtils {

	// cookie过期时间
	private static int cookieTime = 30 * 60;

	/**
	 * 获取cookie 值
	 * 
	 * @param name
	 *            cookie 的 name
	 * @param cks
	 *            cookie数组
	 * @return value
	 */
	public static String getCooKieValue(String name, Cookie[] cks) {
		String result = null;
		for (Cookie item : cks) {
			if (item.getName().equals(name)) {
				try {
					result = URLDecoder.decode(item.getValue(), "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		return result;
	}

	/**
	 * 设置Cookie
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cks
	 */
	public static void setCooKieValue(String key, String value, HttpServletResponse response) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(cookieTime);
		response.addCookie(cookie);
	}

	/**
	 * 设置Cookie
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param flag
	 *            是否设置跨二级
	 * @param cks
	 */
	public static void setCooKieValue(String key, String value, boolean flag, HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(cookieTime);
		// 是否设置跨二级
		if (flag) {
			cookie.setPath("/");
			String domain = GwmUtils.getDomain(request.getServerName());
			if (domain != null && domain.length() > 0) {
				cookie.setDomain(domain);
			}
		}
		response.addCookie(cookie);
	}

	/**
	 * 设置Cookie
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cookieTime
	 *            过期时间
	 * @param cks
	 */
	public static void setCooKieValue(String key, String value, HttpServletResponse response, int cookieTime) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(cookieTime);
		response.addCookie(cookie);
	}

}
