package com.opendata.lg.dao;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.opendata.common.base.BaseHibernateDao;
import com.opendata.lg.model.Language;
import com.opendata.lg.query.LanguageQuery;
/**
	describe:国际化Dao
	 
*/
@Repository
public class LanguageDao   extends BaseHibernateDao<Language,String>
{
	public Class getEntityClass()
	{
		return Language.class;
	}
	/**
		通过LanguageQuery对象，查询国际化
	*/
	public Page findPage(LanguageQuery query,int pageSize,int pageNum)
	{
		StringBuilder hql =new StringBuilder();
		 hql.append(" from Language ett where 1=1 ");
		List param=new ArrayList();
		if(query!=null)
		{
			if(!StringUtils.isEmpty(query.getId()))
			{
				hql.append(" and ett.id=?");
				param.add(query.getId());
			}
			if(!StringUtils.isEmpty(query.getCode()))
			{
				hql.append(" and ett.code=?");
				param.add(query.getCode());
			}
			if(!StringUtils.isEmpty(query.getChinese()))
			{
				hql.append(" and ett.chinese=?");
				param.add(query.getChinese());
			}
			if(!StringUtils.isEmpty(query.getEnglish()))
			{
				hql.append(" and ett.english=?");
				param.add(query.getEnglish());
			}
			if(!StringUtils.isEmpty(query.getJapanese()))
			{
				hql.append(" and ett.japanese=?");
				param.add(query.getJapanese());
			}
			if(!StringUtils.isEmpty(query.getRussian()))
			{
				hql.append(" and ett.russian=?");
				param.add(query.getRussian());
			}
			if(query.getTs()!=null)
			{
				hql.append(" and ett.ts=?");
				param.add(query.getTs());
			}
		}
		if(!StringUtils.isEmpty(query.getSortColumns())){
			hql.append("  order by ett."+query.getSortColumns()+" , ett.ts desc ");
		}else{
			hql.append(" order by ett.ts desc ");
		}
		return super.findByHql(hql.toString(), pageSize, pageNum, param.toArray());
	}
	
	public Language findByCode(String code)
	{
		return (Language) super.findOneByHql("from Language where code=?", code);
	}
}
