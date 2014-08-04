package com.opendata.webservice.sys;

import javax.jws.WebService;

import cn.com.opendata.attachment.model.impl.AttachmentImpl;

@WebService
public interface PictureService {
	/**
	 * 存储附件webservice服务方法（用于前台附件上传）
	 * @see cn.com.opendata.attachment.service.impl.WebAttachManagerImpl#saveAttach(String exteriorId, String clientId, String attach_form_key, String uploaded_file, String attachmentMode)
	 * @param exteriorId	外键ID
	 * @param clientId		客户端ID
	 * @param attach_form_key	图片key
	 * @param uploaded_file		图片控件的名称
	 * @param attachmentMode	自定义模型名称
	 * @return
	 */
	String saveAttach(String exteriorId, String clientId, String attach_form_key, String uploaded_file, String attachmentMode);
	
	
	/**
	 * 按照附件id删除附件
	 * @see cn.com.opendata.attachment.service.impl.WebAttachManagerImpl#deleteAttach(String exteriorId, String isDeleteAll, String attachId, String clientId)
	 * @param exteriorId		外键ID
	 * @param isDeleteAll	
	 * @param attachId
	 * @param clientId
	 * @return
	 */
	String deleteAttach(String exteriorId, String isDeleteAll, String attachId, String clientId);
	
	/**
	 * 复制文章附件 
	 * @see @see cn.com.opendata.attachment.service.impl.WebAttachManagerImpl#copeAttachs(String attachmentIds, String newExteriorId, String clientId)
	 * @param attachmentIds
	 * @param newExteriorId
	 * @param clientId
	 * @return
	 */
	String copeAttachs(String attachmentIds, String newExteriorId, String clientId);
	
	/**
	 * 根据外键ID查找附件的集合
	 * @param exteriorId	外键的ID
	 * @param clientId		客户端ID
	 * @param attachmentMode	附件的模块
	 * @return
	 */
	AttachmentImpl [] findAttachment(String exteriorId, String clientId,String attachmentMode);
}
