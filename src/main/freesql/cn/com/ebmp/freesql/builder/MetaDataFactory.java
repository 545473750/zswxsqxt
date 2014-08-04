package cn.com.ebmp.freesql.builder;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.lob.SerializableClob;

import com.opendata.common.util.Util;

import cn.com.ebmp.freesql.mapping.ResultMap;
import cn.com.ebmp.freesql.mapping.ResultMapping;

public class MetaDataFactory {
	/**
	 * 待转换的数据列表
	 */
	private List<Object[]> originalData;

	/**
	 * 转换对象类型
	 */
	private Class objectClass;

	/**
	 * 待转换对象的属性列表,此列表顺序应与待转换数据的数组序列一致
	 */
	private List<ResultMapping> resultMapping;

	/**
	 * 
	 * @param objectClass
	 *            转换对象类型
	 * @param metaProperty
	 *            转换属性列表
	 * @param originalData
	 *            转换数据列表
	 */
	public MetaDataFactory(ResultMap resultMap, List<Object[]> originalData) {
		this.originalData = originalData;
		if (this.originalData == null) {
			this.originalData = new ArrayList<Object[]>();
		}
		this.objectClass = resultMap.getType();
		this.resultMapping = resultMap.getResultMapping();
	}

	public MetaDataFactory(List<ResultMapping> resultMapping, Class metaObject, List<Object[]> originalData) {
		this.originalData = originalData;
		if (this.originalData == null) {
			this.originalData = new ArrayList<Object[]>();
		}
		this.objectClass = metaObject;
		this.resultMapping = resultMapping;
	}

	/**
	 * 转换数据
	 * 
	 * @return
	 */
	public void metaObject() {
		List metaData = new ArrayList();
		for (Object object : originalData) {
			try {
				if (object != null) {
					Object metaobject = this.buildMetaObject(object);
					metaData.add(metaobject);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.originalData.clear();
		this.originalData.addAll(metaData);
	}

	private Object buildMetaObject(Object obj) throws Exception {

		if (isBaseType(this.objectClass)) {// 返回类型是基本类型
			Object propertyValue = null;
			Object[] originalObject = new Object[] { obj };
			for (ResultMapping mapping : resultMapping) {
				propertyValue = getPropertyValue(originalObject, mapping.getProperty(), mapping.getJavaType());
			}
			return propertyValue;
		} else {
			Object[] originalObject = (Object[]) obj;
			Object object = this.objectClass.newInstance();
			// 如果改变的类型对象为Map
			if (object instanceof Map) {
				Map<String, Object> mapObject = new HashMap<String, Object>();
				for (ResultMapping mapping : resultMapping) {
					Object propertyValue = getPropertyValue(originalObject, mapping.getProperty(), mapping.getJavaType());
					mapObject.put(mapping.getProperty(), propertyValue);
				}
				object = mapObject;
				return object;
			} else {
				Method[] methods = this.objectClass.getMethods();
				for (ResultMapping mapping : resultMapping) {
					for (Method method : methods) {
						if (method.getName().toLowerCase().equals(("set" + mapping.getProperty()).toLowerCase())) {
							method.invoke(object, this.getPropertyValue(originalObject, mapping.getProperty(), method.getParameterTypes()[0]));
							break;
						}
					}
				}
			}
			return object;
		}
	}

	/**
	 * 判断是否是基本类型
	 * 
	 * @param object
	 * @return
	 */
	private boolean isBaseType(Object object) {
		if (object.getClass() == Boolean.class) {
			return true;
		}
		if (object.getClass() == boolean.class) {
			return true;
		} else if (object.getClass() == Short.class) {
			return true;
		} else if (object.getClass() == short.class) {
			return true;
		} else if (object.getClass() == Integer.class) {
			return true;
		} else if (object.getClass() == int.class) {
			return true;
		} else if (object.getClass() == Long.class) {
			return true;
		} else if (object.getClass() == long.class) {
			return true;
		} else if (object.getClass() == Double.class) {
			return true;
		} else if (object.getClass() == double.class) {
			return true;
		} else if (object.getClass() == Float.class) {
			return true;
		} else if (object.getClass() == float.class) {
			return true;
		} else if (object.getClass() == Date.class) {
			return true;
		} else if (object.getClass() == String.class) {
			return true;
		}
		return false;
	}

	/**
	 * 获得属性值
	 * 
	 * @param originalObject
	 * @param property
	 * @param parameterClass
	 * @return
	 */
	private Object getPropertyValue(Object[] originalObject, String property, Class parameterClass) {
		int resultMappingSize = this.resultMapping.size();
		for (int i = 0; i < resultMappingSize; i++) {
			if (property.equals(resultMapping.get(i).getProperty())) {
				if (parameterClass == Boolean.class && originalObject[i] instanceof BigDecimal) {
					if (originalObject[i] != null) {
						return ((BigDecimal) originalObject[i]).intValue() == 1 ? true : false;
					}
				}
				if (parameterClass == boolean.class && originalObject[i] instanceof BigDecimal) {
					if (originalObject[i] != null) {
						return ((BigDecimal) originalObject[i]).intValue() == 1 ? true : false;
					}
				}

				else if (parameterClass == Short.class && originalObject[i] instanceof BigDecimal) {
					if (originalObject[i] != null) {
						return ((BigDecimal) originalObject[i]).shortValue();
					}
				} else if (parameterClass == short.class && originalObject[i] instanceof BigDecimal) {
					if (originalObject[i] != null) {
						return ((BigDecimal) originalObject[i]).shortValue();
					}
				}

				else if (parameterClass == Integer.class && originalObject[i] instanceof BigDecimal) {
					if (originalObject[i] != null) {
						return ((BigDecimal) originalObject[i]).intValue();
					}
				} else if (parameterClass == int.class && originalObject[i] instanceof BigDecimal) {
					if (originalObject[i] != null) {
						return ((BigDecimal) originalObject[i]).intValue();
					}
				}

				else if (parameterClass == Long.class && originalObject[i] instanceof BigDecimal) {
					if (originalObject[i] != null) {
						return ((BigDecimal) originalObject[i]).longValue();
					}

				} else if (parameterClass == long.class && originalObject[i] instanceof BigDecimal) {
					if (originalObject[i] != null) {
						return ((BigDecimal) originalObject[i]).longValue();
					}

				}

				else if (parameterClass == Double.class && originalObject[i] instanceof BigDecimal) {
					if (originalObject[i] != null) {
						return ((BigDecimal) originalObject[i]).doubleValue();
					}
				} else if (parameterClass == double.class && originalObject[i] instanceof BigDecimal) {
					if (originalObject[i] != null) {
						return ((BigDecimal) originalObject[i]).doubleValue();
					}
				}

				else if (parameterClass == Float.class && originalObject[i] instanceof BigDecimal) {
					if (originalObject[i] != null) {
						return ((BigDecimal) originalObject[i]).floatValue();
					}
				} else if (parameterClass == float.class && originalObject[i] instanceof BigDecimal) {
					if (originalObject[i] != null) {
						return ((BigDecimal) originalObject[i]).floatValue();
					}
				} else if (parameterClass == Date.class && originalObject[i] instanceof Timestamp) {
					if (originalObject[i] != null) {
						Timestamp timestamp = (Timestamp) originalObject[i];
						return new Date(timestamp.getTime());
					}
				}

				else if (parameterClass == Date.class && originalObject[i] instanceof Time) {
					if (originalObject[i] != null) {
						Time time = (Time) originalObject[i];
						return new Date(time.getTime());
					}
				}

				else if (parameterClass == Date.class && originalObject[i] instanceof BigDecimal) {
					if (originalObject[i] != null) {
						return new Date(((BigDecimal) originalObject[i]).longValue());
					}
				}

				else if (parameterClass == Date.class && originalObject[i] instanceof Date) {
					if (originalObject[i] != null) {
						return (Date) originalObject[i];
					}
				}

				else if (parameterClass == Date.class && originalObject[i] instanceof String) {
					if (originalObject[i] != null) {
						return Util.StringToDate((String) originalObject[i], "yyyy-MM-dd HH:mm:ss");
					}
				}

				else if (parameterClass == String.class && originalObject[i] instanceof SerializableClob) {
					if (originalObject[i] != null) {
						SerializableClob cb = (SerializableClob) originalObject[i];
						Clob wrapClob = (Clob) cb.getWrappedClob();
						if (wrapClob != null) {
							try {
								return wrapClob.getSubString(1, (int) wrapClob.length());
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						return "";
					}
				}
				if (originalObject[i] != null) {
					return originalObject[i].toString();
				}
				return "";
			}
		}
		return null;
	}
}
