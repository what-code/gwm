
package com.b5m.web.core;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;


/**
 * 业务处理基类
 * @author Wiley
 *
 * @param <T>
 */
public abstract class AbstractBaseService<T extends AbstractBaseModel> implements IBaseService<T>
{

    protected B5MLogger logger = new B5MLogger(AbstractBaseService.class.getName());

    public AbstractBaseService()
    {
        super();
        logger = new B5MLogger(this.getClass().getName());
    }

    private IBaseDao<T> baseDao = null;

    public void setBaseDao(IBaseDao<T> baseDao)
    {
        this.baseDao = baseDao;
    }

    @Override
    public T getById(Integer id)
    {
        return this.baseDao.getById(id);
    }

    @Override
    public T getById(String id)
    {
        return this.baseDao.getById(id);
    }

    @Override
    public int save(Object obj)
    {
        return this.baseDao.save(obj);
    }

    @Override
    public int update(T obj)
    {
        return this.baseDao.update(obj);
    }

    @Override
    public int remove(T obj)
    {
        return this.baseDao.remove(obj);
    }

    @Override
    public long getCounts()
    {
        return this.baseDao.getCounts();
    }

    @Override
    public long getCounts(String hql)
    {
        return this.baseDao.getCounts(hql);
    }

    @Override
    public long getCounts(String hql , List<B5MCondition> conditionList)
    {
        return this.baseDao.getCounts(hql, conditionList);
    }

    @Override
    public B5MPageList<T> getPageList(B5MQuery dto)
    {
        return this.baseDao.getPageList(dto);
    }

    @Override
    public List<T> getList()
    {
        return this.baseDao.getList();
    }

    @Override
    public List<?> getListByQuery(String hql , B5MQuery dto)
    {
        return this.baseDao.getListByQuery(hql, dto);
    }

    @Override
    public List<T> getListByQuery(B5MQuery dto)
    {
        return this.baseDao.getListByQuery(dto);
    }

    @Override
    public List<T> getListByWhere(String hqlWhere)
    {
        return this.baseDao.getListByWhere(hqlWhere);
    }

    @Override
    public List<T> getListByWhere(String hqlWhere , Object[] whereArgs)
    {
        return this.baseDao.getListByWhere(hqlWhere, whereArgs);
    }

    @Override
    public <O> List<O> getListByWhere(String hqlWhere , Class<O> clazz)
    {
        return this.baseDao.getListByWhere(hqlWhere, clazz);
    }

    @Override
    public <O> List<O> getListByWhere(String hqlWhere , Object[] whereArgs , Class<O> clazz)
    {
        return this.baseDao.getListByWhere(hqlWhere, whereArgs, clazz);
    }

    @Override
    public T getByWhere(String hqlWhere)
    {
        return this.baseDao.getByWhere(hqlWhere);
    }

    @Override
    public <O> O getByWhere(String hqlWhere , Class<O> clazz)
    {
        return this.baseDao.getByWhere(hqlWhere, clazz);
    }

    @Override
    public <O> O getByWhere(String hqlWhere , Object[] whereArgs , Class<O> clazz)
    {
        return this.baseDao.getByWhere(hqlWhere, whereArgs, clazz);
    }

    @Override
    public int executeUpdate(String hql)
    {
        return this.baseDao.executeUpdate(hql);
    }

    @Override
    public int executeUpdate(String[] hqls)
    {
        return this.baseDao.executeUpdate(hqls);
    }

    @Override
    public JdbcTemplate getJdbcTemplate()
    {
        return this.baseDao.getJdbcTemplate();
    }

}
