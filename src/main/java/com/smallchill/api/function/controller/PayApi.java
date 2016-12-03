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

    @PostMapping(value = "/prepayid/get")
    @ResponseBody
    public String getPrepayId(Integer userId, Integer groupId, Integer cost, java.lang.String validateInfo,
                              java.lang.String matchType) {

        Map<String, Object> resultMap = null;
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

    @PostMapping(value = "/weixin/group/join/notify")
    public void weixinNotify() {

    }
}
