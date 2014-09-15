package com.b5m.gwm.httppost;

import java.util.Map;

import com.b5m.gwm.params.ParamsDTO;

/**
 * Title:Test.java
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
public interface IHttpPostDAO {

	/**
	 * 验证频道名是否被占用
	 * 
	 * @return true被占用
	 */
	public boolean isChnName(String name);

	/**
	 * 保存频道信息
	 * 
	 * @return 返回对象保存成功
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
	 * 修改频道信息
	 * 
	 * @return 修改成功
	 */
	public boolean updateChannel(ParamsDTO dto);
}
