package com.b5m.gwm.utils;

import java.io.IOException;
import sun.misc.*;

/**
 * Title:BASE64.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-11-25
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Jia Liu
 * 
 * @version 1.0
 */
public class BASE64 {
	private static BASE64Encoder encoder = new BASE64Encoder();
	private static BASE64Decoder decoder = new BASE64Decoder();

	/**
	 * 加密
	 * 
	 * @param s
	 * @return
	 */
	public static String encode(String s) {
		return encoder.encode(s.getBytes());
	}

	/**
	 * 解密
	 * 
	 * @param s
	 * @return
	 */
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
		try {
			String s = encode("sabrina_0123@163.com");
			System.out.println(s);
			System.out.println(decode(s));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
