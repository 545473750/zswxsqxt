package com.opendata.common.base;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
/**
 * Service基类 通过泛型，子类无需扩展任何函数即拥有完整的CRUD操作。
 * @author 付威
 */
@Transactional
public abstract class BaseManager <E,PK extends Serializable>{
	
	/**
	 * 日志记录器
	 */
	protected Log log = LogFactory.getLog(getClass());

	/**
	 * 取得此services的DAO对象
	 * @return DAO对象
	 */
	protected abstract EntityDao getEntityDao();

	/**
	 * 根据ID查询对象
	 * @param id 主键值
	 * @return 结果对象
	 * @throws DataAccessException
	 */
	@Transactional(readOnly=true)
	public E getById(PK id) throws DataAccessException{
		return (E)getEntityDao().getById(id);
	}
	
	/**
	 * 取得所有数据
	 * @return 对象集合
	 * @throws DataAccessException
	 */
	@Transactional(readOnly=true)
	public List<E> findAll() throws DataAccessException{
		return getEntityDao().findAll();
	}
	
	/**
	 * 取得所有未被逻辑删除的所有对象
	 * @return 对象集合
	 * @throws DataAccessException
	 */
	@Transactional(readOnly=true)
	public List<E> findAllByDf() throws DataAccessException{
		return getEntityDao().findAllByDf();
	}
	
	/**
	 * 根据属性值查询未被逻辑删除的对象
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @return 结果对象
	 * @throws DataAccessException
	 */
	@Transactional(readOnly=true)
	public E findByDf(String propertyName, Object value) throws DataAccessException{
		return (E)getEntityDao().findByDf(propertyName, value);
	}
	/**
	 * 根据查询条件查询对象
	 * @param propertyName 对象属性名
	 * @param value 参数值
	 * @return 结果对象
	 */
	@Transactional(readOnly=true)
    public E findByProperty(String propertyName, Object value){
    	return (E)getEntityDao().findByProperty(propertyName, value);
    }
	/**
	 * 根据属性值查询未被逻辑删除的对象集合
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @return 结果对象集合
	 * @throws DataAccessException
	 */
	@Transactional(readOnly=true)
	public List<E> findAllByDf(String propertyName,  Object value) throws DataAccessException{
		return getEntityDao().findAllByDf(propertyName,value);
	}
	
	/**
     * 根据查询条件查询未被逻辑删除的对象集合 此查询是针对有关联关系（一对多、多对多）的对象查询 如用户和角色为多对多关系 先根据角色的某属性查询符合条件的所有用户
     * @param joinName 关联的对象集合名 如用户对象下的角色集合 roles
     * @param joinPropertyName  需查询的关联对象的属性名 如角色的ID属性
	 * @param value 参数值 需查询的关联对象的属性的值 如角色的ID值
	 * @return 查询结果对象集合
	 * @throws DataAccessException
	 */
	@Transactional(readOnly=true)
	public List<E> findAllByDf(String joinName, String joinPropertyName,  Object value) throws DataAccessException{
		return getEntityDao().findAllByDf(joinName,joinPropertyName,value);
	}
	
	/**
	 * 根据属性值查询对象集合
	 * @param propertyName 属性名
	 * @param value 属性值
	 * @return 结果对象集合
	 * @throws DataAccessException
	 */
	@Transactional(readOnly=true)
	public List<E> findAllBy(String propertyName, Object value) throws DataAccessException{
		return getEntityDao().findAllBy(propertyName,value);
	}
	
	
	
	/**
	 * 插入或更新数据 根据id检查是否插入或是更新数据
	 * @param entity 待操作对象
	 */
	public void saveOrUpdate(E entity) throws DataAccessException{
		getEntityDao().saveOrUpdate(entity);
	}
	

	/**
	 * 插入数据
	 * @param entity 待插入对象
	 */
	@SuppressWarnings("unchecked")
	public void save(E entity) throws DataAccessException{
		getEntityDao().save(entity);
	}
	
	/**
	 * 根据ID删除对象
	 * @param id 待删除对象ID
	 * @throws DataAccessException
	 */
	public void removeById(PK id) throws DataAccessException{
		getEntityDao().deleteById(id);
	}
	
	/**
	 * 更新对象
	 * @param entity 待更新对象
	 * @throws DataAccessException
	 */
	public void update(E entity) throws DataAccessException{
		getEntityDao().update(entity);
	}
	
	/**
	 * 删除对象 此删除方法为逻辑删除方法
	 * @param entity 待删除对象
	 * @throws DataAccessException
	 */
	public void delete(E entity) throws DataAccessException{
		getEntityDao().update(entity);
	}
	
	/**
	 * 判断对象某些属性的值在数据库中是否唯一.
	 * @param entity 待判断对象
	 * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 * @return boolean值 如果是唯一则返回true 否则返回false
	 * @throws DataAccessException
	 */
	@Transactional(readOnly=true)
	public boolean isUnique(E entity, String uniquePropertyNames) throws DataAccessException {
		return getEntityDao().isUnique(entity, uniquePropertyNames);
	}
	
	/**
	 * 判断对象某些属性的值在未被逻辑删除的数据中是否唯一.
	 * @param entity 待判断对象
	 * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 * @return boolean值 如果是唯一则返回true 否则返回false
	 * @throws DataAccessException
	 */
	@Transactional(readOnly=true)
	public boolean isUniqueByDf(E entity, String uniquePropertyNames) throws DataAccessException {
		return getEntityDao().isUniqueByDf(entity, uniquePropertyNames);
	}
	
}
