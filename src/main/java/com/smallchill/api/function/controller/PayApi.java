package com.smallchill.api.function.controller;

import com.smallchill.api.common.exception.*;
import com.smallchill.api.common.model.ErrorType;
import com.smallchill.api.function.service.PayService;
import com.smallchill.common.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 支付API
 * Created by yesong on 2016/12/3 0003.
 */
@RequestMapping(value = "/api/pay")
@Controller
public class PayApi extends BaseController {

    @Autowired
    private PayService payService;

    @PostMapping(value = "/joingroup/prepayid/get")
    @ResponseBody
    public String getPrepayIdOfJoingroup(Integer userId, Integer groupId, Integer cost, java.lang.String validateInfo,
                                         java.lang.String matchType) {

        Map<String, Object> resultMap;
        try {
            resultMap = payService.getPrepayId(userId, groupId, cost, validateInfo, matchType, this.getResponse(), this.getRequest());
        } catch (GroupCostException e) {
            return fail(ErrorType.ERROR_CODE_APP_CANNOT_JOIN_GROUP_FAIL);
        } catch (UserHasApprovalException e) {
            return fail(ErrorType.ERROR_CODE_USERHASAPPROVAL);
        } catch (UserHasJoinGroupException e) {
            return fail(ErrorType.ERROR_CODE_USERHASJOIN);
        } catch (UserInOthersBlankException e) {
            return fail(ErrorType.ERROR_CODE_USERINBLANK);
        } catch (GroupLimitException e) {
            return fail(ErrorType.ERROR_CODE_SERVER_EXCEPTION);
        }
        return success(resultMap, "prepayIdConfig");
    }

    /**
     * 加入群-支付回调
     */
    @PostMapping(value = "/weixin/group/join/notify")
    public void wxGroupJoinNotify() {
        payService.joinGroupWxNotify(this.getRequest(), this.getResponse());
    }

    /**
     * 增值服务--获取预支付款ID
     *
     * @param userId 当前用户ID
     * @param type   增值类型 1: 感兴趣人数 2: 熟人人数
     * @param number 增加数量
     * @param money  金额
     * @return result
     */
    @PostMapping(value = "/valueadd/peepayid/get")
    @ResponseBody
    public String getPrepayIdOfValueaddService(Integer userId, Integer type, Integer number, Integer money) {

        Map<String, Object> resultMap = null;
        try {
            resultMap = payService.getPrepayIdOfValueaddService(userId, type, number, money,
                    this.getResponse(), this.getRequest());
        } catch (UserInfoExtendException e) {
            return fail(ErrorType.ERROR_CODE_APP_PAYERROR_REFUND_FAIL);
        }
        return success(resultMap, "prepayIdConfig");
    }

    @PostMapping(value = "/weixin/valueadd/notify")
    public void wxValueaddNotify() {
        payService.valueaddServiceWxNotify(this.getRequest(), this.getResponse());
    }
}
