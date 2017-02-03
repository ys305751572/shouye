package com.smallchill.api.function.controller;

import com.smallchill.api.function.meta.validate.CodeValidate;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.interfaces.ILoader;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.kit.CacheKit;
import com.smallchill.core.toolbox.kit.CollectionKit;
import com.smallchill.system.model.Dict;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.*;

/**
 * 配置访问API
 * Created by yesong on 2016/11/7 0007.
 */
@Controller
@RequestMapping(value = "/api/config")
public class ConfigApi extends BaseController{

    /**
     * @param code 数据字段code
     * code 对应关系
     *             101 : 性别
     *             904 : 年龄段
     *             905 : 事业状态
     *             906 : 行业领域
     *             907 : 单位类型
     *             908 : 组织类型
     *             909 : 举报类型
     *             910 : 职业类型
     *             926 : 内容类型
     * @return result
     */
    @RequestMapping(value = "/selectCode")
    @ResponseBody
    @Before(CodeValidate.class)
    public String selectCode(final String code) {
        List<Map<Integer,Dict>> dict = CacheKit.get(DICT_CACHE, "dict_common_api" + code,
                new ILoader() {
                    public Object load() {
//                        String sql = Blade.dao().getScript("Dict.list2").getSql();
//                        List<Record> list = Db.init().selectList(sql, Record.create().set("code", code));

                        List<Dict> list = Blade.dao().select("Dict.list2", Dict.class, Record.create().set("code", code));
                        LinkedHashMap<Integer,Dict> dictMap = new LinkedHashMap<>();
                        if (list != null && list.size() > 1) {
                            Dict root = list.get(0);
                            int rootId = root.getId();
                            for (Dict dict : list) {
                                if (dict.getPid() != 0 && dict.getPid() == rootId && dictMap.get(dict.getId()) == null ) {
                                    dictMap.put(dict.getId(), dict);
                                }
                                else {
                                    Dict superDict = dictMap.get(dict.getPid());
                                    if (superDict != null) {
                                        superDict.addDicts(dict);
                                    }
                                }
                            }
                        }
                        return mapToList(dictMap);
                    }
                });
        return success(dict);
    }

    /**
     * mapToList
     * @param map
     * @return
     */
    public List<Dict> mapToList(Map<Integer,Dict> map) {
        List<Dict> list = new ArrayList<>();
        for (Map.Entry<Integer,Dict> entity : map.entrySet()) {
            list.add(entity.getValue());
        }
        return list;
    }
}
