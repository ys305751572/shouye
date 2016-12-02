package com.smallchill.api.function.service;

import com.smallchill.core.base.service.IService;
import com.smallchill.core.toolbox.Record;
import com.smallchill.web.model.UserinfoCareer;

import java.util.List;

/**
 * 用户事业状态
 * Created by yesong on 2016/11/30 0030.
 */
public interface UserCareerService extends IService<UserinfoCareer>{

    List<Record> findCareerByUserId(Integer userId);
}
