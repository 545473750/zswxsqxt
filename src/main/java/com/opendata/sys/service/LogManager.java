/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.service;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.opendata.common.base.*;
import cn.org.rapid_framework.page.*;
import com.opendata.sys.model.*;
import com.opendata.sys.dao.*;
import com.opendata.sys.vo.query.*;

/**
 * 日志的service层 负责处理业务逻辑
 * @author 付威
 */
@Service
@Transactional
public class LogManager extends BaseManager<Log, java.lang.String>{

	private LogDao logDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setLogDao(LogDao dao) {
		this.logDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.logDao;
	}
	
	/**
	 * 根据查询条件查询 执行分页
	 * @param query 查询条件对象
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page findPage(LogQuery query) {
		return logDao.findPage(query);
	}
	
	/**
	 * 批量删除日志
	 * @param tsBegin
	 * @param tsEnd
	 */
	public void remove(String tsBegin,String tsEnd){
		StringBuffer hql = new StringBuffer(" delete from t_Log  where df='0'");
		if(tsBegin!=null && !"".equals(tsBegin)){
			hql.append(" and ts >= :tsBegin"); 
		}
		if(tsEnd != null && !"".equals(tsEnd)){
			hql.append(" and ts <= :tsEnd");
		}
		Session session = logDao.getSessionFactory().openSession();
		Query query = session.createSQLQuery(hql.toString());
		if(tsBegin!=null && !"".equals(tsBegin)){
			query.setString("tsBegin", tsBegin); 
		}
		if(tsEnd != null && !"".equals(tsEnd)){
			query.setString("tsEnd", tsEnd); 
		}
		query.executeUpdate();
		session.close();
	}
	
}
