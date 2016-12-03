package com.smallchill.core.toolbox.kit;

import com.smallchill.core.modules.support.Conver;

import java.util.Map;

/**
 * MAP 工具类
 * Created by yesong on 2016/12/3 0003.
 */
public class MapKit {

    public static String getStr(Map<String,Object> map, String key, String defalutValue) {
        final Object object = map.get(key);
        return Conver.toStr(object, defalutValue);
    }

    public static String getStr(Map<String,Object> map, String key) {
        return getStr(map, key,"");
    }

    public static int getInt(Map<String,Object> map, String key, int defaultValue) {
        final Object object = map.get(key);
        return Conver.toInt(object, defaultValue);
    }

    public static int getInt(Map<String,Object> map, String key) {
        return getInt(map, key, 0);
    }

    public static short getShort(Map<String,Object> map, String key, short defaultValue) {
        final Object object = map.get(key);
        return Conver.toShort(object, defaultValue);
    }

    public static short getShort(Map<String,Object> map, String key) {
        return getShort(map, key, (short)0);
    }

    public static byte getByte(Map<String,Object> map, String key, byte defaultValue) {
        final Object value = map.get(key);
        return Conver.toByte(value, defaultValue);
    }

    public static byte getByte(Map<String, Object> map, String key) {
        return getByte(map, key, (byte) 0);
    }

    public static long getLong(Map<String, Object> map, String key, long defaultValue) {
        final Object object = map.get(key);
        return Conver.toLong(object, defaultValue);
    }

    public static long getLong(Map<String, Object> map, String key) {
        return getLong(map, key, 0);
    }

    public static boolean getBoolean(Map<String,Object> map, String key, boolean defaultValue) {
        final Object object = map.get(key);
        return Conver.toBool(object, defaultValue);
    }

    public static boolean getBoolean(Map<String,Object> map, String key) {
        return getBoolean(map, key, false);
    }
}
