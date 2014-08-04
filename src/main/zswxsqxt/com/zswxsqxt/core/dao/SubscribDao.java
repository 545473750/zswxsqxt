package com.zswxsqxt.core.dao;

import org.springframework.stereotype.Repository;

import com.opendata.common.base.BaseHibernateDao;
import com.zswxsqxt.core.model.Subscrib;

/**
 * 客户信息
 */
@Repository
public class SubscribDao extends BaseHibernateDao<Subscrib, String>
{
	@Override
	public Class getEntityClass() {
		return Subscrib.class;
	}
}
