package com.smallchill.web.service.impl;

import com.smallchill.core.base.service.BaseService;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.web.model.GroupLoad;
import com.smallchill.web.service.GroupLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2016/11/9.
 */
@Service
public class GroupLoadServiceImpl extends BaseService<GroupLoad> implements GroupLoadService {

    @Autowired
    private GroupLoadService groupLoadService;


    @Override
    @Transactional
    public Integer loadSave(Integer groupId, Integer id) {
        GroupLoad _groupLoad = findFirstBy("group_id = #{groupId}", Record.create().set("groupId", groupId));
        if(_groupLoad != null){
            return 2;
        }
        GroupLoad groupLoad = findFirstBy("id = #{id}", Record.create().set("id", id));
        if(groupLoad !=null){
            deleteBy("id = #{id}", Record.create().set("id", id));
        }
        groupLoad = new GroupLoad();
        groupLoad.setId(id);
        groupLoad.setGroupId(groupId);
        save(groupLoad);
        return 1;
    }
}
