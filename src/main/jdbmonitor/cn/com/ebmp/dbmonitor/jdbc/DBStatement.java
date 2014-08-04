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
 * DBStatement.java
 * ---------------
 * (C) Copyright 2006-2006, by yang zhongke
 *
 * Original Author:  yang zhongke;
 *
 * Changes
 * -------
 *
 */
package cn.com.ebmp.dbmonitor.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import cn.com.ebmp.dbmonitor.common.LoggerException;
import cn.com.ebmp.dbmonitor.common.SQLInfo;
import cn.com.ebmp.dbmonitor.common.SQLTypeEnum;

/**
 * Statement of the Monitor JDBCDriver
 * 
 * @author yang zhongke
 */
public class DBStatement implements Statement
{
	protected Statement stmt;

	protected DBConnection cn;

	protected StringBuffer sbAddBatch = new StringBuffer();

	public void logSql(SQLInfo info) throws SQLException
	{
		try
		{
			cn.logSql(info);
		} catch (LoggerException e)
		{
			throw JdbcUtils.toSQLException(e);
		}
	}

	public DBStatement(Statement stmt, DBConnection cn)
	{
		super();
		this.stmt = stmt;
		this.cn = cn;
	}

	public void addBatch(String sql) throws SQLException
	{
		sbAddBatch.append(sql).append(";");
		stmt.addBatch(sql);
	}

	public void cancel() throws SQLException
	{
		stmt.cancel();
	}

	public void clearBatch() throws SQLException
	{
		stmt.clearBatch();
		sbAddBatch.setLength(0);
	}

	public void clearWarnings() throws SQLException
	{
		stmt.clearWarnings();
	}

	public void close() throws SQLException
	{
		stmt.close();
		stmt = null;
		sbAddBatch = null;
	}

	public boolean execute(String sql) throws SQLException
	{
		SQLInfo info = new SQLInfo();
		info.setSqlType(SQLTypeEnum.execute);
		info.setBeginTime(JdbcUtils.getTimeStamp());
		info.setSql(sql);
		boolean ret = stmt.execute(sql);

		info.setEndTime(JdbcUtils.getTimeStamp());
		logSql(info);
		return ret;
	}

	public int[] executeBatch() throws SQLException
	{
		SQLInfo info = new SQLInfo();
		info.setSqlType(SQLTypeEnum.executeBatch);
		info.setBeginTime(JdbcUtils.getTimeStamp());
		info.setSql(sbAddBatch.toString());

		int[] ret = stmt.executeBatch();

		info.setEndTime(JdbcUtils.getTimeStamp());
		logSql(info);
		sbAddBatch.setLength(0);
		return ret;
	}

	public ResultSet executeQuery(String sql) throws SQLException
	{
		SQLInfo info = new SQLInfo();
		info.setSqlType(SQLTypeEnum.executeQuery);
		info.setBeginTime(JdbcUtils.getTimeStamp());
		info.setSql(sql);

		ResultSet ret = stmt.executeQuery(sql);

		info.setEndTime(JdbcUtils.getTimeStamp());
		logSql(info);
		return ret;
	}

	public int executeUpdate(String sql) throws SQLException
	{
		SQLInfo info = new SQLInfo();
		info.setSqlType(SQLTypeEnum.executeQuery);
		info.setBeginTime(JdbcUtils.getTimeStamp());
		info.setSql(sql);

		int ret = stmt.executeUpdate(sql);

		info.setEndTime(JdbcUtils.getTimeStamp());
		logSql(info);
		return ret;
	}

	public Connection getConnection() throws SQLException
	{
		return cn;
	}

	public int getFetchDirection() throws SQLException
	{
		return stmt.getFetchDirection();
	}

	public int getFetchSize() throws SQLException
	{
		return stmt.getFetchSize();
	}

	public int getMaxFieldSize() throws SQLException
	{
		return stmt.getMaxFieldSize();
	}

	public int getMaxRows() throws SQLException
	{
		return stmt.getMaxRows();
	}

	public boolean getMoreResults() throws SQLException
	{
		return stmt.getMoreResults();
	}

	public int getQueryTimeout() throws SQLException
	{
		return stmt.getQueryTimeout();
	}

	public ResultSet getResultSet() throws SQLException
	{
		return stmt.getResultSet();
	}

	public int getResultSetConcurrency() throws SQLException
	{
		return stmt.getResultSetConcurrency();
	}

	public int getResultSetType() throws SQLException
	{
		return stmt.getResultSetType();
	}

	public int getUpdateCount() throws SQLException
	{
		return stmt.getUpdateCount();
	}

	public SQLWarning getWarnings() throws SQLException
	{
		return stmt.getWarnings();
	}

	public void setCursorName(String name) throws SQLException
	{
		stmt.setCursorName(name);
	}

	public void setEscapeProcessing(boolean p) throws SQLException
	{
		stmt.setEscapeProcessing(p);
	}

	public void setFetchDirection(int p) throws SQLException
	{
		stmt.setFetchDirection(p);
	}

	public void setFetchSize(int p) throws SQLException
	{
		stmt.setFetchSize(p);
	}

	public void setMaxFieldSize(int p) throws SQLException
	{
		stmt.setMaxFieldSize(p);
	}

	public void setMaxRows(int p1) throws SQLException
	{
		stmt.setMaxRows(p1);
	}

	public void setQueryTimeout(int p1) throws SQLException
	{
		stmt.setQueryTimeout(p1);
	}

	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException
	{
		SQLInfo info = new SQLInfo();
		info.setSqlType(SQLTypeEnum.execute);
		info.setBeginTime(JdbcUtils.getTimeStamp());
		boolean ret = stmt.execute(sql, autoGeneratedKeys);
		info.setEndTime(JdbcUtils.getTimeStamp());
		logSql(info);
		return ret;
	}

	public boolean execute(String sql, int[] columnIndexes) throws SQLException
	{
		SQLInfo info = new SQLInfo();
		info.setSqlType(SQLTypeEnum.execute);
		info.setBeginTime(JdbcUtils.getTimeStamp());
		boolean ret = stmt.execute(sql, columnIndexes);
		info.setEndTime(JdbcUtils.getTimeStamp());
		logSql(info);
		return ret;
	}

	public boolean execute(String sql, String[] columnNames) throws SQLException
	{
		SQLInfo info = new SQLInfo();
		info.setSqlType(SQLTypeEnum.execute);
		info.setBeginTime(JdbcUtils.getTimeStamp());
		boolean ret = stmt.execute(sql, columnNames);
		info.setEndTime(JdbcUtils.getTimeStamp());
		logSql(info);
		return ret;
	}

	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException
	{
		SQLInfo info = new SQLInfo();
		info.setSqlType(SQLTypeEnum.executeUpdate);
		info.setBeginTime(JdbcUtils.getTimeStamp());
		int ret = stmt.executeUpdate(sql, autoGeneratedKeys);
		info.setEndTime(JdbcUtils.getTimeStamp());
		logSql(info);
		return ret;
	}

	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException
	{
		SQLInfo info = new SQLInfo();
		info.setSqlType(SQLTypeEnum.executeUpdate);
		info.setBeginTime(JdbcUtils.getTimeStamp());
		int ret = stmt.executeUpdate(sql, columnIndexes);
		info.setEndTime(JdbcUtils.getTimeStamp());
		logSql(info);
		return ret;
	}

	public int executeUpdate(String sql, String[] columnNames) throws SQLException
	{
		SQLInfo info = new SQLInfo();
		info.setSqlType(SQLTypeEnum.executeUpdate);
		info.setBeginTime(JdbcUtils.getTimeStamp());
		int ret = stmt.executeUpdate(sql, columnNames);
		info.setEndTime(JdbcUtils.getTimeStamp());
		logSql(info);
		return ret;
	}

	public ResultSet getGeneratedKeys() throws SQLException
	{
		return stmt.getGeneratedKeys();
	}

	public boolean getMoreResults(int current) throws SQLException
	{
		return stmt.getMoreResults(current);
	}

	public int getResultSetHoldability() throws SQLException
	{
		return stmt.getResultSetHoldability();
	}

	
	
	
	/*******************以下为jdk6.0及以上版本实现的方法，目前未加功能，但不影响使用***********************/
	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return stmt.isClosed();
	}

	@Override
	public boolean isPoolable() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPoolable(boolean poolable) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}


}
