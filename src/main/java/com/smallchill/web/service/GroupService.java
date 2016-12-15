package com.smallchill.web.service;

import com.smallchill.api.common.exception.UserHasJoinGroupException;
import com.smallchill.api.function.modal.vo.Groupvo;
import com.smallchill.core.base.service.IService;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.web.model.Group;
import com.smallchill.web.model.vo.GroupVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Generated by Blade.
 * 2016-10-25 17:34:42
 */
public interface GroupService extends IService<Group> {

    /**
     * 保存组织信息
     *
     * @param groupVo 组织信息
     */
    void saveGroup(GroupVo groupVo);

    /**
     * 编辑组织信息
     *
     * @param groupVo 组织信息
     */
    void editGroup(GroupVo groupVo);

    JqGrid page(String source);

    /**
     * 申请审批-批准
     *
     * @param groupId 组织ID
     * @param userId  用户ID
     */
    void approval(Integer groupId, Integer userId) throws UserHasJoinGroupException;

    /**
     * 申请审批-拒绝
     *
     * @param groupId 组织Id
     * @param userId  用户ID
     */
    void refuse(Integer groupId, Integer userId);

    /**
     * 申请审批-拉黑
     * 被拉黑的用户无法再次加入组织
     *
     * @param groupId 组织ID
     * @param userId  用户ID
     */
    void blank(Integer groupId, Integer userId);

    /**
     * 申请审批-移除黑名单
     * 被移除黑名单的用户在组织里的状态改为退出
     *
     * @param groupId 组织ID
     * @param userId  用户ID
     */
    void unblank(Integer groupId, Integer userId);

    /**
     * 修改审核状态(审核用户)
     *
     * @param groupId
     * @param userId
     * @param status
     */
    void audit(Integer groupId, Integer userId, Integer status);

    /**
     * 修改审核状态(审核组织)
     *
     * @param groupId
     * @param status
     */
    void audit(Integer groupId, Integer status);

    /**
     * 发送消息
     *
     * @param id       组织ID
     * @param send     发送类型
     * @param sendTime 定时发送时间
     * @param title    标题
     * @param content  内容
     */
    Boolean sendMessage(HttpServletRequest request, String id, Integer send, String sendTime, String title, String content);

    /**
     * 改变组织封禁状态
     *
     * @param id         组织ID
     * @param bannedTime 封禁时间(状态值)
     * @param content    原因
     */
    void banned(Integer id, Integer bannedTime, String content);


    /**
     * 待审组织--行内修改备注
     *
     * @param id      组织ID
     * @param content 修改内容
     */
    void updateNote(Integer id, String content);

    /**
     * 根据ID查询组织名称
     *
     * @param id 组织ID
     * @return 组织名称
     */
    String findNameById(Integer id);

    /**
     * 根据关键字查询组织列表
     *
     * @param userId  用户ID
     * @param keyword 关键字
     * @return 组织列表
     */
    List<Groupvo> findByKeyWord(Integer userId, String keyword);

    /**
     * 退出组织
     *
     * @param groupId 组织ID
     * @param userId  用户ID
     */
    void out(Integer groupId, Integer userId);

    /**
     * 查询组织详情(带与组织关系)
     * @param groupId 组织ID
     * @param userId 用户ID
     * @return groupvo
     */
    Groupvo findGroupWithGa(Integer groupId, Integer userId);

    /**
     * 查询组织加入设置
     * @param groupId 组织ID
     * @return record
     */
    Record findGroupJoinSetting(Integer groupId);

    /**
     * 查询组织标签列表
     * @param groupId 组织ID
     * @return list
     */
    List<Record> findGroupTarget(Integer groupId);
}
