package com.smallchill.api.function.meta.other;

import com.smallchill.api.function.modal.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * 组织详情按钮
 * Created by yesong on 2016/11/17 0017.
 */
public class GroupBtnRegister {

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
        if (status == null || status == 3) {
            addJoin();
        }
        if (istatus == null || istatus == 1) {
            addInterest();
        }
        return this.list;
    }

    public GroupBtnRegister addJoin() {
        Button button = new Button();
        button.setType(3001);
        button.setName("申请加入");
        this.list.add(button);
        return this;
    }

    public GroupBtnRegister addInterest() {
        Button button = new Button();
        button.setType(3002);
        button.setName("感兴趣");
        this.list.add(button);
        return this;
    }
}
