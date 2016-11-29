package com.smallchill.web.controller;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.interfaces.ILoader;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.kit.CacheKit;
import com.smallchill.web.model.ProvinceCity;
import com.smallchill.web.service.ProvinceCityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public List<ProvinceCity> city(Integer id){
        if(id==1){
            return new ArrayList<>();
        }
        ProvinceCity provinceCity = provinceCityService.findById(id);
        List<ProvinceCity> list = provinceCityService.findBy("parent_code = #{code} ", Record.create().set("code",provinceCity.getCode()));
        return list;
    }

}
