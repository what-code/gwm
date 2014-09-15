package com.b5m.web.core;

import java.io.Serializable;



/**
 * 实体对象（数据模型）的基类，将一些公共方法写在这里
 * @author Wiley
 *
 */
public abstract class AbstractBaseModel implements Serializable {
	
	private static final long serialVersionUID = 9015752268276518172L;
	protected B5MLogger logger=new B5MLogger(AbstractBaseModel.class.getName());
	
	public AbstractBaseModel(){
		logger=new B5MLogger(this.getClass().getName());
	}
}
