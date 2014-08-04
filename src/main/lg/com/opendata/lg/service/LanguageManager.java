package com.opendata.lg.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.Page;

import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.opendata.lg.dao.LanguageDao;
import com.opendata.lg.model.Language;
import com.opendata.lg.query.LanguageQuery;

/**
  	 describe:国际化服务类
	 
*/
@Service
@Transactional
public class LanguageManager extends BaseManager<Language, String>
{

	private LanguageDao languageDao;

	public void setLanguageDao(LanguageDao dao)
	{
		this.languageDao = dao;
	}

	public EntityDao getEntityDao()
	{
		return this.languageDao;
	}

	@Transactional(readOnly = true)
	public Page findPage(LanguageQuery query)
	{
		return languageDao.findPage(query,query.getPageSize(),query.getPageNumber());
	}
	
	
	public Language getLanguage(String code){
		Language language=languageDao.findByCode(code);
		return language;
	}
}
