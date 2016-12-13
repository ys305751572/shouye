package com.smallchill.api.function.service.impl;

import com.smallchill.api.function.modal.UserFriendGrouping;
import com.smallchill.api.function.service.UserFriendGroupingService;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import org.springframework.stereotype.Service;
import com.smallchill.core.base.service.BaseService;


/**
 * Generated by Blade.
 * 2016-11-22 17:59:15
 */
@Service
public class UserFriendGroupingServiceImpl extends BaseService<UserFriendGrouping> implements UserFriendGroupingService {

    @Override
    public void deleteMemberForDelFriend(Integer userId, Integer friendId) {
        String sql = "DELETE FROM `tb_user_friend_grouping_member` WHERE ufg_id IN (SELECT ufg.id FROM " +
                "tb_user_friend_grouping ufg WHERE ufg.`user_id` = #{userId}) AND friend_id = #{friendId}";
        Db.init().delete(sql, Record.create().set("userId", userId).set("friendId", friendId));
    }
}
