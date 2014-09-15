package com.b5m.gwm.params;

import java.io.File;

import com.b5m.gwm.utils.BASE64;
import com.b5m.gwm.utils.PWCode;

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
public class ParamsDTO {

	// 邮箱
	private String email;

	// 邮箱激活值
	private String mail;

	// 密码
	private String pwd;

	// 记住的密码
	private String pwdRemember;

	// 验证码
	private String code;

	// 频道ID
	private String chnId;

	// 频道头像
	private String avatar;

	private File file;

	// 频道名称
	private String chnName;

	// 内容简介
	private String content = "ta很懒，什么都没写！";

	// 邮件类型0、新发邮件，1、重发邮件
	private Integer type = 0;

	// 是否记住密码
	private String isRemember = "false";

	// 密码长度
	private Integer pwdLength;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email != null) {
			this.email = email.trim();
		}
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwdLength = pwd.length();
		this.pwd = PWCode.getPassWordCode(pwd);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getChnId() {
		return chnId;
	}

	public void setChnId(String chnId) {
		this.chnId = chnId;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = BASE64.decode(mail);
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getChnName() {
		return chnName;
	}

	public void setChnName(String chnName) {
		this.chnName = chnName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIsRemember() {
		return isRemember;
	}

	public void setIsRemember(String isRemember) {
		this.isRemember = isRemember;
	}

	public String getPwdRemember() {
		return pwdRemember;
	}

	public void setPwdRemember(String pwdRemember) {
		this.pwdRemember = pwdRemember;
	}

	public Integer getPwdLength() {
		return pwdLength;
	}

	public void setPwdLength(Integer pwdLength) {
		this.pwdLength = pwdLength;
	}

}
