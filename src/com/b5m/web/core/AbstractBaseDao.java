package com.b5m.web.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 数据库操作基类
 * 
 * @author Wiley
 * 
 * @param <T>
 */

@SuppressWarnings("unchecked")
public abstract class AbstractBaseDao<T extends AbstractBaseModel> extends
		HibernateDaoSupport implements IBaseDao<T> {

	protected B5MLogger logger = new B5MLogger(AbstractBaseDao.class.getName());

	public AbstractBaseDao() {
		super();
		logger = new B5MLogger(this.getClass().getName());
	}

	@Override
	public T getById(String id) {
		Object obj = this.getHibernateTemplate().get(this.getEntityClass(), id);
		return (obj != null) ? (T) obj : null;
	}

	@Override
	public T getById(Integer id) {
		Object obj = this.getHibernateTemplate().get(this.getEntityClass(), id);
		return (obj != null) ? (T) obj : null;
	}

	@Override
	public int save(Object obj) {
		if (obj instanceof Collection) {// 如果是批量数据保存
			Collection<?> cols = (Collection<?>) obj;
			for (Object o : cols)
				this.save(o);
			return cols.size();
		}
		// 单一对象保存
		if (obj == null || !(obj instanceof AbstractBaseModel))
			return 0;
		this.getHibernateTemplate().save(obj);
		return 1;
	}

	@Override
	public int update(T obj) {
		this.getHibernateTemplate().update(obj);
		return 1;
	}

	@Override
	public int remove(T obj) {
		if (obj == null)
			return 0;
		this.getHibernateTemplate().delete(obj);
		return 1;
	}

	@Override
	public long getCounts() {
		return this.getCounts("select count(*) as nums from "
				+ this.getEntityClass().getSimpleName());
	}

	@Override
	public long getCounts(String hql) {
		return this.getCounts(hql, null);
	}

	@Override
	public long getCounts(String hql, List<B5MCondition> conditionList) {
		if (!hql.startsWith("select")) {
			hql = "select count(*) as nums " + hql;
		}

		if (conditionList != null) {
			for (B5MCondition condition : conditionList) {
				Object value = condition.getValue();
				if (value instanceof Long || value instanceof Integer) {
					hql = hql.replaceFirst("\\?", String.valueOf(value));
				}
				if (value instanceof String) {
					hql = hql.replaceFirst("\\?", "'" + String.valueOf(value)
							+ "'");
				}

			}
		}
		logger.logDebug(hql);
		int intRtn = 0;
		List<?> lstTemp = this.getHibernateTemplate().find(hql);
		if (lstTemp != null && lstTemp.size() > 0) {
			intRtn = Integer.parseInt(lstTemp.get(0).toString());
		}
		return intRtn;
	}

	@Override
	public T getByWhere(String hqlWhere) {
		if (StringUtils.isBlank(hqlWhere))
			return null;
		List<T> lstTemp = this.getListByWhere(hqlWhere);
		return (lstTemp == null || lstTemp.isEmpty()) ? null : lstTemp.get(0);
	}

	@Override
	public <O> O getByWhere(String hqlWhere, Class<O> clazz) {
		if (StringUtils.isBlank(hqlWhere) || clazz == null)
			return null;
		List<O> lstTemp = this.getListByWhere(hqlWhere, clazz);
		return (lstTemp == null || lstTemp.isEmpty()) ? null : lstTemp.get(0);
	}

	@Override
	public <O> O getByWhere(String hqlWhere, Object[] whereArgs, Class<O> clazz) {
		if (StringUtils.isBlank(hqlWhere) || clazz == null)
			return null;
		if (whereArgs == null)
			return this.getByWhere(hqlWhere, clazz);
		List<O> lstTemp = this.getListByWhere(hqlWhere, whereArgs, clazz);
		return (lstTemp == null || lstTemp.isEmpty()) ? null : lstTemp.get(0);
	}

	public T getByWhere(String hqlWhere, Object... whereArgs) {
		if (StringUtils.isBlank(hqlWhere))
			return null;
		if (whereArgs == null)
			return this.getByWhere(hqlWhere);
		List<T> lstTemp = this.getListByWhere(hqlWhere, whereArgs);
		return (lstTemp == null || lstTemp.isEmpty()) ? null : lstTemp.get(0);
	}

	public List<T> getListByWhere(String hqlWhere) {
		if (StringUtils.isBlank(hqlWhere))
			return this.getList();
		hqlWhere = (hqlWhere.trim().toLowerCase().startsWith("where") ? " "
				: " where ") + hqlWhere;
		String hql = "from " + this.getEntityClass().getSimpleName() + " b5m "
				+ hqlWhere;
		return this.getHibernateTemplate().find(hql);
	}

	public List<T> getListByWhere(String hqlWhere, Object[] whereArgs) {
		if (StringUtils.isBlank(hqlWhere))
			return this.getList();
		hqlWhere = (hqlWhere.trim().toLowerCase().startsWith("where") ? " "
				: " where ") + hqlWhere;
		String hql = "from " + this.getEntityClass().getSimpleName() + " b5m "
				+ hqlWhere;
		return this.getHibernateTemplate().find(hql, whereArgs);
	}

	@Override
	public <O> List<O> getListByWhere(String hqlWhere, Class<O> clazz) {
		if (StringUtils.isBlank(hqlWhere) || clazz == null)
			return null;
		hqlWhere = (hqlWhere.trim().toLowerCase().startsWith("where") ? " "
				: " where ") + hqlWhere;
		String hql = "from " + this.getEntityClass().getSimpleName() + " b5m "
				+ hqlWhere;
		return this.getHibernateTemplate().find(hql);
	}

	@Override
	public <O> List<O> getListByWhere(String hqlWhere, Object[] whereArgs,
			Class<O> clazz) {
		if (StringUtils.isBlank(hqlWhere) || clazz == null)
			return null;
		hqlWhere = (hqlWhere.trim().toLowerCase().startsWith("where") ? " "
				: " where ") + hqlWhere;
		String hql = "from " + this.getEntityClass().getSimpleName() + " b5m "
				+ hqlWhere;
		return this.getHibernateTemplate().find(hql, whereArgs);
	}

	@Override
	public List<T> getList() {
		List<T> lst = this.getHibernateTemplate().find(
				"from " + this.getEntityClass().getSimpleName());
		return (lst != null) ? lst : null;
	}

	@Override
	public B5MPageList<T> getPageList(B5MQuery dto) {
		B5MPageList<T> pageList = new B5MPageList<T>();
		Session session = null;
		Query query = null;
		try {
			session = this.getSession();
			String hql = "from " + this.getEntityClass().getSimpleName()
					+ " b5m ";
			// 计算总记录数
			String hqlString = buildHql(hql, dto);
			int pageNo = dto.getPageNo();
			int pageSize = dto.getPageSize();
			long totalCount = this.getCounts(hqlString, dto.getCondition());

			int mod = (int) totalCount % pageSize;
			long totalPages = totalCount / pageSize + (mod > 0 ? 1 : 0);

			if (pageNo < 1)
				pageNo = 1;
			if (pageNo > totalPages)
				pageNo = (int) totalPages;
			dto.setPageNo(pageNo);
			long beginRow = (pageNo - 1) * pageSize + 1;
			long endRow = pageNo == totalPages ? totalCount : beginRow
					+ pageSize - 1;

			boolean isFirstPage = pageNo == 1;
			boolean isLastPage = pageNo == totalPages;
			boolean hasNextPage = pageNo < totalPages;
			boolean hasPrevPage = pageNo > 1;

			pageList.setFirstPage(isFirstPage);
			pageList.setLastPage(isLastPage);
			pageList.setHasNextPage(hasNextPage);
			pageList.setHasPrevPage(hasPrevPage);

			pageList.setPageNo(pageNo);
			pageList.setPageSize(pageSize);
			pageList.setTotalCount(totalCount);
			pageList.setTotalPages((int) totalPages);
			pageList.setBeginRow(beginRow);
			pageList.setEndRow(endRow);

			// 查询分页数据
			query = this.createQuery(session, hql, dto);
			List<T> lst = query.list();
			pageList.addAll(lst);
			return pageList;
		} finally {
			if (null != session)
				this.releaseSession(session);
		}
	}

	public B5MPageList<T> getPageList(B5MQuery dto, String sql, Object[] args) {
		B5MPageList<T> pageList = new B5MPageList<T>();
		Session session = null;
		String tempSql = "select count(*) " + sql.split("order by")[0];
		try {
			session = this.getSession();
			int pageNo = dto.getPageNo();
			int pageSize = dto.getPageSize();
			int totalCount = getHqlCount(tempSql,args);
			int mod = (int) totalCount % pageSize;
			long totalPages = totalCount / pageSize + (mod > 0 ? 1 : 0);

			if (pageNo < 1)
				pageNo = 1;
			if (pageNo > totalPages)
				pageNo = (int) totalPages;
			dto.setPageNo(pageNo);
			long beginRow = (pageNo - 1) * pageSize + 1;
			long endRow = pageNo == totalPages ? totalCount : beginRow
					+ pageSize - 1;

			boolean isFirstPage = pageNo == 1;
			boolean isLastPage = pageNo == totalPages;
			boolean hasNextPage = pageNo < totalPages;
			boolean hasPrevPage = pageNo > 1;

			pageList.setFirstPage(isFirstPage);
			pageList.setLastPage(isLastPage);
			pageList.setHasNextPage(hasNextPage);
			pageList.setHasPrevPage(hasPrevPage);

			pageList.setPageNo(pageNo);
			pageList.setPageSize(pageSize);
			pageList.setTotalCount(totalCount);
			pageList.setTotalPages((int) totalPages);
			pageList.setBeginRow(beginRow);
			pageList.setEndRow(endRow);
			List list = getHqlList(session,sql,args,dto);
			pageList.addAll(list);
			return pageList;
		} finally {
			if (null != session)
				this.releaseSession(session);
		}
	}

	@Override
	public List<T> getListByQuery(B5MQuery dto) {
		Session session = null;
		Query query = null;
		try {
			session = this.getSession();
			String hql = "from " + this.getEntityClass().getSimpleName()
					+ " b5m ";
			query = this.createQuery(session, hql, dto);
			List<T> lst = query.list();
			return lst;
		} finally {
			if (null != session)
				this.releaseSession(session);
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getListByQuery(String hql, B5MQuery dto) {
		Session session = null;
		try {
			session = this.getSession();
			Query query = this.createQuery(session, hql, dto);
			List<?> list = query.list();
			return list;
		} finally {
			if (null != session)
				this.releaseSession(session);
		}
	}

	private String buildHql(String hql, B5MQuery dto) {
		if (hql.toLowerCase().lastIndexOf("where") <= 0)
			hql += " where 1=1 ";
		if (null != dto.getCondition() && !dto.getCondition().isEmpty()) {
			List<B5MCondition> condList = dto.getCondition();
			for (B5MCondition condition : condList) {
				hql += " and " + condition.getName() + " "
						+ condition.getOperation() + " ? ";// " :"+condition.getName();
			}
		}
		// 拼装自定义查询条件字符串
		if (StringUtils.isNotBlank(dto.getHqlWhere()))
			hql += (dto.getHqlWhere().trim().toLowerCase().startsWith("and") ? " "
					: " and ")
					+ dto.getHqlWhere();
		return hql;
	}

	private Query setParamters(Query query, B5MQuery dto) {
		if (null != dto.getCondition() && !dto.getCondition().isEmpty()) {
			List<B5MCondition> condList = dto.getCondition();
			int i = 0;
			for (B5MCondition condition : condList) {
				query.setParameter(i, condition.getValue());
				i++;
			}
		}
		return query;
	}

	/**
	 * 组装hql字符串并生成Query
	 * 
	 * @param session
	 * @param hql
	 * @param qc
	 * @return
	 */
	private Query createQuery(Session session, String hql, B5MQuery dto) {
		Query query = null;
		// 拼装查询条件字符串
		hql = buildHql(hql, dto);
		// 拼装排序字符串
		if (StringUtils.isNotBlank(dto.getOrderBy())){
			hql += (dto.getOrderBy().trim().toLowerCase()
					.startsWith("order by") ? " " : " order by ")
					+ dto.getOrderBy();
		}
		
		if (StringUtils.isNotBlank(dto.getGroupBy())){
			hql += (dto.getGroupBy().trim().toLowerCase()
					.startsWith("group by") ? " " : " group by ")
					+ dto.getGroupBy();
		}
		
		logger.logDebug(hql);
		if (dto.isSqlQuery()) {
			query = session.createSQLQuery(hql).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
		} else{
			query = session.createQuery(hql);
		}
		// 为查询条件置值
		query = this.setParamters(query, dto);
		if (dto.isFirstRow()) {
			query.setFirstResult(0);
			query.setMaxResults(1);
		} else if (dto.getPageNo() != 0 && dto.getPageSize() != 0) {
			query.setFirstResult((dto.getPageNo() - 1) * dto.getPageSize());
			query.setMaxResults(dto.getPageSize());
		}

		return query;
	}

	@Override
	public int executeUpdate(String hql) {
		return this.executeUpdate(new String[] { hql });
	}

	@Override
	public int executeUpdate(String[] hqls) {
		Session session = null;
		int ret = 0;
		try {
			session = this.getSession();
			for (String hql : hqls) {
				if (StringUtils.isBlank(hql))
					continue;
				ret += session.createQuery(hql).executeUpdate();
			}
			session.flush();
			return ret;
		} finally {
			if (null != session)
				this.releaseSession(session);
		}

	}

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return null;
	}

	private Class<?> getGenericClass(Class<?> clazz, int index) {
		Type genType = clazz.getGenericSuperclass();
		if (genType instanceof ParameterizedType) {
			Type[] params = ((ParameterizedType) genType)
					.getActualTypeArguments();
			if ((params != null) && (params.length >= (index - 1))) {
				return (Class<?>) params[index];
			}
		}// end if.
		return null;
	}

	protected Class<?> getEntityClass() {
		return this.getGenericClass(this.getClass(), 0);
	}

	/**
	 * 获取对象实例
	 * 
	 * @return
	 */
	protected Object getEntityObject() {
		Class<?> clazz = this.getEntityClass();
		Object obj = null;
		try {
			obj = clazz.newInstance();
		} catch (InstantiationException ie) {
			logger.logError("获取EntityObject时实例化claszz异常!");
		} catch (IllegalAccessException iae) {
			logger.logError("非法访问异常!");
		}
		return obj;
	}
	
	public int getHqlCount(String csql,Object[] args){
		List list = this.getHibernateTemplate().find(csql, args);
		return Integer.parseInt(list.get(0) + "");
	}
	
	public List getHqlList(Session session,String sql,Object[] args,B5MQuery dto){
		Query query = session.createQuery(sql);
		for(int i = 0;i < args.length;i++){
			query.setParameter(i, args[i]);
		}
		if (dto.isFirstRow()) {
			query.setFirstResult(0);
			query.setMaxResults(1);
		} else if (dto.getPageNo() != 0 && dto.getPageSize() != 0) {
			query.setFirstResult((dto.getPageNo() - 1) * dto.getPageSize());
			query.setMaxResults(dto.getPageSize());
		}
		List<T> list = query.list();
		return list;
	}
}
