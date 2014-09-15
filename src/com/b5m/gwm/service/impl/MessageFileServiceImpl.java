package com.b5m.gwm.service.impl;

import java.util.*;
import java.io.*;
import javax.annotation.Resource;
import com.b5m.web.core.B5MPageList;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.b5m.gwm.dao.IMessageFileDao;
import com.b5m.gwm.model.FileInfo;
import com.b5m.gwm.model.MessageLog;
import com.b5m.gwm.model.SingleImgAndTxt;
import com.b5m.gwm.service.IMessageFileService;
import com.b5m.gwm.utils.GwmUtils;
import com.b5m.gwm.utils.HttpRequest;
import com.b5m.gwm.utils.XMemCachedUtil;
import com.b5m.tfs.util.TfsImageManager;
import com.b5m.tfs.util.TfsImageManager.ImageBean;
import com.b5m.web.core.AbstractBaseService;
import com.b5m.web.core.B5MPageList;
import com.b5m.web.core.ContextUtils;

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
@Service("messageFileService")
public class MessageFileServiceImpl extends
		AbstractBaseService<SingleImgAndTxt> implements IMessageFileService {
	private static Log logger = LogFactory.getLog(MessageFileServiceImpl.class);

	@Resource
	private IMessageFileDao mfDao;
	
	public @Value("#{configProp[app_api_url]}")
	String appServer;

	@Override
	public boolean saveMessageInfo(SingleImgAndTxt sit) {
		try {
			mfDao.saveMessage(sit);
		} catch (Exception e) {
			logger.info("----saveMessageInfo-error-------->"
					+ GwmUtils.getFormatDate());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean saveFileInfo(FileInfo fileInfo) {
		try {
			mfDao.saveFileInfo(fileInfo);
		} catch (Exception e) {
			logger.info("----saveFileInfo-error-------->"
					+ GwmUtils.getFormatDate());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean saveMessageLog(MessageLog log) {
		try {
			mfDao.saveMessageLog(log);
		} catch (Exception e) {
			logger.info("----saveMessageLog-error-------->"
					+ GwmUtils.getFormatDate());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean sendSingleMessage(List<SingleImgAndTxt> list,
			String mesType, String chnId) {
		String url = appServer + "/channel/article/publish";
		String tempUrl = "";
		Map map = new TreeMap();
		map.put("posterId", chnId);
		map.put("posterType","2");
		map.put("type", mesType);
		for (int i = 1; i < list.size(); i++) {
			SingleImgAndTxt sit = list.get(i);
			tempUrl = ContextUtils.getInstance().getCache("CONTEXT_ROOT_PATH")
					+ "/detail/" + sit.getId() + ".html";
			map.put("contents[" + (i-1) + "].cover", sit.getFaceImg());
			map.put("contents[" + (i-1) + "].title", sit.getTitle());
			map.put("contents[" + (i-1) + "].body", tempUrl);
		}
		String result = HttpRequest.postRequest(url, map);
		// logger.info(("--send result-->" + result));
		JSONObject jsonObject = JSONObject.fromObject(result);
		String succ = jsonObject.get("succ").toString();
		if ("1".equals(succ)) {
			return true;
		}
		return false;
	}

	/**
	 * 生成模板页面
	 */
	@Override
	public SingleImgAndTxt messageDetail(String mid) {
		//SingleImgAndTxt sa = (SingleImgAndTxt) mfDao.getMessageDetail(mid);
		//getDataFromCache(mid,mid,0);
		return (SingleImgAndTxt)getDataFromCache("MES_DETAIL_" + mid,new String[]{mid},0);
	}

	/**
	 * 解析正文的html
	 */
	@Override
	public String parseBodyToHtml(SingleImgAndTxt sit, String type) {
		String version = GwmUtils.getVersion();
		Document doc = Jsoup.parse("");
		String server = ContextUtils.getInstance().getCache("CONTEXT_ROOT_PATH").toString();
		// head-----------s---------
		Element head = doc.getElementsByTag("head").get(0);
		head.append("<link rel=\"stylesheet\" href=\"http://staticcdn.b5m.com/css/gowanmei/gowanmei.css?t=" + version + "\" />");
		head.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		head.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\">");
		head.append("<title>购玩美-图文消息</title>");
		// head------------e--------

		Element body = doc.getElementsByTag("body").get(0);
		Element div0 = body.appendElement("div");
		div0.attr("class", "preview");
		Element div = div0.appendElement("div");
		div.attr("class", "msg-style");

		// title 部分
		Element title = div.appendElement("div");
		//非图片模式 显示标题
		if(!"4".equals(type)){
			title.append("<h3>" + sit.getTitle() + "</h3>");
			title.append("<h6><span>" + sit.getAddTime().substring(0, 16)
					+ "</span><span>"
					+ (sit.getAuthor() == null ? "" : sit.getAuthor())
					+ "</span></h6>");
		}
		
		//图文模式 添加封面图片
		if(("1".equals(type) || "2".equals(type)) && sit.getFaceImg() != null && !"".equals(sit.getFaceImg())){
			title.append("<div class=\"frontcover\"><img src=\"" + sit.getFaceImg() + "\" title=\"封面图片\" /></div>");
		}

		// 正文部分
		Element content = div.appendElement("div");
		content.attr("class", "msg-style-cont");

		// 图文模式
		if (("1".equals(type) || "2".equals(type)) && sit.getMesBody() != null) {
			content.html(sit.getMesBody());
			String temp = sit.getMesBody();
			if (!temp.startsWith("<p>") && temp.indexOf("</p>") != -1) {
				temp = "<p>" + temp.substring(0, temp.indexOf("<p>")) + "</p>"
						+ temp.substring(temp.indexOf("<p>"), temp.length());
			}
			content.html(temp);
		}
		// 文本模式
		if ("3".equals(type)) {
			content.html("<p>" + sit.getTitle() + "</p>");
		}

		// 图片模式
		if ("4".equals(type)) {
			content.html("<p><img src=" + sit.getFaceImg() + "/></p>");
		}
		String result = doc.html();
		return "<!doctype html>\n" + result;
	}

	@Override
	public B5MPageList<MessageLog> getSendedMsgById(String uid,String pageNo) {
		String key = "SENDED_LIST_" + uid + "_" + pageNo;
		return (B5MPageList<MessageLog>)getDataFromCache(key,new String[]{uid,pageNo},1);
		//return mfDao.findSendedMsgById(uid);
	}

	@Override
	public boolean saveMultiMessageInfo(List list) {
		try {
			mfDao.saveMultiMessage(list);
		} catch (Exception e) {
			logger.info("----saveMessageLog-error-------->"
					+ GwmUtils.getFormatDate());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List getMultiHtml(String pid) {
		List<SingleImgAndTxt> list = mfDao.findMultiMesContent(pid);
		return list;
	}

	@Override
	public int getUserSendMesCount(String uid) {
		return mfDao.getUserSendMesCount(uid);
	}
	
	/**
	 * 二级缓存 工具方法
	 * @param key
	 * @param mid
	 * @param type
	 * @return
	 */
	private Object getDataFromCache(String key,String[] args,int type) {
		Object data = XMemCachedUtil.getCache(key);
		boolean flag = false;
		if (data == null) {
			flag = XMemCachedUtil.getInstance().add(XMemCachedUtil.getLockKey(key), "KEY_LOCK");
			if(flag){
				if(type == 0){
					data = mfDao.getMessageDetail(args[0]);
				}else if(type == 1){
					data = mfDao.findSendedMsgById(args[0],Integer.parseInt(args[1]));
				}else if(type == 2){
					data =  mfDao.findMultiMesContent(args[0]);
				}
				XMemCachedUtil.setCache(key, data, XMemCachedUtil.GWM_CACHE_TIME_HOUR);
				XMemCachedUtil.setCache(XMemCachedUtil.getNewKey(key), data, XMemCachedUtil.GWM_CACHE_TIME_24HOUR);
				logger.info("---------------DB01------------->" + key);
			}else{
				if(type == 0){
					data = XMemCachedUtil.getCache(XMemCachedUtil.getNewKey(key));
				}else if(type == 1){
					data = mfDao.findSendedMsgById(args[0],Integer.parseInt(args[1]));
				}else if(type == 2){
					data =  mfDao.findMultiMesContent(args[0]);
				}
				if(data == null){
					logger.info("---------------DB02------------->"+ key);
					data = mfDao.getMessageDetail(args[0]);
					XMemCachedUtil.setCache(key, data, XMemCachedUtil.GWM_CACHE_TIME_HOUR);
					XMemCachedUtil.setCache(XMemCachedUtil.getNewKey(key), data, XMemCachedUtil.GWM_CACHE_TIME_24HOUR);
				}
			}
			
		}
		return data;
	}

	@Override
	public List<SingleImgAndTxt> previewMulti(String id) {
		String key = "PREVIEW_MULTI_" + id;
		List<SingleImgAndTxt> list = (List<SingleImgAndTxt>)getDataFromCache(key,new String[]{id},2);
		return list;
	}
}
