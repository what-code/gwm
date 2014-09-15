package com.b5m.gwm.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.b5m.web.core.AbstractBaseModel;

/**
 * Title:SystemAnnouncement.java
 * 
 * Description:系统公告表
 * 
 * Copyright: Copyright (c) 2013-12-2
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Jia Liu
 * 
 * @version 1.0
 */
@Entity
@Table(name = "system_bulletin")
public class SystemBulletin extends AbstractBaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5553038639925763254L;

	@Id
	@GeneratedValue
	private int id;

	// 公告标题
	@Column(name = "title")
	private String title;

	// 公告内容
	@Column(name = "body")
	private String body;

	// 公告生成时间
	@Column(name = "send_time")
	private Date sendTime;

	// 公告上架时间
	@Column(name = "begin_time")
	private Date beginTime;

	// 公告下架时间
	@Column(name = "end_time")
	private Date endTime;

	// 优先级，排序使用
	@Column(name = "piority")
	private int piority;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getPiority() {
		return piority;
	}

	public void setPiority(int piority) {
		this.piority = piority;
	}

}
