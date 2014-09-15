package com.b5m.gwm.service;

import java.util.List;

import com.b5m.gwm.model.SystemBulletin;

/**
 * Title:ISystemBulletinService.java
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
public interface ISystemBulletinService {

	/**
	 * 获取全部系统公告
	 * 
	 * @return
	 */
	public List<SystemBulletin> findBulletinByAll();
}
