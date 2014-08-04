/**
 * 
 */
package cn.com.opendata.attachment.model;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 刘丰
 */
public interface Attachment extends Serializable
{

	/**
	 *获取附近名称
	 */
	public String getAttachmentName();

	/**
	 * 设置附件名称
	 */
	public void setAttachmentName(String attachmentName);

	/**
	 *获得下载次数
	 */
	public Integer getDownloadCount();

	/**
	 *设置下载次数
	 */
	public void setDownloadCount(Integer downloadCount);

	/**
	 * 获得主键ID
	 */
	public String getId();

	/**
	 * 设置主键ID
	 *            
	 */
	public void setId(String id);

	/**
	 * 获得创建时间
	 */
	public Date getCreateDate();

	/**
	 * 设置创建时间
	 *            
	 */
	public void setCreateDate(Date createDate);

	/**
	 * 获得文件大小
	 */
	public Long getFileSize();

	/**
	 * 设置文件大小
	 *            
	 */
	public void setFileSize(Long fileSize);

	/**
	 *获得文件存储目录
	 */
	public String getStorageDir();

	/**
	 * 设置文件存储目录
	 *            
	 */
	public void setStorageDir(String storageDir);

	/**
	 * 获得客户端ID,即附件存储根目录名称
	 */
	public String getClientId();

	/**
	 * 设置客户端ID,即附件存储根目录名称
	 *            
	 */
	public void setClientId(String clientId);

	/**
	 * 获得附件后缀名
	 */
	public String getAttachmentSuffix();

	/**
	 * 设置附件后缀名
	 *           
	 */
	public void setAttachmentSuffix(String attachmentSuffix);
	
	/**
	 * 
	 * 获得附件外部ID
	 */
	public String getExteriorId();

	/**
	 * 设置附件外部ID
	 */
	public void setExteriorId(String exteriorId);
	
	/**
	 * 获得附件所属应用模块名称
	 */
	public String getAttachmentMode() ;

	/**
	 * 设置附件所属应用模块名称
	 */
	public void setAttachmentMode(String attachmentMode);
	
	/**
	 * 获得附件内容
	 * 附件内容以base64方式将原文件转换成字符串
	 */
	public String getAttachmentContent();
	
	/**
	 * 设置附件内容
	 * 附件内容以base64方式将原文件转换成字符串
	 */
	public void setAttachmentContent(String attachmentContent);
	
	/**
	 * 获得附件输入流
	 * @return
	 */
	public InputStream getAttachmentInputStream() throws Exception;

}
