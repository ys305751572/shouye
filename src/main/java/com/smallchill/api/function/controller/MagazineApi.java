package com.smallchill.api.function.controller;

import com.smallchill.api.common.exception.MagazineHasSubscribeException;
import com.smallchill.api.common.kit.ExcludeParams;
import com.smallchill.api.common.model.ErrorType;
import com.smallchill.api.function.meta.intercept.MagazineInfoIntercept;
import com.smallchill.api.function.meta.other.MagazineInfoConvert;
import com.smallchill.api.function.meta.validate.MagazineIdValidate;
import com.smallchill.api.function.meta.validate.UserIdAndMagazineIdValidate;
import com.smallchill.api.function.modal.vo.MaganizeInfoVo;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.web.service.MaganizeService;
import com.smallchill.web.service.MaganizeSubscribeService;
import com.smallchill.web.service.MagazineInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 杂志API
 * Created by yesong on 2017/1/13 0013.
 */
@RequestMapping(value = "/api/magazine")
@Controller
public class MagazineApi extends BaseController {

    @Autowired
    MagazineInfoService magazineInfoService;
    @Autowired
    MaganizeSubscribeService maganizeSubscribeService;
    @Autowired
    MaganizeService maganizeService;

    /**
     * 杂志列表
     *
     * @param userId 当前用户ID
     * @param status 订阅状态 1:已订阅 2:未订阅
     * @return page
     */
    @PostMapping(value = "/list")
    @ResponseBody
    public String list() {
        JqGrid page = apiPaginate("MagazineInfo.list", new MagazineInfoIntercept().addRecord(
                Record.create().set("status", this.getRequest().getParameter("status"))
                        .set("userId", this.getParameter("userId"))
                        .set("pid", this.getParameter("pid")).set("domainId", this.getParameter("domainId"))),
                ExcludeParams.create().set("userId").set("status").set("pid").set("domainId"));
        page.setRows(MagazineInfoConvert.recordToVos(page.getRows()));
        return success(page);
    }

    /**
     * 杂志详情
     * @param magazineId 杂志ID
     * @return result
     */
    @PostMapping(value = "/detail")
    @ResponseBody
    @Before(MagazineIdValidate.class)
    public String detail(Integer magazineId) {
        String sql = Blade.dao().getScript("MagazineInfo.detail").getSql();
        return success(MagazineInfoConvert.recordToVo(Db.init().selectOne(sql, Record.create().set("id", magazineId))));
    }

    /**
     * 订阅杂志
     *
     * @param userId     当前用户ID
     * @param magazineId 杂志ID
     * @return result
     */
    @PostMapping(value = "subscribe")
    @ResponseBody
    @Before(UserIdAndMagazineIdValidate.class)
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
            return fail(ErrorType.ERROR_CODE_APP_MAGAZINE_HASNOTSUBSCRIBE_FAIL);
        } catch (Exception e) {
            return fail();
        }
        return success();
    }

    /**
     * 我的杂志
     *
     * @param userId 当前用户ID
     * @return result
     */
    @PostMapping(value = "/mylist")
    @ResponseBody
    public String listByUserId(Integer userId) {
        try {
            String sql = Blade.dao().getScript("MaganizeSubscribe.listByUserId").getSql();
            List<Record> list = Db.init().selectList(sql, Record.create().set("userId", userId));
            List<MaganizeInfoVo> list2 = MagazineInfoConvert.myRecordToVos(list);
            return success(list2);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
    }

    /**
     * 根据杂志ID查询文章列表 按照每一天分组
     *
     * @param magazineId 杂志ID
     * @param pagenum    当前页
     * @param pagesize   每页显示数量
     * @return result
     */
    @PostMapping(value = "/list/id")
    @ResponseBody
    @Before(MagazineIdValidate.class)
    public String listById(Integer magazineId, Integer pagenum, Integer pagesize) {
        Map<String, Object> resultMap;
        try {
            resultMap = maganizeService.listById(magazineId, pagenum, pagesize);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(resultMap, "magazines");
    }
}