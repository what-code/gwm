package com.b5m.web.core;

import java.util.LinkedList;
import java.util.List;

public class B5MQuery {
	// 是否只查询第一条
	private boolean isFirstRow = false;
	// 区分sql与hql
	private boolean isSqlQuery = false;

	// 排序字符串
	private String orderBy;
	
	private String groupBy;

	// 自字义查询条件字符串
	private String hqlWhere;
	
	//当前页码
	private int pageNo=0;
	
	//每页最大数据量
	private int pageSize=0;

	//查询条件
	//private Map<String, Object> condition = new HashMap<String, Object>();
	private List<B5MCondition> condition=new LinkedList<B5MCondition>();
	
	public boolean isFirstRow() {
		return isFirstRow;
	}

	public void setFirstRow(boolean isFirstRow) {
		this.isFirstRow = isFirstRow;
	}

	public boolean isSqlQuery() {
		return isSqlQuery;
	}

	public void setSqlQuery(boolean isSqlQuery) {
		this.isSqlQuery = isSqlQuery;
	}

	public String getHqlWhere() {
		return hqlWhere;
	}

	public void setHqlWhere(String hqlWhere) {
		this.hqlWhere = hqlWhere;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getGroupBy()
    {
        return groupBy;
    }

    public void setGroupBy(String groupBy)
    {
        this.groupBy = groupBy;
    }

    public void addCondition(B5MCondition condition) {
		this.condition.add(condition);
	}

	public List<B5MCondition> getCondition() {
		return this.condition;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
