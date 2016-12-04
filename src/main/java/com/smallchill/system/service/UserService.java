package com.smallchill.system.service;

import com.smallchill.common.vo.User;
import com.smallchill.core.base.service.IService;
import com.smallchill.web.model.Group;
import com.smallchill.web.model.GroupExtend;

/**
 * Created by Administrator
 * on 2016/12/3.
 */
public interface UserService extends IService<User> {

    void updatePwd(User user, GroupExtend groupExtend, String pwd);

    void updateMoile(Group group, String mobile);

}
