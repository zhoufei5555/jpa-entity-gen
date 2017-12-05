package com.zf.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Created by zhoufei on 2017/11/9.
 * <p>
 * 用来生成一些事件的数据
 */
public class JdbcUtils {
    public static String driver;
    public static String urlPrefix;
    public static String dataBaseName;
    public static String username;
    public static String password;

    static{
        System.out.println("开始初始化数据库工具");
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(new FileInputStream(System.getProperty("user.dir")+"\\config\\jdbc.properties"),"UTF-8"));
        } catch (IOException e) {
            System.err.println("读取数据库配置文件失败,fileName：jdbc.properties");
            e.printStackTrace();
        }
        JdbcUtils.driver = properties.getProperty("driver");
        JdbcUtils.urlPrefix = properties.getProperty("urlPrefix");
        JdbcUtils.dataBaseName = properties.getProperty("dataBaseName");
        JdbcUtils.username = properties.getProperty("username");
        JdbcUtils.password = properties.getProperty("password");

    }

    public static Connection getConn(String  dbName) throws Exception {
        driver = "com.mysql.jdbc.Driver";
        String url = urlPrefix+dataBaseName;
        if(null !=dbName && !"".equals(dbName)){
            url =urlPrefix+dbName.trim();
        }
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn =  DriverManager.getConnection(url, username, password);
        } catch (Exception e){
            System.err.println("获取JDBC连接出错");
            throw e;
        }
        return conn;
    }



}
