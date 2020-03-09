package com.sxzhongf.extension.dubbo.common.utils;

/**
 * Assert
 *
 * @author <a href="mailto:magicianisaac@gmail.com">Isaac.Zhang | 若初</a>
 * @since 2020/3/9
 **/
public class Assert {

    protected Assert() {
    }

    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmptyString(String str,String message) {
        if(StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException(message);
        }
    }
    public static void notNull(Object obj, RuntimeException exception) {
        if (obj == null) {
            throw exception;
        }
    }

}
