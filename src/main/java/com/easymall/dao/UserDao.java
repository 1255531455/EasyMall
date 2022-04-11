package com.easymall.dao;

import com.easymall.domain.User;
import com.easymall.utils.JDBCUtils;
import com.easymall.utils.MD5Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    /**
     * 添加一条用户信息
     * @param user  用户信息对象
     */
    public void addUser(User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement("insert into user values (null,?,?,?,?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNickname());
            ps.setString(4, user.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(conn,ps,null);
        }
    }

    /**
     * 根据用户名查询用户
     * @param username  用户名
     * @return  用户是否存在的布尔值
     */
    public boolean findUserByUsername(String username) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement("select * from user where username=?");
            ps.setString(1,username);
            rs = ps.executeQuery();
            if (rs.next()){//为true则用户名已存在，不能注册
                return true;
            }else {//为false则用户名不存在，可以注册
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }finally {
            JDBCUtils.close(conn,ps,rs);
        }
    }

    /**
     * 根据用户名和密码查询用户
     * @param username  用户名
     * @param password  密码
     * @return  用户信息对象或null
     */
    public User findUserByUsernameAndPassword(String username, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement("select * from user where username=? and password=?");
            ps.setString(1,username);
            ps.setString(2,password);
            rs = ps.executeQuery();
            if (rs.next()){//判断为true，则返回一个user对象
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setNickname(rs.getString("nickname"));
                user.setEmail(rs.getString("email"));
                return user;
            }else {//判断为false，则证明用户名和密码不正确，返回null
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }finally {
            JDBCUtils.close(conn,ps,rs);
        }
    }
}
