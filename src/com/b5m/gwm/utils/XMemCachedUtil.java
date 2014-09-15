package com.b5m.gwm.utils;

import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.commons.lang.StringUtils;

import com.b5m.web.core.ContextUtils;

/**
 * MemCached 工具类 备注： 还需优化配置文件
 * 
 * @author jianyuanyang
 * 
 */
public class XMemCachedUtil {

	// 默认设置缓存有效时间 1min 单位s
	public final static int DEFAULT_CACHE_TIME = 20 * 60;

	// 默认设置缓存有效时间 1hour 单位s
	public final static int TAO_CACHE_TIME = 61 * 63;

	public final static int TAO_CACHE_TIME_NEW = 61 * 67 * 24;

	public final static int TAO_CACHE_TIME_LOCK = 20;

	public final static int TAO_CACHE_M18_TIME = 2 * 60 * 60;

	public final static int TAO_CACHE_M18_TIME_LOCK = 6 * 60 * 60;

	private static XMemCachedUtil _this = null;

	private static MemcachedClient memcachedClient;

	public final static String NEW_KEYS = "_NEW_KEYS";

	public final static String KEY_LOCK = "_KEY_LOCK";

	// 默认设置缓存有效时间 60min 单位s
	public final static int GWM_CACHE_TIME_HOUR = 60 * 60;

	// 默认设置缓存有效时间 24hour 单位s
	public final static int GWM_CACHE_TIME_24HOUR = 15 * 61 * 67;

	/**
	 * 初始化 MemCacheUtil 单例 对象
	 * 
	 * @return
	 */
	public static synchronized XMemCachedUtil getInstance() {
		if (_this == null) {
			_this = new XMemCachedUtil();
			_this.getMemCacheInstance();
		}
		return _this;
	}

	/**
	 * 利用 spring 上下文 根据beanName 获取memCahcheClient 实体
	 */
	public void getMemCacheInstance() {
		if (null == memcachedClient) {
			memcachedClient = (MemcachedClient) ContextUtils.getBean("memcachedClientNew");
		}
	}

	/**
	 * 默认设置memcache 缓存
	 * 
	 * @param key
	 *            缓存的key
	 * @param value
	 *            缓存的value
	 * @return
	 */
	public static void setCache(String key, Object value) {
		setCache(key, value, DEFAULT_CACHE_TIME);
	}

	/**
	 * 设置memcache 缓存包括有效时间
	 * 
	 * @param key
	 *            缓存的key
	 * @param value
	 *            缓存的value
	 * @param exp
	 *            缓存的有效时间
	 * @return
	 */
	public static void setCache(String key, Object value, int exp) {
		if (StringUtils.isNotBlank(key)) {
			try {
				if (value != null) {
					XMemCachedUtil.getInstance().memcachedClient.set(key, exp, value);
				}
			} catch (TimeoutException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (MemcachedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取缓存对象
	 * 
	 * @param key
	 *            缓存的key
	 * @return value
	 */
	public static Object getCache(String key) {
		Object result = null;
		if (StringUtils.isNotBlank(key)) {
			try {
				result = XMemCachedUtil.getInstance().memcachedClient.get(key);
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MemcachedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 清空某缓存对象
	 * 
	 * @param key
	 *            缓存的key
	 * @return
	 */
	public static void cleanCache(String key) {
		try {
			XMemCachedUtil.getInstance().memcachedClient.delete(key);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 清空所有缓存对象
	 * 
	 * @return
	 */
	public static void cleanAllRemoteCache() {
		try {
			XMemCachedUtil.getInstance().memcachedClient.flushAll();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean add(String key, Object value) {
		try {
			return XMemCachedUtil.getInstance().memcachedClient.add(key, XMemCachedUtil.TAO_CACHE_TIME_LOCK, value);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static String getLockKey(String key) {
		return key + XMemCachedUtil.KEY_LOCK;
	}

	public static String getNewKey(String key) {
		return key + XMemCachedUtil.NEW_KEYS;
	}

	public static Integer getDataMemCached(Object data, String key) {
		Integer cacheNum = 0;
		if (("true").equals(ContextUtils.getInstance().getMemcachedOpen())) {
			if (data == null) {
				boolean flag = XMemCachedUtil.getInstance().add(XMemCachedUtil.getLockKey(key), "YOU_KEY_LOCK");
				if (flag) {
					cacheNum = 1;
				} else {
					data = XMemCachedUtil.getCache(XMemCachedUtil.getNewKey(key));
					if (data == null) {
						cacheNum = 2;
					}
				}
			}
		} else {
			cacheNum = 3;
		}
		return cacheNum;
	}
}
