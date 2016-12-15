package com.smallchill.web.service;

import com.smallchill.core.base.service.IService;
import com.smallchill.web.model.Classification;
import com.smallchill.web.model.UserClassification;

/**
 * Created by Administrator on 2016/11/22.
 */
public interface ClassificationService extends IService<Classification> {

    void userClassificationAddForGroupAgree(Integer groupId, Integer userId, Integer matchType, Integer targetType);

    void classification_save(String vals,Integer type);
}
