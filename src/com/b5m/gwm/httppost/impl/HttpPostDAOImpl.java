package com.b5m.gwm.httppost.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.b5m.gwm.httppost.IHttpPostDAO;
import com.b5m.gwm.params.ParamsDTO;
import com.b5m.gwm.utils.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Title:Test.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-11-22
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Jia Liu
 * 
 * @version 1.0
 */
@Repository
public class HttpPostDAOImpl implements IHttpPostDAO, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7646801006338248872L;

	public static Logger logger = Logger.getLogger(HttpPostDAOImpl.class);

	// app接口URL
	public @Value("#{configProp[app_api_url]}")
	String appApiUrl;

	@Override
	public boolean isChnName(String name) {
		String url = appApiUrl + "/channel/exist";
		String data = "name=" + name;
		String post = HttpRequest.postRequest(url, data, "application/x-www-form-urlencoded");
		// logger.info("请求返回json对象---------：" + post);

		return isResult(post);
	}

	@Override
	public Map<String, String> saveChannel(ParamsDTO dto) {
		String url = appApiUrl + "/channel/add";
		String data = "id=" + dto.getChnId() + "&jid=" + dto.getEmail() + "&name=" + dto.getChnName() + "&imgUrl=" + dto.getAvatar()
				+ "&description=" + dto.getContent();
		String post = HttpRequest.postRequest(url, data, "application/x-www-form-urlencoded");
		// logger.info("请求返回json对象---------：" + post);
		// 请求参数返回空时。
		if (post == null) {
			return null;
		}
		return mapResult(post);
	}

	@Override
	public Map<String, String> getChannel(String chnId) {
		String url = appApiUrl + "/channel/get";
		String data = "channelId=" + chnId;
		String post = HttpRequest.postRequest(url, data, "application/x-www-form-urlencoded");
		// logger.info("请求返回json对象---------：" + post);
		// 请求参数返回空时。
		if (post == null) {
			return null;
		}
		return mapResult(post);
	}

	@Override
	public Map<String, String> getChannelByMail(String mail) {
		String url = appApiUrl + "/channel/get";
		String data = "jid=" + mail;
		String post = HttpRequest.postRequest(url, data, "application/x-www-form-urlencoded");
		// logger.info("请求返回json对象---------：" + post);
		// 请求参数返回空时。
		if (post == null) {
			return null;
		}
		return mapResult(post);
	}

	@Override
	public boolean updateChannel(ParamsDTO dto) {
		String url = appApiUrl + "/channel/update";
		String data = "channelId=" + dto.getChnId() + "&imgUrl=" + dto.getAvatar() + "&description=" + dto.getContent();
		String post = HttpRequest.postRequest(url, data, "application/x-www-form-urlencoded");
		// logger.info("请求返回json对象---------：" + post);

		return isResult(post);
	}

	/**
	 * 返回boolean类型
	 * 
	 * @param post
	 * @return
	 */
	private boolean isResult(String post) {
		try {
			JSONObject jsonData = JSONObject.fromObject(post);
			// 请求是否正确
			int succ = jsonData.getInt("succ");
			logger.info("请求是否正确1、正确，0、错误---------：" + succ);
			if (succ == 1) {
				// 返回结果
				boolean result = jsonData.getBoolean("result");
				logger.info("返回结果-----true被占用-----：" + result);
				return result;
			} else {
				// 返回错误信息
				String exception = jsonData.getString("exception");
				logger.info("返回错误信息---------：" + exception);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("json转换异常---------");
			return false;
		}
	}

	/**
	 * 返回map集合，结果集数据
	 * 
	 * @param post
	 * @return
	 */
	private Map<String, String> mapResult(String post) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			JSONObject jsonData = JSONObject.fromObject(post);
			// 请求是否正确
			int succ = jsonData.getInt("succ");
			logger.info("请求是否正确1、正确，0、错误---------：" + succ);
			if (succ == 1) {
				// 返回结果
				String result = jsonData.getString("result");
				logger.info("返回结果---------：" + result);
				if (result == null) {
					return map;
				}
				Gson g = new Gson();
				Map<String, String> fromJson = g.fromJson(result, new TypeToken<Map<String, String>>() {
				}.getType());
				if (fromJson == null) {
					fromJson = new HashMap<String, String>();
				}
				// 清空集合
				map.clear();
				// 结果put进集合
				map.putAll(fromJson);
				return map;
			} else {
				// 返回错误信息
				String exception = jsonData.getString("exception");
				logger.info("返回错误信息---------：" + exception);
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("json转换异常---------");
			return map;
		}
	}

}
