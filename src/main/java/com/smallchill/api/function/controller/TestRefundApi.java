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
        //获得当前目录
//        String path = this.getRequest().getSession().getServletContext().getRealPath("/");
        String path = "D:\\10016225.p12";
        LOGGER.info("path:" + path);

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//可以方便地修改日期格式
        String outRefundNo = "NO" + dateFormat.format(now);

        //获得退款的传入参数
        String transactionID = "4008202001201609012791655620";
        String outTradeNo = "20160901141024";
        Integer totalFee = 1;
        Integer refundFee = totalFee;

        RefundReqData refundReqData = new RefundReqData(transactionID, outTradeNo, outRefundNo, totalFee, refundFee);
        String info = MobiMessage.RefundReqData2xml(refundReqData).replaceAll("__", "_");
        LOGGER.info("info:" + info);
        try {
            RefundRequest refundRequest = new RefundRequest();
            String result = refundRequest.httpsRequest(ConstantUtil.REFUNDEURL, info, path);
            LOGGER.info("result:" + result);
            Map<String, String> getMap = MobiMessage.parseXml(new String(result.getBytes(), "utf-8"));
            if ("SUCCESS".equals(getMap.get("return_code")) && "SUCCESS".equals(getMap.get("return_msg"))) {
                return success();
            } else {
                //返回错误描述
                return fail(ErrorType.ERROR_CODE_APP_PAYERROR_REFUND_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
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
