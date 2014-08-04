package com.opendata.common.util;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;

import com.opendata.sys.model.Sysvariable;
import com.opendata.sys.service.SysvariableManager;

/**
 * 系统参数工具类
 * @author 顾保臣
 */
@Controller
public class SysVariableUtil {

	// 系统参数业务对象
	private SysvariableManager sysvariableManager;
	private SysVariableCache cache;
	
	public SysVariableUtil() {
		cache = SysVariableCache.getInstance();
	}

	/**
	 * 注意： 新增、修改、删除都需要重新初始化数据
	 */
	@PostConstruct
	public void init() {
		cache.removeAll();

		List<Sysvariable> svs = this.sysvariableManager.findAllByDf();
//		Map<String, String> map = new HashMap<String, String>();
		for(Sysvariable sv : svs) {
//			map.put(sv.getName(), sv.getCode());
			cache.putInCache(sv.getName(), sv.getCode());
		}
	}
	
	/**
	 * 通过系统参数名称取得系统参数值
	 * @param name 系统参数名称
	 * @return 系统参数值
	 */
	public String getValue(String name) {
		
		try {
			if(name != null && !name.equals("")) {
				return (String) cache.getFromCache(name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setSysvariableManager(SysvariableManager sysvariableManager) {
		this.sysvariableManager = sysvariableManager;
	}
}
