package com.smallchill.system.service.impl;

import com.smallchill.common.vo.User;
import com.smallchill.core.base.service.BaseService;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.Record;
import com.smallchill.system.service.UserService;
import com.smallchill.web.model.Group;
import com.smallchill.web.model.GroupExtend;
import com.smallchill.web.service.GroupExtendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator
 * on 2016/12/3.
 */
@Service
public class UserServiceImpl extends BaseService<User> implements UserService {

    @Autowired
    GroupExtendService groupExtendService;


    @Override
    @Transactional
    public void updatePwd(User user, GroupExtend groupExtend,String pwd) {
        String salt = ShiroKit.getRandomSalt(5);
        String pwdMd5 = ShiroKit.md5(pwd, salt);
        user.setPassword(pwdMd5);
        user.setSalt(salt);
        this.update(user);
        groupExtend.setPassword(pwd);
        groupExtendService.update(groupExtend);
    }

    @Override
    @Transactional
    public void updateMoile(Group group, String mobile) {
        GroupExtend groupExtend = groupExtendService.findFirstBy("group_id = #{groupId}",Record.create().set("groupId",group.getId()));
        String account = groupExtend.getArtificialPersonMobile();
        User user = this.findFirstBy("account = #{account}", Record.create().set("account",account));
        user.setAccount(mobile);
        groupExtend.setArtificialPersonMobile(mobile);
        groupExtendService.update(groupExtend);
        this.update(user);

    }
}
