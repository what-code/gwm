package com.b5m.gwm.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;

public class HttpClientFactory {

	private final static ThreadLocal<HttpClient> threadLocal = new ThreadLocal<HttpClient>();
	private final static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();

	static {
		connectionManager.getParams().setConnectionTimeout(2000);
		connectionManager.getParams().setSoTimeout(4000);
		// 最大连接主机数
		connectionManager.getParams().setDefaultMaxConnectionsPerHost(2);
		// 最大活动连接数
		connectionManager.getParams().setMaxTotalConnections(10);
	}
	/**
	 * 获得httpCilent 每个请求（request）都会取得同样的httpclient,不同请求会获得不同的httpclient
	 * 
	 * @return
	 */
	public static HttpClient getHttpClient() {
		HttpClient httpClient = (HttpClient) threadLocal.get();
		if (httpClient == null) {
			httpClient = new HttpClient(connectionManager);
			threadLocal.set(httpClient);
		}
		return httpClient;
	}
}