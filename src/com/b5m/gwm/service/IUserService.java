package com.b5m.gwm.service;

import java.util.Map;

import com.b5m.gwm.model.User;
import com.b5m.gwm.params.ParamsDTO;

/**
 * Title:UserService.java
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
public interface IUserService {

	/**
	 * 邮箱验证
	 * 
	 * @param mail
	 *            邮箱
	 * @return 布尔值:true存在，false不存在
	 */
	public boolean isMailValidate(String mail);

	/**
	 * 发送激活邮件
	 * 
	 * @param email
	 *            发送邮箱
	 * @param chnId
	 *            频道ID
	 */
	public void sendActivationMail(String email, String chnId, String rootPath);

	/**
	 * 保存用户信息
	 * 
	 * @param user
	 * @return 是否保存成功
	 */
	public boolean saveUser(User user);

	/**
	 * 激活用户
	 * 
	 * @param chnId
	 *            频道ID
	 * @return 是否激活成功
	 */
	public int activationUser(String chnId);

	/**
	 * 验证频道名是否被占用
	 * 
	 * @return true被占用
	 */
	public boolean isChnName(String name);

	/**
	 * 保存频道信息
	 * 
	 * @return true保存成功
	 */
	public Map<String, String> saveChannel(ParamsDTO dto);

	/**
	 * 获取频道信息
	 * 
	 * @return 获取频道信息
	 */
	public Map<String, String> getChannel(String chnId);

	/**
	 * 获取频道信息
	 * 
	 * @return 获取频道信息
	 */
	public Map<String, String> getChannelByMail(String mail);

	/**
	 * 按邮箱查找用户
	 * 
	 * @param email
	 *            用户邮箱
	 * @return 用户对象
	 */
	public User findUserByEmail(String email);

	/**
	 * 登陆
	 * 
	 * @param email
	 *            用户邮箱
	 * @param pwd
	 *            用户密码
	 * 
	 * @return 用户对象
	 */
	public User login(String email, String pwd);

	/**
	 * 修改频道信息
	 * 
	 * @return 修改成功
	 */
	public boolean updateChannel(ParamsDTO dto);
}
