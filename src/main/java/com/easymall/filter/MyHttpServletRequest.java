package com.easymall.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MyHttpServletRequest extends HttpServletRequestWrapper {
    String encode = null;
    HttpServletRequest request;
    public MyHttpServletRequest(HttpServletRequest request,String encode) {
        super(request);
        this.request = request;
        this.encode = encode;
    }
    @Override
    public Map<String, String[]> getParameterMap() {
        //将原有map中的参数取出，处理乱码之后放入新的map中，并且返回map
        Map<String,String[]> map = request.getParameterMap();
        //取出原map中的参数名和参数值，将处理后的参数值放入新map
        Map<String,String[]> rmap = new HashMap();
        //遍历原map，处理参数值乱码
        try {
            for (Map.Entry<String,String[]> entry: map.entrySet()){
                String key = entry.getKey();//获取键名备用
                String[] value = entry.getValue();
                //遍历数组，将每一个值做乱码处理，再将乱码处理后的数据放入一个新数组中
                String[] rvalue = new String[value.length];
                for (int i=0;i<value.length;i++){
                    rvalue[i] = new String(value[i].getBytes(StandardCharsets.UTF_8),encode);
                }
                //将新数组添加到新map中
                rmap.put(key,rvalue);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return rmap;
    }

    @Override
    public String[] getParameterValues(String name) {
        //获取乱码处理后的getParameterMap方法中的值，即当前方法所需结果
        Map<String, String[]> map = getParameterMap();
        return map.get(name);
    }

    public String getParameter(String name){
        //上方方法的返回值时一个数组
        String[] values = getParameterValues(name);
        return values!=null?values[0] : null;
    }
}