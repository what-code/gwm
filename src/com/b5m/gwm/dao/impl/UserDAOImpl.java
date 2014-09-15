package com.b5m.gwm.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.b5m.gwm.dao.IUserDAO;
import com.b5m.gwm.model.User;
import com.b5m.web.core.AbstractBaseDao;
import com.b5m.web.core.B5MCondition;

/**
 * Title:UserDAOImpl.java
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
public class UserDAOImpl extends AbstractBaseDao<User> implements IUserDAO {

	public static Logger logger = Logger.getLogger(UserDAOImpl.class);

	@Override
	public boolean isMailValidate(String mail) {
		String hql = "FROM User where email=?";
		List<B5MCondition> condition = new ArrayList<B5MCondition>();
		condition.add(new B5MCondition("email", "=", mail));
		long count = this.getCounts(hql, condition);
		if (count == 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean saveUser(User user) {
		try {
			int sInt = this.save(user);
			if (sInt == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.info("***************saveUser* 数据存储问题。*********************");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int activationUser(String email) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String hql = "UPDATE User SET mail_status=1,active_time='" + sdf.format(new Date()) + "' WHERE email='" + email + "'";
		return this.executeUpdate(hql);
	}

	@Override
	public User findUserByEmail(String email) {
		return this.getByWhere("email=?", new Object[] { email });
	}

	@Override
	public User login(String email, String pwd) {
		return this.getByWhere("WHERE email=? and password=?", new Object[] { email, pwd });
	}
}
