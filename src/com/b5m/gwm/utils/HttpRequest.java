package com.b5m.gwm.utils;

import java.util.*;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import java.util.Map;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

public class HttpRequest {

	public static Logger logger = Logger.getLogger(HttpRequest.class);

	public static String postRequest(String url, Map<String, String> params) {
		String result = null;
		HttpClient httpClient = HttpClientFactory.getHttpClient();
		PostMethod method = new PostMethod(url);
		logger.info("----" + url);
		try {
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			// method.setRequestEntity(new StringRequestEntity(data, "application/json", "UTF-8"));
			int i = 0;
			Set<String> set = params.keySet();
			NameValuePair[] data = new NameValuePair[set.size()];
			for (String key : set) {
				String value = params.get(key);
				data[i] = new NameValuePair(key, value);
				i++;
			}
			method.setRequestBody(data);
			int statusCode = httpClient.executeMethod(method);
			if (statusCode == HttpStatus.SC_OK) {
				result = method.getResponseBodyAsString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static String postRequest(String url, String data) {
		return postRequest(url, data, "application/json");
	}

	public static String postRequest(String url, String data, String encode) {
		String result = null;
		HttpClient httpClient = HttpClientFactory.getHttpClient();
		PostMethod method = new PostMethod(url);
		logger.info("----" + url + "---" + data);
		try {
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			method.setRequestEntity(new StringRequestEntity(data, encode, "UTF-8"));
			int statusCode = httpClient.executeMethod(method);
			if (statusCode == HttpStatus.SC_OK) {
				result = method.getResponseBodyAsString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static String getRequest(String url, Map<String, String> params) {
		String response = null;
		HttpClient httpClient = HttpClientFactory.getHttpClient();

		// append parameters
		StringBuilder sb = new StringBuilder(url);
		if (params != null) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
			}
		}

		GetMethod method = new GetMethod(sb.toString());
		try {
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

			int statusCode = httpClient.executeMethod(method);
			if (statusCode == HttpStatus.SC_OK) {
				response = method.getResponseBodyAsString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
