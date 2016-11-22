package com.smallchill.web.service.impl;

import com.smallchill.api.common.exception.UserExitsException;
import com.smallchill.api.function.meta.other.ButtonRegister;
import com.smallchill.api.function.meta.other.Convert;
import com.smallchill.api.function.modal.*;
import com.smallchill.api.function.modal.vo.UserVo;
import com.smallchill.api.function.service.*;
import com.smallchill.core.base.service.BaseService;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.kit.DateTimeKit;
import com.smallchill.core.toolbox.kit.NetKit;
import com.smallchill.platform.model.UserLogin;
import com.smallchill.platform.service.UserLoginService;
import com.smallchill.web.model.UserApproval;
import com.smallchill.web.model.UserInfo;
import com.smallchill.web.service.UserApprovalService;
import com.smallchill.web.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.beetl.sql.core.kit.StringKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * Generated by Blade.
 * 2016-10-18 09:47:31
 */
@Service
public class UserInfoServiceImpl extends BaseService<UserInfo> implements UserInfoService {

    @Autowired
    private UserDomainService userDomainService;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserprofessionalService userprofessionalService;

    @Autowired
    private UserApprovalService userApprovalService;

    @Autowired
    private UserInterestService userInterestService;

    @Autowired
    private GroupUserRecordService groupUserRecordService;

    @Autowired
    private UserFriendGroupingService userFriendGroupingService;
    @Autowired
    private UfgmService ufgmService;

    @Transactional
    @Override
    public UserInfo updateUserInfo(UserInfo userInfo, HttpServletRequest request) throws UserExitsException {

        String domain = userInfo.getDomain();
        String pro = userInfo.getProfessional();
        int per = per(userInfo);
        userInfo.setPer(per);
        domainAndProfessional(userInfo);
        if (userInfo.getUserId() != null) {
            _update(userInfo);
        } else {
            if (userLoginService.userIfExtis(userInfo.getMobile())) {
                throw new UserExitsException();
            }
            // 新建userlogin信息
            int userId = createUserLogin(userInfo, request);
            userInfo.setUserId(userId);
            _save(userInfo);
        }
        userInfo.setDomain(domain);
        userInfo.setProfessional(pro);
        processUserInfo(userInfo);
        return userInfo;
    }

    /**
     * 查询用户详细信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public UserInfo findByUserId(int userId) {
        return this.findFirstBy("where user_id = #{userId}", Record.create().set("userId", userId));
    }

    @Override
    public List<Record> findByParmas(Record params) {
        String sql = Blade.dao().getScript("UserInfo.listPage").getSql();
        return Db.init().selectList(sql, params);
    }

    @Override
    public Record findUserInfoDetail(Integer userId) {
        String sql = Blade.dao().getScript("UserInfo.userInfoDetail").getSql();
        return Db.init().selectOne(sql, Record.create().set("userId", userId));
    }

    @Override
    public UserVo findUserInfoDetail(Integer userId, Integer toUserId, Integer groupId) {
        String sql = Blade.dao().getScript("UserInfo.userInfoDetail").getSql();
        Record record = Db.init().selectOne(sql, Record.create().set("userId", toUserId));

        UserVo vo = Convert.recordToVo(record);
        UserApproval ua = userApprovalService.getUserByFromUserIdAndToUserIdApprovalOfOneWay(userId, toUserId);
        UserInterest ui = userInterestService.getByUserId(userId, toUserId);
        List<Button> list = ButtonRegister.create(userId, toUserId, ua, ui).addBtns();
        List<String> sameKeyList = new ArrayList<>();
        vo.setBtnList(list);
        vo.setSameKeyList(sameKeyList);

        if (groupId != null) {
            saveGroupUserRecord(userId, toUserId, groupId);
        }
        return vo;
    }

    /**
     * 保存组织用户查询历史记录
     *
     * @param toUserId 被查询用户ID
     * @param groupId  组织ID
     */
    private void saveGroupUserRecord(Integer userId, Integer toUserId, Integer groupId) {
        GroupUserRecord gur = new GroupUserRecord();
        gur.setGroupId(groupId);
        gur.setUserId(userId);
        gur.setToUserId(toUserId);
        gur.setCreateTime(DateTimeKit.nowLong());
        groupUserRecordService.save(gur);
    }

    /**
     * 根据ID查询用户名
     *
     * @param userId 用户ID
     * @return 用户名
     */
    @Override
    public String findUsernameByUserId(Integer userId) {
        String sql = "select username from user_info where user_id = #{userId}";
        UserInfo userInfo = this.findFirst(sql, Record.create().set("userId", userId));
        return userInfo.getUsername();
    }


    /**
     * 创建用户
     *
     * @param userInfo 用户详细信息
     */
    private int createUserLogin(UserInfo userInfo, HttpServletRequest request) {
        UserLogin userLogin = new UserLogin();
        String ip = NetKit.getRealIp(request);
        userLogin.setLastLoginIp(ip);
        userLogin.setLastLoginTime(DateTimeKit.nowLong());
        userLogin.setUsername(userInfo.getMobile());
        userLogin.setCreateTime(DateTimeKit.nowLong());
        return userLoginService.saveRtId(userLogin);
    }

    /**
     * 处理userinfo
     * 处理行业/职业关联关系
     *
     * @param userInfo 用户详细信息
     */
    private void processUserInfo(UserInfo userInfo) {
        String domain = userInfo.getDomain();
        String professional = userInfo.getProfessional();

        // 处理行业（领域）
        if (StringUtils.isNotBlank(domain)) {
            // 先删除该用户所有领域信息
            userDomainService.deleteBy("user_id = #{userId}", Record.create().set("userId", userInfo.getUserId()));
            String[] domains = domain.split("\\|");
            for (String c : domains) {
                if (StringUtils.isNotBlank(c)) {
                    String[] ss = c.split(",");
                    int domainId = Integer.parseInt(ss[0]);
                    int pid = Integer.parseInt(ss[1]);
                    String name = ss[2];

                    UserDomain ud = new UserDomain();
                    ud.setUserId(userInfo.getUserId());
                    ud.setDomainId(domainId);
                    ud.setPid(pid);
                    ud.setName(name);
                    userDomainService.save(ud);
                }
            }
        }

        // ---------------------- 处理专业 -------------------------
        if (StringUtils.isNotBlank(professional)) {
            // 先删除该用户所有专业信息
            userprofessionalService.deleteBy("user_id = #{userId}", Record.create().set("userId", userInfo.getUserId()));
            String[] professionals = professional.split("\\|");
            for (String c : professionals) {
                if (StringUtils.isNotBlank(c)) {
                    String[] ss = c.split(",");
                    int proId = Integer.parseInt(ss[0]);
                    int pid = Integer.parseInt(ss[1]);
                    String name = ss[2];

                    UserProfessional up = new UserProfessional();
                    up.setUserId(userInfo.getUserId());
                    up.setProId(proId);
                    up.setProName(name);
                    up.setPid(pid);
                    userprofessionalService.save(up);
                }
            }
        }
    }

    /**
     * 新增
     *
     * @param userInfo 用户详细信息
     */
    private void _save(UserInfo userInfo) {
        this.save(userInfo);
    }

    private void domainAndProfessional(UserInfo userInfo) {
        String domain = userInfo.getDomain();
        String domainName = null;
        if (StringUtils.isNotBlank(domain)) {
            String[] domains = domain.split("\\|");
            StringBuffer buffer = new StringBuffer();
            for (String c : domains) {
                if (StringUtils.isNotBlank(c)) {
                    String[] ss = c.split(",");
                    String name = ss[2];
                    buffer.append(name).append("/");
                }
            }
            domainName = buffer.substring(0, buffer.length() - 1);
        }

        String professional = userInfo.getProfessional();
        String professionalName = null;
        // ---------------------- 处理专业-------------------------
        if (StringUtils.isNotBlank(professional)) {
            String[] professionals = professional.split("\\|");
            StringBuffer professionalBuffer = new StringBuffer();
            for (String c : professionals) {
                if (StringUtils.isNotBlank(c)) {
                    String[] ss = c.split(",");
                    String name = ss[2];
                    professionalBuffer.append(name).append("/");
                }
            }
            professionalName = professionalBuffer.substring(0, professionalBuffer.length() - 1);
        }
        userInfo.setDomain(domainName);
        userInfo.setProfessional(professionalName);
        userInfo.setCreateTime(DateTimeKit.nowLong());
    }

    /**
     * 计算用户资料完善百分比
     *
     * @return 用户资料完善百分比
     */
    private int per(UserInfo userinfo) {
        int per = 0;
        if (StringUtils.isNotBlank(userinfo.getUsername())) {
            per += 5;
        }
        if (StringUtils.isNotBlank(userinfo.getAvater())) {
            per += 5;
        }
        if (userinfo.getAgeIntervalId() != null) {
            per += 5;
        }
        if (userinfo.getGender() != null) {
            per += 5;
        }
        if (userinfo.getProvinceId() != null && userinfo.getCityId() != null) {
            per += 5;
        }
        if (StringUtils.isNotBlank(userinfo.getSchool())) {
            per += 5;
        }
        if (StringUtils.isNotBlank(userinfo.getMobile())) {
            per += 5;
        }
        if (StringUtils.isNotBlank(userinfo.getCareer())) {
            per += 5;
        }
        if (StringUtils.isNotBlank(userinfo.getDomain())) {
            per += 5;
        }
        if (StringUtils.isNotBlank(userinfo.getOrganization())) {
            per += 5;
        }
        if (StringUtils.isNotBlank(userinfo.getProductService()) && userinfo.getProductType() != null) {
            per += 5;
        }
        if (StringUtils.isNotBlank(userinfo.getIndustryRanking())) {
            per += 5;
        }
        if (StringUtils.isNotBlank(userinfo.getQualification())) {
            per += 5;
        }
        if (StringUtils.isNotBlank(userinfo.getProfessional())) {
            per += 5;
        }
        if (StringUtils.isNotBlank(userinfo.getProfessionalLevel())) {
            per += 5;
        }
        if (StringUtils.isNotBlank(userinfo.getZy()) || StringUtils.isNotBlank(userinfo.getSc())
                || StringUtils.isNotBlank(userinfo.getZl()) || StringUtils.isNotBlank(userinfo.getZy2())) {
            per += 5;
        }
        if (StringUtils.isNotBlank(userinfo.getKeyWord())) {
            per += 10;
        }
        if (StringUtils.isNotBlank(userinfo.getDesc())) {
            per += 10;
        }
        return per;
    }

    /**
     * 编辑
     *
     * @param userinfo 用户详细信息
     */
    private void _update(UserInfo userinfo) {
        UserInfo _info = this.findByUserId(userinfo.getUserId());
        if (StringUtils.isNotBlank(userinfo.getUsername())) {
            _info.setUsername(userinfo.getUsername());
        }
        if (userinfo.getAgeIntervalId() != null) {
            _info.setAgeIntervalId(userinfo.getAgeIntervalId());
            _info.setAge(userinfo.getAge());
        }
        if (userinfo.getGender() != null) {
            _info.setGender(userinfo.getGender());
        }
        if (userinfo.getProvinceId() != null && userinfo.getCityId() != null) {
            _info.setProvinceId(userinfo.getProvinceId());
            _info.setCityId(userinfo.getCityId());
            _info.setProvinceCity(userinfo.getProvinceCity());
        }
        if (StringUtils.isNotBlank(userinfo.getSchool())) {
            _info.setSchool(userinfo.getSchool());
        }
        if (StringUtils.isNotBlank(userinfo.getMobile())) {

        }
        if (StringUtils.isNotBlank(userinfo.getCareer())) {
            _info.setCareer(userinfo.getCareer());
        }
        if (StringUtils.isNotBlank(userinfo.getDomain())) {
            _info.setDomain(userinfo.getDomain());
        }
        if (userinfo.getOrgType() != null && StringUtils.isNotBlank(userinfo.getOrganization())) {
            _info.setOrgType(userinfo.getOrgType());
            _info.setOrganization(userinfo.getOrganization());
        }
        if (StringUtils.isNotBlank(userinfo.getProductService()) && userinfo.getProductType() != null) {
            _info.setProductType(userinfo.getProductType());
            _info.setProductService(userinfo.getProductService());
        }
        if (StringUtils.isNotBlank(userinfo.getIndustryRanking())) {
            _info.setIndustryRanking(userinfo.getIndustryRanking());
        }
        if (StringUtils.isNotBlank(userinfo.getQualification())) {
            _info.setQualification(userinfo.getQualification());
        }
        if (StringUtils.isNotBlank(userinfo.getProfessional())) {
            _info.setProfessional(userinfo.getProfessional());
        }
        if (StringUtils.isNotBlank(userinfo.getZy()) && StringUtils.isNotBlank(userinfo.getSc())
                && StringUtils.isNotBlank(userinfo.getZl()) && StringUtils.isNotBlank(userinfo.getZy2())) {
            _info.setZy(userinfo.getZy());
            _info.setSc(userinfo.getSc());
            _info.setZl(userinfo.getZl());
            _info.setZy2(userinfo.getZy2());
        }
        if (StringUtils.isNotBlank(userinfo.getKeyWord())) {
            _info.setKeyWord(userinfo.getKeyWord());
        }
        if (StringUtils.isNotBlank(userinfo.getDesc())) {
            _info.setDesc(userinfo.getDesc());
        }
        _info.setPer(userinfo.getPer());
        this.update(_info);
    }

    /**
     * 改变用户状态
     *
     * @param id         用户id
     * @param bannedTime 时间
     * @param content    原因
     * @param status     状态
     */
    @Override
    public void banned(Integer id, Integer bannedTime, String content, Integer status) {
        UserLogin userLogin = userLoginService.findById(id);
        Long time = 0L;
        Long day = 86400000L;
        if (bannedTime != null) {
            switch (bannedTime) {
                case 1:
                    time = day;
                    break;
                case 2:
                    time = 2L * day;
                    break;
                case 3:
                    time = 7L * day;
                    break;
                case 4:
                    time = 14L * day;
                    break;
                case 5:
                    time = 30L * day;
                    break;
                case 6:
                    time = 60L * day;
                    break;
                case 7:
                    time = -2L;
                    break;
            }
            if (bannedTime != 7) {
                time = System.currentTimeMillis() + time;
            }
            System.out.println(time);
            userLogin.setUnlockTime(time);
            userLogin.setContent(content);
        } else {
            userLogin.setUnlockTime(-1L);
            userLogin.setContent(" ");
        }
        userLogin.setStatus(status);
        userLoginService.update(userLogin);

    }

    @Override
    public void sendMessage(HttpServletRequest request, String id, Integer send, String sendTime, String title, String content) {
        if (StringKit.isNotBlank(id)) {
            //给一个组织发送信息
            System.out.println("----id----");
            System.out.println(id);
            System.out.println("----id----");

        } else {
            //给查询结果的所有组织
            List<Integer> ids = (List<Integer>) request.getSession().getAttribute("userInfoIds");
            System.out.println("----ids----");
            for (Integer _id : ids) {
                System.out.println(_id);
            }
            System.out.println("----ids----");
        }
    }

    @Override
    public List<UserVo> findByKeyWord(Integer userId, String keyWord) {
        return null;
    }

    @Transactional
    @Override
    public void createGrouping(Integer userId, String name, String userIds) {
        UserFriendGrouping ufg = new UserFriendGrouping();
        ufg.setUserId(userId);
        ufg.setName(name);
        Integer newId = userFriendGroupingService.saveRtId(ufg);

        if (StringUtils.isNotBlank(userIds)) {
            joinToGrouping(userId, userIds, newId);
        }
    }

    @Transactional
    @Override
    public void joinToGrouping(Integer userId, String userIds, Integer groupingId) {
        if (StringUtils.isNotBlank(userIds)) {
            String[] ids = userIds.split(",");
            Ufgm ufgm;
            for (String id : ids) {
                if (StringUtils.isNotBlank(id)) {
                    ufgm = new Ufgm();
                    ufgm.setUfgId(groupingId);
                    ufgm.setFriendId(Integer.parseInt(id));
                    ufgmService.save(ufgm);
                }
            }
        }
    }

    /**
     * 删除用户分组
     *
     * @param userId     用户ID
     * @param groupingId 分组ID
     */
    @Transactional
    @Override
    public void deleteGrouping(Integer userId, Integer groupingId) {
        ufgmService.deleteBy("ufg_id = #{ufgId}", Record.create().set("ufgId", groupingId));
        userFriendGroupingService.delete(groupingId);
    }

    /**
     * 查询默认分组信息
     *
     * @param usereId 用户ID
     * @return record
     */
    @Override
    public List<Record> findDefaultGrouping(Integer usereId) {
        String sql =
                "SELECT COUNT(*) AS counts,'熟人' AS `name` FROM tb_user_friend uf WHERE uf.`user_id` = #{userId} AND uf.`label` LIKE CONCAT('%', '熟人','%')\n" +
                        "UNION ALL\n" +
                        "SELECT COUNT(*) AS counts,'校友' AS `name` FROM tb_user_friend uf WHERE uf.`user_id` = #{userId} AND uf.`label` LIKE CONCAT('%', '校友','%')\n" +
                        "UNION ALL\n" +
                        "SELECT COUNT(*) AS counts, '同组织' AS `name` FROM tb_user_friend uf WHERE uf.`user_id` = #{userId} AND uf.`label` LIKE CONCAT('%', '同组织','%')";
        List<Record> records = Db.init().selectList(sql, Record.create().set("userId", usereId));
        return records;
    }

    @Override
    public List<Record> findCustomGrouping(Integer userId) {
        String sql = "SELECT COUNT(ufgm.`ufg_id`) AS counts, ufg.`name` " +
                " FROM tb_user_friend_grouping_member ufgm JOIN tb_user_friend_grouping ufg ON ufgm.`ufg_id` = ufg.`id` " +
                "WHERE ufg.`user_id` = #{userId} GROUP BY ufgm.`ufg_id`";
        List<Record> records = Db.init().selectList(sql, Record.create().set("userId", userId));
        return records;
    }

    /**
     * 查询分组首页
     *
     * @param userId 用户ID
     * @return records
     */
    @Override
    public List<Record> findIndexGrouping(Integer userId) {
        List<Record> defaultRecords = findDefaultGrouping(userId);
        List<Record> customRecods = findCustomGrouping(userId);
        defaultRecords.addAll(customRecods);
        return defaultRecords;
    }
}
