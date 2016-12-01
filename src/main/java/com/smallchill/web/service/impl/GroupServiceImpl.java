package com.smallchill.web.service.impl;

import com.smallchill.api.common.exception.UserHasJoinGroupException;
import com.smallchill.api.function.meta.other.Convert;
import com.smallchill.api.function.modal.vo.Groupvo;
import com.smallchill.api.function.service.MessageService;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.LeomanKit;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.core.toolbox.kit.DateTimeKit;
import com.smallchill.web.model.Group;
import com.smallchill.web.model.GroupBank;
import com.smallchill.web.model.GroupExtend;
import com.smallchill.web.model.UserGroup;
import com.smallchill.web.model.vo.GroupVo;
import com.smallchill.web.service.*;
import org.beetl.sql.core.kit.StringKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smallchill.core.base.service.BaseService;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 组织service
 * Generated by yesong.
 * 2016-10-25 17:34:42
 */
@Service
public class GroupServiceImpl extends BaseService<Group> implements GroupService {

    @Autowired
    private GroupBankService groupBankService;
    @Autowired
    private GroupExtendService groupExtendService;
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private GroupApprovalService groupApprovalService;
    @Autowired
    private MessageService messageService;

    /**
     * 新建组织
     *
     * @param groupVo 组织信息
     */
    @Transactional
    @Override
    public void saveGroup(GroupVo groupVo) {
        try {
            // 1.group
            int groupId = saveGroupInfo(groupVo);
            // 2.groupBank
            saveGroupBank(groupVo, groupId);
            // 3.groupTag
            saveGroupTag(groupVo, groupId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存组织拓展信息
     *
     * @param groupVo 组织信息
     * @param groupId 组织ID
     */
    private void saveGroupTag(GroupVo groupVo, Integer groupId) {
        GroupExtend gg = new GroupExtend();
        gg.setGroupId(groupId);
        gg.setLicenseImage(groupVo.getLicenseImage());
        gg.setCodeImge(groupVo.getCodeImage());
        gg.setIdCard(groupVo.getIdcard()); // 后台自动生成
        gg.setPassword(groupVo.getPassword());
        gg.setCode(groupVo.getCode());
        gg.setLicense(groupVo.getLicense());
        gg.setArtificialPersonName(groupVo.getArtificialPersonName());
        gg.setArtificialPersonIdcard(groupVo.getArtificialPersonIdcard());
        gg.setArtificialPersonMobile(groupVo.getArtificialPersonMobile());
        gg.setCreateAdminId(groupVo.getCreateAdminId()); //创建者ID
        groupExtendService.save(gg);
    }

    /**
     * 保存组织银行信息
     *
     * @param groupVo 组织信息
     * @param groupId 组织ID
     */
    private void saveGroupBank(GroupVo groupVo, Integer groupId) {
        GroupBank groupBank = new GroupBank();
        groupBank.setGroupId(groupId);
        groupBank.setBankUserName(groupVo.getBankUserName());
        groupBank.setBankAccout(groupVo.getBankAccout());
        groupBank.setBankId(groupVo.getBankId());
        groupBank.setBankName(groupVo.getBankName());
        groupBank.setProvince(groupVo.getBanckProvince());
        groupBank.setCity(groupVo.getBankCity());
        groupBank.setBranchName(groupVo.getBranchName());
        groupBank.setCreateTime(DateTimeKit.nowLong());
        groupBankService.save(groupBank);
    }

    /**
     * 保存组织基本信息
     *
     * @param groupVo Vo
     * @return int
     */
    private int saveGroupInfo(GroupVo groupVo) {
        Group group = new Group();
        group.setName(groupVo.getName());
        group.setAvater(groupVo.getAvater());

        group.setType(groupVo.getType());
        group.setProvince(groupVo.getProvince());
        group.setCity(groupVo.getCity());
        group.setProvinceCity(groupVo.getProvinceCity());
        group.setTargat(groupVo.getTarget());
        group.setTelphone(groupVo.getTelphone());

        group.setTitle1(groupVo.getTitle1());
        group.setContent1(groupVo.getContent1());
        group.setIsOpen1(groupVo.getIsOpen1());

        group.setTitle2(groupVo.getTitle2());
        group.setContent2(groupVo.getContent2());
        group.setIsOpen2(groupVo.getIsOpen2());

        group.setTitle3(groupVo.getTitle3());
        group.setContent3(groupVo.getContent3());
        group.setIsOpen3(groupVo.getIsOpen3());

        group.setCreateTime(DateTimeKit.nowLong());
        group.setAuditStatus(1);        //新增组织为待审核状态
        group.setPermissionsType(1);    //权限类型: 1:公开组织 2:隐藏组织
        group.setIsJoin(1);             //能否申请加入: 1:开放 2:关闭
        group.setIsIntroduce(1);        //是否允许引荐: 1:允许 2:拒绝
        group.setSexLimit(0);           //性别限制
        group.setIndustryLimit(0);      //行业限制
        group.setDomainLimit(0);        //领域限制
        group.setProvinceLimit(0);      //省限制
        group.setCity(0);               //市限制
        group.setProfessionalLimit(0);  //职业限制
        group.setZyLimit(0);            //专业限制
        return this.saveRtId(group);
    }

    @Override
    public void editGroup(GroupVo groupVo) {

    }

    @Override
    public JqGrid page(String source) {
        return null;
    }

    /**
     * 申请审批-批准
     *
     * @param groupId 组织ID
     * @param userId  用户ID
     */
    @Transactional
    @Override
    public void approval(Integer groupId, Integer userId) throws UserHasJoinGroupException {
        // 1.改变审核表中状态为2：批准
        // 2.tb_user_group表新增会员信息
        this.audit(groupId, userId, 2);
        UserGroup ug = new UserGroup();
        ug.setGroupId(groupId);
        ug.setUserId(userId);
        ug.setCreateTime(DateTimeKit.nowLong());

        if (!userInGroup(groupId, userId)) {
            userGroupService.save(ug);
            messageService.sendMsgForUserAuditAgree(groupId, userId);
        } else {
            throw new UserHasJoinGroupException();
        }
    }

    /**
     * 用户是否已经在组织记录中
     *
     * @param groupId 组织ID
     * @param userId  用户ID
     * @return 是否已经在组织记录中
     */
    private boolean userInGroup(Integer groupId, Integer userId) {
        List<UserGroup> list = userGroupService.findBy("group_id = #{groupId} and user_id = #{userId}", Record.create().set("groupId", groupId).set("userId", userId));
        return (list != null && list.size() > 0);
    }

    /**
     * 申请审批-拒绝
     *
     * @param groupId 组织Id
     * @param userId  用户ID
     */
    @Transactional
    @Override
    public void refuse(Integer groupId, Integer userId) {
        // 1.改变审核表中状态 为 3：拒绝
        this.audit(groupId, userId, 3);
        messageService.sendMsgForUserAuditRefuse(groupId, userId, null);
    }

    /**
     * 申请审批-拉黑
     * 被拉黑的用户无法再次加入组织
     *
     * @param groupId 组织ID
     * @param userId  用户ID
     */
    @Override
    public void blank(Integer groupId, Integer userId) {
        // 1.改变审核表中状态 为4：拉黑
        this.audit(groupId, userId, 4);
    }

    /**
     * 申请审批-移除黑名单
     * 被移除黑名单的用户在组织里的状态改为退出
     *
     * @param groupId 组织ID
     * @param userId  用户ID
     */
    @Override
    public void unblank(Integer groupId, Integer userId) {
        // 1.删除申请记录
        Record record = Record.create();
        record.put("groupId", groupId);
        record.put("userId", userId);
        userGroupService.deleteBy("group_id = #{groupId} and user_id = #{userId}", record);
    }


    /**
     * 审核改变状态(审核用户)
     *
     * @param groupId 组织ID
     * @param userId  用户ID
     * @param status  状态
     */
    @Override
    public void audit(Integer groupId, Integer userId, Integer status) {
        String set = "set status = " + status + ",through_time = " + DateTimeKit.nowLong();
        String where = " group_id = #{groupId} and user_id = #{userId}";
        Record record = Record.create();
        record.put("groupId", groupId);
        record.put("userId", userId);
        groupApprovalService.updateBy(set, where, record);
    }

    /**
     * 修改审核状态(审核组织)
     *
     * @param groupId 组织ID
     * @param status  状态
     */
    @Override
    public void audit(Integer groupId, Integer status) {
        String set = "set audit_status = #{status}, update_time = #{updateTime} ";
        String where = " id = #{groupId}";
        Record record = Record.create();
        record.put("groupId", groupId);
        record.put("status", status);
        record.put("updateTime", DateTimeKit.nowLong());
        GroupExtend groupExtend = Blade.create(GroupExtend.class).findFirstBy("group_id = #{group_id}", Record.create().set("group_id", groupId));
        Integer adminId = (Integer) ShiroKit.getUser().getId();
        groupExtend.setApprovalAdminId(adminId);
        groupExtendService.update(groupExtend);
        updateBy(set, where, record);
    }

    @Override
    /**
     * 发送消息
     * @param id        组织ID
     * @param send      发送类型
     * @param sendTime  定时发送时间
     * @param title     标题
     * @param content   内容
     */

    public Boolean sendMessage(HttpServletRequest request, String id, Integer send, String sendTime, String title, String content) {
        try {
            if (StringKit.isNotBlank(id)) {
                //给一个组织发送信息
                System.out.println("----id----");
                System.out.println(id);
                System.out.println("----id----");

            } else {
                //给查询结果的所有组织
                List<Integer> ids = (List<Integer>) request.getSession().getAttribute("groupIds");
                System.out.println("----ids----");
                for (Integer _id : ids) {
                    System.out.println(_id);
                }
                System.out.println("----ids----");
            }

        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void banned(Integer id, Integer bannedTime, String content) {
        GroupExtend groupExtend = Blade.create(GroupExtend.class).findFirstBy("group_id = #{group_id}", Record.create().set("group_id", id));
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
            groupExtend.setFreezeStatus(2);
            groupExtend.setFreezeTime(time);
            groupExtend.setWhy1(content);
        } else {
            groupExtend.setFreezeStatus(1);
            groupExtend.setFreezeTime(-1L);
            groupExtend.setWhy1("");
        }
        groupExtendService.update(groupExtend);

    }

    /**
     * 待审组织--行内修改备注
     *
     * @param id      组织ID
     * @param content 修改内容
     */
    @Override
    public void updateNote(Integer id, String content) {
        String set = "set audit_comment = ${content}";
        String where = " group_id = #{groupId}";
        Record record = Record.create();
        record.put("groupId", id);
        record.put("content", content);
        updateBy(set, where, record);
    }

    @Override
    public String findNameById(Integer id) {
        String sql = "select name from tb_group where id = #{id}";
        Group group = this.findFirst(sql, Record.create().set("id", id));
        if (group == null) return "";
        return group.getName();
    }

    @Override
    public List<Groupvo> findByKeyWord(Integer userId, String keyword) {
        return null;
    }

    /**
     * 退出组织
     *
     * @param groupId 组织ID
     * @param userId  用户ID
     */
    @Transactional
    @Override
    public void out(Integer groupId, Integer userId) {
        // 1.删除tb_user_group相关信息
        // 2.删除tb_group_approval相关信息
        String where = "group_id = #{groupId} and user_id = #{userId}";
        Record record = Record.create().set("groupId", groupId).set("userId", userId);
        userGroupService.deleteBy(where, record);
        groupApprovalService.deleteBy(where, record);
    }

    @Override
    public Groupvo findGroupWithGa(Integer groupId, Integer userId) {
        String sql = Blade.dao().getScript("Group.groupDetailWithUa").getSql();
        Record record = Db.init().selectOne(sql, Record.create().set("groupId", groupId).set("userId", userId));
        int isjoin;
        if (record.get("status") == null || Integer.parseInt(record.get("status").toString()) == 1
                || Integer.parseInt(record.get("status").toString()) == 3
                || Integer.parseInt(record.get("status").toString()) == 4) {
            isjoin = 0;
        } else {
            isjoin = 1;
        }
        Groupvo groupvo = Convert.recordToGroupVo(record);
        groupvo.setIsjoin(isjoin);
        return groupvo;
    }
}
