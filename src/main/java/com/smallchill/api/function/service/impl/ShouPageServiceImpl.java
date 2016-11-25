package com.smallchill.api.function.service.impl;

import com.smallchill.api.function.meta.other.Convert;
import com.smallchill.api.function.modal.UserLastReadTime;
import com.smallchill.api.function.modal.vo.Groupvo;
import com.smallchill.api.function.modal.vo.ShouPageVo;
import com.smallchill.api.function.modal.vo.UserVo;
import com.smallchill.api.function.service.ShoupageService;
import com.smallchill.api.function.service.UserLastReadTimeService;
import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.modules.support.Conver;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.kit.CacheKit;
import com.smallchill.core.toolbox.kit.DateKit;
import com.smallchill.core.toolbox.kit.DateTimeKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.web.service.GroupService;
import com.smallchill.web.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 手页录api
 * Created by yesong on 2016/11/1 0001.
 */
@Service
public class ShouPageServiceImpl implements ShoupageService, ConstCache {

    @Autowired
    private UserLastReadTimeService userLastReadTimeService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private GroupService groupService;

    private String USER_BASE_INFO_SQL = " ui.user_id userId,ui.username,IFNULL(ui.avater,'') as avater ,ui.province_city provinceCity,ui.domain,ui.key_word keyWord,ui.organization,ui.professional,ui.per ";

    private String sql = "select" + USER_BASE_INFO_SQL + " ,ua.validate_info,ua.introduce_user_id,ua.status,ua.from_user_id,ua.to_user_id " +
            "from tb_user_approval ua join tb_user_info ui";

    private String SQL_INTEREST_USER = "select" + USER_BASE_INFO_SQL + " from tb_interest_user i join tb_user_info ui on i.user_id = ui.user_id where i.user_id = #{userId}";

    private String GROUP_BASE_INFO_SQL = " g.id,g.name,IFNULL(g.avater,'') as avater,g.province,g.city,g.type,g.province_city provinceCity,g.member_count memberCount,g.targat ";

    private String SQL_INTEREST_GROUP = "select " + GROUP_BASE_INFO_SQL + " from tb_interest_group i join tb_group g on i.group_id = g.id where i.user_id = #{userId}";

    private String SQL_INTERESTED_USER = "select " + USER_BASE_INFO_SQL + " ,ua.status status,i.status istatus from tb_interest_user i join tb_user_info ui on i.user_id = ui.user_id "
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
    private int NOT_PROCESS_TO_USER_ID = 2003; // 等待对方确认
    private int PASS = 2004;                   // 已忽略


    /**
     * 手页录首页
     *
     * @param userId 当前用户ID
     * @return 首页录vo
     */
    @Override
    public ShouPageVo index(Integer userId, Integer domainId, Integer city, Integer grouping, String keyWord) {

        UserLastReadTime userLastReadTime = lastReadTimeByUserId(userId);

        if (userLastReadTime == null) {
            userLastReadTime = new UserLastReadTime();
        }
        List<UserVo> voList = friends(userId, domainId, city, grouping,keyWord);
        ShouPageVo shouPageVo = new ShouPageVo();

        shouPageVo.setNewCount(countNew(userId, userLastReadTime.getNewTime()));
        shouPageVo.setAcquaintancesCount(countAcquaintances(userId, userLastReadTime.getAcquaintances()));
        shouPageVo.setInterestCount(countIntereste(userId, userLastReadTime.getIntereste()));
        shouPageVo.setInterestedCount(countInterested(userId, userLastReadTime.getInterested()));
        shouPageVo.setGroupCount(countGroup(userId, userLastReadTime.getGroup()));
        shouPageVo.setList(voList == null || voList.size() == 0 ? new ArrayList<UserVo>() : voList);
        return shouPageVo;
    }

    @Override
    public List<UserVo> friends(Integer userId, Integer domainId, Integer city, Integer grouping,String keyWord) {
        String sql = Blade.dao().getScript("UserFriend.list").getSql();StringBuffer where = new StringBuffer("");
        Record _r = Record.create().set("userId", userId);
        if (domainId != null) {
            sql += " RIGHT JOIN tb_userinfo_domain ud ON (ui.user_id = ud.user_id AND ud.domain_id = #{domain}) ";
            _r.set("domain", domainId);
        }

        where.append(" 1 = 1");
        if (city != null) {
            where.append(" and ui.city_id = #{city} ");
            _r.set("city", city);
        }
        if (grouping != null) {
            String groupingStr = "";
            switch (grouping) {
                case 1:
                    groupingStr = "熟人";
                    break;
                case 2:
                    groupingStr = "校友";
                    break;
                case 3:
                    groupingStr = "同组织";
            }
            where.append(" and uf.label like concat('%',#{grouping},'%')");
            _r.set("grouping", groupingStr);
        }
        if (StringUtils.isNotBlank(keyWord)) {
            where.append(" and (ui.username LIKE concat('%', #{keyWord},'%') or ui.key_word LIKE concat('%', #{keyWord}, '%'))");
            _r.set("keyWord",keyWord);
        }
        where.append(" and uf.user_id = #{userId}");


        List<Record> friends = Db.init().selectList(sql, where.toString(), _r);
        List<UserVo> voList = new ArrayList<>();
        for (Record record : friends) {
            UserVo userVo = Convert.recordToVo(record);
            userVo.setCarrer(null);
            userVo.setDesc(null);
            userVo.setSameKeyList(Convert.labelToSameKeyList(record.getStr("label")));
            voList.add(userVo);
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

        // 查询当前用户所有申请请求
        List<Record> list0 = listNew0(userId);
        listAdd2Map(list0, resultMap, userId);

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
     * @param list      用户集合
     * @param resultMap 新结识最后返回数据
     */
    private void listAdd2Map(List<Record> list, Map<String, List<UserVo>> resultMap, Integer userId) {
        List<UserVo> voList0 = new ArrayList<>(); // 引荐
        List<UserVo> voList1 = new ArrayList<>(); // 自荐
        List<UserVo> voList2 = new ArrayList<>(); // 推荐
        UserVo vo;
        for (Record record : list) {
            vo = Convert.recordToVo(record);
            setUserVoStatus(vo, record, userId);
            Integer introduceUserId = record.get("introduce_user_id") == null ? 0 : Integer.parseInt(record.get("introduce_user_id").toString());
            Integer groupId = record.get("group_id") == null ? 0 : Integer.parseInt(record.get("group_id").toString());
            if (introduceUserId == 0 && groupId == 0) {
                // 自荐
                String validateInfo = record.get("username") == null ? "" : record.get("username").toString() + ":" + record.get("validate_info");
                vo.setValidateInfo(validateInfo);
                vo.setInfo("");
                voList1.add(vo);
            } else if (introduceUserId != 0 && groupId == 0) {
                // 推荐
                voList2.add(vo);
                String username = userInfoService.findUsernameByUserId(introduceUserId);
                String validateInfo = username == null ? "" : username + ":" + record.get("validate_info");
                vo.setValidateInfo(validateInfo);
                vo.setInfo("来自您的朋友" + username);
            } else {
                // 引荐
                String validateInfo = record.get("username") == null ? "" : record.get("username").toString() + ":" + record.get("validate_info");
                vo.setValidateInfo(validateInfo);
                String name = groupService.findNameById(groupId);
                vo.setInfo("已通过" + name + "审核");
                voList0.add(vo);
            }
        }
        resultMap.put("list0", voList0);
        resultMap.put("list1", voList1);
        resultMap.put("list2", voList2);
    }

    /**
     * 设置当前用户与
     *
     * @param vo 好友信息
     */
    private void setUserVoStatus(UserVo vo, Record record, Integer userId) {
        int type = NOT_FRINED;
        if (record.get("status") == null) {
            type = NOT_FRINED;
        }
        else {
            int status = Integer.parseInt(record.get("status").toString());
            if (status == 1) {
                type = FRIEND;
            } else if (status == 2) {
                type = PASS;
            } else if (status == 0) {
                int fromUserId = Integer.parseInt(record.get("from_user_id").toString());
                int toUserId = Integer.parseInt(record.get("to_user_id").toString());
                if (userId == fromUserId) {
                    type = NOT_PROCESS_TO_USER_ID;
                } else if (userId == toUserId) {
                    type = NOT_PROCESS_FROM_USER_ID;
                }
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

        List<Record> list = Db.init().selectList(SQL_INTERESTED_USER, Record.create().set("userId", userId));
        List<UserVo> voList = new ArrayList<>();
        for (Record record : list) {
            UserVo vo = Convert.recordToVo(record);
            setUserVoStatus(vo, record, userId);
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
        String where = "ui.`user_id` != #{userId} and ua.type = 2 and (ua.status = 1 or ua.status = 0)";
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
                "    ga.validate_info info,\n" +
                "    ga.through_time,\n" +
                "    ga.status\n" +
                "FROM\n" +
                "    tb_group g\n" +
                "LEFT JOIN\n" +
                "    tb_group_approval ga\n" +
                "ON\n" +
                "    (g.id = ga.group_id and ga.status = 2)" +
                " WHERE ga.user_id = #{userId}";

        List<Record> list = Db.init().selectList(sql, Record.create().set("userId", userId));
        List<Groupvo> groupvos = new ArrayList<>();
        for (Record record : list) {
            Groupvo groupvo = Convert.recordToGroupVo(record);
            groupvo.setType(record.getInt("type"));
            groupvo.setInfo(record.getStr("name") + ":" + record.getStr("info"));
            groupvo.setRemainingTime(remainingTime(record.getLong("through_time")));
            int status = record.getInt("status");
            if (status == 1) {
                groupvo.setStatus(0);
            } else {
                groupvo.setStatus(1);
            }
            groupvos.add(groupvo);
        }
        return groupvos;
    }

    public String remainingTime(long throughTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(throughTime);
        calendar.add(Calendar.YEAR, 1);
        long nextTime = calendar.getTimeInMillis();
        long currentTime = DateTimeKit.nowLong();
        long m = DateKit.getMinSub(currentTime, nextTime);
        long days = m / 24;
        long m2 = m % 24;
        return days < 30 ? days + "天" + m2 + "分" : "";
    }
}