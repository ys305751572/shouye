package com.smallchill.web.service.impl;

import com.smallchill.core.base.service.BaseService;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.web.model.Classification;
import com.smallchill.web.model.Group;
import com.smallchill.web.model.Tag;
import com.smallchill.web.model.UserTag;
import com.smallchill.web.service.ClassificationService;
import com.smallchill.web.service.TagService;
import com.smallchill.web.service.UserTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator
 * on 2016/11/22.
 */
@Service
public class TagServiceImpl extends BaseService<Tag> implements TagService {

    @Autowired
    ClassificationService classificationService;

    @Override
    @Transactional
    public void tag_save(String vals, Integer type) {
        Group group = (Group) ShiroKit.getSession().getAttribute("groupAdmin");
        String[] ss = JsonKit.parse(vals,String[].class);
        for(String s : ss){
            Tag tag = new Tag();
            tag.setTag(s);
            tag.setType(type);
            tag.setGroupId(group.getId());
            this.save(tag);
        }

        List<Tag> tags = this.findBy("group_id = #{groupId}",Record.create().set("groupId",group.getId()));
        for(Tag tag : tags){
            tag.setType(type);
            this.update(tag);
        }

        if(type==1){
            List<Classification> classifications = classificationService.findBy("group_id = #{groupId}", Record.create().set("groupId",group.getId()));
            for(Classification classification : classifications){
                classification.setType(2);
                classificationService.update(classification);
            }
        }


    }
}
