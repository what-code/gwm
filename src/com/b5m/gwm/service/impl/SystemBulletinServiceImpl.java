package com.b5m.gwm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.b5m.gwm.dao.ISystemBulletinDAO;
import com.b5m.gwm.httppost.IHttpPostDAO;
import com.b5m.gwm.model.SystemBulletin;
import com.b5m.gwm.service.ISystemBulletinService;

/**
 * Title:SystemBulletinServiceImpl.java
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
public class SystemBulletinServiceImpl implements ISystemBulletinService {

	public static Logger logger = Logger.getLogger(SystemBulletinServiceImpl.class);

	@Resource
	private ISystemBulletinDAO dao;

	@Resource
	private IHttpPostDAO httpDao;

	@Override
	public List<SystemBulletin> findBulletinByAll() {
		return dao.findBulletinByAll();
	}

}
