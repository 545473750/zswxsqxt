package com.opendata.sys.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseHibernateDao;

import com.opendata.sys.model.Attachment;
import com.opendata.sys.vo.query.AttachmentQuery;
/**
	describe:附件表Dao
	 
*/
@Repository
public class AttachmentDao   extends BaseHibernateDao<Attachment,String>
{
	public Class getEntityClass()
	{
		return Attachment.class;
	}
	/**
		通过AttachmentQuery对象，查询附件表
	*/
	public Page findPage(AttachmentQuery query,int pageSize,int pageNum)
	{
		StringBuilder hql=new StringBuilder();
		hql.append(" from Attachment ett where 1=1");
		List param=new ArrayList();
		if(query!=null)
		{
			if(!StringUtils.isEmpty(query.getId()))
			{
				hql.append(" and ett.id=?");
				param.add(query.getId());
			}
			if(!StringUtils.isEmpty(query.getFileName()))
			{
				hql.append(" and ett.fileName=?");
				param.add(query.getFileName());
			}
			if(!StringUtils.isEmpty(query.getFilePath()))
			{
				hql.append(" and ett.filePath=?");
				param.add(query.getFilePath());
			}
			if(!StringUtils.isEmpty(query.getUriFileName()))
			{
				hql.append(" and ett.uriFileName=?");
				param.add(query.getUriFileName());
			}
			if(!StringUtils.isEmpty(query.getExtension()))
			{
				hql.append(" and ett.extension=?");
				param.add(query.getExtension());
			}
			if(!StringUtils.isEmpty(query.getFullName()))
			{
				hql.append(" and ett.fullName=?");
				param.add(query.getFullName());
			}
			if(!StringUtils.isEmpty(query.getRefId()))
			{
				hql.append(" and ett.refId=?");
				param.add(query.getRefId());
			}
			if(!StringUtils.isEmpty(query.getAddUserId()))
			{
				hql.append(" and ett.addUserId=?");
				param.add(query.getAddUserId());
			}
			if(!StringUtils.isEmpty(query.getAddUserName()))
			{
				hql.append(" and ett.addUserName=?");
				param.add(query.getAddUserName());
			}
			if(query.getTs()!=null)
			{
				hql.append(" and ett.ts=?");
				param.add(query.getTs());
			}
			if(query.getFileSize()!=null)
			{
				hql.append(" and ett.fileSize=?");
				param.add(query.getFileSize());
			}
		}
		if(!StringUtils.isEmpty(query.getSortColumns())){
			if(!query.getSortColumns().equals("ts")){
				hql.append("  order by ett."+query.getSortColumns()+" , ett.ts desc ");
			}else{
				hql.append(" order by ett.ts desc ");
			}
		}else{
			hql.append(" order by ett.ts desc ");
		}
		return super.findByHql(hql.toString(), pageSize, pageNum, param.toArray());
	}
	public Attachment getByFinanceId(final String id) {
		final String sql = "select * from attachment where refId=?";
		
		
		Attachment list = (Attachment)this.getHibernateTemplate().execute(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				query.setParameter(0, id);
				return query.list();
			}
			
		
		});
		return list;
	}
	
	
}
