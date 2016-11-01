package com.smallchill.api.function.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.web.model.UserFriend;
import com.smallchill.web.service.UserFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 好友系统
 * Created by yesong on 2016/10/31 0031.
 */
@RequestMapping(value = "/api/friends")
@Controller
public class FriendsApi extends BaseController{

    @Autowired
    private UserFriendService userFriendService;

    /**
     * 删除好友
     * @param uf 好友信息
     * @return result
     */
    @RequestMapping(value = "/del")
    @ResponseBody
    public String del(UserFriend uf) {
        try {
            userFriendService.delFriend(uf);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success();
    }
}
