package com.opendata.common.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.DataAccessException;
/**
 * DAO接口
 * @author 付威
 */
public interface EntityDao <E,PK extends Serializable>{

	/**
	 * 根据ID查询对象
	 * @param id 
	 * @return 结果对象
	 * @throws DataAccessException
	 */
	public Object getById(PK id) throws DataAccessException;
	
	/**
	 * 根据ID删除对象
	 * @param id 待删除对象ID
	 * @throws DataAccessException
	 */
	public void deleteById(PK id) throws DataAccessException;
	

	/**
	 * 保存对象
	 * param entity 待操作实体
	 * @return 保存对象
	 */
	public void save(E entity) throws DataAccessException;
	
	/**
	 * 更新对象
	 * param entity 待操作实体
	 */
	public void update(E entity) throws DataAccessException;

	/**
	 * 插入或更新数据 根据id检查是否插入或是更新数据
	 * @param entity 待操作对象
	 */
	public void saveOrUpdate(E entity) throws DataAccessException;

	/**
	 * 判断对象某些属性的值在数据库中是否唯一.
	 * @param entity 待判断对象
	 * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 * @return boolean值 如果是唯一则返回true 否则返回false
	 */
	public boolean isUnique(E entity, String uniquePropertyNames) throws DataAccessException;
	
	
	/**
	 * 判断对象某些属性的值在未被逻辑删除的数据中是否唯一.
	 * @param entity 待判断对象
	 * @param uniquePropertyNames 在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 * @return boolean值 如果是唯一则返回true 否则返回false
	 */
	public boolean isUniqueByDf(E entity, String uniquePropertyNames) throws DataAccessException;
	

	/**
	 * 用于hibernate.flush() 有些dao实现不需要实现此类
	 */
	public void flush() throws DataAccessException;
	
	
	/**
	 * 取得所有数据
	 * @return 对象集合
	 */
	public List<E> findAll() throws DataAccessException;
	
	/**
	 * 根据属性值查询对象集合
	 * @param propertyName 属性名
	 * @param value 属性值
	 * @return 结果对象集合
	 */
	public List<E> findAllBy(final String propertyName, final Object value);
	
	/**
	 * 取得所有未被逻辑删除的所有对象
	 * @return 对象集合
	 */
	public List<E> findAllByDf();
	
	/**
	 * 根据属性值查询未被逻辑删除的对象集合
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @return 结果对象集合
	 */
	public List<E> findAllByDf(final String propertyName, final Object value);
	
	
	/**
	 * 根据属性值查询未被逻辑删除的对象
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @return 结果对象
	 */
	public E findByDf(final String propertyName, final Object value);
	
	/**
	 * 根据查询条件查询对象
	 * @param propertyName 对象属性名
	 * @param value 参数值
	 * @return 结果对象
	 */
    public E findByProperty(final String propertyName, final Object value);
	/**
	 * 多表查询
	 * @param joinName set 多对多的属性名 例如根据角色查询用户 此属性为Role 对象的 users 
	 * @param joinPropertyName 如是根据用户id查询 则此属性值为id
	 * @param value 对应的用户ID值 
	 * @return
	 */
	public List<E> findAllByDf(final String joinName,final String joinPropertyName, final Object value);
	
}
