package com.opendata.common.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import com.opendata.rs.model.RsExpandProperty;
import com.opendata.rs.model.RsProperty;


/**
 * html工具类
 * @author 付威
 */
public class HTMLUtil {
	public static void main(String[] arg){
		Set<RsExpandProperty> rsExpandPropertys = new HashSet<RsExpandProperty>();
		rsExpandPropertys.add(new RsExpandProperty("00000001","LOM001","姓名","王海龙","文本框",null,"LOM标准库",null,null,null));
		rsExpandPropertys.add(new RsExpandProperty("00000002","LOM002","性别","女","单选组","男|女","LOM标准库",null,null,null));
		rsExpandPropertys.add(new RsExpandProperty("00000003","LOM003","课程","语文,英语,数学","多选组","化学|实训|语文|英语|数学","LOM标准库",null,null,null));
		rsExpandPropertys.add(new RsExpandProperty("00000004","LOM004","所在学院","机械工程学院","下拉菜单","生物科技学院|机械工程学院|物理工程学院","LOM标准库",null,null,null));
		rsExpandPropertys.add(new RsExpandProperty("00000005","LOM005","备注","哈哈哈哈哈哈","文本域",null,"LOM标准库",null,null,null));
//		rsExpandProp
		
		System.out.print(HTMLUtil.generateHTMLbyExpand(rsExpandPropertys));
		
	}
	
	
	/**
	 * 解析字符串获取参数集合
	 * @return
	 */
	public static Map<String,String> parseParam(String jsonParam){
//		JSONObject jsonObject = JSONObject.fromObject("{\"LOM_002\":\"女\",\"LOM_003\":\"撒旦法\",\"LOM_004\":\"反倒是\",\"LOM_005\":\"私有资源,精品资源\"}");  
		Map<String,String> params = new HashMap<String, String>();
		try {
			JSONObject jsonObject = JSONObject.fromObject(jsonParam);
			for (Iterator iter = jsonObject.keys(); iter.hasNext();) {
			    String key = (String)iter.next();  
			    System.out.println(key + "=" + jsonObject.getString(key));
			    params.put(key, jsonObject.getString(key));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return params;
	}
	
	/**
	 * 生成HTML控件组
	 * @param rsPropertys
	 * @return
	 */
	public static String generateHTML(Set<RsProperty> rsPropertys){
		StringBuffer html = new StringBuffer("<table>");
		try {
			for(RsProperty property : rsPropertys){
				html.append("\n\t<tr>");
				if(property.getInputType() != null && property.getInputType().equals("1")){//文本框
					html.append(generateTextField(property));
				}else if(property.getInputType() != null && property.getInputType().equals("2")){//文本域
					html.append(generateTextArea(property));
				}else if(property.getInputType() != null && property.getInputType().equals("3")){//单选组
					html.append(generateInputRadio(property));
				}else if(property.getInputType() != null && property.getInputType().equals("4")){//下拉菜单
					html.append(generateInputSelect(property));
				}else if(property.getInputType() != null && property.getInputType().equals("5")){//多选组
					html.append(generateInputCheckBox(property));
				}else{
					html.append("字段" + property.getAttrName() + "生成失败.");
				}
				
				html.append("\n\t</tr>");
				 
			}
			html.append("\n</table>");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return html.toString();
	}
	
	/**
	 * 生成HTML控件组
	 * @param rsPropertys
	 * @return
	 */
	public static String generateHTMLbyExpand(Set<RsExpandProperty> rsExpandPropertys){
		StringBuffer html = new StringBuffer("<table class=\"table02\" >");
		try {
			for(RsExpandProperty property : rsExpandPropertys){
				html.append("\n\t<tr>");
				if(property.getInputType() != null && property.getInputType().equals("1")){//文本框
					html.append(generateTextFieldbyExpand(property));
				}else if(property.getInputType() != null && property.getInputType().equals("2")){//文本域
					html.append(generateTextAreabyExpand(property));
				}else if(property.getInputType() != null && property.getInputType().equals("3")){//单选组
					html.append(generateInputRadiobyExpand(property));
				}else if(property.getInputType() != null && property.getInputType().equals("4")){//下拉菜单
					html.append(generateInputSelectbyExpand(property));
				}else if(property.getInputType() != null && property.getInputType().equals("5")){//多选组
					html.append(generateInputCheckBoxbyExpand(property));
				}else{
					html.append("字段" + property.getAttrName() + "生成失败.");
				}
				
				html.append("\n\t</tr>");
				 
			}
			html.append("\n</table>");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return html.toString();
	}
	
	/**
	 * 生成文本框HTML
	 * @param attribute
	 * @return
	 */
	public static String generateTextField(RsProperty property){
		String controlHtml = "\n\t\t<td align=\"right\">" + property.getAttrName() + ":</td><td><input type=\"text\" name=\"" + property.getAttrCode() + "\"/></td>";
		return controlHtml;
		
	}
	
	/**
	 * 生成文本域HTML
	 * @param attribute
	 * @return
	 */
	public static String generateTextArea(RsProperty property){
		String controlHtml = "\n\t\t<td align=\"right\">" + property.getAttrName() + ":</td><td><textarea rows=\"5\" cols=\"55\" name=\"" + property.getAttrCode() + "\"></textarea>";
		return controlHtml;
	}
	
	/**
	 * 生成单选框HTML
	 * @param attribute
	 * @return
	 */
	public static String generateInputRadio(RsProperty property){
		StringBuffer controlHtml = new StringBuffer("\n\t\t<td align=\"right\">" + property.getAttrName() + ":</td><td>");
		for(String option : property.getOptionalValue().split("\\|")){
			controlHtml.append("<input type=\"radio\" name=\"" + property.getAttrCode() + "\" value=\"" + option + "\" />" + option + "&nbsp;");
			
		}
		controlHtml.append("</td>");
		return controlHtml.toString();	
	}
	
	/** 
	 * 生成多选框HTML
	 * @param attribute
	 * @return
	 */
	public static String generateInputCheckBox(RsProperty property){
		StringBuffer controlHtml = new StringBuffer("\n\t\t<td align=\"right\">" + property.getAttrName() + ":</td><td>");
		for(String option : property.getOptionalValue().split("\\|")){
			controlHtml.append("<input type=\"checkbox\" name=\"" + property.getAttrCode() + "\" value=\"" + option + "\" />" + option + "&nbsp;");
			
		}
		controlHtml.append("</td>");
		return controlHtml.toString();	
	}
	
	/**
	 * 生成下拉菜单HTML
	 * @param attribute
	 * @return
	 */
	public static String generateInputSelect(RsProperty property){
		StringBuffer controlHtml = new StringBuffer("\n\t\t<td align=\"right\">" + property.getAttrName() + ":</td><td><select name=\"" + property.getAttrCode() + "\">");
		for(String option : property.getOptionalValue().split("\\|")){
			controlHtml.append("<option value=\"" + option + "\">" + option + "</option>");
			
		}
		controlHtml.append("</select></td>");
		return controlHtml.toString();
	}

	//-------------------------带值回显---------------------------//
	
	/**
	 * 生成文本框HTML
	 * @param attribute
	 * @return
	 */
	public static String generateTextFieldbyExpand(RsExpandProperty property){
		String controlHtml = "\n\t\t<td class=\"tdLabel\" align=\"right\">" + property.getAttrName() + ":</td><td><input type=\"text\" value=\"" + property.getAttrValue() + "\" name=\"" + property.getAttrCode() + "\"/></td>";
		return controlHtml;
		
	}
	
	/**
	 * 生成文本域HTML
	 * @param attribute
	 * @return
	 */
	public static String generateTextAreabyExpand(RsExpandProperty property){
		String controlHtml = "\n\t\t<td  class=\"tdLabel\" align=\"right\">" + property.getAttrName() + ":</td><td><textarea rows=\"5\" cols=\"55\" name=\"" + property.getAttrCode() + "\">" + property.getAttrValue() + "</textarea>";
		return controlHtml;
	}
	
	/**
	 * 生成单选框HTML
	 * @param attribute
	 * @return
	 */
	public static String generateInputRadiobyExpand(RsExpandProperty property){
		StringBuffer controlHtml = new StringBuffer("\n\t\t<td  class=\"tdLabel\" align=\"right\">" + property.getAttrName() + ":</td><td>");
		for(String option : property.getOptionalValue().split("\\|")){
			controlHtml.append("<input type=\"radio\"");
			if(property.getAttrValue().equals(option)){//回显被选中的单选
				controlHtml.append(" checked=\"checked\"");
			}
			controlHtml.append(" name=\"" + property.getAttrCode() + "\" value=\"" + option + "\" />" + option + "&nbsp;");
		}
		controlHtml.append("</td>");
		return controlHtml.toString();	
	}
	
	/** 
	 * 生成多选框HTML
	 * @param attribute
	 * @return
	 */
	public static String generateInputCheckBoxbyExpand(RsExpandProperty property){
		StringBuffer controlHtml = new StringBuffer("\n\t\t<td  class=\"tdLabel\" align=\"right\">" + property.getAttrName() + ":</td><td>");
		for(String option : property.getOptionalValue().split("\\|")){
			controlHtml.append("<input type=\"checkbox\"");
			for(String selectedItem : property.getAttrValue().split(",")){
				if(selectedItem.equals(option)){
					controlHtml.append(" checked=\"checked\"");
					break;
				}
			}
			
			controlHtml.append(" name=\"" + property.getAttrCode() + "\" value=\"" + option + "\" />" + option + "&nbsp;");
			
		}
		controlHtml.append("</td>");
		return controlHtml.toString();	
	}
	
	/**
	 * 生成下拉菜单HTML
	 * @param attribute
	 * @return
	 */
	public static String generateInputSelectbyExpand(RsExpandProperty property){
		StringBuffer controlHtml = new StringBuffer("\n\t\t<td class=\"tdLabel\" align=\"right\">" + property.getAttrName() + ":</td><td><select name=\"" + property.getAttrCode() + "\">");
		for(String option : property.getOptionalValue().split("\\|")){
			controlHtml.append("<option");
			if(property.getAttrValue().equals(option)){
				controlHtml.append(" selected=\"selected\"");
			}
			controlHtml.append(" value=\"" + option + "\">" + option + "</option>");
		}
		controlHtml.append("</select></td>");
		return controlHtml.toString();
	}

}


