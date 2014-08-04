package com.opendata.common.util;

import java.util.ResourceBundle;

public class ToolUtil
{
    protected ToolUtil()
    {
    }

    private static ResourceBundle resource = null;

    static
    {
        //初始管理的配置文件
        resource = ResourceBundle.getBundle("tool");
    }

    /**
     * 根据配置名称获取配置值
     * @param name
     * @return
     */
    public static String getString(String name)
    {
        return resource.getString(name);
    }

    /**
     * 获取ids.url
     * @return
     */
    public static String getIdsUrl()
    {
        return resource.getString("ids.url");
    }

    /**
     * 获取dps.publickey
     * @return
     */
    public static String getDpsPublicKey()
    {
        return resource.getString("dps.publickey");
    }
}
