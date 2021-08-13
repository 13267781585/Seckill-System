package com.gzhu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.util.Random;

public class EncryptUtils {
    private static final Logger log = LoggerFactory.getLogger(EncryptUtils.class);

    public static String MD5(String encStr){
        String str = DigestUtils.md5DigestAsHex(encStr.getBytes());
        log.info("md5:" + encStr + "->" + str);
        return str;
    }

    public static String buildSalt(String salt, int saltLength)
    {
        Random random = new Random();
        int length = salt.length();
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<saltLength;i++){
            sb.append(salt.charAt(random.nextInt(length-1)));
        }
        return sb.toString();
    }
}
