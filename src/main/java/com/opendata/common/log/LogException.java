/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opendata.common.log;

/**
 * 日志异常
 * @author 付威
 */
public class LogException extends Exception {

    public LogException() {
        super();
    }

    public LogException(String msg) {
        super(msg);
    }

    public LogException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public LogException(Throwable cause) {
        super(cause);
    }
}
