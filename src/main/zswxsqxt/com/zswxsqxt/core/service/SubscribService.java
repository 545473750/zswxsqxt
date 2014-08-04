package com.zswxsqxt.core.service;

import cn.org.rapid_framework.page.Page;

import com.zswxsqxt.core.model.Subscrib;
import com.zswxsqxt.core.query.SubscribQuery;

/**
 * 用户信息
 */
public interface SubscribService {
	
	/**
	 * 得到单个用户信息
	 * @param id
	 * @return
	 */
    public Subscrib get(String id);
    
    /**
     * 分页查询
     * 作者 于俊宇
     * 日期 2013-10-17
     *
     * @param page
     * @param param ExamAnswer
     */
    public Page findList(SubscribQuery param);
    
    /**
     * 添加或修改
     * @param entity
     */
    public void saveOrUpdate(Subscrib entity);

    /**
     * 删除客户信息
     * @param items
     */
	public void remove(String[] items);
}
