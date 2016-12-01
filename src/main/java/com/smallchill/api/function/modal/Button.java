package com.smallchill.api.function.modal;

import java.io.Serializable;

/**
 * 操作按钮
 * Created by yesong on 2016/11/14 0014.
 */
public class Button implements Serializable{

    private String name;
    private Integer type;
    private Integer isAllowClick = 1; // 是否可点击 1:可点击 2:不可点击

    public Integer getIsAllowClick() {
        return isAllowClick;
    }

    public void setIsAllowClick(Integer isAllowClick) {
        this.isAllowClick = isAllowClick;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "name:" + name + "--type:" + type;
    }
}
