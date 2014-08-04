/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.opendata.common.base.*;
import cn.org.rapid_framework.page.*;
import com.opendata.sys.model.*;
import com.opendata.sys.dao.*;
import com.opendata.sys.vo.query.*;

/**
 * 分区的service层 负责处理业务逻辑
 * @author 付威
 */
@Service
@Transactional
public class PartitionManager extends BaseManager<Partition,java.lang.String>{

	private PartitionDao partitionDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setPartitionDao(PartitionDao dao) {
		this.partitionDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.partitionDao;
	}
	
	/**
	 * 根据查询条件查询 执行分页
	 * @param query 查询条件对象
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page findPage(PartitionQuery query) {
		return partitionDao.findPage(query);
	}
	
}
