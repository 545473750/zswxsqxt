package com.opendata.sys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.opendata.common.util.StrUtil;
import com.opendata.sys.dao.AttachmentDao;
import com.opendata.sys.model.Attachment;
import com.opendata.sys.vo.query.AttachmentQuery;

/**
  	 describe:附件表服务类
	 
*/
@Service
@Transactional
public class AttachmentManager extends BaseManager<Attachment, String>
{

	private AttachmentDao attachmentDao;

	public void setAttachmentDao(AttachmentDao dao)
	{
		this.attachmentDao = dao;
	}

	public EntityDao getEntityDao()
	{
		return this.attachmentDao;
	}

	@Transactional(readOnly = true)
	public Page findPage(AttachmentQuery query)
	{
		return attachmentDao.findPage(query,query.getPageSize(),query.getPageNumber());
	}
	
	@Transactional
	public void delete(Attachment entity)
	{
		attachmentDao.delete(entity);
	}

	public Attachment getByFinanceId(String id) {
		// TODO Auto-generated method stub
		return attachmentDao.getByFinanceId(id);
	}

	public List<Attachment> findByPrepareId(String prepareId) {
		return attachmentDao.findFastByHql("from Attachment where refId=?", new Object[]{prepareId});
	}
	
	
	public List<Attachment> findMideaList(String prepareId){
		
		List<Attachment> attachments = this.attachmentDao.findAllBy("refId", prepareId);
		
		//查找视频
		if(attachments != null){
			Iterator<Attachment> it = attachments.iterator();
			while(it.hasNext()){  
				Attachment attachment = it.next();  
				String suffix = attachment.getSuffix();
				 if("flv".equalsIgnoreCase(suffix) || "f4v".equalsIgnoreCase(suffix) || "mp4".equalsIgnoreCase(suffix)){
					
				 }else if("mp3".equalsIgnoreCase(suffix)){
					 
				 }else{
					 it.remove();
				 }
			}
		}
		return attachments;
	}
	public List<Attachment> findAllBytype(String refId,Integer type) {
		return attachmentDao.findFastByHql("from Attachment where refId='"+refId+"'and attachmentType='"+type+"'");
	}
	public Attachment  findBytype(String refId,Integer type) {
		String hql = "from Attachment where refId=?and attachmentType=?";
		return  (Attachment) attachmentDao.findOneByHql(hql, refId,type);
	}

	public List<Attachment> findByRefId(String refId) {
		String hql = "from Attachment where refId=?";
		return attachmentDao.getHibernateTemplate().find(hql, refId);
	}

	/**
	 * 专题资源查询
	 * @param query
	 * @return
	 */
	public Page findPersonalList(AttachmentQuery query) {
		assert query==null;
		assert StrUtil.isNullOrBlank(query.getAddUserId());
		List<Object> values = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("select atch.id, atch.fileName, atch.fileSize, atch.resId, atch.addUserName, atch.addUserId, atch.refId, atch.ts from jsyx_group_user gu inner join attachment atch on gu.groupId=atch.refId and gu.userId=? where 1=1");
		values.add(query.getAddUserId());
		
		if ( StrUtil.isNotNullOrBlank(query.getFileName()) ) {
			sql.append(" and atch.fileName like ?");
			values.add("%"+query.getFileName()+"%");
		}
		Page page = attachmentDao.findBySql(sql.toString(), query.getPageSize(), query.getPageNumber(), values.toArray());
		List<Object[]> list = page.getResult();
		
		List<Attachment> attachments = new ArrayList<Attachment>();
		Attachment attch = null;
		for ( Object[] obj : list ) {
			attch = new Attachment();
			attch.setId(obj[0].toString());
			attch.setFileName(obj[1].toString());
			attch.setFileSize(obj[2]!=null?(Integer)obj[2]:null);
			attch.setResId(obj[3]!=null?obj[3].toString():null);
			attch.setAddUserName(obj[4].toString());
			attch.setAddUserId(obj[5].toString());
			attch.setRefId(obj[6].toString());
			attch.setTs((Date)obj[7]);
			attachments.add(attch);
		}
		page.setResult(attachments);
		return page;
	}
}
