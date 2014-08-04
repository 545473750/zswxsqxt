package com.opendata.common.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 工具类
 * @author 顾保臣
 */
public class Util {
	public static final String ORIGINAL_PASSWORD = "00000000";
	private static Log log = LogFactory.getLog(Util.class);
	public static final String EMPTY_STING = "";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 取得当前时间
	 * @return 当前时间的字符串
	 */
	public static String getTimeStampString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		return date;
	}
	
	/**
	 * 对字符串进行判NULL 如果是NULL返回""
	 * @param str
	 * @return
	 */
	public static String getString(String str) {
		if (str == null) {
			str = EMPTY_STING;
		}
		return str;
	}
	
	/**
	 * 将date对象根据一定格式yyyy-MM-dd返回String
	 * @param date
	 * @return 
	 */
	public static String getTimeByDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sdate = sdf.format(date);
		return sdate;
	}
	/**
	 * 指定日期的前n天
	 * @param date
	 * @param n
	 * @return
	 */
	public static String getPreTimeByDate(Date date,int n) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)-n);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String sdate = sdf.format(cal.getTime());
		return sdate;
	}
	/**
	 * 将date对象根据一定格式yyyy-MM-dd HH:mm:ss返回String
	 * @param date
	 * @return
	 */
	public static String getTimeStampByDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sdate = sdf.format(date);
		return sdate;
	}
	
	/**
	 * 返回当前日期根据一定格式yyyy-MM-dd返回String
	 * @param date
	 * @return 
	 */
	public static String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		return date;
	}
	
	/**
	 * 根据传过来的日期格式返回当前时间
	 * @param format
	 * @return
	 */
	public static String getDate(String format)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String date=sdf.format(new Date());
		return date;
	}
	
	/**
	 * @author wq
	 * @param str 需要转化得汉字字符串
	 * @return 拼音字符串
	 * @throws BadHanyuPinyinOutputFormatCombination
	 */
	public static String convertPinYin(String str)
	{
		log.fatal("拼音转换开始");
		HanyuPinyinOutputFormat hptt = new HanyuPinyinOutputFormat();
		hptt.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		hptt.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		hptt.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
		char [] chs = new char[str.length()];
		str.getChars(0, str.length(), chs, 0);
		StringBuffer dst = new StringBuffer();
		  
		try {
		for(char ch : chs)
		{
				String[] pinyin;
				
				if(isChinese(ch)){
				pinyin = PinyinHelper.toHanyuPinyinStringArray(ch, hptt);
				dst.append(pinyin[0]);
				}else
				{
					dst.append(ch);
				}
		
		}
	} catch (BadHanyuPinyinOutputFormatCombination e) {
		log.fatal("拼音转换结束");
		return dst.toString().trim();
	}
	log.fatal("拼音转换结束");
	return dst.toString().trim();
	}
	
	// GENERAL_PUNCTUATION 判断中文的“号
	 // CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号
	 // HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
	/**
	 * 判断是否中文
	 */
	 private static final boolean isChinese(char c) {
	  Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
	  if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
	    || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
	    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
	    || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
	    || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
	    || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
	   return true;
	  }
	  return false;
	 }
	
	 /**
	  * 判断是否为数字
	  * @param chr
	  * @return
	  */
	public static boolean isNumeric(char chr){ 
		final String number = "0123456789";
	  
		         if(number.indexOf(chr) == -1){   
		             return false;   
		            }
		         return true;
	}
	
	/**
	 * 将字符串转化为日期。 字符串格式("YYYY-MM-DD")。
	 * 例如："2002-07-01"或者"2002-7-1"或者"2002-7-01"或者"2002-07-1"是等价的。
	 */
	public static Date StringToDate(String str) {
		Date dateTime = null;
		try {
			if (!(str == null || str.equals(""))) {
				java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat("yyyy-MM-dd");
				dateTime = formater.parse(str);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dateTime;
	}
	
	/**
	 * 将字符串转化为日期。 字符串格式("YYYY-MM-DD")。
	 * 例如："2002-07-01"或者"2002-7-1"或者"2002-7-01"或者"2002-07-1"是等价的。
	 */
	public static Date StringToDate(String str, String pattern) {
		Date dateTime = null;
		try {
			if (!(str == null || str.equals(""))) {
				java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(pattern);
				dateTime = formater.parse(str);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return dateTime;

	}
	
	
	public static String DateToString(Date date , String pattern) {
		String dateTime = null;
		try {
			if (!(pattern == null || pattern.equals(""))) {
				java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(pattern);
				dateTime = formater.format(date);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return dateTime;

	}
	/**
	 * 比较日期大小,如果结束时间大于开始时间，则返回true，否则返回false
	 * @param begin 开始时间
	 * @param end 结束时间
	 * @return
	 */
	public static boolean compareDate(String begin, String end) {
		boolean flag = true;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(begin);
            Date dt2 = df.parse(end);
            if (dt1.getTime() > dt2.getTime()) {
            	flag = false;
            } 
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return flag;
    }
	
	//根据年月查询该月最后一天
	public static String getLastDayOfMonth(String yearAndmonth){
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.valueOf(yearAndmonth.split("-")[0]),Integer.valueOf(yearAndmonth.split("-")[1]), 0);
		return getTimeByDate(cal.getTime());
	}
	
	public static Map<Object,Object> sortMapByKey(Map<Object,Object> map){
		Map<Object,Object> newmap = new LinkedHashMap<Object,Object>();
		Object[] objs = map.keySet().toArray();
		Arrays.sort(objs);
		for(Object obj:objs){
			String str = (String)obj;
			newmap.put(str, map.get(str));
		}
		return newmap;
	}
	
	public static String toUtf8String(String s){ 
	     StringBuffer sb = new StringBuffer(); 
	       for (int i=0;i<s.length();i++){ 
	          char c = s.charAt(i); 
	          if (c >= 0 && c <= 255){sb.append(c);} 
	        else{ 
	        byte[] b; 
	         try { b = Character.toString(c).getBytes("utf-8");} 
	         catch (Exception ex) { 
	             System.out.println(ex); 
	                  b = new byte[0]; 
	         } 
	            for (int j = 0; j < b.length; j++) { 
	             int k = b[j]; 
	              if (k < 0) k += 256; 
	              sb.append("%" + Integer.toHexString(k).toUpperCase()); 
	              } 
	     } 
	  } 
	  return sb.toString(); 
	}
}
