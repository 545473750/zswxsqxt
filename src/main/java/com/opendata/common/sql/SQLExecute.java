package com.opendata.common.sql;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * sql执行工具类
 * @author 付威
 */
public class SQLExecute {

	/**
	 * 获取数据库连接
	 * @param db
	 * @param url
	 * @param dbName
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 * @throws java.lang.ClassNotFoundException
	 */
	public static Connection getConnection(String db,String url,String dbName,String username,String password) throws SQLException,java.lang.ClassNotFoundException{
		if(db.equals("MYSQL")){
			url = "jdbc:mysql://" + url + "/" + dbName;
			Class.forName("com.mysql.jdbc.Driver");
		}else if(db.equals("SQLSERVER")){
			url = "jdbc:sqlserver://" + url + "/" + dbName;
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
		}

		return DriverManager.getConnection(url,username,password);
	}

	/**
	 * 执行SQL文件
	 * @param sqlFile
	 * @param connection
	 * @return
	 */
	public static boolean executeSQLFile(String sqlFile,Connection connection) throws Exception{
		Statement statement = connection.createStatement();
        List<String> sqlList = SQLExecute.loadSQL(sqlFile);
        for (String sql : sqlList){
                System.out.println(sql);
        	statement.addBatch(sql);
        }
        int[] rows = statement.executeBatch();

		System.out.println(sqlFile + "执行完成. ");
		return true;
	}


    /**
     * 读取 SQL 文件，获取 SQL 语句
     * @param sqlFile SQL 脚本文件
     * @return List<sql> 返回所有 SQL 语句的 List
     * @throws Exception
     */
    public static List<String> loadSQL(String sqlFile) throws Exception{
    	List<String> sqlList = new ArrayList<String>();
    	InputStream sqlFileIn = new FileInputStream(new File(sqlFile));

    	StringBuffer sqlSb = new StringBuffer();
    	byte[] buff = new byte[1024];
    	int byteRead = 0;
    	while ((byteRead = sqlFileIn.read(buff)) != -1){
    		sqlSb.append(new String(buff, 0, byteRead, "UTF-8"));
    	}

    	// Windows 下换行是 \r\n, Linux 下是 \n
    	String[] sqlArr = sqlSb.toString().split("(;\\s*\\r\\n)|(;\\s*\\n)");
    	for (int i = 0; i < sqlArr.length; i++) {
    		String sql = sqlArr[i].replaceAll("--.*", "").trim();
    		if (!sql.equals("")) {
    			sqlList.add(sql);
    		}
    	}
        return sqlList;
    }


	public static void main(String[] args){
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
