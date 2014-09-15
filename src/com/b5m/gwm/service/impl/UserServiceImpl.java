package com.b5m.gwm.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.b5m.gwm.dao.IUserDAO;
import com.b5m.gwm.httppost.IHttpPostDAO;
import com.b5m.gwm.model.User;
import com.b5m.gwm.params.ParamsDTO;
import com.b5m.gwm.service.IUserService;
import com.b5m.gwm.utils.BASE64;
import com.b5m.gwm.utils.MailUtils;

/**
 * Title:UserServiceImpl.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-11-22
 * 
 * Company: IZENE Software(Shanghai) Co., Ltd.
 * 
 * @author Jia Liu
 * 
 * @version 1.0
 */
@Service
public class UserServiceImpl implements IUserService {

	public static Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Resource
	private IUserDAO dao;

	@Resource
	private IHttpPostDAO httpDao;

	@Override
	public boolean isMailValidate(String mail) {
		return dao.isMailValidate(mail);
	}

	@Override
	public void sendActivationMail(final String email, String chnId, String rootPath) {
		logger.info("发送激活邮件-----points sendMail--" + email);

		String mail = BASE64.encode(email);
		final String mailContent = "你好!<br/><br/>感谢你注册购玩美频道运营平台。 你的登录邮箱为：" + email + "。请点击以下链接激活帐号： <br/><br/><a href=\"" + rootPath
				+ "/register-info.html?mail=" + mail + "&chnId=" + chnId + "\" target=\"_blank\">" + rootPath + "/register-info.html?mail=" + mail
				+ "&chnId=" + chnId + "</a><br/><br/>如果以上链接无法点击，请将上面的地址复制到你的浏览器(如IE)的地址栏进入购玩美频道运营平台。<br/><br/><br/><br/><br/>购玩美团队上";

		logger.info("-----邮件内容-----" + mailContent);

		// 另外开启线程去发送邮件
		new Thread(new Runnable() {

			@Override
			public void run() {
				MailUtils.sendHtmlEmail(email, "购玩美频道运营平台激活邮件", mailContent);
			}
		}).start();

	}

	@Override
	public boolean saveUser(User user) {
		return dao.saveUser(user);
	}

	@Override
	public int activationUser(String email) {
		return dao.activationUser(email);
	}

	@Override
	public boolean isChnName(String name) {
		return httpDao.isChnName(name);
	}

	@Override
	public Map<String, String> saveChannel(ParamsDTO dto) {
		return httpDao.saveChannel(dto);
	}

	@Override
	public Map<String, String> getChannel(String chnId) {
		return httpDao.getChannel(chnId);
	}

	@Override
	public Map<String, String> getChannelByMail(String mail) {
		return httpDao.getChannelByMail(mail);
	}

	@Override
	public User findUserByEmail(String email) {
		// String key = "findUserByEmail_" + email;
		// logger.info("findUserByEmail:" + key);
		//
		// Object data = XMemCachedUtil.getCache(key);
		// Integer num = XMemCachedUtil.getDataMemCached(data, key);
		// if (num != 0) {
		// data = dao.findUserByEmail(email);

		// XMemCachedUtil.setCache(key, data, XMemCachedUtil.GWM_CACHE_TIME_HOUR);
		// XMemCachedUtil.setCache(XMemCachedUtil.getNewKey(key), data, XMemCachedUtil.GWM_CACHE_TIME_24HOUR);
		// }

		// return (User) data;
		return dao.findUserByEmail(email);
	}

	@Override
	public User login(String email, String pwd) {
		return dao.login(email, pwd);
	}

	@Override
	public boolean updateChannel(ParamsDTO dto) {
		return httpDao.updateChannel(dto);
	}
}
