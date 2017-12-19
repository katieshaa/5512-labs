package com.nvisary.util;

import java.io.Serializable;


public class Message implements Serializable {
    private String msg = "";
    private String userName = "client";
    private String destinationUser = "null";
    private int nameColor = 40;
    private String type = "null";

    public Message(String msg, String userName, String destinationUser) {
        this.msg = msg;
        this.userName = userName;
        this.destinationUser = destinationUser;
    }

    public Message(String msg, String userName) {
        this.msg = msg;
        this.userName = userName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(userName + ": " + msg);
        return sb.toString();
    }

    public int getNameColor() {
        return nameColor;
    }

    public void setNameColor(int nameColor) {
        this.nameColor = nameColor;
    }

    public String getMsg() {
        return msg;
    }

    public String getUserName() {
        return userName;
    }

    public String getDestinationUser() {
        return destinationUser;
    }

    public String getMessageType() {
        return type;
    }

    public void setMessageType(String type) {
        this.type = type;
    }
}
