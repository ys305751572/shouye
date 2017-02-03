package com.smallchill.api.function.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.web.service.MagazineInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 杂志API
 * Created by yesong on 2017/1/13 0013.
 */
@RequestMapping(value = "/api/magazine")
@Controller
public class MagazineApi extends BaseController{

    @Autowired
    MagazineInfoService magazineInfoService;

    /**
     * 杂志列表
     * @return result
     */
    @PostMapping(value = "/list")
    @ResponseBody
    public String list() {
        return success(magazineInfoService.findAll2());
    }

    /**
     * 订阅杂志
     * @param userId 当前用户ID
     * @param magazineId 杂志ID
     * @return result
     */
    @PostMapping(value = "subscribe")
    @ResponseBody
    public String subscribe(Integer userId, Integer magazineId) {
        return null;
    }

    @PostMapping(value = "unsubscribe")
    @ResponseBody
    public String unsubscribe(Integer userId, Integer magazineId) {
        return null;
    }
}
