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
 * InstanceUtils.java
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InstanceUtils {

	/**
	 * close a OutputStream
	 * 
	 * @param os
	 */
	public static void closeOutStream(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				throw CommonUtils.toRuntimeException(e);
			}
		}
	}

	/**
	 * close a InputStream
	 * 
	 * @param ins
	 */
	public static void closeInStream(InputStream ins) {
		if (ins != null) {
			try {
				ins.close();
			} catch (IOException e) {
				throw CommonUtils.toRuntimeException(e);
			}
		}
	}

	/**
	 * Close a socket
	 * 
	 * @param socket
	 */
	public static void closeSocket(Socket socket) {
		if (socket != null) {
			try {
				// Stream should be closed before close socket
				if (socket.isInputShutdown()) {
					socket.getInputStream().close();
				}
				if (socket.isOutputShutdown()) {
					socket.getOutputStream().close();
				}
				socket.close();
			} catch (IOException e) {

			}
		}
	}

	/**
	 * close a jdbc statement
	 * 
	 * @param statement
	 */
	public static void closeStatement(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				throw CommonUtils.toRuntimeException(e);
			}
		}
	}

	/**
	 * close a DBConnection
	 * 
	 * @param conn
	 */
	public static void closeConnection(Connection conn) {
		if (conn == null) {
			return;
		}
		try {
			conn.close();
		} catch (SQLException e) {
			throw CommonUtils.toRuntimeException(e);
		}
	}

	public static void closeReader(Reader reader) {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
