package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.web.service.TradingService;
import com.smallchill.web.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 交易流水-交易
 * Created by Administrator on 2016/11/15.
 */
@Controller
@RequestMapping(value = "/withdrawal")
public class WithdrawalController extends BaseController {
    private static String CODE = "withdrawal";
    private static String PERFIX = "tb_withdrawal";
    private static String LIST_SOURCE = "Withdrawal.list";
    private static String BASE_PATH = "/web/withdrawal/";

    @Autowired
    WithdrawalService withdrawalService;

    @RequestMapping(KEY_MAIN)
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "withdrawal_list.html";
    }

    @ResponseBody
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object grid = paginate(LIST_SOURCE);
        return grid;
    }




}
