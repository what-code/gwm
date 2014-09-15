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
@Table(name = "message_file")
public class FileInfo extends AbstractBaseModel{
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name = "file_name")
	private String fileName;
	
	@Column(name = "file_sys_name")
	private String fileSysName;
	
	@Column(name = "file_cdn_url")
	private String fileCdnUrl;
	
	@Column(name = "file_type")
	private String fileType;
	
	@Column(name = "file_size")
	private String fileSize;
	
	@Column(name = "upload_time")
	private String uploadTime;
	
	@Column(name = "upload_user")
	private String uploadUser;
	
	@Column(name = "file_status")
	private String fileStatus;

	public int getId() {
		return id;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileSysName() {
		return fileSysName;
	}

	public String getFileCdnUrl() {
		return fileCdnUrl;
	}

	public String getFileType() {
		return fileType;
	}

	public String getFileSize() {
		return fileSize;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public String getUploadUser() {
		return uploadUser;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileSysName(String fileSysName) {
		this.fileSysName = fileSysName;
	}

	public void setFileCdnUrl(String fileCdnUrl) {
		this.fileCdnUrl = fileCdnUrl;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public void setUploadUser(String uploadUser) {
		this.uploadUser = uploadUser;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}
}
