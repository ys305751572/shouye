package com.smallchill.api.function.modal.vo;

import com.smallchill.api.common.model.BaseVo;

/**
 * 引荐用户
 * Created by yesong on 2016/11/28 0028.
 */
public class IntroduceUserVo extends BaseVo {

    private UserVo fromUserVo;

    private UserVo toUserVo;

    public UserVo getFromUserVo() {
        return fromUserVo;
    }

    public void setFromUserVo(UserVo fromUserVo) {
        this.fromUserVo = fromUserVo;
    }

    public UserVo getToUserVo() {
        return toUserVo;
    }

    public void setToUserVo(UserVo toUserVo) {
        this.toUserVo = toUserVo;
    }
}
