package com.b5m.gwm.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.b5m.web.core.AbstractBaseModel;

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
@Entity
@Table(name = "user")
public class User extends AbstractBaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3183133304658710340L;

	@Id
	@GeneratedValue
	private int id;

	// 注册绑定的email
	@Column(name = "email")
	private String email;

	// 登录密码
	@Column(name = "password")
	private String password;

	// 审核状态：0、未审核，1、已审核
	@Column(name = "check_status")
	private String checkStatus = "0";

	// 邮箱状态：0、未激活，1、已激活
	@Column(name = "mail_status")
	private String mailStatus = "0";

	// 封号状态：0、未封号，1、已封号
	@Column(name = "close_status")
	private String closeStatus = "0";

	// 审核人
	@Column(name = "audit")
	private String audit = "";

	// 添加时间
	@Column(name = "add_time")
	private Date addTime = new Date();

	// 激活时间
	@Column(name = "active_time")
	private Date activeTime;

	// 频道ID
	@Column(name = "chn_id")
	private String chnId;

	// 群组id，用，隔开
	@Column(name = "group_ids")
	private String groupIds;

	public User() {

	}

	public User(String email, String password, Date addTime, String chnId) {
		super();
		this.email = email;
		this.password = password;
		this.addTime = addTime;
		this.chnId = chnId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getMailStatus() {
		return mailStatus;
	}

	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}

	public String getCloseStatus() {
		return closeStatus;
	}

	public void setCloseStatus(String closeStatus) {
		this.closeStatus = closeStatus;
	}

	public String getAudit() {
		return audit;
	}

	public void setAudit(String audit) {
		this.audit = audit;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	public String getChnId() {
		return chnId;
	}

	public void setChnId(String chnId) {
		this.chnId = chnId;
	}

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

}
