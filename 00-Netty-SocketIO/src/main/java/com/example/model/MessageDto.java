package com.example.model;

import java.io.Serializable;

/**
 * MessageDto
 * @author dolyw.com
 * @date 2019/7/31 18:57
 */
public class MessageDto implements Serializable {

    /**
     * 源客户端用户名
     */
    private String sourceUserName;

    /**
     * 目标客户端用户名
     */
    private String targetUserName;

    /**
     * 消息类型
     */
    private String msgType;

    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 空构造方法
     */
    public MessageDto() {}

    /**
     * 构造方法
     * @param sourceUserName
     * @param targetUserName
     * @param msgType
     * @param msgContent
     */
    public MessageDto(String sourceUserName, String targetUserName, String msgType, String msgContent) {
        this.sourceUserName = sourceUserName;
        this.targetUserName = targetUserName;
        this.msgType = msgType;
        this.msgContent = msgContent;
    }

    public String getSourceUserName() {
        return sourceUserName;
    }

    public void setSourceUserName(String sourceUserName) {
        this.sourceUserName = sourceUserName;
    }

    public String getTargetUserName() {
        return targetUserName;
    }

    public void setTargetUserName(String targetUserName) {
        this.targetUserName = targetUserName;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
}
