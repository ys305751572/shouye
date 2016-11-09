package com.smallchill.web.service.impl;

import com.smallchill.core.base.service.BaseService;
import com.smallchill.web.model.GroupLoad;
import com.smallchill.web.service.GroupLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/11/9.
 */
@Service
public class GroupLoadServiceImpl extends BaseService<GroupLoad> implements GroupLoadService {

    @Autowired
    private GroupLoadService groupLoadService;


}
