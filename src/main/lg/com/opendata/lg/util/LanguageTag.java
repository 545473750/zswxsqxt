package com.opendata.lg.util;

import java.io.Writer;

import javax.servlet.jsp.tagext.TagSupport;

import com.opendata.common.util.Platform;
import com.opendata.lg.model.Language;
import com.opendata.lg.service.LanguageManager;

/**
 * 此标签生成表单提交的唯一标识，用于解决表单重复提交产生的问题。
 * 
 * @author Administrator
 * 
 */
public class LanguageTag extends TagSupport
{
	private String code;

	public int doStartTag()
	{
		try
		{
			Writer out = this.pageContext.getOut();
			Object type = this.pageContext.getSession().getAttribute("languageType");
			String strType = "";
			if (type != null)
			{
				strType = (String) type;
			}
			// 取缓存中的内容
			Language lg = LanguageStore.getLanguage(code);
			if (lg == null)
			{
				// 从数据库中查询
				LanguageManager languageManager = Platform.getBean("languageManager");
				Language flg = languageManager.getLanguage(code);
				if (flg == null)
				{
					// 没有直接输入
					out.write(code);
				} else
				{
					// 先放入缓存，再输出
					LanguageStore.putLanguage(flg);
					out.write(this.getLanguageText(flg, strType));
				}
			} else
			{
				// 直接使用缓存中
				out.write(this.getLanguageText(lg, strType));
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return this.SKIP_BODY;
	}

	private String getLanguageText(Language lg, String type)
	{
		if ("english".equals(type.trim().toLowerCase()))
		{
			return lg.getEnglish();
		} else if ("chinese".equals(type.trim().toLowerCase()))
		{
			return lg.getChinese();
		} else if ("japanese".equals(type.trim().toLowerCase()))
		{
			return lg.getJapanese();
		} else if ("russian".equals(type.trim().toLowerCase()))
		{
			return lg.getRussian();
		} else
		{
			return lg.getChinese();
		}
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}
}
