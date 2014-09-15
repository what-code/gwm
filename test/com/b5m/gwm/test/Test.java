package com.b5m.gwm.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

import com.b5m.gwm.utils.MailUtils;
import com.google.code.yanf4j.core.CodecFactory.Encoder;

/**
 * Title:Test.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-10-29
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Jia Liu
 * 
 * @version 1.0
 */
public class Test {

	private File upload;
	// 反射，得到文件类型，文件名称
	private String uploadContentType;
	private String uploadFileName;

	public static String doAddPhoPic() {
		File upload = new File("/home/jia-liu/图片/未命名.jpg");
		String file_path1 = "";
		try {
			System.out.println(upload.length());
			// 上传至该项目所在本地目录
			file_path1 = "/home/jia-liu/workspace/safsd.jpg";
			System.out.println("---" + file_path1);
			// 对文件进行写操作
			FileOutputStream fos1 = new FileOutputStream(file_path1);
			// 对文件进行读操作
			FileInputStream fis = new FileInputStream(upload);
			byte[] buffer = new byte[1024];
			int len = 0;
			// 读入流，保存至byte数组
			while ((len = fis.read(buffer)) > 0) {
				fos1.write(buffer, 0, len);
			}
			fos1.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "chenggong";
	}

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new sun.misc.BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new sun.misc.BASE64Encoder()).encodeBuffer(key);
	}

	private static sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
	private static sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();

	public static String encode(String s) {
		return encoder.encode(s.getBytes());
	}

	public static String decode(String s) {
		try {
			byte[] temp = decoder.decodeBuffer(s);
			return new String(temp);
		} catch (IOException ioe) {
			// handler
		}
		return s;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// String s = "7dQe8yeSi595xrb0NS@lv2";
		// System.out.println(s.length());
		// System.out.println(doAddPhoPic());
		// String mailContent =
		// "你好!<br/><br/>感谢你注册购玩美频道运营平台。 你的登录邮箱为：sabrina_0123@163.com。请点击以下链接激活帐号： <br/><br/><a href=\"https://mp.weixin.qq.com/cgi-bin/activateemail?email=c2FicmluYV8wMTIzQDE2My5jb20%3D&ticket=dfce7606855c8ffe602068351da550c81292584b\" target=\"_blank\">https://mp.weixin.qq.com/cgi-bin/activateemail?email=c2FicmluYV8wMTIzQDE2My5jb20%3D&ticket=dfce7606855c8ffe602068351da550c81292584b<a><br/><br/>如果以上链接无法点击，请将上面的地址复制到你的浏览器(如IE)的地址栏进入购玩美频道运营平台。<br/><br/><br/><br/><br/>购玩美团队上";
		//
		// MailUtils.sendHtmlEmail("xingzhesun@b5m.com", "购玩美频道运营平台激活邮件", mailContent);
		
		System.out.println(JSONObject.fromString("{src:\"http://img.b5m.com/image/T1Y9KbB5Lv1RCvBVdK\"}").get("src"));
//		try {
//			String s = encode("sabrina_0123@163.com");
//			System.out.println(s);
//			System.out.println(decode(s));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

}
