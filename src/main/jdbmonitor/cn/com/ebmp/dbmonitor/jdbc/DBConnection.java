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
 * DBConnection.java
 * ---------------
 * (C) Copyright 2006-2006, by yang zhongke
 *
 * Original Author:  yang zhongke;
 *
 * Changes
 * -------
 * 2006-06-07 close the DBLogger when the Connection closed,
 *   if error ocurred when DBLogger inits,the System will exit;
 */
package cn.com.ebmp.dbmonitor.jdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import cn.com.ebmp.dbmonitor.common.DBListenerInfo;
import cn.com.ebmp.dbmonitor.common.DBLogger;
import cn.com.ebmp.dbmonitor.common.LoggerException;
import cn.com.ebmp.dbmonitor.common.SQLInfo;
import cn.com.ebmp.dbmonitor.common.SQLTypeEnum;

/**
 * the JDBC Connection of DBMonitor
 * 
 * @author yang zhongke
 */
public class DBConnection implements Connection
{
	private Connection conn;

	private DBLogger logger;

	public void logSql(SQLInfo info) throws LoggerException, SQLException
	{
		logger.logSQL(info);
	}

	public DBConnection(Connection conn, DBListenerInfo[] dbListenerInfos, String connectionId)
	{
		super();
		this.conn = conn;
		try
		{
			logger = DBLogger.createLogger(dbListenerInfos, connectionId);
		} catch (LoggerException e)
		{
			e.printStackTrace();
			System.exit(0);
		} catch (SQLException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void clearWarnings() throws SQLException
	{
		conn.clearWarnings();
	}

	public void close() throws SQLException
	{
		try
		{
			logger.close();
		} catch (LoggerException e)
		{
			throw JdbcUtils.toSQLException(e);
		} finally
		{
			conn.close();
			conn = null;
			logger = null;
		}
	}

	public void commit() throws SQLException
	{
		conn.commit();
	}

	public Statement createStatement() throws SQLException
	{
		return new DBStatement(conn.createStatement(), this);
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException
	{
		return new DBStatement(conn.createStatement(resultSetType, resultSetConcurrency), this);
	}

	public boolean getAutoCommit() throws SQLException
	{
		return conn.getAutoCommit();
	}

	public String getCatalog() throws SQLException
	{
		return conn.getCatalog();
	}

	public DatabaseMetaData getMetaData() throws SQLException
	{
		return conn.getMetaData();
	}

	public int getTransactionIsolation() throws SQLException
	{
		return conn.getTransactionIsolation();
	}

	public Map getTypeMap() throws SQLException
	{
		return conn.getTypeMap();
	}

	public SQLWarning getWarnings() throws SQLException
	{
		return conn.getWarnings();
	}

	public boolean isClosed() throws SQLException
	{
		return conn.isClosed();
	}

	public boolean isReadOnly() throws SQLException
	{
		return conn.isReadOnly();
	}

	public String nativeSQL(String sql) throws SQLException
	{
		return conn.nativeSQL(sql);
	}

	public CallableStatement prepareCall(String sql) throws SQLException
	{
		return conn.prepareCall(sql);
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException
	{
		return conn.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException
	{
		return new DBPreparedStatement(conn.prepareStatement(sql), this, sql);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException
	{
		return new DBPreparedStatement(conn.prepareStatement(sql, resultSetType, resultSetConcurrency), this, sql);
	}

	public void rollback() throws SQLException
	{
		try
		{
			SQLInfo info = new SQLInfo();
			info.setSql("");
			info.setSqlType(SQLTypeEnum.rollback);
			info.setBeginTime(JdbcUtils.getTimeStamp());
			conn.rollback();
			info.setEndTime(JdbcUtils.getTimeStamp());
			logSql(info);
		} catch (LoggerException e)
		{
			throw JdbcUtils.toSQLException(e);
		}
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException
	{
		conn.setAutoCommit(autoCommit);
	}

	public void setCatalog(String catalog) throws SQLException
	{
		conn.setCatalog(catalog);
	}

	public void setReadOnly(boolean readOnly) throws SQLException
	{
		conn.setReadOnly(readOnly);
	}

	public void setTransactionIsolation(int level) throws SQLException
	{
		conn.setTransactionIsolation(level);
	}

	/*
	public void setTypeMap(Map map) throws SQLException
	{
		conn.setTypeMap(map);
	}
  */
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException
	{
		Statement stmt = conn.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
		return new DBStatement(stmt, this);
	}

	public int getHoldability() throws SQLException
	{
		return conn.getHoldability();
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException
	{
		CallableStatement stmt = conn.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		return stmt;
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException
	{
		PreparedStatement stmt = conn.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		return new DBPreparedStatement(stmt, this, sql);
	}

	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException
	{
		PreparedStatement stmt = conn.prepareStatement(sql, autoGeneratedKeys);
		return new DBPreparedStatement(stmt, this, sql);
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException
	{
		PreparedStatement stmt = conn.prepareStatement(sql, columnIndexes);
		return new DBPreparedStatement(stmt, this, sql);
	}

	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException
	{
		PreparedStatement stmt = conn.prepareStatement(sql, columnNames);
		return new DBPreparedStatement(stmt, this, sql);
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException
	{
		conn.releaseSavepoint(savepoint);
	}

	public void rollback(Savepoint savepoint) throws SQLException
	{
		try
		{
			SQLInfo info = new SQLInfo();
			info.setSql("");
			info.setSqlType(SQLTypeEnum.rollback);
			info.setBeginTime(JdbcUtils.getTimeStamp());
			conn.rollback(savepoint);
			info.setEndTime(JdbcUtils.getTimeStamp());
			logSql(info);
		} catch (LoggerException e)
		{
			throw JdbcUtils.toSQLException(e);
		}

	}

	public void setHoldability(int holdability) throws SQLException
	{
		conn.setHoldability(holdability);
	}

	public Savepoint setSavepoint() throws SQLException
	{
		return conn.setSavepoint();
	}

	public Savepoint setSavepoint(String name) throws SQLException
	{
		return conn.setSavepoint(name);
	}

	
	
	/*******************以下为jdk6.0及以上版本实现的方法，目前未加功能，但不影响使用***********************/
	public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Clob createClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob createBlob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob createNClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setClientInfo(String name, String value)
			throws SQLClientInfoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClientInfo(Properties properties)
			throws SQLClientInfoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getClientInfo(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSchema(String schema) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSchema() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void abort(Executor executor) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
}
