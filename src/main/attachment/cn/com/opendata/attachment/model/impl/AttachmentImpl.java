/**
 * 
 */
package cn.com.opendata.attachment.model.impl;

import java.io.InputStream;
import java.util.Date;

import cn.com.opendata.attachment.model.Attachment;
import cn.com.opendata.attachment.util.File2Base64Code;

/**
 * @author 耿直
 */
public class AttachmentImpl implements Attachment
{

	private static final long serialVersionUID = -2902023143694699282L;

	private String id;// 附件编号,文件名与之相同
	private String clientId;// 所属模块名称
	private String storageDir;// 归档目录
	private String attachmentName;// 附件名称
	private String attachmentSuffix;// 附件后缀名
	private Integer downloadCount;// 下载次数
	private Long fileSize;// 文件大小
	private Date createDate;// 创建时间
	private String exteriorId;// 外部对象id
	private String attachmentMode;// 附件所属模块
	private String attachmentContent;// 附件內容

	public AttachmentImpl()
	{
		super();
	}

	public String getAttachmentName()
	{
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName)
	{
		this.attachmentName = attachmentName;
	}

	public Integer getDownloadCount()
	{
		return downloadCount;
	}

	public void setDownloadCount(Integer downloadCount)
	{
		this.downloadCount = downloadCount;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	public Long getFileSize()
	{
		return fileSize;
	}

	public void setFileSize(Long fileSize)
	{
		this.fileSize = fileSize;
	}

	public String getStorageDir()
	{
		return storageDir;
	}

	public void setStorageDir(String storageDir)
	{
		this.storageDir = storageDir;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	public String getAttachmentSuffix()
	{
		return attachmentSuffix;
	}

	public void setAttachmentSuffix(String attachmentSuffix)
	{
		this.attachmentSuffix = attachmentSuffix;
	}

	public String getExteriorId()
	{
		return exteriorId;
	}

	public void setExteriorId(String exteriorId)
	{
		this.exteriorId = exteriorId;
	}

	public String getAttachmentMode()
	{
		return attachmentMode;
	}

	public void setAttachmentMode(String attachmentMode)
	{
		this.attachmentMode = attachmentMode;
	}

	public String getAttachmentContent()
	{
		return this.attachmentContent;
	}
	
	public void setAttachmentContent(String attachmentContent)
	{
		this.attachmentContent=attachmentContent;
	}
	
	/**
	 * 获得附件输入流
	 * @return
	 * @throws Exception 
	 */
	public InputStream getAttachmentInputStream() throws Exception
	{
		if(this.attachmentContent!=null&&!this.attachmentContent.equals(""))
		{
			return File2Base64Code.base64ToInputStream(this.attachmentContent);
		}
		return null;
	}

}
