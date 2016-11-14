package com.smallchill.api.function.meta.other;

import com.smallchill.core.toolbox.Record;
import com.smallchill.web.model.UserApproval;

import java.util.List;

/**
 * 根据用户状态添加操作按钮
 * 1.申请结识
 * 2.感兴趣
 * 3.查看交集
 * 4.推荐朋友
 * 5.结为熟人
 * 6.查看对方熟人
 * 7.取消感兴趣
 * Created by yesong on 2016/11/14 0014.
 */
public class AddButton {

    /**
     * @param currentUserId 当前用户
     * @param distUserId    目标用户
     * @return 关系状态 0:未结识 1:朋友 2:熟人 3:等待对方审核 4:等待自己审核
     */
    public void addButtonInUserInfo(Integer currentUserId, Integer distUserId, Record record, UserApproval ua) {

    }
}
