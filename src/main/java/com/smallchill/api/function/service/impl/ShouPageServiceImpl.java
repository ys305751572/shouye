package com.smallchill.api.function.service.impl;

import com.smallchill.api.function.meta.other.Convert;
import com.smallchill.api.function.modal.UserLastReadTime;
import com.smallchill.api.function.modal.vo.Groupvo;
import com.smallchill.api.function.modal.vo.ShouPageVo;
import com.smallchill.api.function.modal.vo.UserVo;
import com.smallchill.api.function.service.ShoupageService;
import com.smallchill.api.function.service.UserLastReadTimeService;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 手页录api
 * Created by yesong on 2016/11/1 0001.
 */
@Service
public class ShouPageServiceImpl implements ShoupageService {

    @Autowired
    private UserLastReadTimeService userLastReadTimeService;

    private static String sql = "select \n" +
            " ui.user_id,ui.username,ui.avater,ui.province_city,ui.domain,ui.key_word,ui.organization,ui.professional,ua.validate_info,ua.introduce_user_id,ua.status,ua.from_user_id,ua.to_user_id\n" +
            "from \n" +
            "    tb_user_approval ua \n" +
            "join \n" +
            "  tb_user_info ui";

    private String sql_interest_user = "select ui.user_id,ui.username,ui.avater,ui.province_city,ui.domain,ui.key_word,ui.organization,ui.professional from tb_interest i join tb_user_info ui on i.to_id = ui.user_id where i.user_id = #{userId} " +
            " and i.type = 0";

    private String sql_interested_user =
            "select ui.user_id,ui.username,ui.avater,ui.province_city,ui.domain,ui.key_word,ui.organization,ui.professional from tb_interest i join tb_user_info ui on i.user_id = ui.user_id where i.to_id = #{userId}";

    private String sql_interest_group = "select * from tb_interest i join tb_group g on i.to_id = g.id where i.user_id = #{userId} and i.type = 1";

    private String sql_my_group = "select * from tb_group_approval ga join tb_group g on ga.group_id = g.id where g.user_id = #{userId} and (g.status = 1 or g.status = 0)";

    private String sql_new = "select count(*) AS count FROM tb_user_approval ua \n" +
            "WHERE (ua.`from_user_id` = #{userId} OR ua.`to_user_id` = #{userId}) AND ua.create_time > #{lastTime} OR ua.status = 1";

    @Override
    public ShouPageVo index(Integer userId) {
        UserLastReadTime userLastReadTime = userLastReadTimeService.findFirstBy("user_id", Record.create().set("userId", userId));
        countNew(userId, userLastReadTime.getNewTime());
        countIntereste(userId, userLastReadTime.getIntereste());
        countInterested(userId, userLastReadTime.getInterested());
        countGroup(userId, userLastReadTime.getGroup());
        return null;
    }

    @Override
    public List<UserVo> friends(Integer userId) {
        String sql = Blade.dao().getScript("UserFriend.list").getSql();
        String where = "uf.user_id = #{userId}";
        List<Record> friends = Db.init().selectList(sql, where, Record.create().set("userId", userId));
        List<UserVo> voList = new ArrayList<>();
        for (Record record : friends) {
            voList.add(Convert.recordToVo(record));
        }
        return voList;
    }

    @Override
    public int countNew(Integer userId, Long date) {
        Record record = Db.init().selectOne(sql_new, Record.create().set("userId", userId));
        return record.get("count") == null ? 0 : Integer.parseInt(record.get("count").toString());
    }

    /**
     * 引荐
     * 推荐
     * findByToUserId
     * (引荐和推荐通过是否有引荐人ID来区分)
     * 自荐
     * findByFromUserId
     *
     * @param userId 用户ID
     * @return 用户集合
     */
    @Override
    public Map<String, List<UserVo>> listNew(Integer userId) {
        Map<String, List<UserVo>> resultMap = new HashMap<>();
        resultMap.put("list0", new ArrayList<UserVo>()); // 自荐
        resultMap.put("list1", new ArrayList<UserVo>()); // 引荐
        resultMap.put("list2", new ArrayList<UserVo>()); // 推荐

        List<Record> list0 = listNew0(userId);
        List<Record> list1 = listNew1(userId);
        listAdd2Map(list0, resultMap, false);
        listAdd2Map(list1, resultMap, true);
        return resultMap;
    }

    /**
     * 查询引荐和推荐
     *
     * @param userId 用户ID
     * @return 用户集
     */
    private List<Record> listNew0(Integer userId) {
        String where = " on ui.user_id = ua.from_user_id where ua.to_user_id = #{userId}";
        return Db.init().selectList(sql + where, Record.create().set("userId", userId));
    }

    /**
     * 查询自荐
     *
     * @param userId 用户ID
     * @return 用户集
     */
    private List<Record> listNew1(Integer userId) {
        String where = " on ui.user_id = ua.to_user_id where ua.from_user_id = #{userId}";
        return Db.init().selectList(sql + where, Record.create().set("userId", userId));
    }

    /**
     * @param list      用户集合
     * @param resultMap 新结识最后返回数据
     */
    private void listAdd2Map(List<Record> list, Map<String, List<UserVo>> resultMap, boolean isIntroduce) {
        List<UserVo> voList = new ArrayList<>();
        List<UserVo> voList2 = null;
        if (isIntroduce) voList2 = new ArrayList<>();
        UserVo vo;
        for (Record record : list) {
            vo = Convert.recordToVo(record);
            vo.setValidateInfo(record.get("validate_info").toString());
            if (!isIntroduce) {
                voList.add(vo);
            } else {
                if (Integer.parseInt(record.get("introduce_user_id").toString()) == 0) {
                    voList.add(vo);
                } else {
                    voList2.add(vo);
                }
            }
        }
        if (!isIntroduce) {
            resultMap.put("list0", voList);
        } else {
            resultMap.put("list1", voList);
            resultMap.put("list2", voList2);
        }
    }

    @Override
    public int countIntereste(Integer userId, Long date) {

        return 0;
    }

    /**
     * 感兴趣的用户和组织
     *
     * @param userId 用户ID
     * @return 感兴趣的用户和组织集合
     */
    @Override
    public Map<String, Object> listIntereste(Integer userId) {
        Record record = Record.create().set("userId", userId);
        List<Record> groupList = Db.init().selectList(sql_interest_group, record);
        List<Record> userList = Db.init().selectList(sql_interest_user, record);

        List<Groupvo> groupvos = new ArrayList<>();
        List<UserVo> uservos = new ArrayList<>();
        for (Record gp : groupList) {
            groupvos.add(Convert.recordToGroupVo(gp));
        }

        for (Record user : userList) {
            uservos.add(Convert.recordToVo(user));
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("uservos", uservos);
        resultMap.put("groupvos", groupvos);
        return resultMap;
    }

    @Override
    public int countInterested(Integer userId, Long date) {
        return 0;
    }

    /**
     * 对我感兴趣的用户集合
     *
     * @param userId 用户ID
     * @return 对我感兴趣的用户集合
     */
    @Override
    public List<UserVo> listInterested(Integer userId) {
        List<Record> list = Db.init().selectList(sql_interested_user, Record.create().set("userId", userId));
        List<UserVo> voList = new ArrayList<>();
        for (Record record : list) {
            voList.add(Convert.recordToVo(record));
        }
        return voList;
    }

    @Override
    public int countAcquaintances(Integer userId, Long date) {
        return 0;
    }

    @Override
    public List<UserVo> listAcquaintances(Integer userId) {
        String sql2 = sql + " on (ui.`user_id` = ua.`from_user_id` OR ui.`user_id` = ua.`to_user_id` ) ";
        String where = "ui.`user_id` != #{userId} and ua.type = 2 and ( ua.status = 0 or ua.status = 1)";
        List<Record> list = Db.init().selectList(sql2, where, Record.create().set("userId", userId));
        List<UserVo> voList = new ArrayList<>();
        UserVo vo;
        for (Record record : list) {
            vo = Convert.recordToVo(record);
            Integer fromUserId = Integer.parseInt(record.get("from_user_id").toString());
            Integer toUserId = Integer.parseInt(record.get("to_user_id").toString());
            Integer status = record.get("status") == null ? null : Integer.parseInt(record.get("status").toString());
            if (status != null && status == 0) {
                if (fromUserId == userId) {
                    // 等待对方审核
                    vo.setStatus("等待对方审核");
                } else if (toUserId == userId) {
                    // 等待自己审核
                    vo.setStatus("-1");
                    vo.setActionName1("忽略");
                    vo.setActionUrl("/api");
                    vo.setActionName2("同意");
                    vo.setActionUrl2("/api");
                }
            } else {
                vo.setStatus("");
            }
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public int countGroup(Integer userId, Long date) {
        return 0;
    }

    /**
     * 查询我的组织
     *
     * @param userId 用户ID
     * @return 组织集合
     */
    @Override
    public List<Groupvo> listGroup(Integer userId) {
        List<Record> list = Db.init().selectList(sql_my_group, Record.create().set("userId", userId));
        List<Groupvo> groupvos = new ArrayList<>();
        for (Record record : list) {
            groupvos.add(Convert.recordToGroupVo(record));
        }
        return groupvos;
    }
}
