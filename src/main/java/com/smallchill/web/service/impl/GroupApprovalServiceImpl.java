package com.smallchill.web.service.impl;

import com.smallchill.api.common.exception.UserHasApprovalException;
import com.smallchill.api.common.exception.UserHasJoinGroupException;
import com.smallchill.api.common.exception.UserInOthersBlankException;
import com.smallchill.api.function.meta.consts.StatusConst;
import com.smallchill.api.function.modal.vo.GroupApprovalVo;
import com.smallchill.api.function.modal.vo.ShouPageVo;
import com.smallchill.api.function.service.MessageService;
import com.smallchill.api.function.service.PayService;
import com.smallchill.api.function.service.UserDomainService;
import com.smallchill.api.function.service.UserprofessionalService;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.kit.DateTimeKit;
import com.smallchill.core.toolbox.support.BladePage;
import com.smallchill.web.model.*;
import com.smallchill.web.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smallchill.core.base.service.BaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * 组织审核
 * Generated by yesong.
 * 2016-10-27 11:45:35
 */
@Service
public class GroupApprovalServiceImpl extends BaseService<GroupApproval> implements
        GroupApprovalService, StatusConst {

    @Autowired
    private GroupService groupService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private GroupExtendService groupExtendService;
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ProvinceCityService provinceCityService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PayService payService;

    /**
     * 是否已经申请
     *
     * @param ga 申请信息
     * @return 结果
     */
    public boolean isApprival(GroupApproval ga) throws UserHasApprovalException,
            UserHasJoinGroupException, UserInOthersBlankException {
        String sql = "select status from tb_group_approval where group_id = #{groupId} and  user_id = #{userId}";
        Record record = Record.create();
        record.put("groupId", ga.getGroupId());
        record.put("userId", ga.getUserId());
        GroupApproval groupApproval = this.findFirst(sql, record);
        if (groupApproval == null) return false;
        if (groupApproval.getStatus() == 2) {
            groupService.audit(ga.getGroupId(), ga.getUserId(), 0);
            return true;
        }
        if (groupApproval.getStatus() == 0) {
            throw new UserHasApprovalException();
        }
        if (groupApproval.getStatus() == 1 || groupApproval.getStatus() == 4) {
            throw new UserHasJoinGroupException();
        }
        if (groupApproval.getStatus() == 3) {
            throw new UserInOthersBlankException();
        }
        return false;
    }

    /**
     * 用户是否满足加入组织
     *
     * @param userId  当前用户id
     * @param groupId 组织ID
     */
    @Override
    public boolean isMeetConditions(Integer userId, Integer groupId) {
        Group group = groupService.findById(groupId);
        UserInfo userInfo = userInfoService.findByUserId(userId);
        int gender = (group.getSexLimit() == null ? 0 : group.getSexLimit());
        if (gender != 0 && gender != userInfo.getGender()) return false;
        int province = (group.getProvinceLimit() == null ? 0 : group.getProvinceLimit());
        if (province != 0 && province != userInfo.getProvinceId()) return false;
        int city = (group.getCityLimit() == null ? 0 : group.getCityLimit());
        if (city != 0 && city != userInfo.getCityId()) return false;
        int industryLimit = group.getIndustryLimit() == null ? 0 : group.getIndustryLimit();
        int domainLimit = group.getDomainLimit() == null ? 0 : group.getDomainLimit();
        // 查询是否有符合的行业领域
        if (industryLimit != 0 || domainLimit != 0) {
            StringBuffer domainSql = new StringBuffer("select count(id) as counts from tb_userinfo_domain where 1 = 1");
            domainSql.append(" and user_id = #{userId}");
            if (industryLimit != 0) {
                domainSql.append(" and p_id = #{industryLimit}");
            }
            if (domainLimit != 0) {
                domainSql.append(" and domain_id = #{domainLimit}");
            }
            Record domainRecord = Db.init().selectOne(domainSql.toString(), Record.create
                    ().set("userId", userId)
                    .set("industryLimit", industryLimit).set("domainLimit", domainLimit));
            if (domainRecord.getInt("counts") <= 0) return false;
        }

        int professionalLimit = (group.getProfessionalLimit() == null ? 0 :
                group.getProfessionalLimit());
        int zyLimit = (group.getZyLimit() == null ? 0 : group.getZyLimit());
        if (professionalLimit != 0 || zyLimit != 0) {
            // 查询是否有符合的专业
            StringBuffer proSql = new StringBuffer("select count(id) as counts from tb_userinfo_professional where 1 = 1");
            proSql.append(" and user_id = #{userId}");
            if (professionalLimit != 0) {
                proSql.append(" and p_id = #{professionalLimit}");
            }
            if (zyLimit != 0) {
                proSql.append(" and pro_id = #{zyLimit}");
            }

            Record proRecord = Db.init().selectOne(proSql.toString(), Record.create().set
                    ("userId", userId)
                    .set("professionalLimit", professionalLimit).set("zyLimit", zyLimit));
            if (proRecord.getInt("counts") <= 0) return false;
        }
        return true;
    }


    @Override
    public int join(GroupApproval ga) throws UserInOthersBlankException,
            UserHasApprovalException, UserHasJoinGroupException {
        // 是否已经发送申请
        if (!isApprival(ga)) {
            ga.setCreateTime(DateTimeKit.nowLong());
            return save1(ga);
        }
        return 0;
    }

    @Override
    @Transactional
    public void userInvitation(Integer userId, String content) {
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        GroupApproval groupApproval = new GroupApproval();
        groupApproval.setUserId(userId);
        groupApproval.setGroupId(group.getId());
        groupApproval.setStatus(1); //未处理
        groupApproval.setType(2); //组织邀请
        groupApproval.setPaied(2); //已付款(邀请免费,默认算付款)
        groupApproval.setValidateInfo(content); //验证信息
        groupApproval.setCreateTime(DateTimeKit.nowLong());
        this.save(groupApproval);
    }

    @Override
    public BladePage cadresList(Integer groupId) {
        String sql = "SELECT \n" +
                "  tug.id AS id,\n" +
                "  tui.user_id AS userId,\n" +
                "  tui.username AS userName,\n" +
                "  tug.vip_type AS vipType,\n" +
                "  tui.mobile AS mobile\n" +
                "FROM\n" +
                "  tb_user_group tug \n" +
                "  LEFT JOIN tb_user_info tui \n" +
                "    ON tug.user_id = tui.user_id \n" +
                "WHERE 1=1 \n" +
                "AND tug.vip_type = 2 \n" +
                "AND tug.group_id = #{groupId} \n" +
                "ORDER BY userId";
        Map<String, Object> map = new HashMap<>();
        map.put("groupId", groupId);
        BladePage page = Db.init().paginate(sql, Map.class, map, 1, 5);
        return page;
    }

    @Override
    @Transactional
    public void appointedSave(Integer id, Integer status) {
        UserGroup userGroup = userGroupService.findById(id);
        userGroup.setType(status);
        userGroupService.update(userGroup);
    }

    @Override
    @Transactional
    public void updateStatus(Integer id, Integer status) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        GroupApproval groupApproval = this.findById(id);
        if (status == 2) {
            //批准通过的时间
            groupApproval.setThroughTime(DateTimeKit.nowLong());
            //新增一条会员组织关系
            GroupExtend groupExtend = groupExtendService.findFirstBy("group_id = #{groupId}",
                    Record.create().set("groupId", groupApproval.getGroupId()));
            UserGroup userGroup = new UserGroup();
            userGroup.setGroupId(groupApproval.getGroupId());
            userGroup.setUserId(groupApproval.getUserId());
            if (groupExtend != null) {
                if (groupExtend.getCostStatus() != null && groupExtend.getCostStatus() == 1) {
                    Calendar calendar = Calendar.getInstance();
                    Date date = new Date(System.currentTimeMillis());
                    calendar.setTime(date);
                    calendar.add(Calendar.YEAR, +1);
                    date = calendar.getTime();
                    Long time = date.getTime();
                    userGroup.setVipEndTime(time);
                } else {
                    userGroup.setVipEndTime(-1L);
                }
            }
            userGroup.setCreateTime(System.currentTimeMillis());
            userGroup.setJoinType(1); //会员
            userGroup.setType(1);   //主动加入

            UserInfo userInfo = userInfoService.findByUserId(groupApproval.getUserId());
            userInfo.setGroupStatus(1); //设置为已经加入机构

            userInfoService.update(userInfo);
            userGroupService.save(userGroup);
            //发现消息
            messageService.sendMsgForUserAuditAgree(groupApproval.getGroupId(),
                    groupApproval.getUserId());
            Order order = orderService.findByGaId(groupApproval.getId());
            if (order != null) {
                orderService.setOrderAgree(order);
            }
        } else if (status == 3) {
            //第三个参数为订单号(暂时没有)
            Order order = orderService.findByGaId(groupApproval.getId());
            if (order != null) {
                payService.refund(id, request, response);
            }
            messageService.sendMsgForUserAuditRefuse(groupApproval.getGroupId(),
                    groupApproval.getUserId(), order != null ? order.getOrderNo() : "");
        } else if (status == 4) {
            Order order = orderService.findByGaId(groupApproval.getId());
            if (order != null) {
                payService.refund(id, request, response);
            }
            messageService.sendMsgForUserAuditBlank(groupApproval.getGroupId(),
                    groupApproval.getUserId(), order != null ? order.getOrderNo() : "");
        }

        groupApproval.setStatus(status);
        this.update(groupApproval);
    }

    @Override
    @Transactional
    public void permissionSetting(Group group, Integer permissionsType, Integer isJoin, Integer
            isIntroduce, Integer costType, Double cost, Integer sexLimit, Integer industryLimit, Integer
                                          domainLimit, Integer provinceLimit, Integer cityLimit, Integer professionalLimit, Integer
                                          zyLimit) {
        GroupExtend groupExtend = null;
        List<UserGroup> userGroups = null;
        if (group.getId() != null) {
            groupExtend = groupExtendService.findFirstBy("group_id = #{groupId}",
                    Record.create().set("groupId", group.getId()));
            userGroups = userGroupService.findBy("group_id = #{groupId}", Record.create().set
                    ("groupId", group.getId()));

        }

        if (groupExtend != null) {

            if (!Objects.equals(costType, groupExtend.getCostType())) {
                //改变了收费模式
                if (costType == 1) {
                    //年费
                    for (UserGroup userGroup : userGroups) {
                        Calendar calendar = Calendar.getInstance();
                        Date date = new Date(System.currentTimeMillis());
                        calendar.setTime(date);
                        calendar.add(Calendar.YEAR, +1);
                        date = calendar.getTime();
                        Long time = date.getTime();
                        userGroup.setVipEndTime(time);
                        userGroupService.update(userGroup);
                    }
                } else {
                    //永久
                    for (UserGroup userGroup : userGroups) {
                        userGroup.setVipEndTime(-1L);
                        userGroupService.update(userGroup);
                    }
                }
            }
            groupExtend.setCostType(costType);
            groupExtend.setCost(cost);

            if (cost == null || cost == 0) {
                //免费
                groupExtend.setCostStatus(1);
            } else {
                //收费
                groupExtend.setCostStatus(2);
            }
            groupExtendService.update(groupExtend);
        }

        group.setPermissionsType(permissionsType);
        group.setIsJoin(isJoin);
        group.setSexLimit(sexLimit);
        group.setIsIntroduce(isIntroduce);
        group.setIndustryLimit(industryLimit);
        group.setDomainLimit(domainLimit);
        ProvinceCity province = null;
        ProvinceCity city = null;
        if (provinceLimit != 0) {
            province = provinceCityService.findFirstBy("code = #{code}", Record.create().set("code", provinceLimit));
        }
        if (cityLimit != 0) {
            city = provinceCityService.findFirstBy("code = #{code}", Record.create().set("code", cityLimit));
        }
        group.setProvinceLimit(province != null ? province.getCode() : 0);
        group.setCityLimit(city != null ? city.getCode() : 0);
        group.setProfessionalLimit(professionalLimit);
        group.setZyLimit(zyLimit);
        groupService.update(group);
    }

    @Override
    public GroupApprovalVo gaInfo(Integer groupId) {
        String sql = "SELECT g.id, g.`targat`, ge.`cost`,ge.`cost_status`, ge.`cost_type` FROM tb_group g LEFT JOIN tb_group_extend ge " +
                "ON g.`id` = ge.`group_id` WHERE g.`id` = #{groupId}";
        Record record = Db.init().selectOne(sql, Record.create().set("groupId", groupId));
        String target = record.getStr("targat");
        BigDecimal cost = record.get("cost") == null ? BigDecimal.valueOf(0) :
                BigDecimal.valueOf(Double.parseDouble(record.get("cost").toString()));
        List<String> targets = new ArrayList<>();
        if (StringUtils.isNotBlank(target)) {
            String[] targetss = target.split("\\|");
            for (String t : targetss) {
                if (StringUtils.isNotBlank(t)) {
                    targets.add(t);
                }
            }
        }
        GroupApprovalVo vo = new GroupApprovalVo();
        vo.setMoney(cost);
        vo.setTarget(targets);
        vo.setCostStatus(record.getInt("cost_status"));
        vo.setCostType(record.getInt("cost_type"));
        return vo;
    }

    /**
     * 新增申请记录
     *
     * @param ga 申请信息
     */
    private int save1(GroupApproval ga) {
        return this.saveRtId(ga);
    }


    /**
     * 组织邀请审核
     *
     * @param groupId 组织ID
     * @param userId  用户ID
     */
    @Transactional
    @Override
    public void userAuditGroupAgree(Integer groupId, Integer userId) {
        GroupApproval groupApproval = this.findFirstBy("group_id = #{groupId} and user_id = # {userId}", Record.create().set("groupId", groupId).set("userId", userId));
        groupApproval.setStatus(2);
        groupApproval.setThroughTime(DateTimeKit.nowLong());
        this.update(groupApproval);

        UserGroup userGroup = new UserGroup();
        userGroup.setGroupId(groupApproval.getGroupId());
        userGroup.setUserId(groupApproval.getUserId());
        userGroup.setVipEndTime(-1L);
        userGroup.setType(1);
        userGroup.setJoinType(2);
        userGroupService.save(userGroup);
    }

    @Override
    public void userAuditGroupRefuse(Integer groupId, Integer userId) {
        GroupApproval groupApproval = this.findFirstBy("group_id = #{groupId} and user_id = # {userId}", Record.create().set("groupId", groupId).set("userId", userId));
        groupApproval.setStatus(3);
        groupApproval.setThroughTime(DateTimeKit.nowLong());
        this.update(groupApproval);
    }

    /**
     * 修改入群申请信息的支付状态
     *
     * @param gaId 申请信息ID
     */
    @Override
    public void setPaiedStatusSuccess(int gaId) {
        this.updateBy("paied = #{paied}", "id = #{gaId}", Record.create().set("paied", HAVE_PAY).set("gaId", gaId));
    }
}
