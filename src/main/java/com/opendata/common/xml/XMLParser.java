package com.opendata.common.xml;

import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * XML工具类
 * @author 付威
 */
public class XMLParser {

	/**
	 * 获取XML根节点
	 * @param xmlFile
	 * @return
	 * @throws Exception
	 */
	public static Element getRootElement(String xmlFile) throws Exception{
		return new SAXBuilder().build(xmlFile).getRootElement(); 
	}
	
	
}