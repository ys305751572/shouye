package com.smallchill.api.function.service.impl;

import com.smallchill.api.function.meta.other.Convert;
import com.smallchill.api.function.modal.UserLastReadTime;
import com.smallchill.api.function.modal.vo.Groupvo;
import com.smallchill.api.function.modal.vo.ShouPageVo;
import com.smallchill.api.function.modal.vo.UserVo;
import com.smallchill.api.function.service.ShoupageService;
import com.smallchill.api.function.service.UserLastReadTimeService;
import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.kit.CacheKit;
import com.smallchill.core.toolbox.kit.DateTimeKit;
import com.smallchill.core.toolbox.kit.JsonKit;
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
public class ShouPageServiceImpl implements ShoupageService, ConstCache {

    @Autowired
    private UserLastReadTimeService userLastReadTimeService;

    private String USER_BASE_INFO_SQL = " ui.user_id,ui.username,ui.avater,ui.province_city,ui.domain,ui.key_word,ui.organization,ui.professional ";

    private String sql = "select" + USER_BASE_INFO_SQL + " ,ua.validate_info,ua.introduce_user_id,ua.status,ua.from_user_id,ua.to_user_id " +
            "from tb_user_approval ua join tb_user_info ui";

    private String SQL_INTEREST_USER = "select" + USER_BASE_INFO_SQL + " from tb_interest_user i join tb_user_info ui on i.user_id = ui.user_id where i.user_id = #{userId}";

    private String GROUP_BASE_INFO_SQL = " g.id,g.name,g.avater,g.province,g.city,g.type,g.province_city provinceCity,g.member_count memberCount,g.targat ";

    private String SQL_INTEREST_GROUP = "select "+ GROUP_BASE_INFO_SQL +" from tb_interest_group i join tb_group g on i.group_id = g.id where i.user_id = #{userId}";

        private String SQL_INTERESTED_USER = "select " + USER_BASE_INFO_SQL + " ,ua.status uastatus from tb_interest_user i join tb_user_info ui on i.user_id = ui.user_id "
            + " LEFT JOIN tb_user_approval ua ON ui.`user_id` = ua.`from_user_id` where i.to_user_id = #{userId} OR ua.`status` != 3";

    private String SQL_MY_GROUP = "select * from tb_group_approval ga join tb_group g on ga.group_id = g.id where g.user_id = #{userId} and (g.status = 1 or g.status = 0)";

    private String NEW_USER_COUNT_SQL = "SELECT COUNT(*) as count FROM tb_user_approval ua WHERE (ua.`from_user_id` = #{userId} OR ua.`to_user_id` = #{userId}) " +
            "AND (ua.`status` = 1 OR ua.`create_time` > #{lastTime})";
    // 感兴趣的用户
    private String INTEREST_USER_COUNT_SQL = "SELECT COUNT(*) as count FROM tb_interest_user iu WHERE iu.create_time > #{lastTime} AND iu.user_id = #{userId}";
    // 感兴趣的组织
    private String INTEREST_GROUP_COUNT_SQL = "SELECT COUNT(*) as count FROM tb_interest_group iu WHERE iu.create_time > #{lastTime} AND iu.user_id = #{userId}";
    // 被谁感兴趣
    private String INTERESTD_USER_COUNT_SQL = "SELECT COUNT(*) as count FROM tb_interest_user iu WHERE iu.create_time > #{lastTime} AND iu.to_user_id = #{userId}";
    // 我的熟人
    private String ACQUAINTANCES_COUNT_SQL = "SELECT COUNT(*) as count FROM tb_user_approval ua WHERE (ua.from_user_id = #{userId} OR ua.to_user_id = #{userId}) AND (ua.create_time > #{lastTime} AND ua.type = 2)";
    // 我的组织
    private String MYGROUP_COUNT_SQL = "SELECT COUNT(*) as count FROM tb_group_approval ga WHERE ga.user_id = #{userId} AND ga.create_time > #{lastTime}";

    private int NOT_FRINED = 2000; // 未结识
    private int FRIEND = 2001; // 已结识
    private int NOT_PROCESS_FROM_USER_ID = 2002; // 显示忽略 结识按钮
    private int NOT_PROCESS_TO_USER_ID = 2003;
    private int PASS = 2004;


    /**
     * 手页录首页
     *
     * @param userId 当前用户ID
     * @return 首页录vo
     */
    @Override
    public ShouPageVo index(Integer userId) {

        UserLastReadTime userLastReadTime = lastReadTimeByUserId(userId);

        if (userLastReadTime == null) {
            userLastReadTime = new UserLastReadTime();
        }
        List<UserVo> voList = friends(userId);
        ShouPageVo shouPageVo = new ShouPageVo();

        shouPageVo.setNewCount(countNew(userId, userLastReadTime.getNewTime()));
        shouPageVo.setAcquaintancesCount(countAcquaintances(userId, userLastReadTime.getAcquaintances()));
        shouPageVo.setInterestCount(countIntereste(userId, userLastReadTime.getIntereste()));
        shouPageVo.setInterestedCount(countInterested(userId, userLastReadTime.getInterested()));
        shouPageVo.setGroupCount(countGroup(userId, userLastReadTime.getGroup()));
        shouPageVo.setList(voList == null || voList.size() == 0 ? new ArrayList<UserVo>() : voList);

        System.out.println(JsonKit.toJson(shouPageVo));
        return shouPageVo;
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
        Record _r = Record.create().set("userId", userId).set("lastTime", date);
        Record record = Db.init().selectOne(NEW_USER_COUNT_SQL, _r);
        return record.get("count") == null ? 0 : Integer.parseInt(record.get("count").toString());
    }

    /**
     * 查询用户最后读取时间
     *
     * @param userId 用户ID
     * @return 最后读取信息
     */
    public UserLastReadTime lastReadTimeByUserId(Integer userId) {
        UserLastReadTime ult = userLastReadTimeService.findFirstBy("user_id", Record.create().set("userId", userId));
        if (ult == null) {
            return new UserLastReadTime();
        }
        UserLastReadTime newUlt = new UserLastReadTime();
        newUlt.setAcquaintances(ult.getAcquaintances() == null ? 0 : ult.getAcquaintances());
        newUlt.setUserId(userId);
        newUlt.setGroup(ult.getGroup() == null ? 0 : ult.getGroup());
        newUlt.setIntereste(ult.getIntereste() == null ? 0 : ult.getIntereste());
        newUlt.setInterested(ult.getInterested() == null ? 0 : ult.getInterested());
        newUlt.setId(ult.getId());
        newUlt.setNewTime(ult.getNewTime() == null ? 0 : ult.getNewTime());
        return newUlt;
    }

    /**
     * 更新用户最后读取时间
     *
     * @param urt 最后读取信息
     */
    public void updateUserLastReadTime(Integer userId, UserLastReadTime urt) {
        urt.setUserId(userId);
        if (urt.getId() == null) {
            // insert
            userLastReadTimeService.save(urt);
        } else {
            // update
            userLastReadTimeService.update(urt);
        }
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

        UserLastReadTime ult = lastReadTimeByUserId(userId);
        ult.setNewTime(DateTimeKit.nowLong());
        this.updateUserLastReadTime(userId, ult);



        Map<String, List<UserVo>> resultMap = new HashMap<>();
        resultMap.put("list0", new ArrayList<UserVo>()); // 自荐
        resultMap.put("list1", new ArrayList<UserVo>()); // 引荐
        resultMap.put("list2", new ArrayList<UserVo>()); // 推荐

        // 查询引荐和推荐
        List<Record> list0 = listNew0(userId);
        // 查询自荐
        List<Record> list1 = listNew1(userId);
        listAdd2Map(list0, resultMap,userId, false);
        listAdd2Map(list1, resultMap,userId, true);
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
    private void listAdd2Map(List<Record> list, Map<String, List<UserVo>> resultMap, Integer userId, boolean isIntroduce) {
        List<UserVo> voList = new ArrayList<>();
        List<UserVo> voList2 = null;
        if (isIntroduce) voList2 = new ArrayList<>();
        UserVo vo;
        for (Record record : list) {
            vo = Convert.recordToVo(record);
            vo.setValidateInfo(record.get("validate_info").toString());
            setUserVoStatus(vo, record, userId);

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

    /**
     * 设置当前用户与
     * @param vo 好友信息
     */
    private void setUserVoStatus(UserVo vo, Record record, Integer userId) {

        int status  = Integer.parseInt(record.get("status").toString());
        int type = 0;
        if (status == 1) {
            type = FRIEND;
        }
        else if (status == 2) {
            type = PASS;
        }
        else if (status == 0) {
            int fromUserId = Integer.parseInt(record.get("from_user_id").toString());
            int toUserId = Integer.parseInt(record.get("to_user_id").toString());
            if (userId == fromUserId) {
                type = NOT_PROCESS_TO_USER_ID;
            }
            else if (userId == toUserId) {
                type = NOT_PROCESS_FROM_USER_ID;
            }
        }
        vo.setStatus(type);
    }

    /**
     * 我感兴趣的组织和用户
     *
     * @param userId 用户ID
     * @param date   最后读取时间
     * @return 数量
     */
    @Override
    public int countIntereste(Integer userId, Long date) {
        Record _r = Record.create().set("userId", userId).set("lastTime", date);
        Record _userRecord = Db.init().selectOne(INTEREST_USER_COUNT_SQL, _r);
        Record _groupRecord = Db.init().selectOne(INTEREST_GROUP_COUNT_SQL, _r);
        int userCount = _userRecord.get("count") == null ? 0 : Integer.parseInt(_userRecord.get("count").toString());
        int groupCount = _groupRecord.get("count") == null ? 0 : Integer.parseInt(_groupRecord.get("count").toString());
        return (userCount + groupCount);
    }

    /**
     * 感兴趣的用户和组织
     *
     * @param userId 用户ID
     * @return 感兴趣的用户和组织集合
     */
    @Override
    public Map<String, Object> listIntereste(Integer userId) {

        UserLastReadTime ult = lastReadTimeByUserId(userId);
        ult.setIntereste(DateTimeKit.nowLong());
        this.updateUserLastReadTime(userId, ult);

        Record record = Record.create().set("userId", userId);
        List<Record> groupList = Db.init().selectList(SQL_INTEREST_GROUP, record);
        List<Record> userList = Db.init().selectList(SQL_INTEREST_USER, record);

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
        Record _r = Record.create().set("userId", userId).set("lastTime", date);
        Record _userRecord = Db.init().selectOne(INTERESTD_USER_COUNT_SQL, _r);
        return _userRecord.get("count") == null ? 0 : Integer.parseInt(_userRecord.get("count").toString());
    }

    /**
     * 对我感兴趣的用户集合
     *
     * @param userId 用户ID
     * @return 对我感兴趣的用户集合
     */
    @Override
    public List<UserVo> listInterested(Integer userId) {
        UserLastReadTime ult = lastReadTimeByUserId(userId);
        ult.setInterested(DateTimeKit.nowLong());
        this.updateUserLastReadTime(userId, ult);

        int type;
        List<Record> list = Db.init().selectList(SQL_INTERESTED_USER, Record.create().set("userId", userId));
        List<UserVo> voList = new ArrayList<>();
        for (Record record : list) {
            UserVo vo = Convert.recordToVo(record);
            Object unstatusObj = record.get("uatatus");
            if (unstatusObj == null || !unstatusObj.toString().equals("1")) {
                // 未结识
                type = NOT_PROCESS_FROM_USER_ID;
            }
            else {
                // 已结识
                type = FRIEND;
            }
            vo.setStatus(type);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public int countAcquaintances(Integer userId, Long date) {
        Record _r = Record.create().set("userId", userId).set("lastTime", date);
        Record _userRecord = Db.init().selectOne(ACQUAINTANCES_COUNT_SQL, _r);
        return _userRecord.get("count") == null ? 0 : Integer.parseInt(_userRecord.get("count").toString());
    }

    @Override
    public List<UserVo> listAcquaintances(Integer userId) {

        UserLastReadTime ult = lastReadTimeByUserId(userId);
        ult.setAcquaintances(DateTimeKit.nowLong());
        this.updateUserLastReadTime(userId, ult);

        String sql2 = sql + " on (ui.`user_id` = ua.`from_user_id` OR ui.`user_id` = ua.`to_user_id` ) ";
        String where = "ui.`user_id` != #{userId} and ua.type = 2 and ( ua.status = 0 or ua.status = 1)";
        List<Record> list = Db.init().selectList(sql2, where, Record.create().set("userId", userId));
        List<UserVo> voList = new ArrayList<>();
        UserVo vo;
        int type = 0;
        for (Record record : list) {
            vo = Convert.recordToVo(record);
            Integer fromUserId = Integer.parseInt(record.get("from_user_id").toString());
            Integer toUserId = Integer.parseInt(record.get("to_user_id").toString());
            Integer status = record.get("status") == null ? null : Integer.parseInt(record.get("status").toString());
            if (status != null && status == 0) {
                if (fromUserId == userId) {
                    // 等待对方审核
                    type = NOT_PROCESS_TO_USER_ID;
                } else if (toUserId == userId) {
                    // 等待己方审核
                    type = NOT_PROCESS_FROM_USER_ID;
                }
            } else if (status != null && status == 1) {
                type = FRIEND;
            } else if (status != null && (status == 2 || status == 4)) {
                type = PASS;
            }
            vo.setStatus(type);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public int countGroup(Integer userId, Long date) {
        Record _r = Record.create().set("userId", userId).set("lastTime", date);
        Record _userRecord = Db.init().selectOne(MYGROUP_COUNT_SQL, _r);
        return _userRecord.get("count") == null ? 0 : Integer.parseInt(_userRecord.get("count").toString());
    }

    /**
     * 查询我的组织
     *
     * @param userId 用户ID
     * @return 组织集合
     */
    @Override
    public List<Groupvo> listGroup(Integer userId) {
        UserLastReadTime ult = lastReadTimeByUserId(userId);
        ult.setGroup(DateTimeKit.nowLong());
        this.updateUserLastReadTime(userId, ult);

        String sql = "SELECT\n" +
                "    g.id,\n" +
                "    g.name,\n" +
                "    g.avater,\n" +
                "    g.province,\n" +
                "    g.city,\n" +
                "    g.type,\n" +
                "    g.province_city provinceCity,\n" +
                "    g.member_count memberCount,\n" +
                "    g.targat,\n" +
                "    ga.user_id userId,\n" +
                "    ga.status\n" +
                "FROM\n" +
                "    tb_group g\n" +
                "RIGHT JOIN\n" +
                "    tb_group_approval ga\n" +
                "ON\n" +
                "    (g.id = ga.group_id)" +
                " WHERE ga.user_id = #{userId}";

        List<Record> list = Db.init().selectList(sql, Record.create().set("userId", userId));
        List<Groupvo> groupvos = new ArrayList<>();

        for (Record record : list) {
            groupvos.add(Convert.recordToGroupVo(record));
        }
        return groupvos;
    }
}