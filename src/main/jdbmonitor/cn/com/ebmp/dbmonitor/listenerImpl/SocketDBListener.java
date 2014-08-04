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
 * SocketDBListener.java
 * ---------------
 * (C) Copyright 2006-2006, by yang zhongke
 *
 * Original Author:  yang zhongke;
 *
 * Changes
 * -------
 *2006-05-25 add method "close",remove "finally"
 *2006-05-26 by yangzhongke,set "portListenerThread" to Daemon thread.
 *2006-06-14 forgot close "serverSocket" at method "close"
 */
package cn.com.ebmp.dbmonitor.listenerImpl;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import cn.com.ebmp.dbmonitor.common.IDBListener;
import cn.com.ebmp.dbmonitor.common.InstanceUtils;
import cn.com.ebmp.dbmonitor.common.LoggerException;
import cn.com.ebmp.dbmonitor.common.LoggerUtils;
import cn.com.ebmp.dbmonitor.common.SQLInfo;

/**
 * <p>
 * this DBListener will send the loginfo to socket Clients
 * </p>
 * <p>
 * the port to be bind is defined in the "arg" attribute of the "Listener" tag
 * </p>
 * <p>
 * eg.&lt;Listener class="com.cownew.JDBMonitor.listenerImpl.SocketDBListener"
 * arg="9527"/&gt;
 * </P>
 * 
 * @author yang zhongke
 */
public class SocketDBListener implements IDBListener
{
	private ServerSocket serverSocket;

	private List clientList;

	private Thread portListenerThread;

	private boolean serverSocketStop;

	public SocketDBListener()
	{
		super();
		clientList = new Vector();
		serverSocketStop = false;
	}

	public void init(String arg) throws LoggerException
	{
		int port = -1;
		if (arg.trim().length() >= 0)
		{
			port = Integer.parseInt(arg.trim());
		}

		try
		{
			if (port > 0)
			{
				serverSocket = new ServerSocket(port);
			} else
			{
				serverSocket = new ServerSocket();
			}
		} catch (IOException e)
		{
			throw LoggerUtils.toLoggerException(e);
		}

		portListenerThread = new Thread(new SocketServerThread());
		portListenerThread.setDaemon(true);
		portListenerThread.start();
	}

	public synchronized void logSql(SQLInfo info) throws LoggerException
	{
		for (int i = clientList.size() - 1; i >= 0; i--)
		{
			Socket clientSocket = (Socket) clientList.get(i);
			if (!clientSocket.isConnected() || clientSocket.isClosed())
			{
				InstanceUtils.closeSocket(clientSocket);
				clientList.remove(i);
				continue;
			}
			OutputStream os = null;
			try
			{
				// this OutputStream cannot be closed
				os = clientSocket.getOutputStream();
				ObjectOutputStream dos = new ObjectOutputStream(new BufferedOutputStream(os));
				dos.writeObject(info);
				dos.flush();
			} catch (IOException e)
			{
				InstanceUtils.closeSocket(clientSocket);
				clientList.remove(i);
			}
		}
	}

	public void close() throws LoggerException
	{
		serverSocketStop = true;
		portListenerThread = null;

		try
		{
			serverSocket.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			for (int i = 0, n = clientList.size(); i < n; i++)
			{
				Socket socket = (Socket) clientList.get(i);
				InstanceUtils.closeSocket(socket);
			}
			clientList.clear();
			serverSocket = null;
			clientList = null;
		}

	}

	class SocketServerThread implements Runnable
	{
		public void run()
		{
			for (;;)
			{
				if (serverSocket == null || clientList == null || serverSocketStop)
				{
					return;
				}
				try
				{
					Socket clientSocket = serverSocket.accept();
					clientList.add(clientSocket);
				} catch (NullPointerException e)
				{
					return;
				} catch (IOException e)
				{
					continue;
				}
			}
		}
	}

}
