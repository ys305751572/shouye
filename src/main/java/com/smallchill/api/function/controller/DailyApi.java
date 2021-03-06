package com.smallchill.api.function.controller;

import com.smallchill.api.function.meta.other.DailyConvert;
import com.smallchill.api.function.meta.validate.DailyValidate;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.web.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 日报API
 * Created by yesong on 2017/2/4 0004.
 */
@Controller
@RequestMapping(value = "/api/daily")
public class DailyApi extends BaseController {

    @Autowired
    private DailyService dailyService;

    @PostMapping(value = "/mylist")
    @ResponseBody
    public String listByUserId(Integer userId) {
        List<Record> list;
        try {
            list = dailyService.listByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(DailyConvert.recordsToDailyVos(list));
    }

    /**
     * @param dailyId 日报ID(组织ID)
     * @return result
     */
    @PostMapping(value = "/list")
    @ResponseBody
    @Before(DailyValidate.class)
    public String listById(Integer dailyId) {
        String sql = Blade.dao().getScript("Daily.listById").getSql();
        return success(DailyConvert.recordsToDailyVos(Db.init().selectList(sql, Record.create().set("id", dailyId))));
    }

    /**
     * 日报详情
     *
     * @param dailyId 日报ID(组织ID)
     * @return result
     */
    @PostMapping(value = "/detail")
    @ResponseBody
    @Before(DailyValidate.class)
    public String detail(Integer dailyId) {
        String sql = Blade.dao().getScript("Daily.detail").getSql();
        return success(DailyConvert.recordToDailyVo(Db.init().selectOne(sql, Record.create().set("id", dailyId))));
    }
}
