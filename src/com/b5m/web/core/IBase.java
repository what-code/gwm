package com.b5m.web.core;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;


/**
 * DAO和SERVICE的公共接口
 * 
 * @author Wiley
 * 
 * @param <T>
 */
public interface IBase<T extends AbstractBaseModel> {

    
    /**
     * 获取指定对象id的对象
     * 
     * @param id
     *            as String
     * @return T
     */
    public T getById(String id);
    
	/**
	 * 获取指定对象id的对象
	 * 
	 * @param id
	 *            as Integer
	 * @return T
	 */
	public T getById(Integer id);

	/**
	 * 用于保存Hibernate的已存在id的实体对象
	 * 
	 * @param obj
	 * @return
	 */
	public int save(Object obj);

	/**
	 * 更新或保存对象T
	 * 
	 * @param obj
	 *            as T
	 * @return int
	 */
	public int update(T obj);

	/**
	 * 删除对象T
	 * 
	 * @param obj
	 *            as T
	 * @return int
	 */
	public int remove(T obj);

	/**
	 * 获取记录总和
	 * 
	 * @return long
	 */
	public long getCounts();

	public long getCounts(String hql);
	
	public long getCounts(String hql,List<B5MCondition> conditionList);

	/**
	 * 获取所有对象列表
	 * 
	 * @return List&lt;T&gt;
	 */
	public List<T> getList();

	/**
	 * 当hql中包含union关键字和以"select * "开头的语句将使用SqlQuery查询再转换为实体对象
	 * 
	 * @param hql
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getListByQuery(String hql, B5MQuery dto);

	/**
	 * 
	 * @param dto
	 * @return
	 */
	public List<T> getListByQuery(B5MQuery dto);

	/**
	 * 
	 * @param hqlWhere
	 * @return
	 */
	public List<T> getListByWhere(String hqlWhere);

	/**
	 * 
	 * @param hqlWhere
	 * @param whereArgs
	 * @return
	 */
	public List<T> getListByWhere(String hqlWhere, Object[] whereArgs);

	public <O> List<O> getListByWhere(String hqlWhere, Class<O> clazz);

	public <O> List<O> getListByWhere(String hqlWhere, Object[] whereArgs, Class<O> clazz);

	public T getByWhere(String hqlWhere);

	public <O> O getByWhere(String hqlWhere, Class<O> clazz);

	public <O> O getByWhere(String hqlWhere, Object[] whereArgs, Class<O> clazz);
	
	//public <O> O getPageList(int pageNo,int pageSize);
	
	public B5MPageList<T> getPageList(B5MQuery dto);
	
	//public <O> O getPageList(QueryDto dto);
	
	public int executeUpdate(String hql);

	public int executeUpdate(String[] hql);

	public JdbcTemplate getJdbcTemplate();

}
