package com.easymall.utils;
//web工具类
public class WebUtils {
    //构造函数私有化
    private WebUtils(){

    }

    //非空校验
    public static boolean isNULL(String name){
        return name==null||"".equals(name);
    }
}
