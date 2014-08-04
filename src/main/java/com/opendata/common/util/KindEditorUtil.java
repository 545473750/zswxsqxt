package com.opendata.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.opendata.common.util.PropertyUtil;

/**
 * KindEditor工具类
 * 
 * @author liufeng
 * 
 */
public class KindEditorUtil
{
	/**
	 * 通过session的key远程查询图片服务器上的图片显示路径
	 * 
	 * @return
	 */
	public String getPicUrlBySessionKey(String key)
	{
		if ("".equals(key))
		{
			return "";
		}
		String showPicUrl = "";
		String urlpath= PropertyUtil.getProperty("innersys.att.path");
		try
		{
			URL url = new URL(urlpath + "?key=" + key + "&type=show");
			URLConnection uc = url.openConnection();
			InputStream is = uc.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while (br.ready())
			{
				showPicUrl += br.readLine();
			}
			is.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return showPicUrl;
	}
}
