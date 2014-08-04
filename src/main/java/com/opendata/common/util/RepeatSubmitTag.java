package com.opendata.common.util;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 此标签生成表单提交的唯一标识，用于解决表单重复提交产生的问题。
 * 
 * @author Administrator
 * 
 */
public class RepeatSubmitTag extends TagSupport
{
	public static String repeatSubmitName = "_Form_RepeatSubmit_Id_";
	private String type = "hidden"; // 当类型为hidden 时，则输出一个隐藏域 当类型为param时，则直接输出 属性

	public int doStartTag()
	{
		try
		{
			Long formRepeatSubmitId = IdFactory.getId();
			HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
			request.getSession().setAttribute(repeatSubmitName, formRepeatSubmitId);
			Writer out = this.pageContext.getOut();
			if (type.equals("param"))
			{
				out.write(RepeatSubmitTag.repeatSubmitName + "=" + formRepeatSubmitId);
			} else
			{
				out.write("<input type='hidden' name='" + RepeatSubmitTag.repeatSubmitName + "' value='" + formRepeatSubmitId + "' />");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return this.SKIP_BODY;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

}
