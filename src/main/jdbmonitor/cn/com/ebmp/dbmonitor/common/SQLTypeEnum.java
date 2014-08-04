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
 * SQLTypeEnum.java
 * ---------------
 * (C) Copyright 2006-2006, by yang zhongke
 *
 * Original Author:  yang zhongke;
 *
 * Changes
 * -------
 *
 */
package cn.com.ebmp.dbmonitor.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SQLTypeEnum implements Serializable {

	private static final long serialVersionUID = 6727735162538372662L;

	private static Map keyValueMap = new HashMap();
	private int type;
	private String alias;

	public final static SQLTypeEnum execute = new SQLTypeEnum(1, "execute");
	public final static SQLTypeEnum executeQuery = new SQLTypeEnum(2, "executeQuery");
	public final static SQLTypeEnum executeUpdate = new SQLTypeEnum(3, "executeUpdate");
	public final static SQLTypeEnum rollback = new SQLTypeEnum(4, "rollback");
	public final static SQLTypeEnum executeBatch = new SQLTypeEnum(5, "executeBatch");

	private SQLTypeEnum(int type, String alias) {
		this.type = type;
		this.alias = alias;
		keyValueMap.put(new Integer(type), this);
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof SQLTypeEnum)) {
			return false;
		}
		SQLTypeEnum e = (SQLTypeEnum) obj;
		return e.type == this.type;
	}

	public int getValue() {
		return type;
	}

	public String toString() {
		if (alias != null) {
			return alias;
		} else {
			return new Integer(type).toString();
		}
	}

	public static SQLTypeEnum getEnum(int type) {
		return (SQLTypeEnum) keyValueMap.get(new Integer(type));
	}

}
