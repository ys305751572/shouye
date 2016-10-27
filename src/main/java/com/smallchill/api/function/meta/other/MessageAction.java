package com.smallchill.api.function.meta.other;

/**
 * 消息事件
 * Created by yesong on 2016/10/27 0027.
 */
public class MessageAction {

    private String actionName;

    private String actionUrl;

    public static MessageAction create(String actionName, String actionUrl) {
        return new MessageAction(actionName, actionUrl);
    }

    private MessageAction() {

    }

    private MessageAction(String actionName, String actionUrl) {
        this.actionName = actionName;
        this.actionUrl = actionUrl;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }
}
