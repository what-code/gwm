package com.b5m.gwm.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.b5m.gwm.dao.ISystemBulletinDAO;
import com.b5m.gwm.model.SystemBulletin;
import com.b5m.web.core.AbstractBaseDao;
import com.b5m.web.core.B5MQuery;

/**
 * Title:SystemBulletinDAOImpl.java
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
@Repository
public class SystemBulletinDAOImpl extends AbstractBaseDao<SystemBulletin> implements ISystemBulletinDAO {

	public static Logger logger = Logger.getLogger(SystemBulletinDAOImpl.class);

	@Override
	public List<SystemBulletin> findBulletinByAll() {
		B5MQuery dto = new B5MQuery();
		dto.setOrderBy("send_time DESC");
		return this.getListByQuery(dto);
	}

}
