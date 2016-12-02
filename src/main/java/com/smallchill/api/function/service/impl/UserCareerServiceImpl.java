package com.smallchill.api.function.service.impl;

import com.smallchill.api.function.service.UserCareerService;
import com.smallchill.core.base.service.BaseService;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.web.model.UserinfoCareer;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户事业状态serviceimlp
 * Created by yesong on 2016/11/30 0030.
 */
@Service
public class UserCareerServiceImpl extends BaseService<UserinfoCareer> implements UserCareerService {
    @Override
    public List<Record> findCareerByUserId(Integer userId) {
        String sql = Blade.dao().getScript("UserInfo.findCareerList").getSql();
        return Db.init().selectList(sql, Record.create().set("userId", userId));
    }
}
