package com.gzhu.util;

public class LoggerUtils {
    private static final String loggerStr = "%s  %s";

    public static void logger(Object object){
        System.out.println(String.format(loggerStr,DateUtils.getFormatDate(),object.toString()));
    }
}
