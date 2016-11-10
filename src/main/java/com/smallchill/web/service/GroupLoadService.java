package com.smallchill.web.service;

import com.smallchill.core.base.service.IService;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.web.model.GroupLoad;

/**
 * Created by Administrator on 2016/11/9.
 */
public interface GroupLoadService extends IService<GroupLoad> {

    Integer loadSave(Integer groupId, Integer id);
}
