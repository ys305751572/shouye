package com.smallchill.api.function.controller;

import com.smallchill.api.common.plugin.CommonUtils;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.kit.CommonKit;
import com.smallchill.core.toolbox.kit.DateTimeKit;
import com.smallchill.web.model.Feekback;
import com.smallchill.web.service.FeekbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 意见反馈API
 * Created by yesong on 2016/11/27.
 */
@RequestMapping(value = "/api/feedback")
@Controller
public class FeedbackApi extends BaseController {

    @Autowired
    private FeekbackService feekbackService;

    /**
     * 新增反馈
     * @param userId 当前用户ID
     * @param content 反馈内容
     * @return result
     */
    @PostMapping(value = "/create")
    @ResponseBody
    public String create(Integer userId, String content) {
        Feekback fb = new Feekback();
        fb.setContent(content);
        fb.setUserId(userId);
        fb.setRno(CommonKit.getNo());
        fb.setAdminId(0);
        fb.setCreateTime(DateTimeKit.nowLong());
        try {
            feekbackService.save(fb);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success();
    }

    /**
     * 反馈列表
     * @param userId 当前用户ID
     * @return result
     */
    @ResponseBody
    @PostMapping(value = "/list")
    public String list(Integer userId) {
        List<Feekback> list;
        try {
            list = feekbackService.findBy("user_id = #{userId}", Record.create().set("userId", userId));
            for (Feekback feekback : list) {
                feekback.setAdminId(null);
                feekback.setRno(null);
                feekback.setUserId(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success(list);
    }
}
