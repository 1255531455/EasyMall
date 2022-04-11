package com.easymall.domain;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import org.apache.log4j.*;

//封装用户信息的javabean
public class User implements HttpSessionBindingListener {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String username;
    private String password;
    private String nickname;
    private String email;

    public User(int id, String username, String password, String nickname, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    public User() {
    }

    public static Logger logger = Logger.getLogger(User.class);
    @Override
    public void valueBound(HttpSessionBindingEvent event) {
//        System.out.println("用户["+username+"]登录EasyMall");
        logger.warn("用户["+username+"]登录EasyMall");
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
//        System.out.println("用户["+username+"]退出EasyMall");
        logger.warn("用户["+username+"]退出EasyMall");
    }
}
