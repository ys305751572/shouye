package com.smallchill.api.function.controller;

import com.smallchill.api.function.modal.vo.UserVo;
import com.smallchill.api.function.service.ShoupageService;
import com.smallchill.common.base.BaseController;
import com.smallchill.web.model.UserFriend;
import com.smallchill.web.service.UserFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 好友系统
 * Created by yesong on 2016/10/31 0031.
 */
@RequestMapping(value = "/api/friends")
@Controller
public class FriendsApi extends BaseController {

    @Autowired
    private UserFriendService userFriendService;

    @Autowired
    private ShoupageService shoupageService;

    /**
     * 删除好友
     *
     * @param uf 好友信息
     * @return result
     */
    @RequestMapping(value = "/friend/del")
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

    /**
     * 删除熟人
     * @param uf
     * @return result
     */
    @PostMapping(value = "/acquaintances/del")
    @ResponseBody
    public String delAcquaintances(UserFriend uf) {
        userFriendService.delAcquaintances(uf);
        return success();
    }

    /**
     * 查看对方熟人
     *
     * @param userId   当前用户ID
     * @param toUserId 目标用户ID
     * @return result
     */
    @PostMapping(value = "/acquaintances")
    @ResponseBody
    public String findAcquaintancesByUserId(Integer userId, Integer toUserId) {
        List<UserVo> list;
        try {
            list = userFriendService.findAcquaintancesByUserId(userId, toUserId);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(list);
    }

    /**
     * 我的好友列表
     *
     * @param userId  当前用户ID
     * @param keyWord 搜索关键字
     * @return result
     */
    @PostMapping(value = "/list")
    @ResponseBody
    public String myFriendList(Integer userId, String keyWord) {
        List<UserVo> userVos;
        try {
            userVos = shoupageService.friends(userId, null, null, null, keyWord, null);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(userVos);
    }
}