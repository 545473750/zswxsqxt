package com.opendata.common.dict;

import java.util.Date;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * 数据字典缓存对象
 * @author 顾保臣
 */
public class DicCache extends GeneralCacheAdministrator{

	/**
	 * 过期时间(单位为秒); -1表示不过期
	 */
	public static final int REFEESHPERIOD = -1;
	
	/**
	 * Key标识前缀
	 */
	public static final String KEY_PREFIX = "DIC";
	
	/**
	 * 数据字典缓存对象实例
	 */
	private static DicCache instance;

	private static final long serialVersionUID = -4397192926052141162L;

	private DicCache() {
		super();
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
	 * 删除一个Key标识的缓存对象.
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

	/**
	 * 根据key标识取得对象
	 * @param key 标识
	 * @return 对象
	 * @throws Exception
	 */
	public Object get(String key) throws Exception {
		try {
			return this.getFromCache(KEY_PREFIX + "_" + key,
					REFEESHPERIOD);
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
	 * 单例模型
	 * @return
	 */
	public synchronized static DicCache getInstance() {
		if (instance == null) {
			instance = new DicCache();
		}
		return instance;
	}

}
