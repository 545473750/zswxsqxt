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
 * DBListenerInfo.java
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

/**
 * the DBListener Information
 * 
 * @author yang zhongke
 */
public class DBListenerInfo {
	private Class listenerClass = null;
	private String arguments = null;

	public void setListenerClass(Class listenerClass) {
		this.listenerClass = listenerClass;
	}

	/**
	 * get ListenerClass
	 * 
	 * @return
	 */
	public Class getListenerClass() {
		return listenerClass;
	}

	/**
	 * set the init arguments
	 * 
	 * @param arguments
	 */
	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

	/**
	 * get the init arguments
	 * 
	 * @return
	 */
	public String getArguments() {
		return arguments;
	}
}
