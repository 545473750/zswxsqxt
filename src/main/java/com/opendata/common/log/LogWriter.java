/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opendata.common.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * 往日志文件里输写日志
 * @author 付威
 */
public class LogWriter {
    /**
     * 默认构造函数
     */
    private LogWriter() throws LogException {
        this.init();
    }

    private LogWriter(String fileName) throws LogException {
        this.logFileName = fileName;
        this.init();
    }

    /**
     * 取得此类的唯一实例 单例模式
     */
    public synchronized static LogWriter getLogWriter(String logFileName) throws LogException {
        if (logWriter == null) {
            logWriter = new LogWriter(logFileName);
        }
        return logWriter;
    }

    /**
     * 往日志中写入一条日志信息
     * 为了防止多线程同时操作，造成文件死锁，使用synchronized
     * @parm logMsg 日志消息
     */
    public synchronized void log(String logMsg) {
        writer.println(new java.util.Date() + ": " + logMsg);
    }

    /**
     * 往日志文件中写入一条异常信息
     * 使用synchronized关键字
     * @param ex 待写入的异常
     */
    public synchronized void log(Exception ex) {
        writer.println(new java.util.Date() + ": ");
        ex.printStackTrace(writer);
    }

    /**
     * 初始化
     * @throws LogException
     */
    private void init() throws LogException {
        //如果用户没有在参数中指定日志文件名，则从配置文件中获取
        if (this.logFileName == null) {
            System.out.println(new File(".").getAbsolutePath());
            this.logFileName = this.getLogFileNameFromConfigFile();

            //如果配置文件不存在或者也没有指定日志文件名，则用默认的日志文件名
            if (logFileName == null) {
                logFileName = DEFAULT_LOG_FILE_NAME;
            }
        }
        File logFile = new File(logFileName);
        try {
            writer = new PrintWriter(new FileWriter(logFile, true), true);
            System.out.println("日志文件的位置：" + logFile.getAbsolutePath());
        } catch (IOException ex) {
            String errmsg = "无法打开日志文件：" + logFile.getAbsolutePath();
            throw new LogException(errmsg, ex);
        }
    }

    /**
     * 从配置文件中获取日志文件名
     * @return 日志文件名
     */
    private String getLogFileNameFromConfigFile() {
        try {
            Properties pro = new Properties();

            //在类的当前位置，查找属性配置文件log.properties
            InputStream fin = getClass().getResourceAsStream(LOG_CONFIGFILE_NAME);
            //InputStream fin = new FileInputStream(LOG_CONFIGFILE_NAME);
            if (fin != null) {
                pro.load(fin);//载入配置文件
                fin.close();
                return pro.getProperty(LOGFILE_TAG_NAME);
            } else {
                System.err.println("无法打开属性配置文件：log.properties");
            }
        } catch (IOException ex) {
            System.err.println("无法打开属性配置文件：log.properties");
        }
        return null;
    }

    /**
     * 关闭LogWriter
     */
    public void close() {
        logWriter = null;
        if (writer != null) {
            writer.close();
        }
    }
    private static LogWriter logWriter; //该类的唯一实例
    PrintWriter writer;
    private String logFileName;
    public static final String LOG_CONFIGFILE_NAME = "log.properties"; //日志的配置文件
    public static final String LOGFILE_TAG_NAME = "logfile"; //日志文件名在配置文件中的标签
    private final String DEFAULT_LOG_FILE_NAME = "./logtext.log"; //默认日志文件的文件名

    public static void main(String[] aa) {
        LogWriter logger = null;

        try {
            String fileName = "d:/logger.log";
            logger = LogWriter.getLogWriter(fileName);

            //logger = LogWriter.getLogWriter(null);
            logger.log("First log");
            logger.log("第二个日志信息");
            logger.log("Third log");
            logger.log("第四个日志信息");
            logger.close();
        } catch (LogException e) {
            e.printStackTrace();
        }

    }
}

