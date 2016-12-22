package com.smallchill.web.service.impl;import com.smallchill.api.common.exception.*;import com.smallchill.api.function.service.UserInterestService;import com.smallchill.core.plugins.dao.Blade;import com.smallchill.core.plugins.dao.Db;import com.smallchill.core.toolbox.Record;import com.smallchill.core.toolbox.kit.DateTimeKit;import com.smallchill.web.model.*;import com.smallchill.web.service.*;import org.apache.commons.lang3.StringUtils;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import com.smallchill.core.base.service.BaseService;import org.springframework.transaction.annotation.Transactional;import java.util.List;/** * 用户审核 * Generated by yesong. * 2016-10-28 14:31:09 */@Servicepublic class UserApprovalServiceImpl extends BaseService<UserApproval> implements UserApprovalService {    private String where = "(from_user_id = #{fromUserId} and to_user_id = #{toUserId}) or (from_user_id = #{toUserId} and to_user_id = #{fromUserId})";    private String where2 = "from_user_id = #{fromUserId} and to_user_id = #{toUserId}";    @Autowired    private UserFriendService userFriendService;    @Autowired    private UserInfoService userInfoService;    @Autowired    private UserInterestService userInterestService;    @Autowired    private AugService augService;    /***************************************************     * 发送******************************************     * <p>     * <p>     * 发送审核申请-单向；     * 只发送给接收用户     * 1.组织审核通过后     * 2.通过共同熟人结识     * 3.扫二维码结识     * 4.附近的人结识     * 5.熟人的熟人结识     */    @Override    public void toUserOneWay(UserApproval ua, String toUserIds) throws UserInOthersBlankException, UserHasApprovalException,            UsernotFriendException, BothUserHasApprovalException, UserHasFriendException, UserInMyBlankException {        if (StringUtils.isBlank(toUserIds)) {            return;        }        String[] toUserIdss = toUserIds.split(",");        for (String toUserId : toUserIdss) {            if (StringUtils.isNotBlank(toUserId)) {                ua.setToUserId(Integer.parseInt(toUserId));                if (this.requestValidate(ua)) {                    ua.setIntroduceUserId(0);                    ua.setGroupId(0);                    ua.setCreateTime(DateTimeKit.nowLong());                    this.save(ua);                }            }        }    }    /**     * 发送审核申请-双向；     * 同时发送给接收用户和发送用户     * 1.引荐     */    @Transactional    @Override    public void toUserTwoWay(UserApproval ua, String toUserIds, String validateInfo2) throws UserInOthersBlankException, UserHasFriendException,            UsernotFriendException, BothUserHasApprovalException, UserHasApprovalException, UserInMyBlankException {        String[] toUserIdss = toUserIds.split(",");        for (String toUserId : toUserIdss) {            if (StringUtils.isNotBlank(toUserId)) {                ua.setToUserId(Integer.parseInt(toUserId));                if (this.requestValidate(ua)) {                    ua.setGroupId(0);                    ua.setStatus(0);//                    UserApproval ua2 = new UserApproval();//                    ua2.setFromUserId(ua.getToUserId());//                    ua2.setToUserId(ua.getFromUserId());//                    ua2.setType(ua.getType());//                    ua2.setIntroduceUserId(ua.getIntroduceUserId());//                    ua2.setValidateInfo(validateInfo2);//                    ua2.setStatus(0);//                    ua2.setGroupId(0);//                    ua2.setCreateTime(DateTimeKit.nowLong());//                    this.save(ua2);                    this.save(ua);                }            }        }    }    /**     * 发送审核申请给组织     * 1.通过组织成员结识     * 2.通过活动临时群     * 3.通过查看交集     */    @Override    public void toGroup(UserApproval ua, String toUserIds) throws UserInOthersBlankException, UserHasApprovalException,            UsernotFriendException, BothUserHasApprovalException, UserHasFriendException, UserInMyBlankException {        String[] toUserIdss = toUserIds.split(",");        for (String toUserId : toUserIdss) {            if (StringUtils.isNotBlank(toUserId)) {                ua.setToUserId(Integer.parseInt(toUserId));                if (this.requestValidate(ua) && this.groupRequestValidate(ua)) {                    Aug aug = new Aug();                    aug.setGroupId(ua.getGroupId());                    aug.setFromUserId(ua.getFromUserId());                    aug.setToUserId(ua.getToUserId());                    aug.setStatus(1);                    aug.setValidateInfo(ua.getValidateInfo());                    aug.setCreateTime(DateTimeKit.nowLong());                    augService.save(aug);                }            }        }    }    /**     * 验证通过组织介绍请求是否合法     *     * @param ua 请求     * @return boolean     */    private boolean groupRequestValidate(UserApproval ua) throws BothUserHasApprovalException, UserInOthersBlankException {        Aug aug = findAugByGroupIdAndFidAndTid(ua.getGroupId(), ua.getFromUserId(), ua.getToUserId());        if (aug == null) {            return true;        }        if (aug.getStatus() == 0 || aug.getStatus() == 1) throw new BothUserHasApprovalException();        if (aug.getStatus() == 3) throw new UserInOthersBlankException();        if (aug.getStatus() == 2) {            this.updateAug(aug);            return false;        }        return true;    }    /**     * 查询审核信息     *     * @param groupId    组织ID     * @param fromUserId 请求发送者Id     * @param toUserId   请求介绍人ID     * @return 审核信息     */    private Aug findAugByGroupIdAndFidAndTid(Integer groupId, Integer fromUserId, Integer toUserId) {        String where = "group_id = #{groupId} and from_user_id = #{fromUserId} and to_user_id = #{toUserId}";        return augService.findFirstBy(where, Record.create().set("groupId", groupId).set("fromUserId", fromUserId).set("toUserId", toUserId));    }    private void updateAug(Aug aug) {        aug.setStatus(1);        augService.update(aug);    }    /** ************************************************审核*******************************************************/    /**     * 组织审核同意     *     * @param augId 申请消息Id     */    @Transactional    @Override    public void groupApprovalAgree(Integer augId) {        Aug aug = this.updateAugStatusById(augId, 1);        UserApproval ua = this.findFirstBy(where + " and 1 = 1", Record.create().set("fromUserId", aug.getFromUserId()).set("toUserId", aug.getToUserId()));        if (ua == null) {            UserApproval newUa = new UserApproval();            newUa.setStatus(0);            newUa.setFromUserId(aug.getFromUserId());            newUa.setToUserId(aug.getToUserId());            newUa.setGroupId(aug.getGroupId());            newUa.setType(1);            newUa.setIntroduceUserId(0);            newUa.setCreateTime(DateTimeKit.nowLong());            this.save(newUa);        }    }    /**     * 组织审核忽略（拒绝）     *     * @param augId 申请消息Id     */    @Override    public void groupApprovalRefuse(Integer augId) {        this.updateAugStatusById(augId, 2);    }    /**     * 组织审核拉黑     *     * @param augId 申请消息Id     */    @Override    public void groupApprovalBlank(Integer augId) {        this.updateAugStatusById(augId, 3);    }    /**     * 组织审核移除黑名单     *     * @param augId 申请消息Id     */    @Override    public void groupApprovalUnBlank(Integer augId) {        this.updateAugStatusById(augId, 4);    }    /**     * 修改审核状态     *     * @param augId  审核信息ID     * @param status 状态     */    private Aug updateAugStatusById(Integer augId, Integer status) {        Aug aug = augService.findById(augId);        aug.setStatus(status);        augService.update(aug);        return aug;    }    /************************************************************************************************     * 用户审核同意     *     * @param ua 申请消息     */    @Override    public void userApprovalAgree(UserApproval ua) throws UserOverAccNumException {        if (userInfoService.isOverAcquaintances(ua.getFromUserId())) {            throw new UserOverAccNumException();        }        this.approval(ua, 2);    }    /**     * 用户审核拒绝     *     * @param ua 申请消息     */    @Override    public void userApprovalRefuse(UserApproval ua) {        this.approval(ua, 3);    }    /**     * 用户审核拉黑     *     * @param ua 申请消息     */    @Transactional    @Override    public void userApprovalBlank(UserApproval ua, String toUserIds) {        if (StringUtils.isNotBlank(toUserIds)) {            String[] useridss = toUserIds.split(",");            for (String userid : useridss) {                if (StringUtils.isNotBlank(userid)) {                    ua.setToUserId(Integer.parseInt(userid));                    Record record = Record.create().set("fromUserId", ua.getFromUserId()).set("toUserId", ua.getToUserId());                    List<UserApproval> list = findByFromUserIdAndToUserIdTwoWay(ua.getFromUserId(), ua.getToUserId());                    record.set("_pageOffset", 0);                    record.set("_pageSize", 100);                    // 删除等待审核数据                    userFriendService.delAug(ua.getFromUserId(), Integer.parseInt(userid));                    // 删除感兴趣数据                    userInterestService.deleteBy("user_id = #{toUserId} and to_user_id = #{userId}", Record.create()                            .set("toUserId", userid).set("userId", ua.getFromUserId()));                    if (list == null || list.size() == 0) {                        createBlack(ua);                    } else if (list.size() == 1) {                        // 正常                        UserApproval distUa = list.get(0);                        distUa.setFromUserId(ua.getFromUserId());                        distUa.setToUserId(ua.getToUserId());                        distUa.setStatus(4);                        this.update(distUa);                        _blank(ua);                    } else if (list.size() == 2) {                        // 推荐情况                        String set = "status = 4";                        this.updateBy(set, where2, record);                        this.deleteBy(where2, record.set("fromUserId", ua.getToUserId()).set("toUserId", ua.getFromUserId()));                    }                }            }        }    }    @Override    public List<UserApproval> findByFromUserIdAndToUserIdTwoWay(Integer fromUserId, Integer toUserId) {        Record record = Record.create().set("fromUserId", fromUserId).set("toUserId", toUserId);        return this.findBy(where, record);    }    /**     * 创建黑名单记录     *     * @param ua     */    private void createBlack(UserApproval ua) {        UserApproval _ua = new UserApproval();        _ua.setFromUserId(ua.getFromUserId());        _ua.setToUserId(ua.getToUserId());        _ua.setIntroduceUserId(0);        _ua.setValidateInfo("");        _ua.setGroupId(0);        _ua.setType(1);        _ua.setStatus(4);        _ua.setCreateTime(DateTimeKit.nowLong());        this.save(_ua);    }    /**     * 用户审核移除黑名单     *     * @param ua 申请消息     */    @Override    public void userApprovalUnBlank(UserApproval ua, String userIds) {        if (StringUtils.isNotBlank(userIds)) {            String[] useridss = userIds.split(",");            for (String userid : useridss) {                if (StringUtils.isNotBlank(userid)) {                    ua.setToUserId(Integer.parseInt(userid));                    this.approval(ua, 5);                }            }        }    }    /**     * 重置审核记录     *     * @param ua fromUserId and toUserId     */    @Override    public void setStatusDel(UserApproval ua) {        String set = "status = 4";        Record record = Record.create();        record.put("fromUserId", ua.getFromUserId());        record.put("toUserId", ua.getToUserId());        this.updateBy(set, where, record);    }    /**     * (推荐)审核     *     * @param ua 审核信息     */    @Transactional    @Override    public void auditAgreeByIntroduce(UserApproval ua) throws ApprovalFailException {        UserApproval _ua = this.findFirstBy(where + " and 1 = 1", Record.create().set("fromUserId", ua.getToUserId()).set("toUserId", ua.getFromUserId()));        if (_ua == null) {            throw new ApprovalFailException();        }        String  processUserids = _ua.getIntroduceUserIdProcess();        if (StringUtils.isBlank(processUserids)) {            _ua.setIntroduceUserIdProcess(String.valueOf(ua.getToUserId()));            this.update(_ua);        }        else {            _ua.setStatus(2);            _ua.setIntroduceUserIdProcess("");            this.update(_ua);            UserFriend uf = new UserFriend();            uf.setUserId(ua.getFromUserId());            uf.setFriendId(ua.getToUserId());            userFriendService.addFriend(uf);        }    }    /**     * 引荐申请审核 - 拒绝     *     * @param ua 审核信息     */    @Transactional    @Override    public void auditRefuseByIntroduce(UserApproval ua) {        Record record = Record.create().set("fromUserId", ua.getFromUserId()).set("toUserId", ua.getToUserId());        boolean flag = true;        // 删除请求信息        List<UserApproval> approvals = this.findBy(where, record);        for (UserApproval _ua : approvals) {            if (_ua.getStatus() == 3) {                flag = false;            }        }        if (flag) {            this.deleteBy(where, record);        } else {            this.deleteBy(where2, record.set("fromUserId", ua.getToUserId()).set("toUserId", ua.getToUserId()));        }    }    @Override    public List<UserApproval> getUserByFromUserIdAndToUserIdApprovalOfTowWay(Integer userId, Integer distUserId) {        Record record = Record.create();        record.put("fromUserId", userId);        record.put("toUserId", distUserId);        return this.findBy(where, record);    }    @Override    public UserApproval getUserByFromUserIdAndToUserIdApprovalOfOneWay(Integer userId, Integer distUserId) {        return getByFromUserIdAndToUserId(userId, distUserId);    }    /**     * @param ua 申请信息     */    @Override    public void auditOfInterest(UserApproval ua) throws BothUserHasApprovalException, UserInOthersBlankException,            UserHasApprovalException, UsernotFriendException, UserInMyBlankException, UserHasFriendException, UserOverAccNumException {        ua.setType(1);        UserApproval _ua = this.findFirstBy(where + " and 1 = 1", Record.create().set("fromUserId", ua.getFromUserId()).set("toUserId", ua.getToUserId()));        if (_ua != null && _ua.getId() != null) {            ua = _ua;        }        else {            this.toUserOneWay(ua, String.valueOf(ua.getToUserId()));        }        if (ua.getStatus() == 1) {            // 同意            this.userApprovalAgree(ua);        } else {            // 拒绝            this.userApprovalRefuse(ua);        }        // 1.查询user_apprvoal表，是否已有数据        // 2.无：新增一条记录 有：改变申请状态    }    /**     * 重置审核记录     *     * @param ua fromUserId and toUserId     */    @Override    public void resetStatus(UserApproval ua) {        Record record = Record.create();        record.put("fromUserId", ua.getFromUserId());        record.put("toUserId", ua.getToUserId());        this.deleteBy(where, record);    }    /**     * 调用MD文件sql     *     * @param source     mdw文件sql key     * @param where      查询条件     * @param modalOrMap 条件内容     * @return 结果集     */    @Override    public List<Record> exceuteBySource(String source, String where, Object modalOrMap) {        String sql = Blade.dao().getScript(source).getSql();        return Db.init().selectList(sql, where, modalOrMap);    }    /**     * 根据用户ID查询申请信息     *     * @param fromUserId 申请发起人     * @param toUserId   申请接受人     * @return 申请信息     */    private List<UserApproval> getsByFromUserIdAndToUserId(int fromUserId, int toUserId) {        Record record = Record.create();        record.put("fromUserId", fromUserId);        record.put("toUserId", toUserId);        return this.findBy(where, record);    }    /**     * 根据用户ID查询申请信息     *     * @param fromUserId 申请发起人     * @param toUserId   申请接受人     * @return 申请信息     */    private UserApproval getByFromUserIdAndToUserId(int fromUserId, int toUserId) {        Record record = Record.create();        record.put("fromUserId", fromUserId);        record.put("toUserId", toUserId);        return this.findFirstBy(where + " and 1 = 1", record);    }    /**     * 审核     *     * @param ua 申请信息     */    @Transactional    private void approval(UserApproval ua, Integer status) {        List<UserApproval> distList = this.getUserByFromUserIdAndToUserIdApprovalOfTowWay(ua.getFromUserId(), ua.getToUserId());        UserApproval dist = null;        if (distList != null && distList.size() ==1) {            dist = distList.get(0);        }        else if (distList != null && distList.size() > 1){            for (UserApproval ua2 : distList) {                if (ua2.getFromUserId().equals(ua.getFromUserId()) && ua2.getToUserId().equals(ua.getToUserId())) {                    dist = ua2;                }            }        }        if (dist != null) {            dist.setStatus(status);            this.update(dist);            // 新增好友            if (status == 2) {                if (dist.getType() == 1) {                    UserFriend uf = new UserFriend();                    uf.setStatus(0);                    uf.setType(ua.getType());                    uf.setUserId(ua.getToUserId());                    uf.setFriendId(ua.getFromUserId());                    userFriendService.addFriend(uf);                } else {                    List<UserFriend> userFriendList = userFriendService.findBy("(user_id = #{userId} and friend_id = #{friendId}) or (user_id = #{friendId} and friend_id = #{userId})",                            Record.create().set("userId", ua.getFromUserId()).set("friendId", ua.getToUserId()));                    for (UserFriend userFriend : userFriendList) {                        userFriend.setType(2);                        userFriendService.update(userFriend);                    }                }            }            // 拉黑好友            if (status == 4) {                _blank(ua);            }        }    }    /**     * 判断两个用户是否在同一组织     *     * @param fromUserId 用户1     * @param toUserId   用户2     * @return result     */    private boolean isSameGroup(Integer fromUserId, Integer toUserId) {        String sql = "SELECT COUNT(*) as counts FROM tb_user_group ug1 WHERE ug1.`user_id` = #{fromUserId} AND ug1.`group_id` IN" +                " (SELECT group_id FROM tb_user_group ug2 WHERE ug2.`user_id` = #{toUserId})";        Record record = Db.init().selectOne(sql, Record.create().set("fromUserId", fromUserId).set("toUserId", toUserId));        return record.getInt("counts") > 0;    }    private void _blank(UserApproval ua) {        UserFriend uf = new UserFriend();        uf.setUserId(ua.getToUserId());        uf.setFriendId(ua.getFromUserId());        userFriendService.blank(uf);    }    /**     * 请求验证     *     * @param src 请求信息     * @return result     */    private boolean requestValidate(UserApproval src) throws UsernotFriendException, UserHasApprovalException,            UserInOthersBlankException, BothUserHasApprovalException, UserHasFriendException, UserInMyBlankException {        List<UserApproval> list = this.getsByFromUserIdAndToUserId(src.getFromUserId(), src.getToUserId());        if ((list == null || list.size() == 0) && src.getType() < 2) {            return true;        } else if (list.size() > 1) {            // 推荐            for (UserApproval ua : list) {                if (ua.getStatus() == 4 && (ua.getToUserId() == src.getFromUserId()))                    throw new UserInOthersBlankException();                if (ua.getStatus() == 4 && (ua.getFromUserId() == src.getFromUserId()))                    throw new UserInMyBlankException();            }            throw new BothUserHasApprovalException();        } else if (list.size() == 1) {            UserApproval dist = list.get(0);            if (dist == null && src.getType() == 2) return true;            if (dist == null && src.getType() >= 3) throw new UsernotFriendException();            if (dist.getStatus() == 1) throw new UserHasApprovalException();            if (src.getType() == 1 && dist.getStatus() == 2) throw new UserHasFriendException();            if (src.getType() == 2 && dist.getStatus() == 2) {                auditAcquaintances(src, dist);                return false;            }            if (dist.getStatus() == 4 && (dist.getToUserId() == src.getFromUserId()))                throw new UserInOthersBlankException();            if (dist.getStatus() == 3 && (dist.getFromUserId() == src.getFromUserId()))                throw new UserInMyBlankException();            UserApproval _dist = list.get(0);            _dist.setValidateInfo(src.getValidateInfo());            _dist.setStatus(0);            _dist.setFromUserId(src.getFromUserId());            _dist.setToUserId(src.getToUserId());            this.update(_dist);        }        return false;    }    /**     * 结为熟人     *     * @param src     * @param dist     */    private void auditAcquaintances(UserApproval src, UserApproval dist) {        dist.setType(2);        dist.setStatus(1);        dist.setFromUserId(src.getFromUserId());        dist.setToUserId(src.getToUserId());        this.update(dist);    }    /**     * 审核验证     *     * @param src  请求信息     * @param dist 数据库信息     * @return result     */    private boolean auditValidate(UserApproval src, UserApproval dist) {        return true;    }}