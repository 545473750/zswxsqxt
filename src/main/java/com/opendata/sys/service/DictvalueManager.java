/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.Page;

import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.opendata.sys.bean.ApplyTree;
import com.opendata.sys.dao.DictitemDao;
import com.opendata.sys.dao.DictvalueDao;
import com.opendata.sys.model.Dictitem;
import com.opendata.sys.model.Dictvalue;
import com.opendata.sys.vo.query.DictvalueQuery;

/**
 * 数据字典值业务层,用于对上层action提供支持
 * @author顾保臣
 */

@Service
@Transactional
public class DictvalueManager extends BaseManager<Dictvalue,java.lang.String>{

	private DictvalueDao dictvalueDao;
	private DictitemDao dictitemDao;
	
	public DictitemDao getDictitemDao() {
		return dictitemDao;
	}
	public void setDictitemDao(DictitemDao dictitemDao) {
		this.dictitemDao = dictitemDao;
	}
	public DictvalueDao getDictvalueDao() {
		return dictvalueDao;
	}
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setDictvalueDao(DictvalueDao dao) {
		this.dictvalueDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.dictvalueDao;
	}
	
	@Transactional(readOnly=true)
	public Page findPage(DictvalueQuery query) {
		return dictvalueDao.findPage(query);
	}
	
	/**
	 * 根据代码查找对应的数据字典值
	 * 
	 * @param code 数据字典值对应的代码
	 * @return
	 */
	public Dictvalue findByCode(String code){
		List<Dictvalue> l = dictvalueDao.findAllByDf("code",code);
		if(l.size()>0){
			Dictvalue dictvalue = l.get(0);
			return dictvalue;
		}
		return null;
	}

	/**
	 * 查询树形下拉菜单
	 * @return
	 */
	public List<ApplyTree>  getAlltree(){
		Dictitem findByProperty = dictitemDao.findByProperty("code", "classApplyNum");
		List<ApplyTree> coms = new ArrayList<ApplyTree>();
		if ( findByProperty!=null ) {
			String itemId = findByProperty.getId();
			String hql = " from Dictvalue where parent_id is null and df='0' and dictitemId='"+itemId+"'";
			String hql2 = "  from Dictvalue where parent_id = ? and df='0' and dictitemId='"+itemId+"'";
			List<Dictvalue> orgs = dictitemDao.getHibernateTemplate().find(hql);
			for(Dictvalue org : orgs){
				ApplyTree com = new ApplyTree();
				com.setId(org.getId());
				com.setCode(org.getCode());
				com.setName(org.getValue());
				List<Dictvalue> child = dictvalueDao.findFastByHql(hql2,org.getId());
				com.setChildren(getCombotree(child,org.getId()));
				coms.add(com);
			}
		}
		return coms;
	}
	public List<ApplyTree> getCombotree(List<Dictvalue> child ,String id){
		
		String hql2 = "  from Dictvalue where parent_id = ? and df='0' and dictitemId=?";
		
		List<ApplyTree> coms = new ArrayList<ApplyTree>();
		for(Dictvalue org : child){
			ApplyTree com = new ApplyTree();
			com.setId(org.getId());
			com.setCode(org.getCode());
			com.setName(org.getValue());
			List<Dictvalue> children = dictvalueDao.findFastByHql(hql2,org.getId(),org.getDictitemId());
			if(children.size()>0){
			com.setChildren(getCombo(children,org.getValue()));
			}
			coms.add(com);
		}
		return coms;
	}
	public List<ApplyTree> getCombo(List<Dictvalue> child ,String id){
		
		String hql2 = "  from Dictvalue where parent_id = ? and df='0' and dictitemId=?";
		
		List<ApplyTree> coms = new ArrayList<ApplyTree>();
		for(Dictvalue org : child){
			ApplyTree com = new ApplyTree();
			com.setId(org.getId());
			com.setCode(org.getCode());
			com.setName(org.getValue());
			List<Dictvalue> children = dictvalueDao.findFastByHql(hql2,org.getId(),org.getDictitemId());
			if(children.size()>0){
				com.setChildren(getCombotree(children,org.getValue()));
			}
			
			coms.add(com);
		}
		return coms;
	}
	/**
	 * 通过字典值和字典条目编码查询数据字典对象
	 * @param dictValue 字典值
	 * @param itemcode	字典条目code
	 * @return
	 */
	public Dictvalue findByCodeAndValue(String dictValue,String itemcode){
		String hql = " from Dictvalue d where d.df='0' and value= ? and  dictitemId = (select id from Dictitem where code = ? ) ";
		List<Dictvalue> l = dictvalueDao.getHibernateTemplate().find(hql,dictValue,itemcode);
		if(l.size()>0){
			Dictvalue dictvalue = l.get(0);
			return dictvalue;
		}
		return null;
	}
	
	public List<Dictvalue> findByCode(String code, String itemid, String nodetype) {
		return this.dictvalueDao.findByCode(code, itemid, nodetype);
	}
	
	/**
	 * 根据itemcode 查询divvalue,只查第一级
	 * @param itemCode
	 * @return
	 */
	public List<Dictvalue> findByItemCode(String itemCode)
	{
		return this.dictvalueDao.findByItemCode(itemCode);
	}
	/**
	 * 修改前判断字典值code是否存在
	 * @param name
	 * @param esApplicationId
	 * @return
	 */
	@Transactional(readOnly=true)
	public boolean ifSameCode(String code,String dictvalueId){
		List<Dictvalue> list = this.dictvalueDao.getHibernateTemplate().find(" from Dictvalue dictvalue where df=0 and code=? and id!=?", code,dictvalueId);
		if(list.isEmpty()){
			return false;
		}else{
			return true;
		}
	}
	
	public Map<String,List<Dictvalue>> getDicValueList() {
		Map<String, List<Dictvalue>> map = new HashMap<String, List<Dictvalue>>();
		List<Dictvalue> list = dictvalueDao.getAll();// 取得所有的字典值
		String dictitemCode = null;// 暂存的字典项CODE 如果是同一个字典项的字典值就存到同一个list
		List<Dictvalue> listByDictitemId = null;// 用来存储同一个字典想的字典值的list
		for (Dictvalue dictvalue : list) {
			// 如果字典项ID不一样，表示是另一个字典项，把先的字典项的字典值存入MAP中
			if (!dictvalue.getDictitem().getCode().equals(dictitemCode)) {
				if (dictitemCode != null) {
					map.put(dictitemCode, listByDictitemId);
				}
				dictitemCode = dictvalue.getDictitem().getCode();// 字典项CODE换为先的CODE
				listByDictitemId = new ArrayList();// 字典值LIST清空
			}
			listByDictitemId.add(dictvalue);
		}
		// 把最后一个字典项的字典值加入map
		if (dictitemCode != null) {
			map.put(dictitemCode, listByDictitemId);
		}
		return map;
	}
	
	/**
	 * 根据字典项ID加载对应的字典值
	 * @param dictitemId
	 * @return
	 */
	public List<Dictvalue> findListByItemId(String dictitemId) {
		return this.dictvalueDao.findListByItemId(dictitemId);
	}
	/**
	 * 查询项目状态（字典值列表）
	 * @param itemcode 字典条目code
	 * @param parentcode 父子点值code
	 * @return
	 */
	public List<Dictvalue> findProjectStates(String itemcode,String parentcode){
		String hql = " from Dictvalue d where d.df='0'  and d.dictitemId = (select id from Dictitem where code = ? ) ";
		if(StringUtils.isNotBlank(parentcode)){
			hql += " and d.parentId in ( select id from Dictvalue where code = '"+parentcode+"' and df='0' ) ";
		}else{
			hql += " and d.parentId is null";
		}
		List<Dictvalue> dicList = dictvalueDao.getHibernateTemplate().find(hql,itemcode);
		return dicList;
	}
	
	
}
