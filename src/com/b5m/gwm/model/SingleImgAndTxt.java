package com.b5m.gwm.model;

import java.sql.Date;

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
@Table(name = "message_base")
public class SingleImgAndTxt extends AbstractBaseModel {

	private static final long serialVersionUID = -3183133304658710340L;

	@Id
	private String id;

	@Column(name = "mes_title")
	private String title;

	@Column(name = "mes_author")
	private String author;

	@Column(name = "mes_face_img")
	private String faceImg;

	@Column(name = "mes_abstract")
	private String mesAbstract;

	@Column(name = "mes_body")
	private String mesBody;

	@Column(name = "mes_add_time")
	private String addTime;

	@Column(name = "mes_add_user")
	private String addUser;

	@Column(name = "mes_type")
	private String mesType;

	@Column(name = "mes_parent")
	private String mesParent;

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	

	public String getMesBody() {
		return mesBody;
	}

	public String getAddTime() {
		return addTime;
	}

	public String getMesParent() {
		return mesParent;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	

	public void setMesBody(String mesBody) {
		this.mesBody = mesBody;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}


	public void setMesParent(String mesParent) {
		this.mesParent = mesParent;
	}

	public String getFaceImg() {
		return faceImg;
	}

	public String getMesAbstract() {
		return mesAbstract;
	}

	public void setFaceImg(String faceImg) {
		this.faceImg = faceImg;
	}

	public void setMesAbstract(String mesAbstract) {
		this.mesAbstract = mesAbstract;
	}

	public String getAddUser() {
		return addUser;
	}

	public String getMesType() {
		return mesType;
	}

	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}

	public void setMesType(String mesType) {
		this.mesType = mesType;
	}
	
	
	
}
