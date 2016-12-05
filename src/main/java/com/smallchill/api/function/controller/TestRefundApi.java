package com.smallchill.api.function.controller;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.api.system.model.PayConfig;
import com.smallchill.common.base.BaseController;
import com.smallchill.common.pay.refund.MobiMessage;
import com.smallchill.common.pay.refund.RefundReqData;
import com.smallchill.common.pay.refund.RefundRequest;
import com.smallchill.common.pay.util.ConstantUtil;
import com.smallchill.core.toolbox.kit.CommonKit;
import com.smallchill.web.service.RefundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 测试退款API
 * Created by yesong on 2016/12/3 0003.
 */
@Controller
@RequestMapping(value = "/api/weixin")
public class TestRefundApi extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestRefundApi.class);

    @Autowired
    private RefundService refundService;

    /**
     * 退款
     *
     * @return result
     */
    @PostMapping(value = "/refund")
    @ResponseBody
    public String refund() {
      return success();
    }

    /**
     * 测试获取预支付款ID
     * @return result
     */
    @PostMapping(value = "/getPrepayId")
    @ResponseBody
    public String getPrepayId() {
        Map<String, Object> resultMap = PayConfig.config(this.getRequest(), this.getResponse(), CommonKit.generateSn(), 1.0, "WEIXIN");
        for (Map.Entry<String, Object> entity : resultMap.entrySet()) {
            System.out.println("key:" + entity.getKey() + "==== value:" + entity.getValue());
        }
        return success();
    }
}
