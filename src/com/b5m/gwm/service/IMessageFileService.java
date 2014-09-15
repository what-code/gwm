package com.b5m.gwm.service;

import java.util.List;

import com.b5m.gwm.model.FileInfo;
import com.b5m.gwm.model.MessageLog;
import com.b5m.gwm.model.SingleImgAndTxt;
import com.b5m.web.core.B5MPageList;
import com.b5m.web.core.IBaseService;

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
public interface IMessageFileService extends IBaseService<SingleImgAndTxt>{
	public boolean saveMessageInfo(SingleImgAndTxt sit);
	public boolean saveMultiMessageInfo(List list);
	public boolean saveFileInfo(FileInfo fileInfo);
	public boolean saveMessageLog(MessageLog log);
	public boolean sendSingleMessage(List<SingleImgAndTxt> list,String mesType,String chnId);
	public SingleImgAndTxt messageDetail(String mid);
	public List getMultiHtml(String pid);
	public String parseBodyToHtml(SingleImgAndTxt sit,String type);
	public B5MPageList<MessageLog> getSendedMsgById(String uid,String pageNo);
	public int getUserSendMesCount(String uid);
	public List<SingleImgAndTxt> previewMulti(String id);
}
