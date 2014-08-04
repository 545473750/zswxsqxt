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
 * DBPreparedStatement.java
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import cn.com.ebmp.dbmonitor.common.InstanceUtils;
import cn.com.ebmp.dbmonitor.common.SQLInfo;
import cn.com.ebmp.dbmonitor.common.SQLTypeEnum;

/**
 * PreparedStatement of the Monitor JDBCDriver
 * 
 * @author yang zhongke
 */
public class DBPreparedStatement extends DBStatement implements PreparedStatement {
	private String sql;

	private Vector paramsVector;

	private List batchParamList;

	public DBPreparedStatement(Statement stmt, DBConnection cn, String sql) {
		super(stmt, cn);
		this.sql = sql;
		batchParamList = new ArrayList();
		paramsVector = new Vector(0);
	}

	public void addBatch() throws SQLException {
		((PreparedStatement) stmt).addBatch();

		batchParamList.add(paramsVector.clone());
		paramsVector.setSize(0);
	}

	public void clearParameters() throws SQLException {
		((PreparedStatement) stmt).clearParameters();
		paramsVector.clear();
		batchParamList.clear();
	}

	public boolean execute() throws SQLException {
		SQLInfo info = new SQLInfo();
		info.setSqlType(SQLTypeEnum.execute);
		info.setBeginTime(JdbcUtils.getTimeStamp());
		info.setSql(sql);

		boolean ret = ((PreparedStatement) stmt).execute();

		info.setEndTime(JdbcUtils.getTimeStamp());

		info.addParameterList((List) paramsVector.clone());
		logSql(info);
		paramsVector.setSize(0);
		return ret;
	}

	public ResultSet executeQuery() throws SQLException {
		SQLInfo info = new SQLInfo();
		info.setSqlType(SQLTypeEnum.executeQuery);
		info.setBeginTime(JdbcUtils.getTimeStamp());
		info.setSql(sql);

		ResultSet ret = ((PreparedStatement) stmt).executeQuery();

		info.setEndTime(JdbcUtils.getTimeStamp());

		info.addParameterList((List) paramsVector.clone());

		logSql(info);
		paramsVector.setSize(0);
		return ret;
	}

	public int executeUpdate() throws SQLException {
		SQLInfo info = new SQLInfo();
		info.setSqlType(SQLTypeEnum.executeUpdate);
		info.setBeginTime(JdbcUtils.getTimeStamp());
		info.setSql(sql);

		int ret = ((PreparedStatement) stmt).executeUpdate();

		info.setEndTime(JdbcUtils.getTimeStamp());

		info.addParameterList((List) paramsVector.clone());
		logSql(info);
		paramsVector.setSize(0);
		return ret;
	}

	public int[] executeBatch() throws SQLException {
		SQLInfo info = new SQLInfo();
		info.setSqlType(SQLTypeEnum.executeBatch);
		info.setBeginTime(JdbcUtils.getTimeStamp());
		info.setSql(sql);

		int[] ret = stmt.executeBatch();

		info.setEndTime(JdbcUtils.getTimeStamp());

		for (int i = 0, n = batchParamList.size(); i < n; i++) {
			info.addParameterList((List) batchParamList.get(i));
		}

		batchParamList.clear();
		paramsVector.setSize(0);

		logSql(info);
		sbAddBatch.setLength(0);
		return ret;
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		return ((PreparedStatement) stmt).getMetaData();
	}

	public void setArray(int index, Array val) throws SQLException {
		setstmtParams(index, val);
		((PreparedStatement) stmt).setArray(index, val);
	}

	public void setAsciiStream(int index, InputStream val, int length) throws SQLException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(val));

			StringBuffer sb = new StringBuffer();
			String str = null;
			while ((str = reader.readLine()) != null) {
				sb.append(str);
			}
			setstmtParams(index, sb.toString());
		} catch (IOException e) {
			throw JdbcUtils.toSQLException(e);
		} finally {
			InstanceUtils.closeReader(reader);
		}
		((PreparedStatement) stmt).setAsciiStream(index, val, length);
	}

	public void setBigDecimal(int index, BigDecimal val) throws SQLException {
		setstmtParams(index, val);
		((PreparedStatement) stmt).setBigDecimal(index, val);
	}

	public void setBinaryStream(int index, InputStream val, int p3) throws SQLException {
		setstmtParams(index, val);
		((PreparedStatement) stmt).setBinaryStream(index, val, p3);
	}

	public void setBlob(int index, Blob val) throws SQLException {
		setstmtParams(index, val);
		((PreparedStatement) stmt).setBlob(index, val);
	}

	public void setBoolean(int index, boolean val) throws SQLException {
		setstmtParams(index, val ? Boolean.TRUE : Boolean.FALSE);
		((PreparedStatement) stmt).setBoolean(index, val);
	}

	public void setByte(int index, byte val) throws SQLException {
		setstmtParams(index, new Byte(val));
		((PreparedStatement) stmt).setByte(index, val);
	}

	public void setBytes(int index, byte[] val) throws SQLException {
		setstmtParams(index, val);
		((PreparedStatement) stmt).setBytes(index, val);
	}

	public void setCharacterStream(int index, Reader val, int p3) throws SQLException {
		setstmtParams(index, val);
		((PreparedStatement) stmt).setCharacterStream(index, val, p3);
	}

	public void setClob(int index, Clob val) throws SQLException {
		setstmtParams(index, val);
		((PreparedStatement) stmt).setClob(index, val);
	}

	public void setDate(int index, Date val) throws SQLException {
		setstmtParams(index, val);
		((PreparedStatement) stmt).setDate(index, val);
	}

	public void setDate(int index, Date val, Calendar p3) throws SQLException {
		setstmtParams(index, val);
		((PreparedStatement) stmt).setDate(index, val, p3);
	}

	public void setDouble(int index, double val) throws SQLException {
		setstmtParams(index, new Double(val));
		((PreparedStatement) stmt).setDouble(index, val);
	}

	public void setFloat(int index, float val) throws SQLException {
		setstmtParams(index, new Float(val));
		((PreparedStatement) stmt).setFloat(index, val);
	}

	public void setInt(int index, int val) throws SQLException {
		setstmtParams(index, new Integer(val));
		((PreparedStatement) stmt).setInt(index, val);
	}

	public void setLong(int index, long val) throws SQLException {
		setstmtParams(index, new Long(val));
		((PreparedStatement) stmt).setLong(index, val);
	}

	public void setNull(int index, int sqlType) throws SQLException {
		setstmtParams(index, null);
		((PreparedStatement) stmt).setNull(index, sqlType);
	}

	public void setNull(int index, int p2, String p3) throws SQLException {
		setstmtParams(index, null);
		((PreparedStatement) stmt).setNull(index, p2, p3);
	}

	public void setObject(int index, Object val) throws SQLException {
		setstmtParams(index, val);
		((PreparedStatement) stmt).setObject(index, val);
	}

	public void setObject(int index, Object val, int p3) throws SQLException {
		setstmtParams(index, val);
		((PreparedStatement) stmt).setObject(index, val, p3);
	}

	public void setObject(int index, Object val, int p3, int p4) throws SQLException {
		setstmtParams(index, val);
		((PreparedStatement) stmt).setObject(index, val, p3, p4);
	}

	public void setRef(int index, Ref val) throws SQLException {
		setstmtParams(index, val);
		((PreparedStatement) stmt).setRef(index, val);
	}

	public void setShort(int index, short val) throws SQLException {
		setstmtParams(index, new Short(val));
		((PreparedStatement) stmt).setShort(index, val);
	}

	public void setString(int index, String val) throws SQLException {
		setstmtParams(index, val);
		((PreparedStatement) stmt).setString(index, val);
	}

	private void setstmtParams(int index, Object val) {
		if (paramsVector.size() < index) {
			paramsVector.setSize(index);
		}

		paramsVector.set(index - 1, val);

	}

	public void setTime(int index, Time val) throws SQLException {
		setstmtParams(index, val);
		((PreparedStatement) stmt).setTime(index, val);
	}

	public void setTime(int index, Time val, Calendar p3) throws SQLException {
		setstmtParams(index, val);
		((PreparedStatement) stmt).setTime(index, val, p3);
	}

	public void setTimestamp(int index, Timestamp val) throws SQLException {
		setstmtParams(index, val);
		((PreparedStatement) stmt).setTimestamp(index, val);
	}

	public void setTimestamp(int index, Timestamp val, Calendar p3) throws SQLException {
		setstmtParams(index, val);
		((PreparedStatement) stmt).setTimestamp(index, val, p3);
	}

	/**
	 * @deprecated
	 */
	public void setUnicodeStream(int index, InputStream val, int p3) throws SQLException {
		setstmtParams(index, val);
		((PreparedStatement) stmt).setUnicodeStream(index, val, p3);
	}

	public ParameterMetaData getParameterMetaData() throws SQLException {
		PreparedStatement preStmt = (PreparedStatement) stmt;
		return preStmt.getParameterMetaData();
	}

	public void setURL(int index, URL val) throws SQLException {
		setstmtParams(index, val);
		PreparedStatement preStmt = (PreparedStatement) stmt;
		preStmt.setURL(index, val);
	}

	public void close() throws SQLException {
		super.close();
		sql = null;
		paramsVector.removeAllElements();
		paramsVector = null;
		batchParamList.clear();
		batchParamList = null;
	}
	/*******************以下为jdk6.0及以上版本实现的方法，目前未加功能，但不影响使用***********************/

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader,
			long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(int parameterIndex, Reader reader, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value,
			long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNString(int parameterIndex, String value)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSQLXML(int parameterIndex, SQLXML xmlObject)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
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
}
