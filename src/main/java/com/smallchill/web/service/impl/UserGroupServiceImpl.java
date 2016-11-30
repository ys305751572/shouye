package com.smallchill.web.service.impl;

import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.Record;
import com.smallchill.web.model.Group;
import com.smallchill.web.model.GroupApproval;
import com.smallchill.web.model.UserApproval;
import com.smallchill.web.model.UserGroup;
import com.smallchill.web.service.GroupApprovalService;
import com.smallchill.web.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smallchill.core.base.service.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 组织会员
 * Generated by yesong.
 * 2016-10-28 11:08:59
 */
@Service
public class UserGroupServiceImpl extends BaseService<UserGroup> implements UserGroupService {

    private String DELETEBYUSERIDANDGROUPID_WHERE = "group_id = #{groupId} AND user_id = #{userId}";

    @Autowired
    GroupApprovalService groupApprovalService;

    @Override
    @Transactional
    public void removeUser(Integer id) {
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        this.deleteBy(DELETEBYUSERIDANDGROUPID_WHERE,Record.create().set("groupId",group.getId()).set("userId",id));
        GroupApproval groupApproval = groupApprovalService.findFirstBy(DELETEBYUSERIDANDGROUPID_WHERE,Record.create().set("groupId",group.getId()).set("userId",id));
        if(groupApproval!=null){
            groupApproval.setStatus(3);
            groupApprovalService.update(groupApproval);
        }
    }

    @Override
    public List findInvitationUser() {
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        String sql = "SELECT \n" +
                "  tui.user_id AS userId,\n" +
                "  tui.username AS userName,\n" +
                "  tui.mobile AS mobile \n" +
                "FROM\n" +
                "  tb_user_info tui \n" +
                "  LEFT JOIN tb_user_group tug \n" +
                "    ON tui.user_id = tug.user_id \n" +
                "WHERE tui.user_id NOT IN (SELECT user_id FROM tb_user_group WHERE group_id = #{groupId})\n" +
                "  AND tui.user_id NOT IN (SELECT user_id FROM tb_group_approval WHERE group_id = #{groupId}) \n" +
                "AND tui.mobile <> \" \" \n" +
                "GROUP BY tui.user_id ";
        List list = Db.init().selectList(sql,Record.create().set("groupId",group.getId()));
        return list;
    }
}
