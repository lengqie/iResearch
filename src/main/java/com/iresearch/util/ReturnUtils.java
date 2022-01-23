package com.iresearch.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回信息工具
 * @author lengqie
 */
public class ReturnUtils {

    public static Map getMap(String status, Object data){
        Map<String,Object> map =new HashMap<>(1);
        map.put("status",status);
        map.put("msg","null");
        map.put("data",data);
        return map;
    }

    public static Map getMap(String status, String msg){
        Map<String,String> map =new HashMap<>(1);
        map.put("status",status);
        map.put("msg",msg);
        return map;
    }

    public static Map getMap(String status){
        Map<String,String> map =new HashMap<>(1);
        map.put("status",status);
        map.put("msg","null");
        return map;
    }
    public static Map getMap(){
        Map<String,String> map =new HashMap<>(1);
        map.put("status","404");
        map.put("msg","null");
        return map;
    }
}
