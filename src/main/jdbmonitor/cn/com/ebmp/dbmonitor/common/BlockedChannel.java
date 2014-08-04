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
 * BlockedChannel.java
 * ---------------
 * (C) Copyright 2006-2006, by yang zhongke
 *
 * Original Author:  yang zhongke;
 *
 * Changes
 * -------
 * 2006-06-13 catch the InterruptedException in method "take"
 * 2006-06-15 remove method "take",using "peek" and "remove" instead
 */
package cn.com.ebmp.dbmonitor.common;

import java.util.ArrayList;
import java.util.List;

/**
 * a blocked channel,producer offer product ,consumer take the product, the
 * consumer will wait if there isn't any products
 * 
 * @author yang zhongke
 */
public class BlockedChannel implements IChannel
{
	private List logList = null;

	public BlockedChannel()
	{
		super();
		logList = new ArrayList();
	}

	/**
	 * producer offer the product
	 * 
	 * @param obj
	 *            the product
	 * @throws InterruptedException
	 */
	public synchronized void offer(Object obj)
	{
		logList.add(obj);
		notifyAll();
	}

	/**
	 * whether there is products
	 * 
	 * @return
	 */
	public boolean isEmpty()
	{
		return logList.isEmpty();
	}

	/**
	 * Looks at the object that will be token without removing it from the
	 * channel
	 */
	public synchronized Object peek()
	{
		if (logList.isEmpty())
		{
			try
			{
				wait();
			} catch (InterruptedException e)
			{
				return null;
			}
		}
		return logList.get(0);
	}

	/**
	 * removing object from channel
	 */
	public synchronized void remove(Object obj)
	{
		logList.remove(obj);
	}

}
