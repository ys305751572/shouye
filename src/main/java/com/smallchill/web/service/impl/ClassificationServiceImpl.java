package com.smallchill.web.service.impl;

import com.smallchill.core.base.service.BaseService;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.kit.CollectionKit;
import com.smallchill.web.model.Classification;
import com.smallchill.web.model.UserClassification;
import com.smallchill.web.service.ClassificationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator
 * on 2016/11/22.
 */
@Service
public class ClassificationServiceImpl extends BaseService<Classification> implements ClassificationService {

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
}
