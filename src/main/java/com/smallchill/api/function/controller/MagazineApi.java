package com.smallchill.api.function.controller;

import com.smallchill.common.base.BaseController;
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

    /**
     * 杂志列表
     * @return result
     */
    @PostMapping(value = "/list")
    @ResponseBody
    public String list() {
        return null;
    }
}
