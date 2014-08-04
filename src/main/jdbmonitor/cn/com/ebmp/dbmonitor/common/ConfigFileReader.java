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
 * ConfigFileReader.java
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
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cn.com.ebmp.dbmonitor.jdbc.JdbcUtils;

/**
 * the class read the config.xml
 * 
 * @author yang zhongke
 */
public class ConfigFileReader {

	/**
	 * parse the InputStream of the configFile
	 * 
	 * @param is
	 * @return the ConfigFileInfo
	 * @throws SQLException
	 */
	public static ConfigFileInfo getConfigFileInfo(InputStream is) throws SQLException {
		ConfigFileInfo configFileInfo = new ConfigFileInfo();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw JdbcUtils.toSQLException(e);
		}

		Document doc = null;
		try {
			doc = db.parse(is);
		} catch (IOException e) {
			throw JdbcUtils.toSQLException(e);
		} catch (SAXException e) {
			throw JdbcUtils.toSQLException(e);
		}

		Element root = doc.getDocumentElement();

		// parse the JdbcDrivers
		NodeList driverNodeList = root.getElementsByTagName("JdbcDriver");
		for (int i = 0, n = driverNodeList.getLength(); i < n; i++) {
			Node listenterNode = driverNodeList.item(i);
			String driverClass = listenterNode.getAttributes().getNamedItem("class").getNodeValue();
			configFileInfo.getRealJdbcDriverList().add(driverClass);
		}

		// parse the DBListeners Class
		NodeList listentersNodeList = root.getElementsByTagName("Listener");
		for (int i = 0, n = listentersNodeList.getLength(); i < n; i++) {
			Node listenterNode = listentersNodeList.item(i);
			String lisClass = listenterNode.getAttributes().getNamedItem("class").getNodeValue();
			String arg = listenterNode.getAttributes().getNamedItem("arg").getNodeValue();
			DBListenerInfo info = new DBListenerInfo();
			info.setArguments(arg);
			info.setListenerClass(CommonUtils.getListenerInstance(lisClass));
			configFileInfo.getListenerInfoList().add(info);
		}

		// parse the Active attributes of the monitor
		NodeList activeNodeList = root.getElementsByTagName("Active");
		if (activeNodeList.getLength() <= 0) {
			configFileInfo.setActive(true);
		} else {
			String active = activeNodeList.item(0).getChildNodes().item(0).getNodeValue();
			if (active.equalsIgnoreCase("true")) {
				configFileInfo.setActive(true);
			} else if (active.equalsIgnoreCase("false")) {
				configFileInfo.setActive(false);
			} else {
				throw new SQLException("Invalid 'Active' Value:" + active);
			}
		}

		return configFileInfo;
	}

}
