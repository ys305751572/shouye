package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.toolbox.Record;
import com.smallchill.web.model.ProvinceCity;
import com.smallchill.web.service.ProvinceCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 组织管理
 * Created by 史龙 on 2016/10/28.
 */
@Controller
@RequestMapping("/provinceCity")
public class ProvinceCityController extends BaseController {

    @Autowired
    ProvinceCityService provinceCityService;

    @RequestMapping(value = "city")
    @ResponseBody
    public List<ProvinceCity> city(Integer code){
        if(code==0){
            return new ArrayList<>();
        }
        List<ProvinceCity> list = provinceCityService.findBy("parent_code = #{code} ", Record.create().set("code",code));
        return list;
    }
}
