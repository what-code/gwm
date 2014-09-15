package com.b5m.gwm.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.b5m.gwm.dao.IMessageFileDao;
import com.b5m.gwm.model.FileInfo;
import com.b5m.gwm.model.MessageLog;
import com.b5m.gwm.model.SingleImgAndTxt;
import com.b5m.web.core.AbstractBaseDao;
import com.b5m.web.core.B5MPageList;
import com.b5m.web.core.B5MQuery;

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
@Repository
public class MessageFileDaoImpl extends AbstractBaseDao<SingleImgAndTxt> implements IMessageFileDao{

	public void saveMessage(SingleImgAndTxt sit) {
		this.save(sit);
	}

	@Override
	public void saveFileInfo(FileInfo fileInfo) {
		this.getHibernateTemplate().save(fileInfo);
	}

	@Override
	public void saveMessageLog(MessageLog log) {
		this.getHibernateTemplate().save(log);
	}

	@Override
	public Object getMessageDetail(String id) {
		return this.getById(id);
	}

	@Override
	public B5MPageList<MessageLog> findSendedMsgById(String uid,int pageNo) {
		B5MQuery bq = new B5MQuery();
		bq.setPageNo(pageNo);
		bq.setPageSize(5);
		String sql = "from MessageLog where sendUserId=? order by mesSendTime desc";
		return (B5MPageList)this.getPageList(bq, sql, new Object[]{uid});
		//return this.getHibernateTemplate().find("from MessageLog where sendUserId=? order by mesSendTime desc", uid);
	}

	@Override
	public void saveMultiMessage(List list) {
		 this.getHibernateTemplate().saveOrUpdateAll(list);
	}

	@Override
	public List<SingleImgAndTxt> findMultiMesContent(String pid) {
		return this.getHibernateTemplate().find("from SingleImgAndTxt where mesParent=?", pid);
	}

	@Override
	public int getUserSendMesCount(String uid) {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String begin = date + " 00:00:00";
		String end = date + " 23:59:59";
		List list = this.getHibernateTemplate().find("select count(*) from MessageLog where sendUserId=? and mesSendTime between '" + begin + "' and '" + end + "'", uid);
		String count = list.get(0) + "";
		return Integer.parseInt(count);
	}

	@Override
	public int messageLogCount(String uid) {
		List list = this.getHibernateTemplate().find("select count(*) from MessageLog where sendUserId=?", uid);
		return Integer.parseInt(list.get(0)+"");
	}
}
