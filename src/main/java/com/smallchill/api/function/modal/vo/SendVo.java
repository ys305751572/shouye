package com.smallchill.api.function.modal.vo;

/**
 * Created by Administrator on 2016/11/23.
 */
public class SendVo {

    private static Integer toId;
    private static Integer sendType;
    private static Integer receiveType;
    private static Long sendTime;
    private static String title;
    private static String content;

    public static Integer getSendType() {
        return sendType;
    }

    public static void setSendType(Integer sendType) {
        SendVo.sendType = sendType;
    }

    public static Integer getToId() {
        return toId;
    }

    public static void setToId(Integer toId) {
        SendVo.toId = toId;
    }

    public static Integer getReceiveType() {
        return receiveType;
    }

    public static void setReceiveType(Integer receiveType) {
        SendVo.receiveType = receiveType;
    }

    public static Long getSendTime() {
        return sendTime;
    }

    public static void setSendTime(Long sendTime) {
        SendVo.sendTime = sendTime;
    }

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        SendVo.title = title;
    }

    public static String getContent() {
        return content;
    }

    public static void setContent(String content) {
        SendVo.content = content;
    }
}
