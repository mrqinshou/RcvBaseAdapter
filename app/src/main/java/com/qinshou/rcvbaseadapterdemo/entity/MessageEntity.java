package com.qinshou.rcvbaseadapterdemo.entity;

/**
 * Description:
 * Created by 禽兽先生
 * Created on 2018/10/9
 */
public class MessageEntity {
    private String message;
    private int userType;   //0 表示发送的消息，1 表示接收的消息

    public MessageEntity() {
        
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "message='" + message + '\'' +
                ", userType=" + userType +
                '}';
    }
}
