package com.smallchill.api.system.controller;

import com.smallchill.api.system.model.PayConfig;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.toolbox.LeomanKit;
import com.smallchill.web.service.GroupExtendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 支付API
 * Created by yesong on 2016/10/27 0027.
 */
@RequestMapping(value = "/api/pay")
@Controller
public class PayApi extends BaseController {

    @Autowired
    private GroupExtendService groupExtendService;

    /**
     * 获取支付信息
     *
     * @param groupId 组织ID
     * @return result
     */
    @RequestMapping(value = "/info")
    @ResponseBody
    public String payInfo(Integer groupId) {
        int cost = groupExtendService.getCost(groupId);
        Map<String, Object> configMap = PayConfig.config(this.getRequest(), this.getResponse(), LeomanKit.generateUUID(), (cost * 1.0), PayConfig.WEIXIN);
        return success(configMap);
    }

    @RequestMapping(value = "/notify/weixin")
    public void notifyWeixin() {
        Map<String, Object> result = parse(this.getRequest());
        String status = result.get("result_code").toString();
        if ("SUCCESS".equals(status)) {
            System.out.println("SUCCESS");
            String sn = result.get("out_trade_no").toString();
            try {
//                service.payComplate(sn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
