package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.web.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 *
 * Created by shilong on 2016/11/11.
 */
@Controller
@RequestMapping("/report")
public class ReportController extends BaseController {
    private static String CODE = "report";
    private static String PERFIX = "tb_report";
    private static String LIST_SOURCE = "Report.list";
    private static String OPERATION = "Report.operation";
    private static String BASE_PATH = "/web/report/";

    @Autowired
    ReportService reportService;

    //举报页面
    @RequestMapping(KEY_MAIN)
    public String reportIndex(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "report.html";
    }

    //
    @RequestMapping(value = "/statistical")
    public String statistical(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "report_statistical.html";
    }

    //操作页面
    @RequestMapping(value = "/operation")
    public String operation(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "report_operation.html";
    }

    //记录页面
    @RequestMapping(value = "/record")
    public String record(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "report_record.html";
    }

    //操作列表
    @ResponseBody
    @RequestMapping(value = "/operation_list")
    public Object operationList() {
        Object grid = paginate(OPERATION);
        return grid;
    }

    //记录列表
    @ResponseBody
    @RequestMapping(value = "/record_list")
    public Object recordList() {
        Object grid = paginate(LIST_SOURCE);
        return grid;
    }



}
