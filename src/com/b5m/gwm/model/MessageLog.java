package com.b5m.gwm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.b5m.web.core.AbstractBaseModel;

/**
 * Title:FileInfo.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-11-25
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Shengjie Guo
 * 
 * @version 1.0
 */
@Entity
@Table(name = "message_log")
public class MessageLog extends AbstractBaseModel{
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name = "mes_base_id")
	private String mesId;
	
	@Column(name = "mes_name")
	private String mesName;
	
	@Column(name = "face_img")
	private String faceImg;
	
	@Column(name = "mes_target")
	private String mesTarget;
	
	@Column(name = "mes_sex")
	private String mesSex;
	
	@Column(name = "mes_send_time")
	private String mesSendTime;
	
	@Column(name = "mes_status")
	private String mesStatus;
	
	@Column(name = "send_user_id")
	private String sendUserId;
	
	@Column(name = "send_user_name")
	private String sendUserName;
	
	@Column(name = "mes_type")
	private String mesType;
	
	public int getId() {
		return id;
	}

	public String getMesId() {
		return mesId;
	}

	public String getMesName() {
		return mesName;
	}

	public String getMesTarget() {
		return mesTarget;
	}

	public String getMesSex() {
		return mesSex;
	}

	public String getMesSendTime() {
		return mesSendTime;
	}

	public String getMesStatus() {
		return mesStatus;
	}

	public String getSendUserId() {
		return sendUserId;
	}

	public String getSendUserName() {
		return sendUserName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMesId(String mesId) {
		this.mesId = mesId;
	}

	public void setMesName(String mesName) {
		this.mesName = mesName;
	}

	public void setMesTarget(String mesTarget) {
		this.mesTarget = mesTarget;
	}

	public void setMesSex(String mesSex) {
		this.mesSex = mesSex;
	}

	public void setMesSendTime(String mesSendTime) {
		this.mesSendTime = mesSendTime;
	}

	public void setMesStatus(String mesStatus) {
		this.mesStatus = mesStatus;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

	public String getMesType() {
		return mesType;
	}

	public void setMesType(String mesType) {
		this.mesType = mesType;
	}

	public String getFaceImg() {
		return faceImg;
	}

	public void setFaceImg(String faceImg) {
		this.faceImg = faceImg;
	}
}
