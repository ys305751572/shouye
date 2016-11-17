package com.smallchill.api.function.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.toolbox.kit.DateTimeKit;
import com.smallchill.web.model.Report;
import com.smallchill.web.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 举报API
 * Created by yesong on 2016/11/17 0017.
 */
@RequestMapping(value = "/api/report")
@Controller
public class ReportApi extends BaseController{

    @Autowired
    private ReportService reportService;

    /**
     * 新增举报信息
     * @param report 举报信息
     * @return result
     */
    @PostMapping(value = "/create")
    @ResponseBody
    public String create(Report report) {
        try {
            report.setStatus(0);
            report.setCreateTime(DateTimeKit.nowLong());
            reportService.save(report);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success();
    }
}
