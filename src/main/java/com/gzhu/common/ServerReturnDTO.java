package com.gzhu.common;

import com.gzhu.pojo.user.User;

import java.io.Serializable;

public class ServerReturnDTO implements Serializable {
    private int status;
    private String msg;
    private User user;
    private String crsfToken;

    public ServerReturnDTO(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ServerReturnDTO(ServerReturnStatus returnStatus) {
        this.status = returnStatus.getStatus();
        this.msg = returnStatus.getStatusMsg();
    }

    public static ServerReturnDTO createBySuccess(){
        return new ServerReturnDTO(ServerReturnStatus.SUCCESS);
    }

    public static ServerReturnDTO createByFail(){
        return new ServerReturnDTO(ServerReturnStatus.FAIL);
    }

    public static ServerReturnDTO createBySuccessAndMsg(String msg){
        return new ServerReturnDTO(ServerReturnStatus.SUCCESS.getStatus(),msg);
    }

    public static ServerReturnDTO createByFailAndMsg(String msg){
        return new ServerReturnDTO(ServerReturnStatus.FAIL.getStatus(),msg);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCrsfToken() {
        return crsfToken;
    }

    public void setCrsfToken(String crsfToken) {
        this.crsfToken = crsfToken;
    }
}
