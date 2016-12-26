package com.smallchill.web.controller;

import com.smallchill.api.function.modal.Message;
import com.smallchill.api.function.service.MessageService;
import com.smallchill.common.base.BaseController;
import com.smallchill.common.pay.util.TimeUtil;
import com.smallchill.core.constant.Cst;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.grid.GridManager;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.core.toolbox.kit.CollectionKit;
import com.smallchill.core.toolbox.kit.DateTimeKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.system.model.Demo;
import com.smallchill.system.model.RoleGroup;
import com.smallchill.web.meta.intercept.GroupAdminIntercept;
import com.smallchill.web.model.*;
import com.smallchill.web.model.UserClassification;
import com.smallchill.web.service.*;
import org.beetl.sql.core.kit.CaseInsensitiveHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator
 * on 2016/11/22.
 */
@RequestMapping(value = "/userGroup")
@Controller
public class UserGroupController extends BaseController {

    private static String LIST_SOURCE = "UserGroup.list";
    private static String BASE_PATH = "/web/usergroup/";
    private static String CODE = "userGroup";
    private static String PERFIX = "tb_user_group";

    @Autowired
    MessageService messageService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    UserGroupService userGroupService;
    @Autowired
    ClassificationService classificationService;
    @Autowired
    UserClassificationService userClassificationService;
    @Autowired
    UserTagService userTagService;
    @Autowired
    TagService tagService;
    @Autowired
    GroupApprovalService groupApprovalService;

    @RequestMapping(value = "/")
    public String groupIndex(ModelMap mm) {
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        List<Classification> list = classificationService.findBy("group_id=#{groupId}", Record.create().set("groupId", group.getId()));
        List<Tag> list2 = tagService.findBy("group_id=#{groupId}", Record.create().set("groupId", group.getId()));
        mm.put("tagList", JsonKit.toJson(list2));
        mm.put("classificationList", JsonKit.toJson(list));
        mm.put("code", CODE);
        return BASE_PATH + "userGroup_list.html";
    }

    @ResponseBody
    @RequestMapping(KEY_LIST)
    public Object loadList() {
        Object object = paginate(LIST_SOURCE, new GroupAdminIntercept());

        Integer page = getParameterToInt("page", 1);
        Integer rows = userGroupService.findAll().size();
        String where = getParameter("where", "");
        String sidx = getParameter("sidx", "");
        String sord = getParameter("sord", "");
        String sort = getParameter("sort", "");
        String order = getParameter("order", "");

        if (StrKit.notBlank(sidx)) {
            sort = sidx + " " + sord
                    + (StrKit.notBlank(sort) ? ("," + sort) : "");
        }

        JqGrid grid1 = (JqGrid) GridManager.paginate(null, page, rows, LIST_SOURCE, where, sort, order, new GroupAdminIntercept(), this);
        List<Integer> ids = new ArrayList<>();
        List<CaseInsensitiveHashMap> list = grid1.getRows();
        //查询结果所有ID
        for (CaseInsensitiveHashMap map : list) {
            Integer id = (Integer) map.get("userId");
            ids.add(id);
        }

        getRequest().getSession().setAttribute("userGroupIds", ids);
        getRequest().getSession().setAttribute("userGroupNum", list.size());

        return object;
    }


    /**
     * 移除用户
     */
    @ResponseBody
    @RequestMapping(value = "/removeUser")
    public AjaxResult removeUser(Integer id) {
        try {
            userGroupService.removeUser(id);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return error("移除失败");
        }
        return success("移除成功");
    }


    /**
     * 分类
     */
    @RequestMapping("/classification")
    public String classification(ModelMap mm) {
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        List<Classification> list = classificationService.findBy("group_id=#{groupId}", Record.create().set("groupId", group.getId()));
        mm.put("classificationList", JsonKit.toJson(list));
        mm.put("code", CODE);
        return BASE_PATH + "userGroup_classification.html";
    }


    @ResponseBody
    @RequestMapping("/classification_save")
    public AjaxResult classification_save(String vals, Integer type) {
        try {
            classificationService.classification_save(vals, type);
        } catch (Exception e) {
            e.printStackTrace();
            return error(SAVE_FAIL_MSG);
        }
        return success(SAVE_SUCCESS_MSG);
    }

    @ResponseBody
    @RequestMapping("/classification_del")
    public AjaxResult classification_del(Integer id) {
        if (id == null) return error(DEL_FAIL_MSG);
        List<UserClassification> userClassifications = userClassificationService.findBy(" classification_id = #{classificationId}", Record.create().set("classificationId", id));
        for (UserClassification userClassification : userClassifications) {
            userClassificationService.delete(userClassification.getId());
        }
        Integer index = classificationService.delete(id);
        if (index != 0) {
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }

    @ResponseBody
    @RequestMapping("/user_classification_add")
    public AjaxResult userClassificationAdd(Integer classificationId, Integer userId) {
        UserClassification userClassification = new UserClassification();
        List<UserClassification> uc = userClassificationService.findBy("classification_id = #{classificationId} AND user_id = #{userId}", Record.create().set("classificationId", classificationId).set("userId", userId));
        if (!uc.isEmpty()) {
            return error("已存在");
        }
        userClassification.setUserId(userId);
        userClassification.setClassificationId(classificationId);
        boolean index = userClassificationService.save(userClassification);
        if (index) {
            return success(SAVE_SUCCESS_MSG);
        } else {
            return error(SAVE_FAIL_MSG);
        }
    }


    @ResponseBody
    @RequestMapping("/user_classification_del")
    public AjaxResult userClassificationDel(String classification, Integer userId) {
        Classification _c = classificationService.findFirstBy(" classification = #{classification}", Record.create().set("classification", classification));
        UserClassification userClassification = userClassificationService.findFirstBy(" user_id = #{userId} AND classification_id = #{classificationId}", Record.create().set("userId", userId).set("classificationId", _c.getId()));
        Integer index = userClassificationService.delete(userClassification.getId());
        if (index != 0) {
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }

    /**
     * 标签
     */
    @RequestMapping("/tag")
    public String tag(ModelMap mm) {
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        List<Tag> list = tagService.findBy("group_id=#{groupId}", Record.create().set("groupId", group.getId()));
        mm.put("tagList", JsonKit.toJson(list));
        mm.put("code", CODE);
        return BASE_PATH + "userGroup_tag.html";
    }

    @ResponseBody
    @RequestMapping("/tag_save")
    public AjaxResult tag_save(String vals, Integer type) {
        try {
            tagService.tag_save(vals, type);
        } catch (Exception e) {
            e.printStackTrace();
            return error(SAVE_FAIL_MSG);
        }
        return success(SAVE_SUCCESS_MSG);
    }

    @ResponseBody
    @RequestMapping("/tag_del")
    public AjaxResult tag_del(Integer id) {
        if (id == null) return error(DEL_FAIL_MSG);
        List<UserTag> userTags = userTagService.findBy(" tag_id = #{tagId}", Record.create().set("tagId", id));
        for (UserTag userTag : userTags) {
            userTagService.delete(userTag.getId());
        }
        Integer index = tagService.delete(id);
        if (index != 0) {
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }

    @ResponseBody
    @RequestMapping("/user_tag_add")
    public AjaxResult userTagAdd(Integer tagId, Integer userId) {
        UserTag userTag = new UserTag();
        List<UserTag> ut = userTagService.findBy("tag_id = #{tagId} AND user_id = #{userId}", Record.create().set("tagId", tagId).set("userId", userId));
        if (!ut.isEmpty()) {
            return error("已存在");
        }
        userTag.setUserId(userId);
        userTag.setTagId(tagId);
        boolean index = userTagService.save(userTag);
        if (index) {
            return success(SAVE_SUCCESS_MSG);
        } else {
            return error(SAVE_FAIL_MSG);
        }
    }

    @ResponseBody
    @RequestMapping("/user_tag_del")
    public AjaxResult userTagDel(String tag, Integer userId) {
        Tag _t = tagService.findFirstBy(" tag = #{tag}", Record.create().set("tag", tag));
        UserTag userTag = userTagService.findFirstBy(" user_id = #{userId} AND tag_id = #{tagId}", Record.create().set("userId", userId).set("tagId", _t.getId()));
        Integer index = userTagService.delete(userTag.getId());
        if (index != 0) {
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }


    /**
     * 发送
     */
    //消息发送页面(单发)
    @RequestMapping("/message" + "/{id}")
    public String userMessages(ModelMap mm, @PathVariable String id) {
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        int sendMass = 1;
        String username = "";
        String userId = "";
        if (id.contains(",")) {
            sendMass = 2;
            List<Integer> userids = new ArrayList<>();
            String[] ss = id.split(",");
            for (String s : ss) {
                userids.add(Integer.parseInt(s));
            }
            List<UserInfo> userInfos = userInfoService.findByUserIds(userids);
            for (UserInfo userInfo : userInfos) {
                username += userInfo.getUsername() + ",";
                userId += userInfo.getUserId() + ",";
            }
            username = username.substring(0, username.length() - 1);
            userId = userId.substring(0, userId.length() - 1);
        } else {
            UserInfo userInfo = userInfoService.findByUserId(Integer.parseInt(id));
            username = userInfo.getUsername();
            userId = String.valueOf(userInfo.getUserId());
        }


        List<Message> messages = messageService.findBy(
                "from_id = #{fromId} AND send_date >= #{startDate} AND send_date < #{endDate} AND send_mass = #{sendMass} GROUP BY send_date",
                Record.create()
                        .set("fromId", group.getId())
                        .set("startDate", TimeUtil.getTimesmorning())
                        .set("endDate", TimeUtil.getTimesnight())
                        .set("sendMass", sendMass)
        );

        mm.put("flag", messages.size());
        mm.put("username", username);
        mm.put("userId", userId);
        mm.put("userInfoNum", 0);
        mm.put("code", CODE);
        return BASE_PATH + "userGroup_message.html";
    }

    //消息发送页面(群发)
    @RequestMapping("/message")
    public String _userMessages(ModelMap mm, HttpServletRequest request) {
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        UserInfo userInfo = new UserInfo();

        List<Message> messages = messageService.findBy(
                "from_id = #{fromId} AND send_date >= #{startDate} AND send_date < #{endDate} AND send_mass = #{sendMass} GROUP BY send_date",
                Record.create()
                        .set("fromId", group.getId())
                        .set("startDate", TimeUtil.getTimesmorning())
                        .set("endDate", TimeUtil.getTimesnight())
                        .set("sendMass", 2)
        );

        mm.put("flag", messages.size());
        mm.put("userInfoNum", request.getSession().getAttribute("userGroupNum"));
        mm.put("userInfo", userInfo);
        mm.put("code", CODE);
        return BASE_PATH + "userGroup_message.html";
    }

    //消息发送
    @ResponseBody
    @RequestMapping("/send_message")
    public AjaxResult sendMessage(String userId, String title, String content) {
        try {
            Long time = DateTimeKit.nowLong();
            if (userId != null && userId.contains(",")) {
                String[] userIdss = userId.split(",");
                for (String _userId : userIdss) {
                    messageService.sendMsgFromGroupToUser(Integer.parseInt(_userId), 1, time, title, content);
                }
            } else {
                List<Integer> ids = (List<Integer>) ShiroKit.getSession().getAttribute("userGroupIds");
                messageService.sendMsgFromGroupToUser(ids, 2, time, title, content);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return error(SEND_FAIL_MSG);
        }
        return success(SEND_SUCCESS_MSG);

    }

    /**
     * 邀请加入
     */
    @RequestMapping("/invitation")
    public String invitation(ModelMap mm) {
        List list = userGroupService.findInvitationUser();
        mm.put("list", list);
        mm.put("code", CODE);
        return BASE_PATH + "userGroup_invitation.html";
    }

    @RequestMapping(value = "/user_invitation")
    @ResponseBody
    public AjaxResult userInvitation(Integer userId, String content) {
        UserInfo userInfo = userInfoService.findByUserId(userId);
        try {
            if (userInfo == null) {
                return error("没有找到用户");
            }
            groupApprovalService.userInvitation(userId, content);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return error(SEND_FAIL_MSG);
        }
        return success(SEND_SUCCESS_MSG);

    }

    @RequestMapping(value = "/add_userName")
    @ResponseBody
    public AjaxResult addUserName(String mobile) {
//        UserInfo userInfo = userInfoService.findByUserId(userId);
        List uglist = userGroupService.findMembers(mobile);
        List galist = groupApprovalService.findMembersApproval(mobile);
        try {

            if (!uglist.isEmpty()) {
                return error("该用户已经是会员");
            }

            if (!galist.isEmpty() && isCan(galist)) {
                return error("该用户已经申请加入组织");
            }

            UserInfo userInfo = userInfoService.findFirstBy("mobile = #{mobile}", Record.create().set("mobile", mobile));
            if (userInfo == null) {
                return error("该用户不存在");
            }
            String success = userInfo.getUserId() + "|" + userInfo.getUsername();
            return success(success);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return error("添加失败");
        }

    }

    private boolean isCan(List galist) {
        for (Object obj : galist) {
            Record ga = (Record) obj;
            if (ga.getInt("status") == 3) {
                groupApprovalService.delete(ga.getInt("id"));
                return false;
            }
        }
        return true;
    }
}
