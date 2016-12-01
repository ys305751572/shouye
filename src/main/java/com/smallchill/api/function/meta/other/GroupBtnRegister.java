package com.smallchill.api.function.meta.other;

import com.smallchill.api.function.meta.consts.ButtonConst;
import com.smallchill.api.function.modal.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * 组织详情按钮
 * Created by yesong on 2016/11/17 0017.
 */
public class GroupBtnRegister implements ButtonConst {

    private List<Button> list = new ArrayList<>();

    public static GroupBtnRegister create() {
        return new GroupBtnRegister();
    }

    /**
     * 获取组织详情按钮
     *
     * @param status  申请状态
     * @param istatus 感兴趣状态
     * @return btnlist
     */
    public List<Button> registerBtns(Integer status, Integer istatus) {
        if (status == null) {
            addJoin();
        } else if (status == 1) {
            addWaiting();
        } else if (status == 3) {
            addRefuse();
        }

        if ((istatus == null || istatus == 1) && (status == null || status != 2)) {
            addInterest();
        }
        else {
            addInterested();
        }
        return this.list;
    }

    public GroupBtnRegister addJoin() {
        Button button = new Button();
        button.setType(JOIN);
        button.setName("申请加入");
        button.setIsAllowClick(ALLOW_CLICK);
        this.list.add(button);
        return this;
    }

    public GroupBtnRegister addWaiting() {
        Button button = new Button();
        button.setType(WAITING);
        button.setName("等待加入审核");
        button.setIsAllowClick(UNALLOW_CLICK);
        this.list.add(button);
        return this;
    }

    public GroupBtnRegister addRefuse() {
        Button button = new Button();
        button.setType(REFUSE);
        button.setName("审核不通过");
        button.setIsAllowClick(UNALLOW_CLICK);
        this.list.add(button);
        return this;
    }

    public GroupBtnRegister addInterest() {
        Button button = new Button();
        button.setType(INTEREST);
        button.setName("感兴趣");
        button.setIsAllowClick(ALLOW_CLICK);
        this.list.add(button);
        return this;
    }

    public GroupBtnRegister addInterested() {
        Button button = new Button();
        button.setType(INTERESTED);
        button.setName("已感兴趣");
        button.setIsAllowClick(UNALLOW_CLICK);
        this.list.add(button);
        return this;
    }

}
