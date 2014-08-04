package com.opendata.common.util;


import java.util.Map;

import cn.org.rapid_framework.page.Page;

/**
 * @author 刘丰 翻页对象
 */
public class PageBar {
	private Page page;

	private Map parameter;
	
	private Integer allPageCount;//总页数
	
	public Integer getAllPageCount() {
		return allPageCount;
	}

	public void setAllPageCount(Integer allPageCount) {
		this.allPageCount = allPageCount;
	}

	public PageBar(Page page, Map parameter) {
		this.page = page;
		this.parameter = parameter;
		if (page.getTotalCount() == 0 || page.getPageSize() == 0)
			allPageCount = 0;
		if (page.getTotalCount() % page.getPageSize() == 0)
			allPageCount = page.getTotalCount() / page.getPageSize();
		else
			allPageCount = page.getTotalCount() / page.getPageSize() + 1;
	}

	public Page getPage() {
		return page;
	}

	public Map getParameter() {
		return parameter;
	}
}
