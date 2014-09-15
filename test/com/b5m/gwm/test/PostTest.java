package com.b5m.gwm.test;

import java.util.Map;

import net.sf.json.JSONObject;

import com.b5m.gwm.utils.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Title:PostTest.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-11-28
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Jia Liu
 * 
 * @version 1.0
 */
public class PostTest {

	/**
	 * @param args
	 */
	public static void main1(String[] args) {
		// TODO Auto-generated method stub
		String url = "http://mgwmei.stage.bang5mai.com/api/channel/exist";
		String data = "name=脱光";
		String post = HttpRequest.postRequest(url, data, "application/x-www-form-urlencoded");
		System.out.println(post);
		try {
			JSONObject jsonData = JSONObject.fromObject(post);
			int succ = jsonData.getInt("succ");// 请求是否正确
			if (succ == 1) {
				boolean result = jsonData.getBoolean("result");// 返回结果
				System.out.println(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main2(String[] args) {
		String url = "http://mgwmei.stage.bang5mai.com/api/channel/add";
		String data = "id=ESS12313&jid=123456@qq.com&name=脱光&imgUrl=http://img.b5m.com/image/T1Y9KbB5Lv1RCvBVdK&description=sss";
		String post = HttpRequest.postRequest(url, data, "application/x-www-form-urlencoded");
		System.out.println(post);
		try {
			JSONObject jsonData = JSONObject.fromObject(post);
			// 请求是否正确
			int succ = jsonData.getInt("succ");
			System.out.println(succ);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String url = "http://mgwmei.stage.bang5mai.com/api/channel/get";
		String data = "channelId=GC_3e7e23016ee94dc5ad4045a02d57ccad";
		String post = HttpRequest.postRequest(url, data, "application/x-www-form-urlencoded");
		System.out.println(post);
		try {
			JSONObject jsonData = JSONObject.fromObject(post);
			// 请求是否正确
			int succ = jsonData.getInt("succ");
			if (succ == 1) {
				// 返回结果
				String result = jsonData.getJSONObject("result").toString();
				Gson g = new Gson();
				Map<String, String> map = g.fromJson(result, new TypeToken<Map<String, String>>() {
				}.getType());
				System.out.println(map.get("amount"));

				System.out.println(result);
			} else {
				// 返回错误信息
				String exception = jsonData.getString("exception");
				System.out.println(exception);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
