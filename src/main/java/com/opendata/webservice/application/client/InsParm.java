package com.opendata.webservice.application.client;

/**
 *
 * @author 王海龙
 * @version 1.0
 */
public class InsParm {
    private String appCode;//应用编号
    private String ftpIP;//FTP地址
    private String ftpPort;//FTP端口
    private String username;//FTP用户名
    private String password;//FTP密码
    private String ftpDic;//FTP目录
    private String ftpFile;//FTP文件名
    

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getFtpDic() {
        return ftpDic;
    }

    public void setFtpDic(String ftpDic) {
        this.ftpDic = ftpDic;
    }

    public String getFtpFile() {
        return ftpFile;
    }

    public void setFtpFile(String ftpFile) {
        this.ftpFile = ftpFile;
    }

    public String getFtpIP() {
        return ftpIP;
    }

    public void setFtpIP(String ftpIP) {
        this.ftpIP = ftpIP;
    }

    public String getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(String ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
