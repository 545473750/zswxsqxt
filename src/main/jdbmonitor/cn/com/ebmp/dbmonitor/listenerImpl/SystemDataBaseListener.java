package cn.com.ebmp.dbmonitor.listenerImpl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.ebmp.dbmonitor.common.LisenterTableOpera;
import cn.com.ebmp.dbmonitor.common.LoggerException;
import cn.com.ebmp.dbmonitor.common.SQLInfo;

public class SystemDataBaseListener implements cn.com.ebmp.dbmonitor.common.IDBListener {
	protected Log logger = LogFactory.getLog(getClass());

	// private SqlRunMonitorService sqlRunMonitorService;

	public SystemDataBaseListener() {
		super();
	}

	/**
	 * 初始化信息
	 */
	public void init(String arg) throws LoggerException {

	}

	/**
	 * 修改记录，获得主键ID信息，表信息，操作信息
	 * 
	 * @param sql
	 * @param param
	 */
	private String findPrimaryKeyForUpdate(String sql, List param) {
		int whereIndex = sql.indexOf("where") + 6;
		String whereAfter = sql.substring(whereIndex, sql.length()).trim();
		String whereBefor = sql.substring(0, whereIndex - 6);

		int whereBeginParam = 0;
		if (whereBefor.indexOf("?") != -1) {
			for (int i = 0; i < whereBefor.length(); i++) {
				if (whereBefor.substring(i, i + 1).equals("?")) {
					whereBeginParam++;
				}
			}
		}
		// 取得新的where条件
		int sumPram = 0;
		String newWhere = "";
		for (int i = 0; i < whereAfter.length(); i++) {
			String tmp = whereAfter.substring(i, i + 1);
			if (tmp.equals("?")) {
				sumPram++;
				tmp = "'" + param.get(whereBeginParam + sumPram - 1).toString() + "'";
			}
			newWhere += tmp;
		}
		return newWhere;
	}

	/**
	 * 删除记录，获得主键ID信息，表信息，操作信息
	 * 
	 * @param sql
	 * @param param
	 */
	private String findPrimaryKeyForDelete(String sql, List param) {
		int whereIndex = sql.indexOf("where") + 6;
		String whereAfter = sql.substring(whereIndex, sql.length()).trim();
		String whereBefor = sql.substring(0, whereIndex - 6);

		int whereBeginParam = 0;
		if (whereBefor.indexOf("?") != -1) {
			for (int i = 0; i < whereBefor.length(); i++) {
				if (whereBefor.substring(i, i + 1).equals("?")) {
					whereBeginParam++;
				}
			}
		}
		// 取得新的where条件
		int sumPram = 0;
		String newWhere = "";
		for (int i = 0; i < whereAfter.length(); i++) {
			String tmp = whereAfter.substring(i, i + 1);
			if (tmp.equals("?")) {
				sumPram++;
				tmp = "'" + param.get(whereBeginParam + sumPram - 1).toString() + "'";
			}
			newWhere += tmp;
		}
		return newWhere;
	}

	/**
	 * 插入记录，获得主键ID信息，表信息，操作信息
	 * 
	 * @param sql
	 * @param param
	 */
	private String findPrimaryKeyForInsert(String sql, List param) {
		String primarykey = "id";
		String value = "";
		int leftKF = sql.indexOf("(") + 1;
		int rightKF = sql.indexOf(")");
		String insertField = sql.substring(leftKF, rightKF);
		String[] insertFieldArr = insertField.split(",");
		for (int i = 0; i < insertFieldArr.length; i++) {
			if (insertFieldArr[i].trim().equals(primarykey)) {
				value = param.get(i).toString();
				break;
			}
		}
		return primarykey + "='" + value + "'";
	}

	/**
	 * 获得表名
	 * 
	 * @param sql
	 * @param operator
	 * @return
	 */
	private String findTableName(String sql, String operator) {
		String tableName = "";
		if (operator.equals("delete")) {
			int whereIndex = sql.indexOf("where");
			int tableIndex = sql.indexOf("delete from") + "delete from".length();
			tableName = sql.substring(tableIndex, whereIndex).trim();
		}

		if (operator.equals("update")) {

			int updateIndex = sql.indexOf("update") + 6;
			int setIndex = sql.indexOf("set");
			tableName = sql.substring(updateIndex, setIndex);
		}

		if (operator.equals("insert")) {
			int intoFlag = sql.indexOf(" into ") + 6;
			int leftKF = sql.indexOf("(") + 1;
			tableName = sql.substring(intoFlag, leftKF - 1).trim();
		}

		return tableName;
	}

	/**
	 * 对系统执行的SQL进行监控，并对超过系统配置中的SQL执行时间进行记录
	 * 
	 * @param info
	 * 
	 * private void sqlRunMonitor(SQLInfo info) {
	 * 
	 * if (sqlRunMonitorService == null) { sqlRunMonitorService =
	 * Platform.getBean(SqlRunMonitorService.BEAN_ID); } SqlRunMonitor sql = new
	 * SqlRunMonitor(); sql.setBeginDate(info.getBeginTime());
	 * sql.setEndDate(info.getEndTime());
	 * sql.setSqlType(info.getSqlType().toString()); List[] param =
	 * info.getParameterGroup(); StringBuffer paramString = new StringBuffer();
	 * for (int i = 0; i < param.length; i++) { List p = param[i]; for (int ps =
	 * 0; ps < p.size(); ps++) { paramString.append(p.get(ps) + "\t"); } }
	 * sql.setSql(info.getSql()); sql.setParameter(paramString.toString());
	 * sql.setUseMillisecond(sql.getEndDate().getTime() -
	 * sql.getBeginDate().getTime()); sqlRunMonitorService.saveMonitorSql(sql); }
	 */

	/**
	 * 对sql信息进行记录
	 */
	public void logSql(SQLInfo info) {
		// 执行系统sql监控
		// this.sqlRunMonitor(info);
		try {
			String infoSql = info.getSql().toLowerCase();
			logger.info("dbmonitor  sql=> " + infoSql);
			if (!info.getSql().trim().equals("")) {
				String sqlType = info.getSql().toLowerCase().split(" ")[0];
				if (sqlType.trim().equals("update") || sqlType.trim().equals("delete") || sqlType.trim().equals("insert")) {
					String sql = info.getSql().toLowerCase();
					List[] param = info.getParameterGroup();
					String paramString = "";
					for (int i = 0; i < param.length; i++) {
						List p = param[i];
						for (int ps = 0; ps < p.size(); ps++) {
							paramString += p.get(ps) + "\t";
						}
					}

					String tableName = "";
					String whereCondition = "";

					tableName = this.findTableName(sql, sqlType.trim()).trim().toLowerCase();
					if (sqlType.trim().equals("update")) {
						whereCondition = this.findPrimaryKeyForUpdate(sql, param[0]);
					}
					if (sqlType.trim().equals("delete")) {
						whereCondition = this.findPrimaryKeyForDelete(sql, param[0]);
					}
					if (sqlType.trim().equals("insert")) {
						whereCondition = this.findPrimaryKeyForInsert(sql, param[0]);
					}
					logger.info("执行语句=>" + sql);
					logger.info("参数值=>" + paramString);
					logger.info("dbmonitor sql=>" + sqlType + "操作数据 表名:" + tableName + "条件为:" + whereCondition);

					LisenterTableOpera.writeInfo(tableName, info.getBeginTime().toLocaleString(), info.getEndTime().toLocaleString(), sql, paramString, sqlType.trim(), whereCondition);

					// if (sqlType.trim().equals("update")) {
					// tableName = this.findTableName(sql,
					// "update").trim().toLowerCase();
					// if (LisenterTableOpera.isLisenterTable(tableName)) {
					// logger.info("执行语句=>" + sql);
					// logger.info(paramString);
					// whereCondition = this.findPrimaryKeyForUpdate(sql,
					// param[0]);
					// logger.info("dbmonitor sql=>" + "修改数据 表名:" + tableName +
					// "条件为:" + whereCondition);
					// }
					// return;
					// }
					//
					// if (sqlType.trim().equals("delete")) {
					// tableName = this.findTableName(sql, "delete");
					// if (LisenterTableOpera.isLisenterTable(tableName)) {
					// logger.info("执行语句=>" + sql);
					// logger.info(paramString);
					// whereCondition = this.findPrimaryKeyForDelete(sql,
					// param[0]);
					// logger.info("dbmonitor sql=>" + "删除数据 表名:" + tableName +
					// "条件为:" + whereCondition);
					// }
					// return;
					// }
					//
					// if (sqlType.trim().equals("insert")) {
					// tableName = this.findTableName(sql, "insert");
					// if (LisenterTableOpera.isLisenterTable(tableName)) {
					// logger.info("执行语句=>" + sql);
					// logger.info(paramString);
					// whereCondition = this.findPrimaryKeyForInsert(sql,
					// param[0]);
					// logger.info("dbmonitor sql=>" + "新增数据 表名:" + tableName +
					// "条件为:" + whereCondition);
					// }
					// return;
					// }

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() throws LoggerException {
	}
}
