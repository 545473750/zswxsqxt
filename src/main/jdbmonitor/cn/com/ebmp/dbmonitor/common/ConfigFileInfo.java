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
 * ConfigFileInfo.java
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

import java.util.List;
import java.util.Vector;

/**
 * the DBMonitor configFile Information
 * 
 * @author yang zhongke
 */
public class ConfigFileInfo {
	private List listenerInfoList = null;
	private List realJdbcDriverList = null;
	private boolean active = true;

	/**
	 * whether the Monitor is active
	 * 
	 * @return
	 */
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public ConfigFileInfo() {
		super();
		listenerInfoList = new Vector();
		realJdbcDriverList = new Vector();
	}

	/**
	 * get the listener class
	 * 
	 * @return the element classType in the List is DBListenerInfo
	 */
	public List getListenerInfoList() {
		return listenerInfoList;
	}

	/**
	 * get the JdbcDriver need to be registe
	 * 
	 * @return the element classType in the List is String
	 */
	public List getRealJdbcDriverList() {
		return realJdbcDriverList;
	}
}
