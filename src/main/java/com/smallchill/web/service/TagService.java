package com.smallchill.web.service;

import com.smallchill.core.base.service.IService;
import com.smallchill.web.model.Tag;
import com.smallchill.web.model.UserTag;

/**
 * Created by Administrator on 2016/11/22.
 */
public interface TagService extends IService<Tag>{

    void tag_save(String vals,Integer type);
}
