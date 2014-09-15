package com.b5m.gwm.dao;

import java.util.List;

import com.b5m.gwm.model.FileInfo;
import com.b5m.gwm.model.MessageLog;
import com.b5m.gwm.model.SingleImgAndTxt;
import com.b5m.web.core.B5MPageList;
import com.b5m.web.core.IBaseDao;

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
public interface IMessageFileDao extends IBaseDao<SingleImgAndTxt>{
	public void saveMessage(SingleImgAndTxt sit);
	public void saveMultiMessage(List list);
	public void saveFileInfo(FileInfo fileInfo);
	public void saveMessageLog(MessageLog log);
	public Object getMessageDetail(String id);
	public B5MPageList<MessageLog> findSendedMsgById(String uid,int pageNo);
	public List<SingleImgAndTxt> findMultiMesContent(String pid);
	public int getUserSendMesCount(String uid);
	public int messageLogCount(String uid);
}
