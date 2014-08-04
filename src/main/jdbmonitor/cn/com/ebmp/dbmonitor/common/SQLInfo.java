/* ===========================================================
 * JDBMonitor : a flexiable JDBC Monitor for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2006-2006, by yang zhongke
 *
 * Project Info:  http://www.cownew.com
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, 
 * USA.  
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * ---------------
 * SQLInfo.java
 * ---------------
 * (C) Copyright 2006-2006, by yang zhongke
 *
 * Original Author:  yang zhongke;
 *
 * Changes
 * -------
 *2006-05-26
 *getParameterGroup,addParameterList,clearParameterGroup instead of getParameters,because with addBatch and exectueBatch,there will be more than one parameterList
 */
package cn.com.ebmp.dbmonitor.common;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * this class define the infomation to be log
 * 
 * @author yang zhongke
 */
public class SQLInfo implements Serializable {
	private static final long serialVersionUID = -7701615259267967786L;
	private Timestamp beginTime;
	private Timestamp endTime;

	private SQLTypeEnum sqlType;
	private String sql;

	private Vector paramGroupVector = new Vector(1);

	/**
	 * get the type of sql statement
	 * 
	 * @return
	 */
	public SQLTypeEnum getSqlType() {
		return sqlType;
	}

	/**
	 * set the type of sql statement
	 * 
	 * @param sqlType
	 */
	public void setSqlType(SQLTypeEnum sqlType) {
		this.sqlType = sqlType;
	}

	/**
	 * get the execution beginTimeStamp
	 * 
	 * @return
	 */
	public Timestamp getBeginTime() {
		return beginTime;
	}

	/**
	 * set the execution beginTimeStamp
	 * 
	 * @param beginTime
	 */
	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * get the sql statement
	 * 
	 * @return
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * set the sql statement
	 * 
	 * @param ssql
	 */
	public void setSql(String ssql) {
		this.sql = ssql;
	}

	/**
	 * get the execution endTimeStamp
	 * 
	 * @return
	 */
	public Timestamp getEndTime() {
		return endTime;
	}

	/**
	 * set the execution endTimeStamp
	 * 
	 * @return
	 */
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	/**
	 * get the array of parameterList of the SQL statement, if with addBatch and
	 * exectueBatch,there will be more than one parameterList
	 * 
	 * @return
	 */
	public List[] getParameterGroup() {
		List[] list = new List[paramGroupVector.size()];
		for (int i = 0, n = paramGroupVector.size(); i < n; i++) {
			list[i] = (List) paramGroupVector.get(i);
		}
		return list;
	}

	/**
	 * add a parameterList,
	 * 
	 * @param with
	 *            addBatch and exectueBatch,there will be more than one
	 *            parameterList
	 */
	public void addParameterList(List list) {
		paramGroupVector.add(list);
	}

	/**
	 * clear all the parameters
	 * 
	 */
	public void clearParameterGroup() {
		paramGroupVector.clear();
	}

	public String parameterToString() {
		StringBuffer sb = new StringBuffer();
		Iterator itGroupVector = paramGroupVector.iterator();
		while (itGroupVector.hasNext()) {
			sb.append("[");
			List list = (List) itGroupVector.next();
			for (int i = 0, n = list.size(); i < n; i++) {
				if (i >= 1) {
					sb.append(",");
				}
				sb.append(list.get(i));
			}
			sb.append("]\n");
		}
		return sb.toString();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("begintime:").append(beginTime).append("\n");
		sb.append("endtime:").append(endTime).append("\n");
		sb.append("sqlType:").append(sqlType).append("\n");
		sb.append("sql:").append(sql).append("\n");
		sb.append("paramters:").append(parameterToString());

		return sb.toString();
	}

}
