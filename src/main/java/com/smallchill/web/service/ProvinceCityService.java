package com.smallchill.web.service;

import com.smallchill.core.base.service.IService;
import com.smallchill.web.model.ProvinceCity;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator
 * on 2016/11/28.
 */
public interface ProvinceCityService extends IService<ProvinceCity> {

    List<Map<String, Object>> province();

    List<Map<String, Object>> city();
}
