package com.smallchill.api.function.controller;

import com.smallchill.common.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 社区（手页录）api
 * Created by yesong on 2016/11/1 0001.
 */
@RequestMapping(value = "/api/shou")
@Controller
public class ShouPageyApi extends BaseController{

    /**
     * 手页录首页
     * @return result
     */
    @RequestMapping(value = "/index")
    @ResponseBody
    public String index() {

        return null;
    }

    /**
     * 新结识
     * @return
     */
    @RequestMapping(value = "/new")
    @ResponseBody
    public String newFriend() {
        return null;
    }

    /**
     * 我感兴趣的用户和组织
     * @return result
     */
    @RequestMapping(value = "/intereste")
    @ResponseBody
    public String intereste() {
        return null;
    }

    /**
     * 被谁感兴趣
     * @return result
     */
    @RequestMapping(value = "/interested")
    @ResponseBody
    public String interested() {
        return null;
    }

    public String acquaintances() {
        return null;
    }
}
