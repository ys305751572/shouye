package com.smallchill.core.toolbox.kit;

/**
 * 枚举工具类
 * Created by yeong on 2016/12/3 0003.
 */
public class EnumKit {

    /**
     * 根据索引获取
     * @param <T>
     * @param clazz
     * @param ordinal
     * @return
     */
    public static <T extends Enum<T>> T valueOf(Class<T> clazz, int ordinal) {
        return (T) clazz.getEnumConstants()[ordinal];
    }

    /**
     * 根据name获取
     *
     * @param <T>
     * @param enumType
     * @param name
     * @return
     */
    public static <T extends Enum<T>> T valueOf(Class<T> enumType, String name) {
        return (T) Enum.valueOf(enumType, name);
    }
}

