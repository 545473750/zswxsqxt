package com.opendata.webservice.application.client;

import javax.jws.WebService;

@WebService
public interface InsService {

    /**
     * 安装应用
     * @param parm 安装参数对象
     */
    public void install(InsParm parm);

    /**
     * 更新应用
     * @param parm 安装参数对象
     */
    public void update(InsParm parm);
}
