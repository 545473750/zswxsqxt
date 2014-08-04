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
 * IDBListener.java
 * ---------------
 * (C) Copyright 2006-2006, by yang zhongke
 *
 * Original Author:  yang zhongke;
 *
 * Changes
 * -------
 * 2006-5-25 add method "close" to this interface
 */
package cn.com.ebmp.dbmonitor.common;

/**
 * every DBListener must implements this interface
 * 
 * @author yang zhongke
 */
public interface IDBListener {
	/**
	 * set the initial arguments where from the "arg" attribute of the listener
	 * in the configXMLFile
	 * 
	 * @param arg
	 * @throws LoggerException
	 */
	public void init(String arg) throws LoggerException;

	/**
	 * write SQLInfo to the listener
	 * 
	 * @param info
	 * @throws LoggerException
	 */
	public void logSql(SQLInfo info) throws LoggerException;

	/**
	 * close the listener
	 * 
	 * @throws LoggerException
	 */
	public void close() throws LoggerException;
}
