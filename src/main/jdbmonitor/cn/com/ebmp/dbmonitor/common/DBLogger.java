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
 * DBLogger.java
 * ---------------
 * (C) Copyright 2006-2006, by yang zhongke
 *
 * Original Author:  yang zhongke;
 *
 * Changes
 * -------
 * 2006-06-06,add parameter "connectionId" to method"getLogger" ,by yangzhongke
 * 2006-06-07,refactoring code to make more than one instance can run in the same JVM, 
 *            using counter to free resource rightly.
 * 2006-06-13 delete the code catch exception(InterruptedException)
 * 2006-06-15 not use method"take" of IChannek,instead,use "peek" and "remove".
 * 2006-06-16 use signal "isConsumerStop" instead of method "interrupt" of Thread
 *
 */
package cn.com.ebmp.dbmonitor.common;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 * the Class receive the SQLInfos , the LogConsumer is in another Thread,it
 * dispatch the these SQLInfos to the DBListeners
 * 
 * @author yang zhongke
 */
public class DBLogger {
	private static final int SHUTDOWNSLEEPMSECOND = 1000;
	private static Map instanceMap;

	private IChannel channel;
	private IDBListener[] dbListeners;
	private Thread consumerThread;
	private int refCounter;
	private String connectionId;

	private boolean isConsumerStop;

	private DBLogger(DBListenerInfo[] dbListenerInfos) throws LoggerException, SQLException {
		super();
		channel = new BlockedChannel();
		refCounter = 0;
		isConsumerStop = false;

		// instante the DBListener class
		dbListeners = new IDBListener[dbListenerInfos.length];
		for (int i = 0, n = dbListenerInfos.length; i < n; i++) {
			DBListenerInfo info = dbListenerInfos[i];
			IDBListener lis;
			try {
				lis = (IDBListener) info.getListenerClass().newInstance();
			} catch (InstantiationException e) {
				throw LoggerUtils.toLoggerException(e);
			} catch (IllegalAccessException e) {
				throw LoggerUtils.toLoggerException(e);
			}
			lis.init(info.getArguments());
			dbListeners[i] = lis;
		}

		// start the SQLInfo consumer thread
		consumerThread = new Thread(new LogConsumer());
		consumerThread.setName("DBLogger LogConsumer");
		consumerThread.setDaemon(true);
		consumerThread.start();
	}

	/**
	 * get the single instance of DBLogger,there is maybe more than one
	 * instanceof JDBMonitor running in the same JVM,so we differentiates them
	 * through the "connectionId"
	 * 
	 * @param dbListenerInfos
	 * @param connectionId
	 * @return
	 * @throws LoggerException
	 * @throws SQLException
	 */
	public static synchronized DBLogger createLogger(DBListenerInfo[] dbListenerInfos, String connectionId) throws LoggerException, SQLException {

		DBLogger instance = null;
		if (instanceMap.containsKey(connectionId)) {
			instance = (DBLogger) instanceMap.get(connectionId);
		}
		if (instance == null) {
			instance = new DBLogger(dbListenerInfos);
			instance.connectionId = connectionId;
			instanceMap.put(connectionId, instance);
		}
		instance.addRef();
		return instance;
	}

	/**
	 * log the SQLInfo,put it to the channel
	 * 
	 * @param info
	 * @throws LoggerException
	 */
	public synchronized void logSQL(SQLInfo info) throws LoggerException {
		channel.offer(info);
	}

	public synchronized void addRef() {
		refCounter++;
	}

	public synchronized void releaseRef() {
		refCounter--;
	}

	public synchronized void close() throws LoggerException {
		releaseRef();
		if (refCounter > 0) {
			return;
		}
		while (!channel.isEmpty()) {
			try {
				wait(SHUTDOWNSLEEPMSECOND);
			} catch (Exception e) {
				throw CommonUtils.toRuntimeException(e);
			}
		}

		// close the listeners when JVM shutdown(add by yangzhongke)
		// 2006-05-25
		for (int i = 0, n = dbListeners.length; i < n; i++) {
			try {
				dbListeners[i].close();
			} catch (LoggerException e) {
				throw CommonUtils.toRuntimeException(e);
			}
		}

		instanceMap.remove(connectionId);
		isConsumerStop = true;

		channel = null;
		for (int i = 0, n = dbListeners.length; i < n; i++) {
			dbListeners[i] = null;
		}
		dbListeners = null;
		consumerThread = null;
		connectionId = null;
	}

	/**
	 * the dispatcher of SQLInfos,it dispatchs SQLInfos to the listeners
	 * 
	 * @author yang zhongke
	 */
	class LogConsumer implements Runnable {
		public void run() {
			try {
				startConsumer();
			} catch (LoggerException e) {
				throw CommonUtils.toRuntimeException(e);
			}
		}

		private void startConsumer() throws LoggerException {
			for (;;) {
				if (isConsumerStop == true || channel == null) {
					return;
				}

				SQLInfo info;
				try {
					info = (SQLInfo) channel.peek();
				} catch (NullPointerException e) {
					return;
				}

				// if info is null, it means when call
				// "take",InterruptedException ocurred
				if (info == null) {
					continue;
				}
				for (int i = 0, n = dbListeners.length; i < n; i++) {
					dbListeners[i].logSql(info);
				}
				channel.remove(info);
				info = null;
			}
		}

	}

	static {
		instanceMap = new Hashtable();

		// add hook to the JVM,when JVM will shutDown
		// the hook will be executed
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				// once JVM will shutDown,this hook will pause the shutingdown
				// until all the DBLogger be closed
				// bug fixed by yangzhongke @20060611,modify entrySet() to
				// values()
				Iterator it = instanceMap.values().iterator();
				while (it.hasNext()) {
					DBLogger logger = (DBLogger) it.next();
					try {
						logger.close();
					} catch (LoggerException e) {
						// do nothing
					}
				}

			}
		});

	}

}
