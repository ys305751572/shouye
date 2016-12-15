package com.smallchill.web.service;

import com.smallchill.core.base.service.IService;
import com.smallchill.web.model.UserClassification;

/**
 * Created by Administrator on 2016/11/22.
 */
public interface UserClassificationService extends IService<UserClassification>{

    void deleteByGroupIdAndUserId(Integer groupId, Integer userId);
}
