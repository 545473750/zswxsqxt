package cn.com.opendata.attachment.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import cn.com.opendata.attachment.ClientAttachmentApplication;

import com.opendata.common.util.Platform;

public class ServletServiceUtil
{
	// 调用服务方法
	public static String transferWebService(String method, String... str)
	{
		String result = "";
		try
		{
			ClientAttachmentApplication attachmentApp = (ClientAttachmentApplication) Platform.getBean(ClientAttachmentApplication.Bean);
			String servicesParm = "";
			for (String parm : str)
			{
				servicesParm += parm + "|";
			}
			URL url = new URL(attachmentApp.getWebservicesUrl() + "?servicesName=" + method + "&servicesParm=" + servicesParm);
			URLConnection uc = url.openConnection();
			InputStream is = uc.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));  
			while (br.ready())
			{
				result += br.readLine();
			}
			is.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
}
