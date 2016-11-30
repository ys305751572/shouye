package com.smallchill.api.function.modal.vo;

import com.smallchill.api.common.model.BaseVo;

/**
 * 用户扩展信息
 * Created by yesong on 2016/11/30 0030.
 */
public class UserExtendVo extends BaseVo{

    private String desc;
    private String org;
    private String career;
    private String school;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
