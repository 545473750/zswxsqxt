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
 * IChannel.java
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
 * a channel between producer and consumer, producer offer product ,consumer
 * take the product
 * 
 * @author yang zhongke
 */
public interface IChannel {

	/**
	 * producer offer the product
	 * 
	 * @param obj
	 *            the product
	 */
	public void offer(Object obj);

	/**
	 * remove product from channel
	 * 
	 * @param obj
	 *            the prouct to be removed
	 * @return the product
	 */
	public void remove(Object obj);

	/**
	 * whether there is products
	 * 
	 * @return
	 */
	public boolean isEmpty();

	/**
	 * Looks at the object that will be token without removing it from the
	 * channel
	 * 
	 * @return
	 */
	public Object peek();

}