package com.opendata.webservice.sys.impl;

import java.util.Iterator;
import java.util.Map;

import javax.jws.WebService;

import com.opendata.common.dict.DicUtil;
import com.opendata.webservice.sys.DictWebService;

@WebService(endpointInterface="com.opendata.webservice.sys.DictWebService")
public class DictWebServiceImpl implements DictWebService{
	
	private DicUtil  dicUtil;

	public void setDicUtil(DicUtil dicUtil) {
		this.dicUtil = dicUtil;
	}

	@Override
	public String getDicList(String type) {
		Map<String,String> dicMap = dicUtil.getDicList(type);
		String result = "";
		Iterator<String> keyIterator = dicMap.keySet().iterator();
		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			result += key +":" + dicMap.get(key)+"|";
		}
		return result;
	}

	@Override
	public String getDicValue(String type, String key) {
		return dicUtil.getDicValue(type, key);
	}
    
}
