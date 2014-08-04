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
 * DataBaseDBListener.java
 * ---------------
 * (C) Copyright 2006-2006, by yang zhongke
 *
 * Original Author:  yang zhongke;
 *
 * Changes
 * -------
 *2006-05-25 add method "close",remove "finally"
 *2006-06-07 using "setCharacterStream" instead of "setString" to store the long string.
 *2006-06-11 delete "dbConnection" in method "getConnection"
 */
package cn.com.ebmp.dbmonitor.listenerImpl;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.ebmp.dbmonitor.common.IDBListener;
import cn.com.ebmp.dbmonitor.common.InstanceUtils;
import cn.com.ebmp.dbmonitor.common.LoggerException;
import cn.com.ebmp.dbmonitor.common.LoggerUtils;
import cn.com.ebmp.dbmonitor.common.SQLInfo;

/**
 * <p>
 * this DataBaseDBListener will write the loginfo to database
 * </p>
 * <p>
 * the database to be writen is defined in the "arg" attribute of the "Listener"
 * tag
 * </p>
 * &lt;Listener
 * class=&quot;com.cownew.JDBMonitor.listenerImpl.DataBaseDBListener&quot;
 * <br />
 * arg=&quot;dburl=jdbc:odbc:MQIS;user=;password=;logtable=T_Log_SQLLog&quot;/&gt;<br/>
 * 
 * @author yang zhongke
 */
public class DataBaseDBListener implements IDBListener
{
	private String dbUrl;
	private String user;
	private String password;
	private String logTable;
	private Connection dbConnection;
	private String insertSQL;
	public static final String DEFAULTSQLLOGTABLE = "T_Log_SQLLog";

	public DataBaseDBListener()
	{
		super();
	}

	public void init(String arg) throws LoggerException
	{
		// not dburl=(.+);?(user=.*;password=.*;)?(logtable=.+)?
		// because "+" in regular express is greedy.so add ? after + to make it
		// lazy
		Pattern patAll = Pattern.compile("dburl=(.+?);?(user=.*;password=.*;)?(logtable=.+)?");
		Matcher matAll = patAll.matcher(arg);
		if (!matAll.matches())
		{
			throw new LoggerException("Invalid argument:" + arg);
		}
		dbUrl = matAll.group(1);
		String userPwd = matAll.group(2);

		String logTableAll = matAll.group(3);
		if (logTableAll != null && logTableAll.trim().length() > 0)
		{
			Pattern patLogTableAll = Pattern.compile("logtable=(.+)");
			Matcher matLogTable = patLogTableAll.matcher(logTableAll);
			matLogTable.matches();
			logTable = matLogTable.group(1);
		} else
		{
			logTable = DEFAULTSQLLOGTABLE;
		}

		if (userPwd != null && userPwd.trim().length() > 0)
		{
			Pattern patUserPwd = Pattern.compile("user=(.*);password=(.*);");
			Matcher matUserPwd = patUserPwd.matcher(userPwd);
			if (!matUserPwd.matches())
			{
				throw new LoggerException("Invalid argument:" + arg);
			}

			user = matUserPwd.group(1);
			password = matUserPwd.group(2);
		}

		StringBuffer sbSQL = new StringBuffer();
		sbSQL.append("insert into ").append(logTable).append("(FID,FBeginTime,FEndTime,FSQL,FSQLType,FParameters)\n");
		sbSQL.append("values(?,?,?,?,?,?)");
		insertSQL = sbSQL.toString();
	}

	/**
	 * if application don't active for a long time,the Connection may be closed
	 * be DataBase due to timeout,so before use the Connection,we must check out
	 * whether it's closed.
	 * 
	 * @return
	 * @throws LoggerException
	 * @throws SQLException
	 */
	private Connection getConnection() throws LoggerException, SQLException
	{
		if (dbConnection != null && !dbConnection.isClosed())
		{
			return dbConnection;
		}

		try
		{
			if (user != null && user.trim().length() > 0)
			{
				dbConnection = DriverManager.getConnection(dbUrl, user, password);
			} else
			{
				dbConnection = DriverManager.getConnection(dbUrl);
			}
			return dbConnection;
		} catch (SQLException e)
		{
			throw new LoggerException("error ocurred when DataBaseDBListener try" + "to connect to the target DataBase:" + e.getMessage());
		}
	}

	public synchronized void logSql(SQLInfo info) throws LoggerException
	{

		PreparedStatement logStatement = null;
		try
		{
			logStatement = getConnection().prepareStatement(insertSQL);
			RandomGUID randomGUID = new RandomGUID();
			logStatement.setString(1, randomGUID.toString());
			logStatement.setTimestamp(2, info.getBeginTime());
			logStatement.setTimestamp(3, info.getEndTime());
			String sql = info.getSql();

			// if not store null string as blank,some JDBC Driver may report
			// error
			// when read from database
			if (sql == null || sql.length() <= 0)
			{
				sql = "  ";
			}
			StringReader readerSQL = new StringReader(sql);
			try
			{
				logStatement.setCharacterStream(4, readerSQL, sql.length());
			} finally
			{
				readerSQL.close();
			}

			logStatement.setInt(5, info.getSqlType().getValue());

			String params = info.parameterToString();
			if (params == null || params.length() <= 0)
			{
				params = "  ";
			}

			StringReader readerParam = new StringReader(params);
			try
			{
				logStatement.setCharacterStream(6, readerParam, params.length());
			} finally
			{
				readerParam.close();
			}
			logStatement.execute();
		} catch (SQLException e)
		{
			throw LoggerUtils.toLoggerException(e);
		} finally
		{
			InstanceUtils.closeStatement(logStatement);
		}
	}

	public void close() throws LoggerException
	{
		InstanceUtils.closeConnection(dbConnection);
		dbUrl = null;
		user = null;
		password = null;
		logTable = null;
		dbConnection = null;
		insertSQL = null;
	}

}
