package com.smallchill.api.function.controller;

import com.smallchill.api.common.exception.UserExitsException;
import com.smallchill.api.common.exception.UserIsNotManagerException;
import com.smallchill.api.common.kit.ExcludeParams;
import com.smallchill.api.common.model.ErrorType;
import com.smallchill.api.function.meta.intercept.UserApiIntercept;
import com.smallchill.api.function.meta.other.Convert;
import com.smallchill.api.function.meta.validate.*;
import com.smallchill.api.function.modal.GroupInterest;
import com.smallchill.api.function.modal.Message;
import com.smallchill.api.function.modal.UserFriendGrouping;
import com.smallchill.api.function.modal.UserInterest;
import com.smallchill.api.function.modal.vo.IntroduceUserVo;
import com.smallchill.api.function.modal.vo.UserVo;
import com.smallchill.api.function.service.*;
import com.smallchill.api.system.service.VcodeService;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.interfaces.ILoader;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.core.toolbox.kit.CacheKit;
import com.smallchill.core.toolbox.kit.DateTimeKit;
import com.smallchill.system.meta.intercept.UserValidator;
import com.smallchill.web.model.UserApproval;
import com.smallchill.web.model.UserInfo;
import com.smallchill.web.service.UserApprovalService;
import com.smallchill.web.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户API
 * Created by yesong on 2016/11/4 0004.
 */
@RequestMapping(value = "/api/user")
@Controller
public class UserApi extends BaseController implements ConstCache {

    private static String LIST_SOURCE = "UserInfo.listPage";
    private String CACHE_KEY = "user_";

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserApprovalService userApprovalService;
    @Autowired
    private UserInterestService userInterestService;
    @Autowired
    private GroupInterestService groupInterestService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private VcodeService vcodeService;


    /**
     * 全局用户列表
     *
     * @return result
     */
    @RequestMapping(value = "/global/user/list")
    @ResponseBody
    @Before(GlobalUsersValidate.class)
    public String globalUserlist() {
        JqGrid jqGrid;
        try {
            jqGrid = apiPaginate(LIST_SOURCE, new UserApiIntercept());
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(jqGrid);
    }

    /**
     * 组织用户列表
     *
     * @return result
     */
    @RequestMapping(value = "/group/list")
    @ResponseBody
    @Before(GroupUserValidate.class)
    public String groupUserList() {
        JqGrid jqGrid;
        try {
            jqGrid = apiPaginate(LIST_SOURCE
                    , new UserApiIntercept().addRecord(Record.create().set("userId", this.getRequest().getParameter("userId"))
                            .set("groupId", this.getRequest().getParameter("groupId"))
                            .set("history", this.getRequest().getParameter("history"))
                            .set("domain", this.getRequest().getParameter("domain"))),
                    ExcludeParams.create().set("userId").set("groupId").set("history").set("domain"));
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(jqGrid);
    }

    /**
     * 查询用户详情
     *
     * @param userId
     * @param toUserId
     * @return
     */
    @RequestMapping(value = "/userInfo")
    @ResponseBody
    public String userInfo(Integer userId, Integer toUserId, Integer groupId) {
        UserVo vo;
        final Integer userid = userId;
        final Integer toUserid = toUserId;
        final Integer groupid = groupId;
        try {
//            vo = CacheKit.get(DIY_CACHE, CACHE_KEY + userId + "_" + toUserId, new ILoader() {
//                @Override
//                public Object load() {
//                    return userInfoService.findUserInfoDetail(userid, toUserid, groupid);
//                }
//            });
            vo = userInfoService.findUserInfoDetail(userid, toUserid, groupid);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(vo);
    }

    /**
     * 个人详情
     *
     * @param userId 用户ID
     * @return result
     */
    @RequestMapping(value = "/info")
    @ResponseBody
    @Before(GroupPageValidator.class)
    public String info(Integer userId) {
        Record record;
        try {
            record = userInfoService.findUserInfoDetail(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(record, "userInfo");
    }

    /**
     * 个人中心修改页
     *
     * @param userId 当前用户ID
     * @return result
     */
    @ResponseBody
    @PostMapping(value = "/info/update/page")
    public String updatePage(Integer userId) {
        UserVo userVo = null;
        try {
            userVo = userInfoService.findUserinfo(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success(userVo);
    }

    /**
     * 修改用户信息
     *
     * @param userInfo 用户信息
     * @return result
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @Before(GroupPageValidator.class)
    public String update(UserInfo userInfo) {

        UserInfo userInfo1;
        UserVo userVo = null;
        try {
            userInfo1 = userInfoService.updateUserInfo(userInfo, null);
            userVo = Convert.userInfoToRecord(userInfo1);
        } catch (UserExitsException e) {
            e.printStackTrace();
        }
        return success(userVo);
    }

    /**
     * 用户-拉黑用户
     *
     * @return result
     */
    @RequestMapping(value = "/blank")
    @ResponseBody
    @Before(UserBlankValidate.class)
    public String blank(UserApproval ua) {
        userApprovalService.userApprovalBlank(ua);
        return success();
    }

    /**
     * 用户 - 移除黑名单
     *
     * @return result
     */
    @RequestMapping(value = "/unblank")
    @ResponseBody
    @Before(UserBlankValidate.class)
    public String unBlank(UserApproval ua) {
        userApprovalService.userApprovalUnBlank(ua);
        return success();
    }

    /**
     * 用户-感兴趣
     *
     * @param userId
     * @return result
     */
    @RequestMapping(value = "/interest")
    @ResponseBody
    @Before(UserInterestValidate.class)
    public String userInterest(Integer userId, String toUserIds) {
        try {
            userInfoService.interest(userId, toUserIds);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success();
    }

    /**
     * 用户-取消感兴趣
     *
     * @param ui
     * @return
     */
    @RequestMapping(value = "/uninterest")
    @ResponseBody
    @Before(UserUnInterestValidate.class)
    public String userUnInterest(UserInterest ui) {

        String where = "user_id = #{userId} and to_user_id = #{toUserId}";
        Record record = Record.create().set("userId", ui.getUserId()).set("toUserId", ui.getToUserId());
        try {
            userInterestService.updateBy("status = 1", where, record);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success();
    }

    /**
     * 组织-感兴趣
     *
     * @param gi
     * @return
     */
    @RequestMapping(value = "/group/interest")
    @ResponseBody
    @Before(GroupJoinValidator.class)
    public String groupInterest(GroupInterest gi) {
        String where = "user_id = #{userId} and group_id = #{groupId}";
        Record record = Record.create().set("userId", gi.getUserId()).set("groupId", gi.getGroupId());

        GroupInterest groupInterest = groupInterestService.findFirstBy(where, record);
        if (groupInterest != null) {
            groupInterestService.updateBy("status = 0", where, record);
        } else {
            groupInterest = new GroupInterest();
            groupInterest.setStatus(0);
            groupInterest.setUserId(gi.getUserId());
            groupInterest.setGroupId(gi.getGroupId());
            groupInterest.setCreateTime(DateTimeKit.nowLong());
            groupInterestService.save(groupInterest);
        }
        return success();
    }

    /**
     * 组织-取消感兴趣
     *
     * @param gi gi
     * @return result
     */
    @RequestMapping(value = "/group/uninterest")
    @ResponseBody
    @Before(GroupJoinValidator.class)
    public String groupUnInterest(GroupInterest gi) {
        String where = "user_id = #{userId} and group_id = #{groupId}";
        Record record = Record.create().set("userId", gi.getUserId()).set("groupId", gi.getGroupId());
        try {
            groupInterestService.updateBy("status = 1", where, record);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success();
    }

    /**
     * 分组首页
     *
     * @param userId 用户ID
     * @return result
     */
    @PostMapping(value = "/grouping/index")
    @ResponseBody
    @Before(GroupPageValidator.class)
    public String grouping(Integer userId) {
        List<Record> record;
        try {
            record = userInfoService.findIndexGrouping(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(record);
    }

    /**
     * 用户新增分组
     *
     * @param userId  用户ID
     * @param userIds 加入分组用户ID，id之间用“，”分割
     * @return
     */
    @PostMapping(value = "/grouping/create")
    @ResponseBody
    @Before(GroupPageValidator.class)
    public String createGrouping(Integer userId, String name, String userIds) {
        userInfoService.createGrouping(userId, name, userIds);
        return success();
    }

    /**
     * 修改分组信息
     *
     * @param userId
     * @param groupingId
     * @param name
     * @return
     */
    @PostMapping(value = "/grouping/update")
    @ResponseBody
    @Before(UpdateGroupingValidate.class)
    public String updateGrouping(Integer userId, Integer groupingId, String name) {
        try {
            userInfoService.updateGrouping(userId, groupingId, name);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success();
    }

    /**
     * 分组用户列表
     *
     * @param groupingId 分组ID
     * @return
     */
    @PostMapping(value = "/grouping/user/list")
    @ResponseBody
    @Before(UserIdValidate.class)
    public String userListByGroupingId(Integer userId, Integer groupingId, Integer defaultId) {
        List<UserVo> list = null;
        try {
            if (groupingId != null) {
                list = userInfoService.findUserListByGroupingId(groupingId);
            } else {
                list = userInfoService.findUserListByDefaultId(userId, defaultId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(list);
    }

    /**
     * 加入分组用户
     *
     * @param userId  用户ID
     * @param userIds 加入分组用户ID，id之间用“，”分割
     * @return result
     */
    @PostMapping(value = "/grouping/join")
    @ResponseBody
    @Before(GroupPageValidator.class)
    public String joinUserToGrouping(Integer userId, String userIds, Integer groupingId) {
        userInfoService.joinToGrouping(userId, userIds, groupingId);
        return success();
    }

    /**
     * 删除分组
     *
     * @param userId     当前用户ID
     * @param groupingId 分组ID
     * @return result
     */
    @PostMapping(value = "/grouping/delete")
    @ResponseBody
    @Before(GroupingValidate.class)
    public String deleteGrouping(Integer userId, Integer groupingId) {
        try {
            userInfoService.deleteGrouping(userId, groupingId);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success();
    }

    /**
     * 查看交集
     *
     * @return result
     */
    @PostMapping(value = "/intersection")
    @ResponseBody
    @Before(IntersectionValidate.class)
    public String intersection(Integer userId, Integer toUserId) {
        Record record;
        try {
            record = userInfoService.intersection(userId, toUserId);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(record, "intersection");
    }

    /**
     * 我的消息列表
     *
     * @param userId 当前用户ID
     * @return result
     */
    @PostMapping(value = "/message/list")
    @ResponseBody
    @Before(GroupPageValidator.class)
    public String messageList(Integer userId) {
        List<Message> list = messageService.findByUserId(userId);
        if (list != null) {
            for (Message message : list) {
                message.setAction1(null);
                message.setAction2(null);
                message.setAction3(null);
                message.setAction4(null);
                message.setFromId(null);
                message.setSendTime(null);
                message.setToId(null);
                message.setReplaces(null);
            }
        } else list = new ArrayList<>();
        return success(list);
    }

    /**
     * 黑名单列表
     *
     * @param userId 当前用户ID
     * @return result
     */
    @PostMapping(value = "/blank/list")
    @ResponseBody
    public String blankList(Integer userId) {
        String sql = Blade.dao().getScript("UserInfo.blanklist").getSql();
        List<Record> list = Db.init().selectList(sql, Record.create().set("userId", userId));
        List<UserVo> userVos = new ArrayList<>();
        for (Record record : list) {
            userVos.add(Convert.recordToVo(record));
        }
        return success(userVos);
    }

    /**
     * 验证验证码是否正确
     *
     * @param mobile 手机号
     * @param code   验证码
     * @return result
     */
    @PostMapping(value = "/code/validate")
    @ResponseBody
    @Before(VcodeValidate.class)
    public String validateCode(String mobile, String code) {
        if (vcodeService.validate(mobile, code)) {
            return success();
        } else {
            return fail(ErrorType.ERROR_CODE_VALIDATECODE_FAIL);
        }
    }

    /**
     * 重新绑定手机号
     *
     * @param mobile 手机号
     * @param code   验证码
     * @return result
     */
    @PostMapping(value = "/mobile/rebind")
    @ResponseBody
    @Before(VcodeValidate.class)
    public String updateBindMobile(String mobile, String code, Integer userId) {
        if (vcodeService.validate(mobile, code)) {
            try {
                userInfoService.updateMobile(mobile, userId);
            } catch (UserExitsException e) {
                e.printStackTrace();
                return fail(ErrorType.ERROR_CODE_USERHASEXTIS);
            }
            return success();
        } else {
            return fail(ErrorType.ERROR_CODE_VALIDATECODE_FAIL);
        }
    }

    /**
     * 组织后台-加入组织用户审核列表
     *
     * @param userId 当前用户ID
     * @return result
     */
    @PostMapping(value = "/groupserver/joinlist")
    @ResponseBody
    @Before(UserIdValidate.class)
    public String groupServerJoinList(Integer userId) {
        List<UserVo> list = null;
        try {
            list = userInfoService.findUserListByJoinGroup(userId);
        } catch (UserIsNotManagerException e) {
            return fail(ErrorType.ERROR_CODE_APP_NOT_MANAGER_FAIL);
        }
        return success(list);
    }

    /**
     * 组织后台-引荐列表
     *
     * @param userId 当前用户ID
     * @return result
     */
    @PostMapping(value = "/groupserver/introducelist")
    @ResponseBody
    @Before(UserIdValidate.class)
    public String groupServerIntroduceList(Integer userId) {
        List<IntroduceUserVo> list;
        try {
            list = userInfoService.findIntroduceUserVoList(userId);
        } catch (UserIsNotManagerException e) {
            return fail(ErrorType.ERROR_CODE_APP_NOT_MANAGER_FAIL);
        }
        return success(list);
    }

    /**
     * 设置组织是否允许加入
     *
     * @param userId 当前用户ID
     * @param status 状态  1:开放 2:关闭
     * @return result
     */
    @PostMapping(value = "/groupserver/isjoin")
    @ResponseBody
    @Before(UserAndStatusValidate.class)
    public String setIsJoin(Integer userId, Integer status) {
        try {
            userInfoService.setIsJoin(userId, status);
        } catch (UserIsNotManagerException e) {
            return fail(ErrorType.ERROR_CODE_APP_NOT_MANAGER_FAIL);
        }
        return success();
    }

    /**
     * 设置组织是否允许引荐
     *
     * @param userId 当前用户ID
     * @param status 状态  1:允许 2:拒绝
     * @return result
     */
    @PostMapping(value = "/groupserver/isintroduc")
    @ResponseBody
    @Before(UserAndStatusValidate.class)
    public String setisIntroduce(Integer userId, Integer status) {
        try {
            userInfoService.setisIntroduce(userId, status);
        } catch (UserIsNotManagerException e) {
            return fail(ErrorType.ERROR_CODE_APP_NOT_MANAGER_FAIL);
        }
        return success();
    }


}
