package com.smallchill.api.function.controller;

import com.smallchill.api.common.kit.ExcludeParams;
import com.smallchill.api.function.meta.intercept.AroundUserIntercept;
import com.smallchill.api.function.meta.validate.AroundUserValidate;
import com.smallchill.api.function.modal.vo.UserVo;
import com.smallchill.api.function.service.LocalSerivce;
import com.smallchill.api.function.service.ScanService;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.grid.JqGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 扫一扫API
 * Created by yesong on 2016/11/24 0024.
 */
@Controller
@RequestMapping(value = "/api/scan")
public class ScanApi extends BaseController {

    @Autowired
    private LocalSerivce localSerivce;

    @Autowired
    private ScanService scanService;
    /**
     * 上传坐标
     *
     * @return result
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public String upload(Integer userId, Double lon, Double lat) {
        try {
            localSerivce.upload(userId, lon, lat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success();
    }

    /**
     * 周围用户
     *
     * @return result
     */
    @PostMapping(value = "/list")
    @ResponseBody
    @Before(AroundUserValidate.class)
    public String list(Integer userId, Double lon, Double lat) {
        JqGrid page = apiPaginate("Scan.list", new AroundUserIntercept().addRecord(Record.create().set("userId", userId)
                .set("lon", lon).set("lat", lat)), ExcludeParams.create().set("lon").set("lat").set("userId"));
        return success(page);
    }

    /**
     * 扫描用户
     *
     * @param userId   当前用户
     * @param toUserId 目标用户
     * @return result
     */
    @PostMapping(value = "/user")
    @ResponseBody
    public String scanUser(Integer userId, Integer toUserId) {
        UserVo uservo  = scanService.scanUser(userId, toUserId);
        return success(uservo);
    }

    /**
     * 扫描组织
     *
     * @param userId  当前用户ID
     * @param groupId 组织用户ID
     * @return result
     */
    @PostMapping(value = "/group")
    @ResponseBody
    public String scanGroup(Integer userId, Integer groupId) {


        return null;
    }

    /**
     * 识别会员
     *
     * @param userId   当前用户ID
     * @param toUserId 目标用户ID
     * @return
     */
    @PostMapping(value = "/inentify/member")
    @ResponseBody
    public String identifyMember(Integer userId, Integer toUserId) {
        return null;
    }
}
