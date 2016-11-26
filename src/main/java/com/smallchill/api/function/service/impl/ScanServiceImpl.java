package com.smallchill.api.function.service.impl;

import com.smallchill.api.function.modal.vo.Groupvo;
import com.smallchill.api.function.modal.vo.UserVo;
import com.smallchill.api.function.service.ScanService;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.web.service.GroupService;
import com.smallchill.web.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Generated by Blade.
 * 2016-11-26 19:17:36
 */
@Service
public class ScanServiceImpl implements ScanService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private GroupService groupService;

    @Override
    public UserVo scanUser(Integer userId, Integer toUserId) {
        return userInfoService.findUserDetailWithUa(userId, toUserId);
    }

    @Override
    public Groupvo scanGroup(Integer userId, Integer groupId) {
        return groupService.findGroupWithGa(groupId, userId);
    }

    /**
     * 识别会员
     * @param userId
     * @param toUserId
     * @return
     */
    @Override
    public UserVo identifyMember(Integer userId, Integer toUserId) {
        UserVo userVo = scanUser(userId, toUserId);
        String sql = Blade.dao().getScript("Scan.isInSameGroup").getSql();
        Record record = Db.init().selectOne(sql, Record.create().set("userId", userId).set("toUserId", toUserId));
        int counts = record.getInt("counts");
        if(counts > 0) {
            userVo.setIsMember(2);
        }
        else {
            userVo.setIsMember(1);
        }
        return userVo;
    }
}
