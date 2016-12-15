package com.smallchill.web.service.impl;

import com.smallchill.core.base.service.BaseService;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.web.model.UserTag;
import com.smallchill.web.service.UserTagService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator
 * on 2016/11/22.
 */
@Service
public class UserTagServiceImpl extends BaseService<UserTag> implements UserTagService {
    @Override
    public void deleteByGroupIdAndUserId(Integer groupId, Integer userId) {
        String sql = "DELETE FROM tb_user_tag WHERE `id` IN (SELECT a.id FROM (SELECT uc.`id` FROM " +
                "tb_user_tag uc LEFT JOIN tb_tag c ON uc.`tag_id` = c.`id` " +
                "WHERE uc.`user_id` = #{userId} AND group_id = #{groupId}) AS a)";
        Db.init().delete(sql, Record.create().set("userId", userId).set("groupId", groupId));
    }
}
