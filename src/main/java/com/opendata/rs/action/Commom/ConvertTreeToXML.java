package com.opendata.rs.action.Commom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.opendata.rs.model.RsStructure;

/**
 * 将LIST组装成树形结构，并以xml形式组成字符串返回
 * @author 王海龙
 *
 */
public class ConvertTreeToXML {

	public static String convertTreeToXML(List<RsStructure> list,String ctx,RsStructure... sets) {
		Document document = DocumentHelper.createDocument();  
	    document.setXMLEncoding("UTF-8");  
	    Element root = document.addElement("tree");  
	    root.addAttribute("id", "0");
	    if(list==null || list.size()==0)
	    	return document.asXML();
	    for(RsStructure rss : list) {
				Element item = root.addElement("item");  
			    item.addAttribute("text", rss.getName());  
			    item.addAttribute("id", rss.getId());  
			    item.addAttribute("im0", "folderClosed.gif");  
			    item.addAttribute("im1", "folderOpen.gif");  
			    item.addAttribute("im2", "folderClosed.gif");
			    if(sets!=null && sets.length!=0) {
			    	for(RsStructure rs : sets) {
			    		if(rs.getId().equals(rss.getId()))
			    			item.addAttribute("checked","1");
			    	}
			    }
			    Element userdata = item.addElement("userdata");
			    userdata.addAttribute("name", "url");
			    userdata.setText(ctx+"/rs/RsResources!findRootChildren.do?tid="+rss.getId());
			    convertTreeToXML(item, rss.getRsStructures(),ctx,sets);
	    }
		return document.asXML();
	}
	
	public static  void convertTreeToXML(Element itemparent, Set<RsStructure> set,String ctx,RsStructure... sets) {
		List<RsStructure> list = new ArrayList<RsStructure>(set);
		Collections.sort(list);
		for(RsStructure rss : list) {
			Element item = itemparent.addElement("item");  
		    item.addAttribute("text", rss.getName());  
		    item.addAttribute("id", rss.getId());  
		    item.addAttribute("im0", "folderClosed.gif");  
		    item.addAttribute("im1", "folderOpen.gif");  
		    item.addAttribute("im2", "folderClosed.gif");
			if (sets != null && sets.length != 0) {
				for (RsStructure rs : sets) {
					if (rs.getId().equals(rss.getId()))
						item.addAttribute("checked", "1");
				}
			}
		    Element userdata = item.addElement("userdata");
		    userdata.addAttribute("name", "url");
		    userdata.setText(ctx+"/rs/RsResources!findRootChildren.do?tid="+rss.getId());
		    convertTreeToXML(item, rss.getRsStructures(),ctx,sets);
		}
	}
}
