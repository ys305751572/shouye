package com.smallchill.api.function.controller;

import com.smallchill.api.common.exception.MagazineHasSubscribeException;
import com.smallchill.api.common.model.ErrorType;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.web.service.MaganizeSubscribeService;
import com.smallchill.web.service.MagazineInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 杂志API
 * Created by yesong on 2017/1/13 0013.
 */
@RequestMapping(value = "/api/magazine")
@Controller
public class MagazineApi extends BaseController{

    @Autowired
    MagazineInfoService magazineInfoService;
    @Autowired
    MaganizeSubscribeService maganizeSubscribeService;

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
        try {
            maganizeSubscribeService.subscribe(userId, magazineId);
        } catch (MagazineHasSubscribeException e) {
            return fail(ErrorType.ERROR_CODE_APP_MAGAZINE_HASSUBSCRIBE_FAIL);
        } catch (Exception e) {
            return fail();
        }
        return success();
    }

    @PostMapping(value = "unsubscribe")
    @ResponseBody
    public String unsubscribe(Integer userId, Integer magazineId) {
        try {
            maganizeSubscribeService.unsubscribe(userId, magazineId);
        } catch (MagazineHasSubscribeException e) {
            return fail(ErrorType.ERROR_CODE_APP_MAGAZINE_HASSUBSCRIBE_FAIL);
        } catch (Exception e) {
            return fail();
        }
        return success();
    }

    /**
     * 我的杂志
     * @param userId 当前用户ID
     * @return result
     */
    @PostMapping(value = "/list/{userId}")
    @ResponseBody
    public String listByUserId(Integer userId) {
        String sql = Blade.dao().getScript("MaganizeSubscribe.listByUserId").getSql();
        List<Record> list = Db.init().selectList(sql, Record.create().set("userId", userId));
        return success(list);
    }
}