package com.opendata.rs.action;

import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opendata.common.base.BaseStruts2Action;

import com.opendata.rs.service.RsResourcesManager;
import com.opendata.rs.service.RsTypeManager;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * 资源展示
 * @author 王海龙
 */
@Namespace("/rs")
@Results({
@Result(name="SHOW_JSP", location="/WEB-INF/pages/rs/resoucesShow/officeedit.jsp", type="dispatcher"),
})
public class RsresoucesShowAction extends BaseStruts2Action implements Preparable, ModelDriven {

	private RsTypeManager rsTypeManager;
	private RsResourcesManager rsResourcesManager;
	
    private String docPath;//文件路径,相对路径
    public InputStream fileStream; //文件流
  
    private ServletContext context;
	
    public String show() {
    	return SHOW_JSP;
    }
    
    public void fileIo() {
        String path = "/" + docPath;
        fileStream = context.getResourceAsStream(path);
    }
    
	@Override
	public Object getModel() {
		return null;
	}

	@Override
	public void prepare() throws Exception {
	}
	
	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setRsTypeManager(RsTypeManager manager) {
		this.rsTypeManager = manager;
	}	
	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setRsResourcesManager(RsResourcesManager manager) {
		this.rsResourcesManager = manager;
	}

	public InputStream getFileStream() {
		return fileStream;
	}

	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	
}
