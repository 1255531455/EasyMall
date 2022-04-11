package com.easymall.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//JDBCUtils工具类
public class JDBCUtils {
    //私有化构造方法
    private JDBCUtils(){

    }
    //创建数据源只完成一次初始化
    public static ComboPooledDataSource source = new ComboPooledDataSource();
    //获取数据源对象，如果需要修改数据源配置，可以通过此次方法临时调用修改。
    public static ComboPooledDataSource getSource(){
        return source;
    }
    //获取连接
    public static Connection getConnection() throws SQLException {
        return source.getConnection();
    }
    //关闭资源
    public static void close(Connection conn, Statement stat, ResultSet rs){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                rs = null;
            }
        }
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                stat = null;
            }
        }
        if (stat != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                stat = null;
            }
        }
    }
}
