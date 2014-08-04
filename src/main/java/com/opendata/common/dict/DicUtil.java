package com.opendata.common.dict;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;

import com.opendata.sys.model.Dictvalue;
import com.opendata.sys.service.DictvalueManager;

/**
 * 用于取得数据字典的工具类
 * @author 顾保臣
 *
 */
@Controller
public class DicUtil {
	
	private DicCache dicCache;
	private DictvalueManager dictvalueManager;
	public static final String DEPT_TYPE = "DEPTTYPE";
	public static final String CLASS_DEPT_TYPE = "CLASSDEPTTYPE";
	
	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setdictvalueManager(DictvalueManager manager) {
		this.dictvalueManager = manager;
	}

	public DicUtil () {
		dicCache = DicCache.getInstance();
		
		//deptCache = DeptCache.getInstance();
		//install();
	}
	
	/**
	 * 初始化函数执行完毕后，执行缓存的初始化操作
	 */
	@PostConstruct
	public void install() {

		dicCache.removeAll();
		Map<String, List<Dictvalue>> map = dictvalueManager.getDicValueList();
		Set<String> set = map.keySet();
		for (String key : set) {
			List<Dictvalue> dicvallist = map.get(key);
			Map<String, String> dicmap = new HashMap<String, String>();
			for (Dictvalue dictvalue : dicvallist) {
				dicmap.put(dictvalue.getCode(), dictvalue.getValue());
			}
			dicCache.put(key, dicmap);
		}
	}
	
	/**
	 * 更新整个数据字典缓存
	 */
	public void update() {
		Map<String,List<Dictvalue>> map = dictvalueManager.getDicValueList();
		Set<String> set = map.keySet();
		for (String key : set) {
			List<Dictvalue> dicvallist = map.get(key);
			Map<String,String> dicmap = new HashMap<String, String>();
			for (Dictvalue dictvalue :dicvallist) {
				dicmap.put(dictvalue.getCode(), dictvalue.getValue());
			}
			dicCache.put(key, dicmap);
		}
	}
	
	/**
	 * 通过字典项和字典值CODE取得字典值
	 * @param dic 字典项
	 * @param code 字典值CODE
	 * @return 字典值
	 */
	@SuppressWarnings("unchecked")
	public String getDicValue(String dic, String code) {
		String value = null;
		try {
			Map<String,String> dicmap = (Map<String,String>) dicCache.get(dic);
			value = dicmap.get(code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 通过字典项取的此字典项下所有字典值
	 * @param dic 字典项
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, String> getDicList(String dic) {
		HashMap<String,String> dicmap = new HashMap<String,String>();
		try {
			dicmap = (HashMap<String,String>) dicCache.get(dic);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dicmap;
	}
	/**
	 * 查询有序的map，按照key值排序
	 * @param dic
	 * @return
	 */
	public Map<String, String> getLinkedMap(String dic) {
		Map<String,String> map = new LinkedHashMap<String,String>();
		try {
			Map<String,String> dicmap = (HashMap<String,String>) dicCache.get(dic);
			Object[] objs = dicmap.keySet().toArray();
			Arrays.sort(objs);
			for(Object obj:objs){
				String str = (String)obj;
				map.put(str, dicmap.get(str));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 通过字典项取的此字典项下所有字典值
	 * @param dic 字典项
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap<Integer, String> getDicIntegerList(String dic) {
		HashMap<Integer,String> dicmap = new HashMap<Integer,String>();
		try {
			HashMap<String,String> 	dicmapString = (HashMap<String,String>) dicCache.get(dic);
			for(Object key:dicmapString.keySet())
			{
				dicmap.put(Integer.parseInt(key.toString()), dicmapString.get(key));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dicmap;
	}

	/**
	 * 通过字典项取的此字典项下所有字典值
	 * @param dic
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getDicListWithEmptyValue(String dic) {
		Map<String,String> dicmap = null;
		try {
			dicmap = (Map<String,String>) dicCache.get(dic);
			if (dicmap == null) {
				dicmap = new HashMap<String,String>();
				dicmap.put("", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (dicmap == null) {
				dicmap = new HashMap<String,String>();
				dicmap.put("", "");
			}
		}
		return dicmap;
	}
	
}
