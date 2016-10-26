package com.smallchill.web.service.impl;

import com.smallchill.core.toolbox.LeomanKit;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.core.toolbox.kit.DateTimeKit;
import com.smallchill.web.model.Group;
import com.smallchill.web.model.GroupBank;
import com.smallchill.web.model.GroupExtend;
import com.smallchill.web.model.vo.GroupVo;
import com.smallchill.web.service.GroupBankService;
import com.smallchill.web.service.GroupExtendService;
import com.smallchill.web.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smallchill.core.base.service.BaseService;
import org.springframework.transaction.annotation.Transactional;

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

    private void saveGroupTag(GroupVo groupVo, Integer groupId) {
        GroupExtend gg = new GroupExtend();
        gg.setGroupId(groupId);
        gg.setIdCard(LeomanKit.generateUUID()); // 后台自动生成
        gg.setPassword(groupVo.getPassword());
        gg.setCode(groupVo.getCode());
        gg.setLicense(groupVo.getLicense());
        gg.setArtificialPersonName(groupVo.getArtificialPersonName());
        gg.setArtificialPersonIdcard(groupVo.getArtificialPersonIdcard());
        gg.setArtificialPersonMobile(groupVo.getArtificialPersonMobile());
        groupExtendService.save(gg);
    }

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

    private int saveGroupInfo(GroupVo groupVo) {
        Group group = new Group();
        group.setName(groupVo.getName());

        group.setType(groupVo.getType());
        group.setProvince(groupVo.getProvince());
        group.setCity(groupVo.getCity());
        group.setTargat(groupVo.getTarget());

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
        return this.saveRtId(group);
    }

    @Override
    public void editGroup(GroupVo groupVo) {

    }

    @Override
    public JqGrid page(String source) {

        return null;
    }
}
