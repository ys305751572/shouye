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

    private String USER_BASE_INFO_SQL = " ui.user_id,ui.username,ui.avater,ui.province_city,ui.domain,ui.key_word,ui.organization,ui.professional ";

    private String sql = "select" + USER_BASE_INFO_SQL + " ,ua.validate_info,ua.introduce_user_id,ua.status,ua.from_user_id,ua.to_user_id " +
            "from tb_user_approval ua join tb_user_info ui";

    private String SQL_INTEREST_USER = "select" + USER_BASE_INFO_SQL + " from tb_interest i " + "join tb_user_info ui on i.to_id = ui.user_id where i.user_id = #{userId} " +
            " and i.type = 0";

    private String SQL_INTERESTED_USER = "select "+ USER_BASE_INFO_SQL +" from tb_interest i join tb_user_info ui on i.user_id = ui.user_id where i.to_id = #{userId}";

    private String sql_interest_group = "select * from tb_interest i join tb_group g on i.to_id = g.id where i.user_id = #{userId} and i.type = 1";

    private String sql_my_group = "select * from tb_group_approval ga join tb_group g on ga.group_id = g.id where g.user_id = #{userId} and (g.status = 1 or g.status = 0)";


    private String NEW_USER_COUNT_SQL = "SELECT COUNT(*) FROM tb_user_approval ua WHERE (ua.`from_user_id` = #{userId} OR ua.`to_user_id` = #{userId}) " +
            "AND (ua.`status` = 1 OR ua.`create_time` > #{lastTime})";

    // 感兴趣的用户
    private String INTEREST_USER_COUNT_SQL = "SELECT COUNT(*) FROM tb_interest_user iu WHERE iu.create_time > #{lastTime} AND iu.user_id = #{userId}";
    // 感兴趣的组织
    private String INTEREST_GROUP_COUNT_SQL = "SELECT COUNT(*) FROM tb_interest_group iu WHERE iu.create_time > #{lastTime} AND iu.user_id = #{userId}";

    // 被谁感兴趣
    private String INTERESTD_USER_COUNT_SQL = "SELECT COUNT(*) FROM tb_interest_user iu WHERE iu.create_time > #{lastTime} AND iu.to_user_id = #{userId}";

    // 我的熟人
    private String ACQUAINTANCES_COUNT_SQL = "SELECT COUNT(*) FROM tb_user_approval ua WHERE (ua.from_user_id = #{userId} OR ua.to_user_id = #{userId}) AND (ua.create_time > #{lastTime})";

    // 我的组织
    private String MYGROUP_COUNT_SQL = "SELECT COUNT(*) FROM tb_group_approval ga WHERE ga.user_id = #{userId} AND ga.create_time > #{lastTime}";

    /**
     * 手页录首页
     * @param userId 当前用户ID
     * @return 首页录vo
     */
    @Override
    public ShouPageVo index(Integer userId) {

        UserLastReadTime userLastReadTime = userLastReadTimeService.findFirstBy("user_id", Record.create().set("userId", userId));

        if(userLastReadTime == null) {
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
        Record record = Db.init().selectOne(NEW_USER_COUNT_SQL,_r);
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

    /**
     * 我感兴趣的组织和用户
     * @param userId 用户ID
     * @param date 最后读取时间
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
        Record record = Record.create().set("userId", userId);
        List<Record> groupList = Db.init().selectList(sql_interest_group, record);
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
        List<Record> list = Db.init().selectList(SQL_INTERESTED_USER, Record.create().set("userId", userId));
        List<UserVo> voList = new ArrayList<>();
        for (Record record : list) {
            voList.add(Convert.recordToVo(record));
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
        List<Record> list = Db.init().selectList(sql_my_group, Record.create().set("userId", userId));
        List<Groupvo> groupvos = new ArrayList<>();
        for (Record record : list) {
            groupvos.add(Convert.recordToGroupVo(record));
        }
        return groupvos;
    }
}