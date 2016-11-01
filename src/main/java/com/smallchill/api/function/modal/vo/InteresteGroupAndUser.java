package com.smallchill.api.function.modal.vo;

import java.util.List;

/**
 * 感兴趣的人或组织
 * Created by yesong on 2016/11/1 0001.
 */
public class InteresteGroupAndUser {

    private List<Groupvo> groupvos;

    private List<UserVo> userVos;

    public List<Groupvo> getGroupvos() {
        return groupvos;
    }

    public void setGroupvos(List<Groupvo> groupvos) {
        this.groupvos = groupvos;
    }

    public List<UserVo> getUserVos() {
        return userVos;
    }

    public void setUserVos(List<UserVo> userVos) {
        this.userVos = userVos;
    }
}
