package com.opendata.common.struts2.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.opendata.common.util.RepeatSubmitTag;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
/**
 * 重复提交过滤器
 * @author Administrator
 *
 */
public class RepeatSubmitInterceptor implements Interceptor
{

	private static final long serialVersionUID = 1L;

	public void init()
	{
	}

	public String intercept(ActionInvocation invocation) throws Exception
	{
		ActionContext actionContext = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
		//统一进行重复提交处理
		if(!this.checkRepeatSubmit(request))
		{
			return invocation.invoke();
		}
		return "repeatSubmit";
	}
	/**
	 * 检查是否为重复提交
	 * 
	 * @return
	 */
	public boolean checkRepeatSubmit(HttpServletRequest request)
	{
		boolean isRepeatSubmit = true;
		Object submitKey =request.getSession().getAttribute(RepeatSubmitTag.repeatSubmitName);
		Object paramSubmitKey = request.getParameter(RepeatSubmitTag.repeatSubmitName);
		if (paramSubmitKey != null)
		{
			if (submitKey != null)
			{
				Long longSubmitKey = (Long) submitKey;
				Long longParamSubmitKey = Long.parseLong(paramSubmitKey.toString());
				if (longSubmitKey.longValue() == longParamSubmitKey.longValue())
				{
					isRepeatSubmit = false;
					request.getSession().removeAttribute(RepeatSubmitTag.repeatSubmitName);
				}
			}
		} else
		{
			isRepeatSubmit = false;
		}
		return isRepeatSubmit;
	}

	public void destroy()
	{
	}

}
