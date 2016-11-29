package com.smallchill.web.service.impl;

import com.smallchill.core.base.service.BaseService;
import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.interfaces.ILoader;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.kit.CacheKit;
import com.smallchill.web.model.ProvinceCity;
import com.smallchill.web.service.ProvinceCityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator
 * on 2016/11/28.
 */
@Service
public class ProvinceCityServiceImpl extends BaseService<ProvinceCity> implements ProvinceCityService {

    @Override
    public List<Map<String, Object>> province(){
        List<Map<String, Object>> province = CacheKit.get(ConstCache.PROVINCE_CACHE, "province_all",
                new ILoader() {
                    public Object load() {
                        return Db.init().selectList(
                                "SELECT \n" +
                                        "  id AS id,\n" +
                                        "  code AS code,\n" +
                                        "  name AS name,\n" +
                                        "  parent_code AS parentCode\n" +
                                        "FROM\n" +
                                        "  tb_province_city \n" +
                                        "WHERE parent_code = '0' ");
                    }
                });
        return province;
    }
}
