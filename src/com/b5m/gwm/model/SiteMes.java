package com.b5m.gwm.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.b5m.web.core.AbstractBaseModel;

/**
 * Title:SiteMes.java
 * 
 * Description:站内信表
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
@Table(name = "site_mes")
public class SiteMes extends AbstractBaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5007690644359716319L;

	@Id
	@GeneratedValue
	private int id;

	// 站内信 title
	@Column(name = "title")
	private String title;

	// 站内信内容
	@Column(name = "body")
	private String body;

	// 站内信接收者ID
	@Column(name = "send_to")
	private String sendTo;

	// 发送时间
	@Column(name = "send_time")
	private Date sendTime;

	// 是否已阅读 0：未阅读 1：已阅读
	@Column(name = "is_read")
	private String isRead;

	// 阅读时间
	@Column(name = "read_time")
	private String readTime;

	// 类型：0：未知 1：频道审核
	@Column(name = "mes_type")
	private String mesType;

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

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public String getReadTime() {
		return readTime;
	}

	public void setReadTime(String readTime) {
		this.readTime = readTime;
	}

	public String getMesType() {
		return mesType;
	}

	public void setMesType(String mesType) {
		this.mesType = mesType;
	}

}
