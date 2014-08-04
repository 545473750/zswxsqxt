package cn.com.ebmp.dbmonitor.common;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class LisenterTableMap {

	private static Map<String, String> lisenterTableMap = null;// 监控表名

	private static String configFile = "/JDBCMonitorConfig.xml";

	/**
	 * 初始化要监听的表
	 * 
	 */
	private static boolean init() {
		boolean isSuccess = true;
		try {
			lisenterTableMap = new HashMap<String, String>();
			// 读取配置文件
			Element rootElement = new SAXBuilder().build(getConfigFilePath()) .getRootElement();
			Element lisenterTables = rootElement.getChild("lisenterTables");
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
	 * 系统配置文件路径
	 * 
	 * @return
	 */
	private static InputStream getConfigFilePath() {
		URL url = LisenterTableMap.class.getResource(configFile);
		InputStream in = null;
		try {
			in = new BufferedInputStream(url.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return in;
	}
}
