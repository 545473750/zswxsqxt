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
 * ConsoleDBListener.java
 * ---------------
 * (C) Copyright 2006-2006, by yang zhongke
 *
 * Original Author:  yang zhongke;
 *
 * Changes
 * -------
 *2006-05-25 add method "close",remove "finally"
 */
package cn.com.ebmp.dbmonitor.listenerImpl;

import cn.com.ebmp.dbmonitor.common.IDBListener;
import cn.com.ebmp.dbmonitor.common.LoggerException;
import cn.com.ebmp.dbmonitor.common.SQLInfo;

/**
 * this DBListener will print the loginfo to the console
 * 
 * @author yang zhongke
 */
public class ConsoleDBListener implements IDBListener {

	public ConsoleDBListener() {
		super();
	}

	public void init(String arg) throws LoggerException {

	}

	public synchronized void logSql(SQLInfo info) throws LoggerException {
		System.out.println(info.toString());
	}

	public void close() throws LoggerException {

	}

}
