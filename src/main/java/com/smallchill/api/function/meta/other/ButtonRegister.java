package com.smallchill.api.function.meta.other;

import com.smallchill.api.function.meta.consts.ButtonConst;
import com.smallchill.api.function.modal.Button;
import com.smallchill.api.function.modal.UserInterest;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.web.model.Aug;
import com.smallchill.web.model.UserApproval;

import java.util.ArrayList;
import java.util.List;

/**
 * 按钮注册管理器
 * Created by yesong on 2016/11/14 0014.
 */
public class ButtonRegister implements ButtonConst {

    private List<Button> list = new ArrayList<>();

    private UserApproval ua;
    private UserInterest ui;
    private Aug aug;
    private boolean isSameGroup;

    private Integer currentUserId;
    private Integer toUserId;

    private ButtonRegister(Integer currentUserId, Integer toUserId, UserApproval ua, UserInterest ui, Aug aug, boolean isSameGroup) {
        this.currentUserId = currentUserId;
        this.toUserId = toUserId;
        this.ua = ua;
        this.ui = ui;
        this.aug = aug;
        this.isSameGroup = isSameGroup;
    }

    public static ButtonRegister create(Integer currentUserId, Integer toUserId, UserApproval ua, UserInterest ui, Aug aug, boolean isSameGroup) {
        return new ButtonRegister(currentUserId, toUserId, ua, ui, aug, isSameGroup);
    }

    /**
     * 加入用户详情
     *
     * @return button list
     */
    public List<Button> addBtns() {
        applyFriend();
        applyAcquaintances();
        insterest();
        intersection();
        queryUserAcquaintances();
        recommendFriend();
        applyWaiting();
        applyRefuse();
        applyGroupWaiting();
        return getList();
    }

    /**
     * 审核拒绝
     */
    private ButtonRegister applyRefuse() {
        if (ua != null && ua.getStatus() == 3) {
            Button button = new Button();
            button.setType(apply_refuse);
            button.setIsAllowClick(UNALLOW_CLICK);
            button.setName("审核不通过");
            this.list.add(button);
        }
        return this;
    }

    /**
     * 等待用户审核
     */
    private ButtonRegister applyWaiting() {
        if (isUserApplying()) {
            Button button = new Button();
            button.setType(apply_waiting);
            button.setIsAllowClick(UNALLOW_CLICK);
            button.setName("等待对方审核");
            this.list.add(button);
        }
        return this;
    }

    /**
     * 等待组织审核
     * @return
     */
    private ButtonRegister applyGroupWaiting() {
        if (isGroupApplying()) {
            Button button = new Button();
            button.setType(apply_group_waiting);
            button.setIsAllowClick(UNALLOW_CLICK);
            button.setName("等待组织引荐审核");
            this.list.add(button);
        }
        return this;
    }

    /**
     * 新增自定义按钮Button
     *
     * @param button 按钮
     * @return ButtonRegister
     */
    public ButtonRegister addButton(Button button) {
        this.list.add(button);
        return this;
    }

    /**
     * 添加申请结识
     *
     * @return ButtonRegister
     */
    public ButtonRegister applyFriend() {
        if (isStranger() && isInSameGroup() && !isUserApplying() && !isGroupApplying() && isSameGroup) {
            Button button = new Button();
            button.setName("申请结识");
            button.setType(applyFriend_type);
            this.list.add(button);
        }
        return this;
    }

    /**
     * 申请熟人
     *
     * @return ButtonRegister
     */
    public ButtonRegister applyAcquaintances() {
        if (isFriend() && !isFreindWaiting()) {
            Button button = new Button();
            button.setName("结为熟人");
            button.setType(applyAcquaintances_type);
            this.list.add(button);
        }
        return this;
    }

    /**
     * 感兴趣按钮事件
     *
     * @return ButtonRegister
     */
    public ButtonRegister insterest() {
        if (isStranger() && !isInterest() && !isFriend()) {
            Button button = new Button();
            button.setName("感兴趣");
            button.setType(insterest_type);
            this.list.add(button);
        }
        return this;
    }

    /**
     * 查看交集
     *
     * @return ButtonRegister
     */
    public ButtonRegister intersection() {
        if (isStranger() && !isFriend()) {
            Button button = new Button();
            button.setName("查看交集");
            button.setType(intersection_type);
            this.list.add(button);
        }
        return this;
    }

    /**
     * 查看对方熟人
     *
     * @return ButtonRegister
     */
    public ButtonRegister queryUserAcquaintances() {
        if (isAcquaintances()) {
            Button button = new Button();
            button.setName("查看对方熟人");
            button.setType(queryUserAcquaintances_type);
            this.list.add(button);
        }
        return this;
    }

    /**
     * 推荐朋友
     *
     * @return result
     */
    public ButtonRegister recommendFriend() {

        if (isFriend() || isAcquaintances()) {
            Button button = new Button();
            button.setName("推荐朋友");
            button.setType(recommendFriend_type);
            this.list.add(button);
        }
        return this;
    }

    /**
     * 判断两个用户是否在同一个组织
     *
     * @return boolean
     */
    public boolean isInSameGroup() {
        String sql = "SELECT COUNT(*) AS counts FROM tb_user_group ug WHERE ug.group_id IN (SELECT ug2.group_id FROM tb_user_group ug2 WHERE ug2.user_id = #{toUserId}) AND ug.user_id = #{userId}";
        Record record = Record.create().set("userId", currentUserId).set("toUserId", toUserId);
        Record resultMap = Db.init().selectOne(sql, record);
        int counts = resultMap.get("counts") == null ? 0 : Integer.parseInt(resultMap.get("counts").toString());
        return counts > 0;
    }

    /**
     * 是否陌生人
     *
     * @return boolean
     */
    public boolean isStranger() {
        return ua == null || ua.getStatus() == 5 || ua.getStatus() == 1;
    }

    /**
     * 是否等待组织审核
     *
     * @return boolean
     */
    public boolean isGroupApplying() {
        return aug != null && aug.getStatus() == 1;
    }

    /**
     * 是否正在审核
     *
     * @return
     */
    public boolean isUserApplying() {
        return ua != null && ua.getStatus() == 1;
    }

    /**
     * 是否是朋友
     *
     * @return boolean
     */
    public boolean isFriend() {
        return (ua != null && ua.getStatus() == 2 && ua.getType() == 1) || (ua != null && ua.getType() == 2);
    }

    public boolean isFreindWaiting() {
        return ua != null && ua.getType() == 2 && ua.getStatus() == 1;
    }

    /**
     * 是否熟人
     *
     * @return boolean
     */
    public boolean isAcquaintances() {
        return ua != null && ua.getStatus() == 1 && ua.getType() == 2;
    }

    /**
     * 是否已经感兴趣
     *
     * @return boolean
     */
    public boolean isInterest() {
        return ui != null && ui.getStatus() == 0;
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

    public void setList(List<Button> list) {
        this.list = list;
    }

    public Aug getAug() {
        return aug;
    }

    public void setAug(Aug aug) {
        this.aug = aug;
    }

    public boolean isSameGroup() {
        return isSameGroup;
    }

    public void setSameGroup(boolean sameGroup) {
        isSameGroup = sameGroup;
    }
}
