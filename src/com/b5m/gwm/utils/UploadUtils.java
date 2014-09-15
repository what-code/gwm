package com.b5m.gwm.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.lang.RandomStringUtils;

import com.b5m.tfs.util.TfsImageManager;
import com.b5m.tfs.util.TfsImageManager.ImageBean;

/**
 * Title:UploadUtils.java
 * 
 * Description: 上传工具类
 * 
 * Copyright: Copyright (c) 2013-11-25
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Jia Liu
 * 
 * @version 1.0
 */
public class UploadUtils {

	// 服务器保存路径
	private static String serverPath = "/home/jia-liu/图片/未命名.jpg";

	// 保存以后的文件名
	private static String fileName = RandomStringUtils.randomAlphanumeric(8);

	/**
	 * 保存上传图片
	 * 
	 * @return
	 */
	public static String saveUploadPic(String filePath) {
		File upload = new File(filePath);
		String serverFile = serverPath + fileName;
		try {
			System.out.println(upload.length());
			// 上传至该项目所在本地目录
			serverFile = "/home/jia-liu/workspace/safsd.jpg";
			System.out.println("---" + serverFile);
			// 对文件进行写操作
			FileOutputStream fos1 = new FileOutputStream(serverFile);
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
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String projectName = "gwmweb";
		String url = "/home/jia-liu/图片/未命名.jpg";
		try {
			File file = new File(url);
			System.out.println(file.length());
			// 对文件进行读操作
			FileInputStream fis = new FileInputStream(file);
			int filelong = fis.available();
			byte[] long_buf = new byte[filelong];
			fis.read(long_buf);
			System.out.println(filelong);
			fis.close();

			ImageBean ib = TfsImageManager.save(long_buf, projectName);
			System.out.println("http://img.b5m.com/image/" + ib.getImageName());
			// TfsImageManager.delete(ib.getImageName());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println(getSuffix("/home/jia-liu/workspace/safsd.jpg"));
	}
}
