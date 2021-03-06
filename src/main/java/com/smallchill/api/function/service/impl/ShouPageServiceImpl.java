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
import com.smallchill.core.toolbox.kit.*;
import com.smallchill.web.model.UserInfo;
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

    private static String USER_BASE_INFO_SQL = " ui.user_id userId,ui.username,IFNULL(ui.avater,'') as avater ,ui.province_city provinceCity,ui.domain,ui.key_word keyWord,ui.organization,ui.professional,ui.per ";

    private String sql = "select" + USER_BASE_INFO_SQL + " ,ua.validate_info,ua.introduce_user_id,ua.introduce_user_id_process,ua.group_id, ua.friend_id,ua.status,ua.from_user_id,ua.to_user_id " +
            "from tb_user_approval ua join tb_user_info ui";

    public static String SQL_INTEREST_USER = "select" + USER_BASE_INFO_SQL + " from tb_interest_user i join tb_user_info ui on i.to_user_id = ui.user_id " +
            "left join tb_user_approval ua on \n" +
            "(ua.from_user_id = i.`to_user_id` and ua.to_user_id = #{userId}) or (ua.from_user_id = #{userId} and ua.to_user_id = i.`to_user_id`) \n" +
            "and ua.`status` != 2 \n" +
            "WHERE i.user_id = #{userId} AND i.status = 0 and ua.`status` IS NOT NULL GROUP BY ui.`user_id`";

    private String GROUP_BASE_INFO_SQL = " g.id,g.name,IFNULL(g.avater,'') as avater,g.province,g.city,g.type,g.province_city provinceCity,g.member_count memberCount,g.targat ";

    private String SQL_INTEREST_GROUP = "select " + GROUP_BASE_INFO_SQL + ",ga.status from tb_interest_group i join tb_group g on i.group_id = g.id LEFT JOIN tb_group_approval ga ON i.`group_id` = ga.`group_id` AND ga.`user_id` = #{userId} where i.user_id = #{userId} and i.status = 0";

    public static String SQL_INTERESTED_USER = "select " + USER_BASE_INFO_SQL + " ,ua.status status,i.status istatus from tb_interest_user i left join tb_user_info ui on i.user_id = ui.user_id "
            + " LEFT JOIN tb_user_approval ua ON (ua.`from_user_id` = ui.`user_id`  AND ua.to_user_id = #{userId}) OR (ua.`from_user_id` = #{userId} \n" +
            " AND ua.`to_user_id` = ui.`user_id` ) where i.to_user_id = #{userId} and (ua.`status` != 4 OR ua.`status` IS NULL) AND (ua.`status` != 2 OR ua.`status` IS NULL) group by i.user_id";

    private String SQL_MY_GROUP = "select * from tb_group_approval ga join tb_group g on ga.group_id = g.id where g.user_id = #{userId} and (g.status = 1 or g.status = 0)";

    private String NEW_USER_COUNT_SQL = "SELECT COUNT(*) as count FROM tb_user_approval ua WHERE (ua.`to_user_id` = #{userId}) " +
            "AND (ua.`status` = 1 OR ua.`create_time` > #{lastTime}) AND ua.type = 1";
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

        ShouPageVo shouPageVo = new ShouPageVo();
        List<UserVo> voList = friends(userId, domainId, city, grouping, keyWord, shouPageVo);

        shouPageVo.setNewCount(countNew(userId, userLastReadTime.getNewTime()));
        shouPageVo.setAcquaintancesCount(countAcquaintances(userId, userLastReadTime.getAcquaintances()));
        shouPageVo.setInterestCount(countIntereste(userId, userLastReadTime.getIntereste()));
        shouPageVo.setInterestedCount(countInterested(userId, userLastReadTime.getInterested()));
        shouPageVo.setGroupCount(countGroup(userId, userLastReadTime.getGroup()));
        shouPageVo.setList(voList == null || voList.size() == 0 ? new ArrayList<UserVo>() : voList);
        return shouPageVo;
    }

    @Override
    public List<UserVo> friends(Integer userId, Integer domainId, Integer city, Integer grouping, String keyWord, ShouPageVo shouPageVo) {
        String sql = Blade.dao().getScript("UserFriend.list").getSql();
        StringBuffer where = new StringBuffer("");
        Record _r = Record.create().set("userId", userId);
        if (domainId != null) {
            sql += " RIGHT JOIN tb_userinfo_domain ud ON (ui.user_id = ud.user_id AND ud.domain_id = #{domain}) ";
            _r.set("domain", domainId);
        }
        if (grouping != null && grouping > 3) {
            where.append(" right join tb_user_friend_grouping_member ufgm on ui.user_id = ufgm.friend_id and ufgm.ufg_id = #{ufgId}");
            _r.set("ufgId", grouping);
        }
        where.append(" where 1 = 1");
        if (city != null) {
            where.append(" and ui.city_id = #{city} ");
            _r.set("city", city);
        }
        if (StringUtils.isNotBlank(keyWord)) {
            where.append(" and (ui.username LIKE concat('%', #{keyWord},'%') or ui.key_word LIKE concat('%', #{keyWord}, '%'))");
            _r.set("keyWord", keyWord);
        }
        where.append(" and uf.user_id = #{userId}");

        List<Record> friends = Db.init().selectList(sql + where.toString(), _r);
        List<Record> includeRecordList = lables(friends, userId, shouPageVo, grouping);
        if (grouping != null && grouping <= 3) {
            friends = includeRecordList;
        }
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

    /**
     * 设置标签
     *
     * @param friends
     */
    public List<Record> lables(List<Record> friends, Integer userId, ShouPageVo shouPageVo, Integer grouping) {
        List<UserVo> acqList = listAcquaintances(userId, null);
        List<Integer> sameGroupUserList = findSameGroupUserId(userId, friends);
        UserInfo userInfo = userInfoService.findByUserId(userId);

        List<Record> includeRecordList = new ArrayList<>();
        int acqCount = 0;
        int schoolCount = 0;
        int groupCount = 0;
        for (Record record : friends) {
            // 判断是否同组织
            // 判断是否同校
            // 判断是否熟人
            if (isSameGroup(record.getInt("usereId"), sameGroupUserList)) {
                ++groupCount;
                if (StringUtils.isNotBlank(record.getStr("label"))) {
                    record.set("label", record.getStr("label") + "|同组织");
                } else {
                    record.set("label", "同组织");
                }
                if (grouping != null && grouping == 3) {
                    includeRecordList.add(record);
                }
            }
            if (isSameSchool(userInfo.getSchool(), record.getStr("school"))) {
                ++schoolCount;
                if (StringUtils.isNotBlank(record.getStr("label"))) {
                    record.set("label", record.getStr("label") + "|同校");
                } else {
                    record.set("label", "同校");
                }
                if (grouping != null && grouping == 2) {
                    includeRecordList.add(record);
                }
            }
            if (isAcq(record.getInt("userId"), acqList)) {
                ++acqCount;
                if (StringUtils.isNotBlank(record.getStr("label"))) {
                    record.set("label", record.getStr("label") + "|熟人");
                } else {
                    record.set("label", "熟人");
                }
                if (grouping != null && grouping == 1) {
                    includeRecordList.add(record);
                }
            }
        }

        List<Record> recordList = new ArrayList<Record>();
        recordList.add(Record.create().set("name", "熟人").set("counts", acqCount));
        recordList.add(Record.create().set("name", "同校").set("counts", schoolCount));
        recordList.add(Record.create().set("name", "同组织").set("counts", groupCount));
        if (shouPageVo != null) {
            shouPageVo.setGroupings(recordList);
        }
        return includeRecordList;
    }

    public boolean isSameGroup(int userId, List<Integer> sameGroupUserList) {
        return CollectionKit.isNotEmpty(sameGroupUserList) && sameGroupUserList.contains(userId);
    }

    public boolean isSameSchool(String userSchool, String friendSchool) {
        if (StringUtils.isBlank(userSchool)) return false;
        if (StringUtils.isBlank(friendSchool)) return false;
        String[] userSchools = userSchool.split("\\|");
        String[] friendSchools = friendSchool.split("\\|");
        boolean flag = false;
        for (String uString : userSchools) {
            for (String fSchool : friendSchools) {
                if (StringUtils.isNotBlank(uString) && StringUtils.isNotBlank(fSchool) && uString.equals(fSchool)) {
                    flag = true;
                }
            }
        }
        return StringUtils.isNotBlank(userSchool) && StringUtils.isNotBlank(friendSchool) && flag;
    }

    public boolean isAcq(int userId, List<UserVo> acqlist) {
        return CollectionKit.isNotEmpty(acqlist) && containsAcq(userId, acqlist);
    }

    public boolean containsAcq(int userId, List<UserVo> acqlist) {
        for (UserVo userVo : acqlist) {
            if (userId == userVo.getUserId())
                return true;
        }
        return false;
    }

    /**
     * 查询与当前用户在同一组织的用户
     *
     * @param userId
     * @return
     */
    public List<Integer> findSameGroupUserId(Integer userId, List<Record> friends) {
        String sql = "SELECT ug.`user_id` FROM tb_user_group ug \n" +
                "WHERE ug.user_id IN (#{userIds}) AND ug.`group_id` \n" +
                "IN (SELECT ug2.`group_id` FROM tb_user_group ug2 WHERE ug2.`user_id` = #{userId})";
        StringBuffer buffer = new StringBuffer();
        if (CollectionKit.isEmpty(friends)) {
            return new ArrayList<>();
        }
        for (Record record : friends) {
            buffer.append(record.getInt("userId")).append(",");
        }
        List<Integer> userIds = new ArrayList<>();
        List<Record> recordList = Db.init().selectList(sql, Record.create().set("userIds", buffer.substring(0, buffer.length() - 1)).set("userId", userId));
        for (Record record : recordList) {
            userIds.add(record.getInt("userId"));
        }
        return userIds;
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
    @Override
    public UserLastReadTime lastReadTimeByUserId(Integer userId) {
        UserLastReadTime ult = userLastReadTimeService.findFirstBy("user_id = #{userId}", Record.create().set("userId", userId));
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
        newUlt.setFeekback(ult.getFeekback() == null ? 0 : ult.getFeekback());
        return newUlt;
    }

    /**
     * 更新用户最后读取时间
     *
     * @param urt 最后读取信息
     */
    @Override
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
        resultMap.put("list0", new ArrayList<UserVo>()); // 引荐
        resultMap.put("list1", new ArrayList<UserVo>()); // 自荐
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
        String where = " on ui.user_id = ua.from_user_id where ua.to_user_id = #{userId} and ua.type = 1";
        List<Record> list =  Db.init().selectList(sql + where, Record.create().set("userId", userId));
        List<Record> list2 = listNew1(userId);
        if (CollectionKit.isNotEmpty(list2)) {
            list.addAll(list2);
        }
        return list;
    }

    private List<Record> listNew1(Integer userId) {
        String where = " on ui.user_id = ua.to_user_id where ua.from_user_id = #{userId} and ua.type = 1 and ua.introduce_user_id != 0";
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
            Convert.setUserVoStatus(vo, record, userId);
            Integer introduceUserId = record.get("introduce_user_id") == null ? 0 : Integer.parseInt(record.get("introduce_user_id").toString());
            Integer groupId = record.get("group_id") == null ? 0 : Integer.parseInt(record.get("group_id").toString());
            if (introduceUserId == 0 && groupId == 0) {
                // 自荐
                String validateInfo = record.get("username") == null ? "" : record.get("username").toString() + ":" + record.get("validate_info");
                vo.setValidateInfo(validateInfo);
                vo.setInfo("来自共同熟人引荐/扫码结识/多人结识");
                vo.setOrganization(record.getStr("username"));
                voList1.add(vo);
            } else if (introduceUserId != 0 && groupId == 0) {
                // 推荐
                voList2.add(vo);
                String username = userInfoService.findUsernameByUserId(introduceUserId);
                String validateInfo = username == null ? "" : username + ":" + record.get("validate_info");
                vo.setValidateInfo(validateInfo);
                vo.setInfo("来自您的朋友" + username);
                vo.setOrganization(username);
            } else {
                // 引荐
                String validateInfo = record.get("username") == null ? "" : record.get("username").toString() + ":" + record.get("validate_info");
                vo.setValidateInfo(validateInfo);
                String name = groupService.findNameById(groupId);
                vo.setInfo("已通过" + name + "审核");
                vo.setOrganization(name);
                voList0.add(vo);
            }
        }
        resultMap.put("list0", voList0);
        resultMap.put("list1", voList1);
        resultMap.put("list2", voList2);
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
            UserVo userVo = Convert.recordToVo(user);
            String username = Convert.hiddenRealUsername(user.getStr("username"));
            userVo.setUsername(username);
            uservos.add(userVo);
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
            Convert.setUserVoStatus(vo, record, userId);
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
    public List<UserVo> listAcquaintances(Integer userId, Integer backupUserId) {

        if (backupUserId == null) {
            UserLastReadTime ult = lastReadTimeByUserId(userId);
            ult.setAcquaintances(DateTimeKit.nowLong());
            this.updateUserLastReadTime(userId, ult);
        }

        String sql2 = sql + " on (ui.`user_id` = ua.`from_user_id` OR ui.`user_id` = ua.`to_user_id` ) AND (ua.`from_user_id` = #{userId} OR ua.`to_user_id` = #{userId}) ";
        String where = "ui.`user_id` != #{userId} and ua.type = 2 and (ua.status = 2 or ua.status = 1) ORDER BY ua.status,ua.create_time desc";
        List<Record> list = Db.init().selectList(sql2, where, Record.create().set("userId", userId));
        List<UserVo> voList = new ArrayList<>();
        UserVo vo;
        for (Record record : list) {
            if (record.getInt("from_user_id") == userId && record.getInt("status") == 1) {
                continue;
            }
            vo = Convert.recordToVo(record);
            Convert.setUserVoStatus(vo, record, userId);
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
                "   (select count(*) from tb_user_group ug where ug.group_id = g.id) memberCount,\n" +
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
                "    (g.id = ga.group_id AND (ga.`status` = 2 OR (ga.`status` = 1 AND ga.`type` = 2)))" +
                " WHERE ga.user_id = #{userId} order by ga.type desc, ga.create_time desc";

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