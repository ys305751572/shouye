package com.smallchill.api.function.meta.other;

import com.smallchill.api.function.modal.Button;
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

    private ButtonRegister() {
    }

    public static ButtonRegister create() {
        return new ButtonRegister();
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

    /**
     * 添加申请结识
     *
     * @param fromUserId 请求发起人
     * @param toUserId   请求接收人
     * @return ButtonRegister
     */
    public ButtonRegister applyFriend(Integer fromUserId, Integer toUserId, UserApproval ua) {


        Button button = new Button();
        button.setName("申请结识");
        button.setUrl("approval/introduce");
        button.setParams(Record.create().set("fromUserId", fromUserId).set("toUserId", toUserId).set("type", 1));
        this.list.add(button);
        return this;
    }

    /**
     * 申请熟人
     *
     * @param fromUserId 请求发起人
     * @param toUserId   请求接收人
     * @return ButtonRegister
     */
    public ButtonRegister applyAcquaintances(Integer fromUserId, Integer toUserId) {
        Button button = new Button();
        button.setName("结为熟人");
        button.setUrl("approval/introduce");
        button.setParams(Record.create().set("fromUserId", fromUserId).set("toUserId", toUserId).set("type", 2));
        this.list.add(button);
        return this;
    }

    /**
     * 感兴趣按钮事件
     *
     * @param currentUserId 当前用户ID
     * @param toUserId      感兴趣用户ID
     * @return ButtonRegister
     */
    public ButtonRegister insterest(Integer currentUserId, Integer toUserId) {
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
     * @param currentUserId 当前用户ID
     * @param toUserId      目标用户
     * @return ButtonRegister
     */
    public ButtonRegister intersection(Integer currentUserId, Integer toUserId) {
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
     * @param currentUserId 当前用户ID
     * @param toUserId      目标用户ID
     * @return ButtonRegister
     */
    public ButtonRegister queryUserAcquaintances(Integer currentUserId, Integer toUserId) {
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
     * @param currentUserId 当前用户ID
     * @param toUserId      目标用户ID
     * @return result
     */
    public ButtonRegister recommendFriend(Integer currentUserId, Integer toUserId) {
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
}
