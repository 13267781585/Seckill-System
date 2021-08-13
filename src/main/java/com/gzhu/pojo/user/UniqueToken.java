package com.gzhu.pojo.user;

import com.gzhu.util.EncryptUtils;

import java.util.UUID;

public class UniqueToken {
    private String uniqueToken;
    private long expireTime;
    private String signature;

    @Override
    public String toString() {
        return "UniqueToken{" +
                "uniqueToken='" + uniqueToken + '\'' +
                ", expireTime=" + expireTime +
                ", signature='" + signature + '\'' +
                '}';
    }

    public String getUniqueToken() {
        return uniqueToken;
    }

    public void setUniqueToken(String uniqueToken) {
        this.uniqueToken = uniqueToken;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public static UniqueToken builder(long exTime, String salt){
        UniqueToken uniqueToken = new UniqueToken();
        String token = UUID.randomUUID().toString().replace("-","");
        uniqueToken.setUniqueToken(token);
        long expireTime = System.currentTimeMillis() + exTime;
        uniqueToken.setExpireTime(expireTime);
        uniqueToken.setSignature(EncryptUtils.MD5(token + salt + expireTime));
        return uniqueToken;
    }

    public static void main(String[] args) {
        //System.out.println(UniqueToken.builder(600000000,"dZ70I25y73XLNC15fxtehc0hFqv4C6dZ70I25y73XLNC15fxtehc0hFqv4C6"));
        System.out.println(EncryptUtils.MD5("9cc83bfc3b904d8eb7731df03b5f3ee7" + "dZ70I25y73XLNC15fxtehc0hFqv4C6" + 1628737616361L));
        System.out.println("9cc83bfc3b904d8eb7731df03b5f3ee7dZ70I25y73XLNC15fxtehc0hFqv4C61628737616361".equals("9cc83bfc3b904d8eb7731df03b5f3ee7dZ70I25y73XLNC15fxtehc0hFqv4C61628737616361"));
    }
}
