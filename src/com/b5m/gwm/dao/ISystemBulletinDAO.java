package com.b5m.gwm.dao;

import java.util.List;

import com.b5m.gwm.model.SystemBulletin;

/**
 * Title:ISystemBulletinDAO.java
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
public interface ISystemBulletinDAO {

	/**
	 * 获取全部系统公告
	 * 
	 * @return
	 */
	public List<SystemBulletin> findBulletinByAll();
}
