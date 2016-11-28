package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.web.meta.intercept.GroupAdminIntercept;
import com.smallchill.web.meta.intercept.GroupIntercept;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 交易流水
 * Created by shilong
 * on 2016/11/28.
 */
@Controller
@RequestMapping(value = "/groupOrder")
public class GroupOrderController extends BaseController {

    private static String LIST_SOURCE = "Order.groupOrderList";
    private static String BASE_PATH = "/web/groupOrder/";
    private static String CODE = "groupOrder";

    @RequestMapping(value = "/")
    public String groupIndex(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "groupOrder_list.html";
    }

    @ResponseBody
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object object = paginate(LIST_SOURCE, new GroupAdminIntercept());
        return object;
    }

}
