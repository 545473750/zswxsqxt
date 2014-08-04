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
 * JdbcUtils.java
 * ---------------
 * (C) Copyright 2006-2006, by yang zhongke
 *
 * Original Author:  yang zhongke;
 *
 * Changes
 * -------
 *
 */
package cn.com.ebmp.dbmonitor.jdbc;

import java.sql.SQLException;
import java.sql.Timestamp;

public class JdbcUtils {
	/**
	 * convert Throwable to SQLException
	 * 
	 * @param e
	 * @return
	 */
	public static SQLException toSQLException(Throwable e) {
		SQLException se = new SQLException(e.getMessage());
		se.setStackTrace(e.getStackTrace());
		se.printStackTrace();
		return se;
	}

	/**
	 * get current Timestamp
	 * 
	 * @return
	 */
	public static Timestamp getTimeStamp() {
		return new Timestamp(System.currentTimeMillis());
	}
}
