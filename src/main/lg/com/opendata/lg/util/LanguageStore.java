package com.opendata.lg.util;

import java.util.HashMap;
import java.util.Map;

import com.opendata.lg.model.Language;

public final class LanguageStore
{

	public static final Map<String,Language>  language=new HashMap<String,Language>();
	
	public static void putLanguage(Language language)
	{
		if(language!=null)
		{
			LanguageStore.language.put(language.getCode(), language);
		}
	}
	
	public static Language getLanguage(String code)
	{
		if(language.containsKey(code))
		{
			return language.get(code);
		}
		return null;
	}
}
