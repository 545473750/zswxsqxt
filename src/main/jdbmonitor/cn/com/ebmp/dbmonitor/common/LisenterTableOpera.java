package cn.com.ebmp.dbmonitor.common;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import cn.com.ebmp.dbmonitor.listenerImpl.RandomGUID;

import com.opendata.common.util.Platform;

/**
 * 监听表操作类
 * 
 * @author zhangyong
 * 
 */
public class LisenterTableOpera {

	protected static Log logger = LogFactory.getLog(LisenterTableOpera.class);

	private static Map<String, String> lisenterTableMap = null;// 监控表名

	private static String configFile = "/JDBCMonitorConfig.xml";

	private static String lisenterTableName = "";// 保存监听记录的表

	private static String isWrite = "false";// 是否进行记录入库

	private static String insertSQL = "";// 插入sql

	/**
	 * 判断该表是否被监听
	 * 
	 * @param tableName
	 * @return
	 */
	public static boolean isLisenterTable(String tableName) {
		if (lisenterTableMap == null) {
			boolean isSuccess = init();
			if (!isSuccess) {
				return false;
			}
		}
		return lisenterTableMap.containsKey(tableName);
	}

	/**
	 * 将监听信息入库
	 * 
	 * @param tableName
	 *            表名
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param sql
	 *            完整sql
	 * @param paramenters
	 *            参数
	 * @param type
	 *            类型 insert|update|delete|select
	 * @param whereCondition
	 *            where条件
	 * @return
	 * 
	 */
	public static boolean writeInfo(String tableName, String beginTime, String endTime, String sql, String paramenters, String type, String whereCondition) {
		boolean result = false;
		if (isLisenterTable(tableName) && isWrite()) {
			PreparedStatement insertStatement = null;
			try {
				insertStatement = getConnection().prepareStatement(insertSQL);
				RandomGUID randomGUID = new RandomGUID();
				insertStatement.setString(1, randomGUID.toString());
				insertStatement.setString(2, beginTime);
				insertStatement.setString(3, endTime);
				insertStatement.setString(4, sql);
				insertStatement.setString(5, paramenters);
				insertStatement.setString(6, type);
				insertStatement.setString(7, tableName);
				insertStatement.setString(8, whereCondition);
				insertStatement.executeUpdate();
				logger.info("监听信息入库成功");
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
				logger.info("监听信息入库失败");
			} finally {
				InstanceUtils.closeStatement(insertStatement);
			}
		}
		return result;
	}

	/**
	 * 判断是否进行入库
	 * 
	 * @return
	 */
	private static boolean isWrite() {
		if (lisenterTableName != null && !"".equals(lisenterTableName)) {
			// 检查数据库中是否有此表，没有则创建该表
			if (!checkTable(lisenterTableName)) {
				isWrite = "false";// 检查出错，不记录
			}
		} else {
			isWrite = "false";// 不记录
		}
		if (isWrite != null && "true".equals(isWrite)) {
			return true;
		}
		return false;
	}

	/**
	 * 初始化要监听的表
	 * 
	 */
	private static boolean init() {
		boolean isSuccess = true;
		try {
			lisenterTableMap = new HashMap<String, String>();
			// 读取配置文件
			Element rootElement = new SAXBuilder().build(getConfigFilePath()).getRootElement();
			Element lisenterTables = rootElement.getChild("lisenterTables");
			lisenterTableName = lisenterTables.getAttributeValue("lisenterTableName");
			isWrite = lisenterTables.getAttributeValue("isWrite");

			// 设置监听的表
			List tableList = lisenterTables.getChildren("table");
			for (int i = 0; i < tableList.size(); i++) {
				Element element = (Element) tableList.get(i);
				String name = element.getAttributeValue("name");
				if (name != null && !"".equals(name)) {
					lisenterTableMap.put(name, name);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		return isSuccess;
	}

	/**
	 * 系统配置文件路径
	 * 
	 * @return
	 */
	private static InputStream getConfigFilePath() {
		URL url = LisenterTableOpera.class.getResource(configFile);
		InputStream in = null;
		try {
			in = new BufferedInputStream(url.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return in;
	}

	/**
	 * 取得连接
	 * 
	 * @return
	 * @throws LoggerException
	 * @throws SQLException
	 */
	private static Connection getConnection() {
		Connection con = null;
		try {
			DataSource dataSource = Platform.getBean("dataSource");
			con = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * 检查表是否存在，不存在创建
	 * 
	 * @return
	 */
	private static boolean checkTable(String lisenterTableName) {
		boolean result = true;// FID,FBeginTime,FEndTime,FSQL,FSQLType,FParameters
		String ctSql = "select * from " + lisenterTableName;
		String createSql = "create table " + lisenterTableName
				+ " (id varchar(36),beginTime varchar(19),endTime varchar(19),sql varchar(1000),paramenters varchar(1000),type varchar(10),tableName varchar(30),whereCondition varchar(1000))";
		Connection con = getConnection();
		Statement ctStatement = null;
		try {
			ctStatement = con.createStatement();
			ctStatement.execute(ctSql);
		} catch (Exception e) {
			logger.info("没有找到表 " + lisenterTableName);
			result = false;
		} finally {
			InstanceUtils.closeStatement(ctStatement);
		}
		if (!result) {
			// 创建表
			PreparedStatement createStatement = null;
			try {
				createStatement = con.prepareStatement(createSql);
				result = createStatement.execute();
				result = true;
				logger.info("创建表 " + lisenterTableName + "成功");
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
				logger.info("创建表 " + lisenterTableName + "失败");
			} finally {
				InstanceUtils.closeStatement(createStatement);
			}
		}
		// 设置插入的sql语句
		insertSQL = "insert into " + lisenterTableName + "(id,beginTime,endTime,sql,paramenters,type,tableName,whereCondition) values(?,?,?,?,?,?,?,?)";
		return result;
	}
}
