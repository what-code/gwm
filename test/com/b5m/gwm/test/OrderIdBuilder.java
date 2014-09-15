package com.b5m.gwm.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * Title:OrderIdBuilder.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-11-11
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Jia Liu
 * 
 * @version 1.0
 */
public class OrderIdBuilder {

	private static final String ID_PREFIX = "EC_";

	public static String orderId() {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
		int ran = (int) (Math.random() * 10000);
		int s = (int) ((date.getTime() - ran) / 2);
		String orderId = ID_PREFIX + format.format(date) + s;
		return orderId;
	}

	public static void main(String[] args) {
		String s = UUID.randomUUID().toString();
		String[] sp = s.split("-");
		String uuid = s.substring(0, s.indexOf('-'));
		System.out.println(s);
		System.out.println(uuid.toUpperCase());
		System.out.println((sp[0] + sp[1]).toUpperCase());
		System.out.println(orderId());
	}
}
