/**
 * 
 */
package cn.com.opendata.attachment.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.opendata.attachment.ClientAttachmentApplication;
import cn.com.opendata.attachment.util.UUIDGenerator;

import com.opendata.common.util.Platform;

/**
 * @author 耿直
 */
public class UploadUIServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6265324724507082532L;

	// 附件管理应用
	private static ClientAttachmentApplication attachmentApp = (ClientAttachmentApplication) Platform.getBean(ClientAttachmentApplication.Bean);

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String fieldName = request.getParameter("fieldName");
		String objectId = request.getParameter("objectId");
		String clientId = request.getParameter("attach_client_id");
		String previewimg = request.getParameter("previewimg");
		String uploadAction = attachmentApp.getUploadPath();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		// 处理客户端附件上传
		String key = "";
		String isClient = "";
		{
			isClient = request.getParameter("isClient");
			if ("1".equals(isClient))
			{
				key = UUIDGenerator.getInstance().generate();
			} else
			{
				isClient = "";
			}
		}

		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"");
		out.println("\"http://www.w3.org/TR/html4/loose.dtd\">");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		out.println("<title>文件上传</title>");
		out.println("<style>");
		out.println(" table{");
		out.println("   border:4px solid #cccccc");
		out.println(" }");
		out.println(" input{");
		out.println("   height:22px;font-size:12px; ");
		out.println(" }");
		out.println("</style>");
		out.println("<script type=\"text/javascript\">");
		out.println("window.onload = function(){");
		out.println("if(!window.opener.document.getElementById('attach_client_id') || !window.opener.document.getElementById('attach_form_key')){");
		out.println("alert('非法访问,本页面即将关闭...');");
		out.println("window.close();");
		out.println("}");
		out.println("var clientId = opener.document.getElementById('attach_client_id').value;");

		// 如果来自客户端附件处理
		if ("1".equals(isClient))
		{
			// 把父窗口attach_form_key值赋值
			out.println("opener.document.getElementById('attach_form_key').value='" + key + "';");
		} else
		{
			key = request.getParameter("attach_form_key");
		}
		out.println("var key = opener.document.getElementById('attach_form_key').value;");
		out.println("document.getElementById('key').value = key;");
		out.println("document.getElementById('clientId').value = clientId;");
		out.println("}");

		// 提交参数
		out.println("function uploadSubmit(){");
		out.println("var file= document.getElementById('fileId').value;");
		out.println("var fileName='';");
		out.println("if(file!=''){");

		out.println("fileName=file.substring(file.lastIndexOf(\"\\\\\")+1,file.length);");
		out.println("}else{");
		out.println("alert('文件名不能为空!');");
		out.println("return false;");
		out.println("}");
		out.println("window.opener.document.getElementById('" + objectId + "').value = fileName;");
		out.println("window.opener.document.getElementById('" + objectId + "_').value = window.opener.document.getElementById('" + objectId + "').name;");
		out.println("return true;");
		out.println("}");

		out.println("</script>");
		out.println("</head>");
		out.println("<body style='margin: 0px'>");
		out.println("<form enctype=\"multipart/form-data\" action=\"" + uploadAction + "\" method=\"post\">");
		out.println(" <table width=\"400\" align=\"center\">");
		out.println("   <tr>");
		out.println("     <td height=\"150\" align=\"center\" valign=\"middle\">");
		out.println("       <font face=\"黑体\" size=\"+1\" color=\"#666666\">请您选择要上传的文件</font><br>");
		out.println("       <input type=\"file\" id=\"fileId\" name=\"" + fieldName + "\">");
		out.println("       <input type=\"hidden\" id=\"key\" name=\"key\" value=\"" + key + "\">");
		out.println("       <input type=\"hidden\" id=\"clientId\" name=\"clientId\" value=\"" + clientId + "\">");
		out.println("       <input type=\"hidden\" id=\"objectId\" name=\"objectId\" value=\"" + objectId + "\">");
		out.println("       <input type=\"hidden\" id=\"previewimg\" name=\"previewimg\" value=\"" + previewimg + "\">");
		// 如果来自客户端附件处理
		if ("1".equals(isClient))
		{
			out.println("       <input type=\"hidden\" id=\"isClient\" name=\"isClient\" value=\"" + isClient + "\">");
		}
		out.println("     </td>");
		out.println("   </tr>");
		out.println("   <tr>");
		out.println("     <td>");
		out.println("       <hr width=\"98%\" noshade style=\"color:#999999 \">");
		out.println("     </td>");
		out.println("   </tr>");
		out.println("   <tr>");
		out.println("     <td align=\"center\"> ");
		out.println("       <input type=\"button\" value=\"关闭窗口\" onclick=\"window.close();\">");
		out.println("       &nbsp;&nbsp;&nbsp;&nbsp;");
		out.println("       <input type=\"submit\" value=\" 上  传 \" onclick=\"return uploadSubmit();\"><br>&nbsp;");
		out.println("     </td>");
		out.println("   </tr>");
		out.println(" </table>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		doGet(request, response);
	}
}
