/**
 * 
 */
package cn.com.opendata.attachment.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import cn.com.opendata.attachment.ClientAttachmentApplication;
import cn.com.opendata.attachment.util.UUIDGenerator;

import com.opendata.common.util.Platform;

/**
 * @author 耿直
 */
public class AttachmentFileUploadTag extends TagSupport
{
	public static final String DEST_FOLDER_NAME = "/cn.com.opendata.attachments/";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1192186383226620289L;

	public static final String UPLOAD_KEY_NAME = "UPLOAD_KEY";

	public static final String HAS_SCRIPT_NAME = "HAS_SCRIPT";

	private String buttontext = "上 传";
	private String propertytext = "";

	public String getPropertytext() {
		return propertytext;
	}

	public void setPropertytext(String propertytext) {
		this.propertytext = propertytext;
	}

	private String inserthtml = "&nbsp;";

	private String name = "";

	private String client = "clientId";

	private String styleid = "";

	private String moduleid = "";

	private String previewimg = "false";

	// 附件管理应用
	private static ClientAttachmentApplication attachmentApp = (ClientAttachmentApplication) Platform.getBean(ClientAttachmentApplication.Bean);

	public int doStartTag()
	{
		JspWriter out = this.pageContext.getOut();
		String key = (String) this.pageContext.getAttribute(UPLOAD_KEY_NAME);
		String clientId = (String) this.pageContext.getRequest().getAttribute(client);
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		String resultPath = request.getContextPath() + AttachmentFileUploadTag.DEST_FOLDER_NAME;
		if (clientId == null || "".equals(clientId))
		{
			try
			{
				throw new NullPointerException("在request中未找到键为" + client + "的客户端ID,请将本表单上传操作使用的clientId设置到request中");
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}

		if (key == null || "".equals(key))
		{
			key = UUIDGenerator.getInstance().generate();
			this.pageContext.setAttribute(UPLOAD_KEY_NAME, key);
			try
			{
				out.println("<style type='text/css'>/* CSS Document */div#PreviewBox{  position:absolute;  padding-left:6px; display: none;  Z-INDEX:2006;}div#PreviewBox span{  width:7px;  height:13px;  position:absolute;  left:0px; top:9px;  background:url(" + resultPath
						+ "arrow.gif) 0 0 no-repeat;}div#PreviewBox div.Picture{ float:left; border:1px #666 solid;  background:#FFF;}div#PreviewBox div.Picture div{  border:4px #e8e8e8 solid;}div#PreviewBox div.Picture div a img{ margin:19px;  border:1px #b6b6b6 solid; display: block; max-height: 250px;  max-width: 250px;}</style>");
				out
						.println("<div id=\"PreviewBox\" onmouseout=\"hidePreview(event);\"> <div class=\"Picture\" onmouseout=\"hidePreview(event);\">    <span></span>   <div>     <a id=\"previewUrl\" href=\"javascript:void(0)\" target=\"_blank\"><img oncontextmenu=\"return(false)\" id=\"PreviewImage\" src=\"about:blank\" border=\"0\" onmouseout=\"hidePreview(event);\" /></a>    </div>  </div></div>");
				//out.println("<script type=\"text/javascript\" src=\"" + resultPath + "ImageDialog.js\"></script>");
				//out.println("<script type=\"text/javascript\">");
				//out.println("loadingImg = '" + resultPath + "' + loadingImg;");
				//out.println("</script>");
				out.println("<input type='hidden' id='attach_form_key' name='attach_form_key' value='" + key + "'>");
				out.println("<input type='hidden' id='attach_client_id' name='attach_client_id' value='" + clientId + "'>");
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		try
		{
			if (!"true".equals(this.pageContext.getAttribute(HAS_SCRIPT_NAME)))
			{
				out.println(createJavaScript());
			}
			out.println("<div");
			if (!"".equals(moduleid) || null != moduleid)
			{
				out.print(" id='" + moduleid + "'");
			}
			out.print(">");
			out.print("<input type='text' name='" + name + "' readonly id='" + styleid + "'");
			if ("true".equals(previewimg))
			{
				out.print(" onmouseover='showPreview(event);' onmouseout='hidePreview(event);'");
			}
//			out.print(" datatype='string' tip='请选择附件!' ");
			out.print(this.propertytext);
			out.print("/>");
			out.println(inserthtml);
			out.println("<input type='button' value='" + buttontext + "' onclick='odUploadFile(\"" + styleid + "\",\"" + name + "\")' />");
			out.println("<input type='hidden' name='uploaded_file' id='" + styleid + "_'/>");
			out.println("</div>");
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return EVAL_PAGE;
	}

	private String createJavaScript()
	{
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		String path =  request.getContextPath()+attachmentApp.getUploadUIPath();
		StringBuffer script = new StringBuffer("<script type=\"text/javascript\">");
		script.append("\nfunction odUploadFile(objectId, objectName){");
		script.append("\n  window.open(\"" + path + "?objectId=\" + objectId + \"&fieldName=\" + objectName + \"&previewimg=" + previewimg + "\", \"odUploadWindow\", \"height=229, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no\");");
		script.append("\n}");
		script.append("\nfunction createUploadFileModuleHTML(objectId, objectName, moduleId){");
		script.append("\n  var htmlCode = \"<div id=\" + moduleId + \">\";");
		script.append("\n  htmlCode += \"<input type='text' name='\" + objectName + \"' readonly id='\" + objectId + \"'>\";");
		script.append("\n  htmlCode += \"" + inserthtml + "\";");
		script.append("\n  htmlCode += \"<input type='button' class='buttonbox' value='" + buttontext + "' onclick='odUploadFile(\" + objectId + \",\" + objectName + \")'>\";");
		script.append("\n  htmlCode += \"<input type='hidden' name='uploaded_file' />\";");
		script.append("\n  htmlCode += \"</div>\";");
		script.append("\n  return htmlCode;");
		script.append("\n}");
		script.append("\n</script>");
		this.pageContext.setAttribute(HAS_SCRIPT_NAME, "true");
		return new String(script);
	}

	/**
	 * @return the buttonText
	 */
	public String getButtontext()
	{
		return buttontext;
	}

	/**
	 * @param buttonText
	 *            the buttonText to set
	 */
	public void setButtontext(String buttontext)
	{
		this.buttontext = buttontext;
	}

	/**
	 * @return the client
	 */
	public String getClient()
	{
		return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(String client)
	{
		this.client = client;
	}

	/**
	 * @return the insertHtml
	 */
	public String getInserthtml()
	{
		return inserthtml;
	}

	/**
	 * @param insertHtml
	 *            the insertHtml to set
	 */
	public void setInserthtml(String inserthtml)
	{
		this.inserthtml = inserthtml;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the styleid
	 */
	public String getStyleid()
	{
		return styleid;
	}

	/**
	 * @param styleid
	 *            the styleid to set
	 */
	public void setStyleid(String styleid)
	{
		this.styleid = styleid;
	}

	/**
	 * @return the previewimg
	 */
	public String getPreviewimg()
	{
		return previewimg;
	}

	/**
	 * @param previewimg
	 *            the previewimg to set
	 */
	public void setPreviewimg(String previewimg)
	{
		this.previewimg = previewimg;
	}

	/**
	 * @return the moduleid
	 */
	public String getModuleid()
	{
		return moduleid;
	}

	/**
	 * @param moduleid
	 *            the moduleid to set
	 */
	public void setModuleid(String moduleid)
	{
		this.moduleid = moduleid;
	}

}
