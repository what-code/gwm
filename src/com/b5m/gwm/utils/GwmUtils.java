package com.b5m.gwm.utils;

import ij.ImagePlus;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.*;

import com.b5m.gwm.model.*;
import com.b5m.tfs.util.TfsImageManager;
import com.b5m.tfs.util.TfsImageManager.ImageBean;
import com.izenesoft.sf1r.util.HttpClientFactory;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/*import com.drew.imaging.jpeg.JpegMetadataReader;
 import com.drew.imaging.jpeg.JpegProcessingException;
 import com.drew.metadata.Directory;
 import com.drew.metadata.Metadata;
 import com.drew.metadata.Tag;*/

/**
 * Title:Test.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-10-30
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
public class GwmUtils {
	/**
	 * 获取uuid
	 * 
	 * @return
	 */
	public static String getUuid() {
		String s = UUID.randomUUID().toString();
		return s.replaceAll("-", "");
	}

	/**
	 * 获取页面参数
	 * 
	 * @param resuest
	 * @param name
	 * @return
	 */
	public static String getParamsValueFromPage(HttpServletRequest resuest, String name) {
		return resuest.getParameter(name);
	}

	/**
	 * 获取格式化的日期字符串
	 * 
	 * @return
	 */
	public static String getFormatDate() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	/**
	 * 获取格式化的日期字符串
	 * 
	 * @return
	 */
	public static String getFormatDate0() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}

	/**
	 * 获取胆单图文的内容
	 * 
	 * @param request
	 * @return
	 */
	public static SingleImgAndTxt getSitObject(HttpServletRequest request) {
		SingleImgAndTxt sit = new SingleImgAndTxt();
		String mid = GwmUtils.getUuid();
		String title = GwmUtils.getParamsValueFromPage(request, "mes_title");
		String author = GwmUtils.getParamsValueFromPage(request, "mes_author");
		String faceImg = GwmUtils.getParamsValueFromPage(request, "face_img");
		String mesAbstract = GwmUtils.getParamsValueFromPage(request, "mes_abstract");
		String body = GwmUtils.getParamsValueFromPage(request, "mes_body");
		String userEmail = (String) request.getAttribute("user_session_channel_id");

		sit.setId(mid);
		sit.setTitle(title);
		sit.setAuthor(author);
		sit.setFaceImg(faceImg);
		sit.setMesAbstract(mesAbstract);
		sit.setMesBody(body);
		sit.setAddUser(userEmail);
		sit.setAddTime(getFormatDate());
		sit.setMesType("0");
		return sit;
	}

	/**
	 * 获取上传文件的信息
	 * 
	 * @param file
	 * @param fileType
	 * @param cdnUrl
	 * @return
	 */
	public static FileInfo getFileInfoObject(MultipartFile file, String fileType, String cdnUrl, HttpServletRequest request) {
		String userEmail = (String) request.getAttribute("user_session_channel_id");
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileName(file.getOriginalFilename());
		fileInfo.setFileType(fileType);
		fileInfo.setFileStatus("0");
		fileInfo.setFileSysName(getUuid() + "." + fileType);
		fileInfo.setFileSize(file.getSize() / 1024 + "");
		fileInfo.setUploadTime(getFormatDate());
		fileInfo.setUploadUser(userEmail);
		fileInfo.setFileCdnUrl(cdnUrl);
		return fileInfo;
	}

	/**
	 * 发送消息的记录
	 * 
	 * @param sit
	 * @param tid
	 * @return
	 */
	public static MessageLog getMessageLogInfo(SingleImgAndTxt sit, String tid, HttpServletRequest request) {
		MessageLog log = new MessageLog();
		String userEmail = (String) request.getAttribute("user_session_channel_id");
		String userName = (String) request.getAttribute("user_session_name");

		log.setMesId(sit.getId());
		log.setMesName(sit.getTitle());
		log.setMesSendTime(getFormatDate());
		log.setMesSex("-1");
		log.setMesStatus("1");
		log.setMesTarget(tid);
		log.setSendUserId(userEmail);
		log.setSendUserName(userName);
		log.setFaceImg(sit.getFaceImg());
		return log;
	}

	/**
	 * 向cdn上传文件
	 * 
	 * @param is
	 * @param server
	 * @param filePath
	 */
	public static void uploadFileToCdn(InputStream is, String server, String filePath) {
		HttpClient httpClient = HttpClientFactory.getHttpClient();
		FileOutputStream os = null;
		HttpMethod method = new GetMethod(server);
		try {
			httpClient.executeMethod(method);
			is = method.getResponseBodyAsStream();
			os = new FileOutputStream(new File(filePath));
			byte[] bytes = new byte[1024];
			int off = 0;
			while ((off = is.read(bytes)) != -1) {
				os.write(bytes, 0, off);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 把json转换为list
	 * 
	 * @param json
	 * @return
	 */
	public static List<SingleImgAndTxt> getMultiObjectFromJson(String json, String userEmail) {
		SingleImgAndTxt sit = null;
		JSONArray js = new JSONArray(json);
		String fid = GwmUtils.getUuid();
		List<SingleImgAndTxt> list = new ArrayList<SingleImgAndTxt>();
		sit = new SingleImgAndTxt();
		sit.setId(fid);
		// 将父级的title设置为第一个图文的title
		JSONObject obj01 = JSONObject.fromObject(js.get(0).toString());
		sit.setTitle(obj01.getString("title"));
		sit.setAddUser(userEmail);
		sit.setAddTime(GwmUtils.getFormatDate());
		sit.setFaceImg(obj01.get("faceImg") + "");
		// 2 表示多图文
		sit.setMesType("2");
		list.add(sit);

		for (int i = 0; i < js.length(); i++) {
			JSONObject obj = JSONObject.fromObject(js.get(i).toString());
			sit = new SingleImgAndTxt();
			sit.setId(GwmUtils.getUuid());
			sit.setMesParent(fid);
			sit.setTitle(obj.get("title") + "");
			sit.setAuthor(obj.get("author") + "");
			sit.setFaceImg(obj.get("faceImg") + "");
			sit.setAddUser(userEmail);
			sit.setAddTime(GwmUtils.getFormatDate());
			sit.setMesAbstract(obj.get("abst") + "");
			sit.setMesBody(obj.get("body") + "");
			// 1 表示单图文
			sit.setMesType("1");
			list.add(sit);
		}
		return list;
	}

	/**
	 * 向TFS上传图片
	 * 
	 * @param bytes
	 * @return
	 */
	public static ImageBean uploadFileToTFS(byte[] bytes) {
		String projectName = "gwmweb";
		try {
			ImageBean ib = TfsImageManager.save(bytes, projectName);
			return ib;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 重新编码百度控件上传来的文件
	 * 
	 * @param path
	 * @return
	 */
	public static String createImage(String path, boolean force) {
		// boolean flag = false;
		File file = new File(path);
		/*
		 * try { System.out.println(excmd(file)); } catch (Exception e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
		 */
		String fileType = path.substring(path.lastIndexOf("."));
		String newFileName = file.getParent() + "/" + System.currentTimeMillis() + RandomStringUtils.randomNumeric(6) + fileType;
		FileOutputStream fos = null;
		try {
			BufferedImage image = ImageIO.read(file);
			int height = image.getHeight();
			int width = image.getWidth();
			// 经过百度处理的图片，重新进行处理；否则，原图返回不做处理
			if (height == 900 || width == 900 || force) {
				System.out.println("image---" + image.getHeight() + "---" + image.getWidth());
				fos = new FileOutputStream(newFileName);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
				encoder.encode(image);
				bos.close();
				return newFileName;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return path;
	}

	public static String changeImageToJpg(String path) {
		File file = new File(path);
		String fileType = path.substring(path.lastIndexOf("."));
		String newFileName = file.getParent() + "/" + System.currentTimeMillis() + RandomStringUtils.randomNumeric(6) + fileType;
		ImagePlus ip = new ImagePlus(path);
		BufferedImage bi = ip.getBufferedImage();

		/*
		 * BufferedImage bi1 = null; try { bi1 = ImageUtil.abscut(new FileInputStream(path), 0, 0, 0, 0); } catch (FileNotFoundException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

		// 方式1
		/*
		 * SdImage si=new SdImage(bi); try { si.saveTo("jpg", new File(newFileName)); } catch (IOException e) { e.printStackTrace(); }
		 */

		// 方式2
		FileOutputStream fos = null;
		try {
			System.out.println("image---" + bi.getHeight() + "---" + bi.getWidth());
			fos = new FileOutputStream(newFileName);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(bi);
			bos.close();
			return newFileName;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return newFileName;
	}

	public static void deleteTempFile(String path) {
		new File(path).delete();
	}

	public static String getVersion() {
		String today = "";
		Object version = XMemCachedUtil.getInstance().getCache("v_version");
		if (version != null) {
			today = version.toString();
		} else {
			today = GwmUtils.getFormatDate0();
		}
		return today;
	}

	public static String getDomain(String hostName) {
		if (isLocal(hostName)) {
			return "";
		} else if (hostName.indexOf(".bang5mai.com") > -1) {
			return ".bang5mai.com";
		} else if (hostName.indexOf(".gwmei.com") > -1) {
			return ".gwmei.com";
		} else {
			return hostName;
		}
	}

	private static boolean isLocal(String hostName) {
		if (StringUtils.isBlank(hostName)) {
			return true;
		}
		return hostName.startsWith("127.0.0.1") || hostName.startsWith("localhost");
	}

	/*
	 * private static String excmd(File file) throws JpegProcessingException, IOException {
	 * 
	 * Metadata metadata = JpegMetadataReader.readMetadata(file); Iterator<Directory> it = metadata.getDirectories().iterator(); StringBuffer sb = new
	 * StringBuffer(); while (it.hasNext()) { Directory exif = it.next(); Collection<Tag> cot = exif.getTags(); sb.append(cot.toString());
	 * sb.append("\n"); } return sb.toString(); }
	 */

	public static void main(String[] args) {
		deleteTempFile("/home/gsj/doc/test0002.jpg");
	}
}
