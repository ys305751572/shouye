package com.smallchill.api.function.controller;

import com.smallchill.api.function.service.ShieldingService;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.toolbox.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 屏蔽API
 * Created by yesong on 2017/2/6 0006.
 */
@Controller
@RequestMapping(value = "/api/as")
public class ArticleShieldingApi extends BaseController {

    @Autowired
    private ShieldingService shieldingService;

    /**
     * 屏蔽列表
     *
     * @param userId 当前用户ID
     * @return result
     */
    @PostMapping(value = "/list/{userId}")
    @ResponseBody
    public String listByUserId(Integer userId) {
        List<Record> list;
        try {
            list = shieldingService.listByUserId(userId);
        } catch (Exception e) {
            return fail();
        }
        return success(list);
    }
}
