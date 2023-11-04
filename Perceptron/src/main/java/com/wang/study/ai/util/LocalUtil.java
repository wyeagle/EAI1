package com.wang.study.ai.util;

import java.util.HashMap;
import java.util.Map;

public class LocalUtil {
    private static ThreadLocal<Map<String,Object>> _local = new ThreadLocal<Map<String, Object>>();

    public static void add(String key, Object value){
        Map<String,Object> map = getMap();

        map.put(key,value);
    }

    public static Object get(String key){
        Map<String,Object> map = getMap();

        return map.get(key);
    }

    public static boolean isUT(){
        Boolean isUT = (Boolean)get("UNITTEST");
        if(isUT == null){
            return false;
        }
        return isUT;
    }

    public static void remove(String key){
        Map<String,Object> map = getMap();

        map.remove(key);
    }

    public static void clear(){
        _local.remove();
    }

    private static Map<String,Object> getMap(){
        Map<String,Object> map = _local.get();
        if(map == null){
            map = new HashMap<String, Object>();
        }
        _local.set(map);
        return map;
    }

}
