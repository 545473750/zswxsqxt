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
 * CommonUtils.java
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

import java.sql.SQLException;

public class CommonUtils {
	/**
	 * convert Throwable to RuntimeException
	 * 
	 * @param e
	 * @return
	 */
	public static RuntimeException toRuntimeException(Throwable e) {
		RuntimeException re = new RuntimeException(e);
		re.setStackTrace(e.getStackTrace());
		e.printStackTrace(System.err);
		return re;
	}

	/**
	 * whether the String s is Empty(null or length is zero after trimed)
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmptyString(String s) {
		if (s == null) {
			return true;
		}
		return s.trim().length() <= 0;
	}

	/**
	 * get the Class from the className
	 * 
	 * @param lisClassName
	 *            must implements IDBListener
	 * @return
	 * @throws SQLException
	 */
	public static Class getListenerInstance(String lisClassName) throws SQLException {
		Class lisClass;
		try {
			lisClass = Class.forName(lisClassName);
		} catch (ClassNotFoundException e) {
			throw new SQLException(e.getMessage());
		}

		if (!IDBListener.class.isAssignableFrom(lisClass)) {
			throw new SQLException("the DBListener must implements IDBListener!");
		}

		return lisClass;

	}

}
