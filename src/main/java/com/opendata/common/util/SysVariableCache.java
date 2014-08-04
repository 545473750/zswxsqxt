package com.opendata.common.util;

import java.util.Date;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * 系统参数缓存对象
 * @author 付威
 *
 */
public class SysVariableCache extends GeneralCacheAdministrator {

	private static final long serialVersionUID = 1L;
	/**
	 * 过期时间(单位为秒); -1表示不过期
	 */
	public static final int REFEESHPERIOD = -1;
	/**
	 * Key标识前缀
	 */
	public static final String KEY_PREFIX = "SYSV";
	
	private static SysVariableCache instance;
	
	private SysVariableCache() {
		super();
	}
	
	/**
	 * 取得此对象实例 为单例模式
	 * @return
	 */
	public synchronized static SysVariableCache getInstance() {
		if (instance == null) {
			instance = new SysVariableCache();
		}
		return instance;
	}
	
	/**
	 * 根据key标识取得对象
	 * @param key 标识
	 * @return 对象
	 * @throws Exception
	 */
	public Object get(String key) throws Exception {
		try {
			return this.getFromCache(key, REFEESHPERIOD);
		} catch (NeedsRefreshException e) {
			this.cancelUpdate(KEY_PREFIX + "_" + key);
			throw e;
		}
	}
	
	/**
	 * 缓存是否为空
	 * @return 为空返回true否则返回false
	 */
	public boolean isEmpty() {
		int i = instance.cacheCapacity;
		boolean flag = false;
		if (i > 0) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 存储一个由Key标识的缓存对象
	 * @param key 标识
	 * @param value 对象
	 */
	public void put(String key, Object value) {
		this.putInCache(KEY_PREFIX + "_" + key, value);
	}

	/**
	 * 删除一个Key标识的缓存对象
	 * @param key 标识
	 */
	public void remove(String key) {
		this.flushEntry(KEY_PREFIX + "_" + key);
	}

	/**
	 * 在指定时间删除整个缓存
	 * @param date 时间
	 */
	public void removeAll(Date date) {
		this.flushAll(date);
	}

	/**
	 * 立即删除整个缓存
	 */
	public void removeAll() {
		this.flushAll();
	}
	
}
