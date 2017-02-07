package com.smallchill.api.function.controller;

import com.smallchill.common.base.BaseController;
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

    @PostMapping(value = "/list/{userId}")
    @ResponseBody
    public String listByUserId(Integer userId) {
        List<Record> list;
        try {
            list = dailyService.listByUserId(userId);
        } catch (Exception e) {
            return fail();
        }
        return success(list);
    }
}
