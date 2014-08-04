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
 * DBDriver.java
 * ---------------
 * (C) Copyright 2006-2006, by yang zhongke
 *
 * Original Author:  yang zhongke;
 *
 * Changes
 * -------
 * 2006-05-25 by YangZhongke
 * in linux or unix,listenerconfig=/root/config.xml is token for classpath,
 * in fact it's real file path,becasue
 * in linux or unix,real file path not start with words like c:/ d:/
 * 2006-05-26 by Yangzhongke
 * According to JDBC specification,in method "connect",if the url not accepted by the Driver,
 * "connect" should return null instead of raise exception 
 */
package cn.com.ebmp.dbmonitor.jdbc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.ebmp.dbmonitor.common.CommonUtils;
import cn.com.ebmp.dbmonitor.common.ConfigFileInfo;
import cn.com.ebmp.dbmonitor.common.ConfigFileReader;
import cn.com.ebmp.dbmonitor.common.DBListenerInfo;
import cn.com.ebmp.dbmonitor.common.InstanceUtils;

/**
 * the JDBC driver of the DBMonitor
 * 
 * @author yang zhongke
 */
public class DBDriver implements java.sql.Driver {
	public synchronized Connection connect(String url, Properties info) throws SQLException {
		if (!acceptsURL(url)) {
			// return null instead of raise exception
			return null;
		}

		Matcher match = getURLMatcher(url);
		match.matches();
		match.group();
		String configFile = match.group(1);
		String realUrl = match.group(2);

		ConfigFileInfo configInfo = null;

		InputStream is = null;
		try {

			// assumn configFile as classpath,if fail then treate it as filepath
			try {
				is = getClass().getResourceAsStream(configFile);
			} catch (RuntimeException e) {
				e.printStackTrace();
				is = null;
			}
			if (is == null) {
				try {
					is = new FileInputStream(configFile);
				} catch (RuntimeException e) {
					is = null;
				}
			}

			if (is == null) {
				throw new SQLException("config file not found:" + configFile);
			}
			// get the configFileInfo from configFile
			configInfo = ConfigFileReader.getConfigFileInfo(is);

			// register the necessary JDBCDriver
			// generally, it's the real JdbcDriver of the database to be logged
			registerRealJdbcDriver(configInfo);
		} catch (FileNotFoundException e) {
			throw JdbcUtils.toSQLException(e);
		} finally {
			InstanceUtils.closeInStream(is);
		}

		// get the real database connection of the database to be logged
		Connection cn = DriverManager.getConnection(realUrl, info);

		// if the monitor is not active,return the real database connection
		if (!configInfo.isActive()) {
			return cn;
		}

		List lisList = configInfo.getListenerInfoList();
		DBListenerInfo[] dbListenerInfos = new DBListenerInfo[lisList.size()];
		for (int i = 0, n = lisList.size(); i < n; i++) {
			DBListenerInfo lisInfo = (DBListenerInfo) lisList.get(i);
			dbListenerInfos[i] = lisInfo;
		}
		// "configFile" will be unique in one JVM,so use it as connecitonId
		return new DBConnection(cn, dbListenerInfos, configFile);
	}

	/**
	 * get the regular expression matcher of the jdbcurl
	 * 
	 * @param url
	 * @return
	 */
	private Matcher getURLMatcher(String url) {
		Pattern pattern = Pattern.compile("listenerconfig=(.+):url=(.+)");
		Matcher match = pattern.matcher(url);
		return match;
	}

	private synchronized void registerRealJdbcDriver(ConfigFileInfo configInfo) throws SQLException {
		List list = configInfo.getRealJdbcDriverList();
		for (int i = 0, n = list.size(); i < n; i++) {
			try {
				Class.forName(list.get(i).toString());
			} catch (ClassNotFoundException e) {
				throw JdbcUtils.toSQLException(e);
			}
		}

	}

	public boolean acceptsURL(String url) throws SQLException {
		Matcher m = getURLMatcher(url);
		return m.matches();
	}

	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		DriverPropertyInfo[] props = new DriverPropertyInfo[1];
		props[0] = new DriverPropertyInfo("author", "Yang Zhongke");
		return props;
	}

	public int getMajorVersion() {
		return 1;
	}

	public int getMinorVersion() {
		return 0;
	}

	public boolean jdbcCompliant() {
		return true;
	}

	static {
		try {
			DriverManager.registerDriver(new DBDriver());
		} catch (Exception e) {
			throw CommonUtils.toRuntimeException(e);
		}
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
}
