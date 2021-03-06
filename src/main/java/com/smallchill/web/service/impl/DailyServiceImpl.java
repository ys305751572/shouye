package com.smallchill.web.service.impl;

import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.web.model.Daily;
import com.smallchill.web.service.DailyService;
import org.springframework.stereotype.Service;
import com.smallchill.core.base.service.BaseService;

import java.util.List;


/**
 * Generated by Blade.
 * 2017-01-13 13:52:05
 */
@Service
public class DailyServiceImpl extends BaseService<Daily> implements DailyService {

    /**
     * 查询所有日报机构
     * 每个组织有且只有一个日报机构，（默认名字为组织名字加上【日报】）
     *
     * @return list
     */
    @Override
    public List<Record> simpleListByUserId(Integer userId) {
        String sql = Blade.dao().getScript("Daily.simpleListByUserId").getSql();
        List<Record> list = Db.init().selectList(sql, Record.create().set("userId", userId));
        for (Record record : list) {
            record.put("name", record.getStr("name") + "日报");
        }
        return list;
    }

    @Override
    public List<Record> listByUserId(Integer userId) {
        return listDaily(userId);
    }

    /**
     * 根据用户ID查询日报列表
     *
     * @param userId 当前用户ID
     * @return list
     */
    private List<Record> listDaily(Integer userId) {
        String sql = Blade.dao().getScript("Daily.listByUserId").getSql();
        return Db.init().selectList(sql, Record.create().set("userId", userId));
    }
}
