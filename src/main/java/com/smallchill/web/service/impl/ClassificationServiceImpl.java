package com.smallchill.web.service.impl;

import com.smallchill.core.base.service.BaseService;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.kit.CollectionKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.web.model.Classification;
import com.smallchill.web.model.Group;
import com.smallchill.web.model.Tag;
import com.smallchill.web.model.UserClassification;
import com.smallchill.web.service.ClassificationService;
import com.smallchill.web.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator
 * on 2016/11/22.
 */
@Service
public class ClassificationServiceImpl extends BaseService<Classification> implements ClassificationService {

    @Autowired
    TagService tagService;

    public void userClassificationAddForGroupAgree(int groupId, String classification) {
        UserClassification userClassification = new UserClassification();
        List<Classification> uc = this.findBy("classification = #{classification} and group_id = #{groupId}",
                Record.create().set("classification", classification).set("groupId", groupId));
        if (CollectionKit.isEmpty(uc)) {
            Classification classification1 = new Classification();
            classification1.setGroupId(groupId);
            classification1.setClassification(classification);
            this.save(classification1);
        }
    }

    @Override
    @Transactional
    public void classification_save(String vals, Integer type) {
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        String[] ss = JsonKit.parse(vals,String[].class);
        for(String s : ss){
            Classification classification = new Classification();
            classification.setClassification(s);
            classification.setGroupId(group.getId());
            classification.setType(type);
            this.save(classification);
        }

        List<Classification> classifications = this.findBy("group_id = #{groupId}",Record.create().set("groupId",group.getId()));
        for(Classification classification : classifications){
            classification.setType(type);
            this.update(classification);
        }

        if(type==1){
            List<Tag> tags = tagService.findBy("group_id = #{groupId}",Record.create().set("groupId",group.getId()));
            for(Tag tag : tags){
                tag.setType(2);
                tagService.update(tag);
            }
        }
    }
}
