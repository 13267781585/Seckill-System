package com.gzhu.common;

import java.io.Serializable;

public enum  ServerReturnStatus implements Serializable {
    SUCCESS("请求成功!",1),
    FAIL("请求失败!",0);

    private String statusMsg;
    private int status;

    ServerReturnStatus(String statusMsg, int status) {
        this.statusMsg = statusMsg;
        this.status = status;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
