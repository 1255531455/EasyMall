package com.easymall.service;

import com.easymall.dao.UserDao;
import com.easymall.domain.User;
import com.easymall.exception.MsgException;

public class UserService {
    public  UserDao userDao = new UserDao();
    /**
     * 注册用户
     * @param user
     */
    public void registUser(User user) throws MsgException {
        //判断用户名是否存在
        boolean flag = userDao.findUserByUsername(user.getUsername());

        if (flag){
            //用户存在
            //创建自定义异常，作为通知用户名已存在
            throw new MsgException("用户名已存在");
        }else {
            //用户不存在
            //添加一条数据
            userDao.addUser(user);
        }

    }

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return  返回用户信息对象（放入session域时使用）
     */
    public User loginUser(String username, String password) throws MsgException {
        //查询到结果返回user
        //没有查询结果返回null
        User user = userDao.findUserByUsernameAndPassword(username,password);
        if (user == null){//用户名和密码不匹配，使用异常信息，做出提示
            throw new MsgException("用户名或密码不正确");
        }else {
            return user;
        }
    }
}
