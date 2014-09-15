package com.b5m.gwm.dao;

import com.b5m.gwm.model.User;

/**
 * Title:IUserDAO.java
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
public interface IUserDAO {

	/**
	 * 邮箱验证
	 * 
	 * @param mail
	 *            邮箱
	 * @return 布尔值:true存在，false不存在
	 */
	public boolean isMailValidate(String mail);

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
	 * @param email
	 *            用户邮箱
	 * @return 是否激活成功
	 */
	public int activationUser(String email);

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
}
