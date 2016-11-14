package com.smallchill.api.function.meta.other;

import com.smallchill.api.function.modal.Button;
import com.smallchill.api.function.modal.UserInterest;
import com.smallchill.core.toolbox.Record;
import com.smallchill.web.model.UserApproval;

import java.util.ArrayList;
import java.util.List;

/**
 * 按钮注册管理器
 * Created by yesong on 2016/11/14 0014.
 */
public class ButtonRegister {

    private List<Button> list = new ArrayList<>();

    private UserApproval ua;
    private UserInterest ui;

    private Integer currentUserId;
    private Integer toUserId;

    private ButtonRegister(Integer currentUserId, Integer toUserId, UserApproval ua, UserInterest ui) {
        this.currentUserId = currentUserId;
        this.toUserId = toUserId;
        this.ua = ua;
        this.ui = ui;
    }

    public static ButtonRegister create(Integer currentUserId, Integer toUserId, UserApproval ua, UserInterest ui) {
        return new ButtonRegister(currentUserId, toUserId, ua, ui);
    }

    /**
     * 新增自定义按钮Button
     *
     * @param button 按钮
     * @return
     */
    public ButtonRegister addButton(Button button) {
        this.list.add(button);
        return this;
    }

    public ButtonRegister getBtnOfUserinfo() {

        return this;
    }

    /**
     * 添加申请结识
     *
     * @return ButtonRegister
     */
    public ButtonRegister applyFriend() {
        if (ua != null && (ua.getStatus() == 2 || ua.getStatus() == 4)) return this;

        Button button = new Button();
        button.setName("申请结识");
        button.setUrl("approval/introduce");
        button.setParams(Record.create().set("fromUserId", currentUserId).set("toUserId", toUserId).set("type", 1));
        this.list.add(button);
        return this;
    }

    /**
     * 申请熟人
     *
     * @return ButtonRegister
     */
    public ButtonRegister applyAcquaintances() {
        if (ua != null && (ua.getStatus() == 2 || ua.getStatus() == 4) && ua.getType() == 1) return this;
        Button button = new Button();
        button.setName("结为熟人");
        button.setUrl("approval/introduce");
        button.setParams(Record.create().set("fromUserId", currentUserId).set("toUserId", toUserId).set("type", 2));
        this.list.add(button);
        return this;
    }

    /**
     * 感兴趣按钮事件
     *
     * @return ButtonRegister
     */
    public ButtonRegister insterest() {
        if (ui != null && ui.getStatus() == 1) return this;
        Button button = new Button();
        button.setName("感兴趣");
        button.setUrl("user/interest");
        button.setParams(Record.create().set("userId", currentUserId).set("toUserId", toUserId));
        this.list.add(button);
        return this;
    }

    /**
     * 查看交集
     *
     * @return ButtonRegister
     */
    public ButtonRegister intersection() {
        Button button = new Button();
        button.setName("查看交集");
        button.setUrl("user/introduce");
        button.setParams(Record.create().set("userId", currentUserId).set("toUserId", toUserId));
        this.list.add(button);
        return this;
    }

    /**
     * 查看对方熟人
     *
     * @return ButtonRegister
     */
    public ButtonRegister queryUserAcquaintances() {
        Button button = new Button();
        button.setName("查看对方熟人");
        button.setUrl("user/introduce");
        button.setParams(Record.create().set("userId", currentUserId).set("toUserId", toUserId));
        this.list.add(button);
        return this;
    }

    /**
     * 推荐朋友
     *
     * @return result
     */
    public ButtonRegister recommendFriend() {
        if (ua != null && ua.getStatus() == 1 && (ua.getType() == 1 || ua.getType() == 2)) return this;

        Button button = new Button();
        button.setName("推荐朋友");
        button.setUrl("user/intersection");
        button.setParams(Record.create().set("introduceUserId", currentUserId).set("toUserId", toUserId));
        this.list.add(button);
        return this;
    }

    public List<Button> getList() {
        return this.list;
    }

    public UserApproval getUa() {
        return ua;
    }

    public void setUa(UserApproval ua) {
        this.ua = ua;
    }

    public UserInterest getUi() {
        return ui;
    }

    public void setUi(UserInterest ui) {
        this.ui = ui;
    }

    public Integer getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Integer currentUserId) {
        this.currentUserId = currentUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }
}
