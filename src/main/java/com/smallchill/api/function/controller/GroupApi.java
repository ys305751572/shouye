package com.smallchill.api.function.controller;

import com.smallchill.api.common.exception.UserHasApprovalException;
import com.smallchill.api.common.exception.UserHasJoinGroupException;
import com.smallchill.api.common.exception.UserInOthersBlankException;
import com.smallchill.api.common.kit.ExcludeParams;
import com.smallchill.api.common.model.ErrorType;
import com.smallchill.api.common.model.Result;
import com.smallchill.api.function.meta.intercept.GroupApiIntercept;
import com.smallchill.api.function.meta.intercept.GroupApiIntercept2;
import com.smallchill.api.function.meta.intercept.SearchUserIntercept;
import com.smallchill.api.function.meta.other.Convert;
import com.smallchill.api.function.meta.other.GroupBtnRegister;
import com.smallchill.api.function.meta.validate.GroupJoinValidator;
import com.smallchill.api.function.meta.validate.GroupPageValidator;
import com.smallchill.api.function.meta.validate.GroupUserValidate;
import com.smallchill.api.function.modal.Button;
import com.smallchill.api.function.modal.vo.Groupvo;
import com.smallchill.api.function.modal.vo.SearchResult;
import com.smallchill.api.function.modal.vo.UserVo;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.core.toolbox.support.BladePage;
import com.smallchill.web.model.Group;
import com.smallchill.web.model.GroupApproval;
import com.smallchill.web.model.Order;
import com.smallchill.web.model.UserInfo;
import com.smallchill.web.service.GroupApprovalService;
import com.smallchill.web.service.GroupService;
import com.smallchill.web.service.OrderService;
import com.smallchill.web.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.beetl.sql.core.kit.CaseInsensitiveHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 组织API
 * Created by yesong on 2016/10/26 0026.
 */
@Controller
@RequestMapping(value = "/api/group")
public class GroupApi extends BaseController {

    @Autowired
    private GroupApprovalService groupApprovalService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private GroupService groupService;

    /**
     * 组织列表
     *
     * @return result
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @Before(GroupPageValidator.class)
    public String list(Integer method, Integer userId) {
        JqGrid page;
        if (method == null || method == 1) {
            // 全部
            try {
                page = apiPaginate("Group.listPage",
                        new GroupApiIntercept().addRecord(Record.create().set("userId", this.getRequest().getParameter("userId"))),
                        ExcludeParams.create().set("userId").set("method"));
            } catch (Exception e) {
                e.printStackTrace();
                return fail();
            }
        } else {
            // 猜你喜欢
            try {
                UserInfo userInfo = userInfoService.findFirstBy("user_id = #{userId}", Record.create().set("userId", userId));
                page = apiPaginate("Group.listPage",
                        new GroupApiIntercept2().addRecord(Record.create().set("userId", userId).set("userInfo", userInfo)),
                        ExcludeParams.create().set("userId").set("method"));
            } catch (Exception e) {
                e.printStackTrace();
                return fail();
            }
        }

        return success(page);
    }

    /**
     * 组织详情
     *
     * @param groupId 组织ID
     * @return result
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    @Before(GroupJoinValidator.class)
    public String detail(Integer groupId, Integer userId) {
        String sql = "SELECT ga.id as gaId,ga.through_time, telphone, title1,content1,is_open1,title2,content2,is_open2,title3,content3,is_open3,ga.`status`,ig.`status` AS istatus FROM tb_group g " +
                "LEFT JOIN tb_group_approval ga ON g.`id` = ga.`group_id` AND ga.`user_id` = #{userId} LEFT JOIN tb_interest_group ig ON g.`id` = ig.`group_id` " +
                "AND ig.`user_id`= #{userId} WHERE g.id = #{groupId} ";
        Record record = Db.init().selectOne(sql, Record.create().set("groupId", groupId).set("userId", userId));
        Integer status = record.get("status") == null ? null : (Integer) record.get("status");
        Integer istatus = record.get("istatus") == null ? null : (Integer) record.get("istatus");
        List<Record> list = Convert.recordToGroupDetail(record, status);

        if (status != null && status == 3 && groupApprovalService.isOverRefuseMaxTime(record.getLong("through_time"), record.getInt("gaId"))) {
            // 判断拒绝时间是否超过72小时
            status = null;
        }
        if (status != null && status == 1) {
            Order order = orderService.findByGaId(record.getInt("gaId"));
//            if (order == null) {
//                return fail(ErrorType.ERROR_CODE_APP_USERHASAPPROVAL);
//            }
//            else {
            if (order != null && order.getStatus() == 2) {
                orderService.delete(order.getId());
                groupApprovalService.delete(record.getInt("gaId"));
                status = null;
            }
//            }
        }
        List<Button> btnList = GroupBtnRegister.create().registerBtns(status, istatus);
        String telphone = record.getStr("telphone");
        List<String> telList = new ArrayList<>();
        if (status == null || status == 1 || status == 3 || status == 4) {
            telphone = "";
        } else {
            if (StringUtils.isNotBlank(telphone)) {
                String[] tels = telphone.split("\\|");
                for (String tel : tels) {
                    if (StringUtils.isNotBlank(tel)) {
                        telList.add(tel);
                    }
                }
            }
        }
        return success(Record.create().set("tagList", list).set("btnList", btnList).set("telphoneList", telList), "group");
    }

    /**
     * 加入组织
     *
     * @return result
     */
    @RequestMapping(value = "/join")
    @ResponseBody
    @Before(GroupJoinValidator.class)
    public String join(GroupApproval ga, Integer targetType) {

        // 判断用户是否满足组织的加入限制条件
        if (!groupApprovalService.isMeetConditions(ga.getUserId(), ga.getGroupId())) {
            return fail(ErrorType.ERROR_CODE_APP_CANNOT_JOIN_GROUP_FAIL);
        }
        try {
            groupApprovalService.join(ga);
        } catch (UserHasApprovalException e) {
            return fail(ErrorType.ERROR_CODE_USERHASAPPROVAL);
        } catch (UserHasJoinGroupException e) {
            return fail(ErrorType.ERROR_CODE_USERHASJOIN);
        } catch (UserInOthersBlankException e) {
            return fail(ErrorType.ERROR_CODE_USERINBLANK);
        } catch (Exception e) {
            return fail(ErrorType.ERROR_CODE_SERVER_EXCEPTION);
        }
        return success();
    }

    /**
     * 圈脉搜索
     *
     * @param userId  当前用户ID
     * @param keyWord 关键词
     * @return result
     */
    @PostMapping(value = "/search")
    @ResponseBody
    public String search(Integer userId, String keyWord, Integer pageNum, Integer pageSize) {

        String sql = "SELECT * FROM (\n" +
                "\n" +
                "SELECT \n" +
                "\tui.`user_id` AS id,\n" +
                "\tui.`username` AS `name`,\n" +
                "\t IFNULL(ui.`avater`,'') AS avater,\n" +
                "\tui.`province_city` AS province_city,\n" +
                "\tui.`per` AS `special1`,\n" +
                "\tui.`domain` AS `special2` ,\n" +
                "\tui.`key_word` AS `special3`," +
                "\tuf.`id` AS `status`,\n" +
                "\t1 AS `type`\n" +
                "FROM \n" +
                "\ttb_user_info ui \n" +
                "\t\n" +
                "LEFT JOIN\n" +
                "\ttb_user_friend uf \n" +
                "ON\n" +
                "\t(ui.`user_id` = uf.friend_id AND uf.`user_id` = #{userId})\n" +
                "\t\n" +
                "WHERE \n" +
                "\tui.`province_city` LIKE CONCAT('%',#{keyWord},'%') \n" +
                "OR \n" +
                "\tui.`school` LIKE CONCAT('%',#{keyWord},'%')\n" +
                "OR\n" +
                "\tui.`career` LIKE CONCAT('%',#{keyWord},'%')\n" +
                "OR\n" +
                "\tui.`domain` LIKE CONCAT('%',#{keyWord},'%')\n" +
                "OR\n" +
                "\tui.`professional` LIKE CONCAT('%',#{keyWord},'%')\n" +
                "OR\n" +
                "\tui.`product_service_name` LIKE CONCAT('%',#{keyWord},'%')\t\n" +
                "OR\n" +
                "\tui.`zy` LIKE CONCAT('%',#{keyWord},'%')\n" +
                "OR\n" +
                "\tui.`sc` LIKE CONCAT('%',#{keyWord},'%')\n" +
                "OR\n" +
                "\tui.`zl` LIKE CONCAT('%',#{keyWord},'%')\n" +
                "OR\n" +
                "\tui.`zy2` LIKE CONCAT('%',#{keyWord},'%')\n" +
                "OR\n" +
                "\tui.`industry_ranking` LIKE CONCAT('%',#{keyWord},'%')\n" +
                "OR \n" +
                "\tui.`qualification` LIKE CONCAT('%',#{keyWord},'%')\n" +
                "OR\n" +
                "\tui.`key_word` LIKE CONCAT('%',#{keyWord},'%')\n" +
                "OR\n" +
                "\t(ui.`organization` LIKE CONCAT('%',#{keyWord},'%') AND ui.`org_is_open` = 1)\n" +
                "\t\n" +
                "UNION ALL\n" +
                "\n" +
                "SELECT  \n" +
                "\tg.`id` AS id,\n" +
                "\tg.`name` AS `name`,\n" +
                "\t IFNULL(g.`avater`,'') AS avater,\n" +
                "\tg.`province_city` AS province_city,\n" +
                "\tg.`member_count` AS `special1`,\n" +
                "\tg.`targat` AS `special2`,\n" +
                "\t'' as `special3`," +
                "\tug.`id` AS `status`,\n" +
                "\t2 AS `type`\n" +
                "\t\n" +
                "FROM \n" +
                "\ttb_group g\n" +
                "LEFT JOIN\n" +
                "\ttb_user_group ug\n" +
                "ON\n" +
                "\t(g.id = ug.group_id AND ug.user_id = #{userId})\n" +
                "WHERE\n" +
                "\t1 = 1\n" +
                "\tAND (g.permissions_type = 1 or ga.status = 2)\n" +
                "  AND (\n" +
                "\tg.`name` LIKE CONCAT('%',#{keyWord},'%') OR g.`targat` LIKE CONCAT('%',#{keyWord},'%')\n" +
                "  )\n" +
                ") AS t1  ORDER BY t1.id ASC\n" +
                "\n";


        int page = (pageNum == null ? 1 : pageNum);
        int size = (pageSize == null ? 10 : pageSize);
        BladePage page2 = Db.init().paginate(sql, Map.class, Record.create().set("keyWord", keyWord).set("userId", userId), page, size);
        JqGrid jqGrid = new JqGrid(page2.getRows(), page2.getTotal(), page2.getPage(), page2.getRecords());
        Convert.recordToSearchResult(jqGrid.getRows());

        for (Object obj : jqGrid.getRows()) {
            CaseInsensitiveHashMap map = (CaseInsensitiveHashMap) obj;
            if (map.get("type") != null && Integer.parseInt(map.get("type").toString()) == 1 &&
                    Integer.parseInt(map.get("status").toString()) == 1 &&
                    Integer.parseInt(map.get("id").toString()) != userId) {
                map.put("name", Convert.hiddenRealUsername(map.get("name").toString()));
            }
        }
        return success(jqGrid);
    }

    /**
     * 退出组织
     *
     * @param groupId 组织ID
     * @param userId  用户ID
     * @return result
     */
    @PostMapping(value = "/out")
    @Before(GroupUserValidate.class)
    @ResponseBody
    public String out(Integer groupId, Integer userId) {
        groupService.out(groupId, userId);
        return success();
    }

    /**
     * 热门关键字
     *
     * @return result
     */
    @PostMapping(value = "/hotword")
    @ResponseBody
    public String hotWord(Integer userId) {
        List<Record> list = Db.init().selectList("SELECT name FROM tb_hot_keyword limit 0,10");
        List<String> hotList = new ArrayList<>();
        for (Record record : list) {
            hotList.add(record.getStr("name"));
        }

        UserInfo userInfo = userInfoService.findByUserId(userId);
        String keyword = userInfo.getKeyWord();
        List<String> keywordList = new ArrayList<>();
        if (StringUtils.isNotBlank(keyword)) {
            String[] keywords = keyword.split("\\|");
            for (String k : keywords) {
                if (StringUtils.isNotBlank(k)) {
                    keywordList.add(k);
                }
            }
        }
        return success(Record.create().set("hotWordList", hotList).set("keyWordList", keywordList), "guessWord");
    }
}
