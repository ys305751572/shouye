/**
 * Copyright (c) 2015-2016, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smallchill.system.controller;

import com.smallchill.core.constant.ConstErrorMsg;
import com.smallchill.system.service.DictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.CacheKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.system.model.Dict;

import java.util.List;

@Controller
@RequestMapping("/dict")
public class DictController extends BaseController implements ConstErrorMsg {
    private static String LIST_SOURCE = "Dict.list";
    private static String BASE_PATH = "/system/dict/";
    private static String CODE = "dict";
    private static String PERFIX = "tfw_dict";

    @Autowired
    DictService dictService;

    @RequestMapping("/")
    public String index(ModelMap mm) {
        mm.put("code", CODE);
        return BASE_PATH + "dict.html";
    }


    @ResponseBody
    @RequestMapping(KEY_LIST)
    public Object list() {
        Object gird = paginate(LIST_SOURCE);
        return gird;
    }

    @RequestMapping(KEY_ADD)
    public String add(ModelMap mm) {
        mm.put("code", CODE);
        mm.put("dictcode",findLastCode());
        return BASE_PATH + "dict_add.html";
    }

    @RequestMapping(KEY_ADD + "/{id}")
    public String add(@PathVariable String id, ModelMap mm) {
        if (StrKit.notBlank(id)) {
            Dict dict = Blade.create(Dict.class).findById(id);
            mm.put("dictcode", dict.getCode());
            mm.put("pId", id);
            mm.put("num", findLastNum(dict.getCode()));
        }
        mm.put("code", CODE);
        return BASE_PATH + "dict_add.html";
    }

    @RequestMapping(KEY_EDIT + "/{id}")
    public String edit(@PathVariable String id, ModelMap mm) {
        Dict dict = Blade.create(Dict.class).findById(id);
        mm.put("model", JsonKit.toJson(dict));
        mm.put("code", CODE);
        return BASE_PATH + "dict_edit.html";
    }

    @RequestMapping(KEY_VIEW + "/{id}")
    public String view(@PathVariable String id, ModelMap mm) {
        Blade blade = Blade.create(Dict.class);
        Dict dict = blade.findById(id);
        Dict parent = blade.findById(dict.getPid());
        String pName = (null == parent) ? "" : parent.getName();
        Record rd = Record.parse(dict);
        rd.set("pName", pName);
        mm.put("model", JsonKit.toJson(rd));
        mm.put("code", CODE);
        return BASE_PATH + "dict_view.html";
    }

    @ResponseBody
    @RequestMapping(KEY_SAVE)
    public AjaxResult save() {
        Dict dict = mapping(PERFIX, Dict.class);
        if(dict.getPid() == null && codeIsExist(dict.getCode())) {
            return error(CODE_EXIST);
        }

        dict.setPid(dict.getPid() == null ? 0 : dict.getPid());
        boolean temp = Blade.create(Dict.class).save(dict);
        if (temp) {
            CacheKit.removeAll(DICT_CACHE);
            CacheKit.removeAll(DIY_CACHE);
            return success(SAVE_SUCCESS_MSG);
        } else {
            return error(SAVE_FAIL_MSG);
        }
    }

    @ResponseBody
    @RequestMapping(KEY_UPDATE)
    public AjaxResult update() {
        String pid = this.getRequest().getParameter("tfw_dict.pid");
        Dict dict = mapping(PERFIX, Dict.class);
//        if(dict.getId() == null && dict.getPid() == null && codeIsExist(dict.getCode())) {
//            return error(CODE_EXIST);
//        }
        dict.setVersion(getParameterToInt("VERSION", 0) + 1);
        dict.setPid(dict.getPid() == null ? 0 : dict.getPid());
        boolean temp = Blade.create(Dict.class).update(dict);
        if (temp) {
            CacheKit.removeAll(DICT_CACHE);
            CacheKit.removeAll(DIY_CACHE);
            return success(UPDATE_SUCCESS_MSG);
        } else {
            return error(UPDATE_FAIL_MSG);
        }
    }

    @ResponseBody
    @RequestMapping(KEY_REMOVE)
    public AjaxResult remove(@RequestParam String ids) {
        int cnt = Blade.create(Dict.class).deleteByIds(ids);
        if (cnt > 0) {
            CacheKit.removeAll(DICT_CACHE);
            return success(DEL_SUCCESS_MSG);
        } else {
            return error(DEL_FAIL_MSG);
        }
    }

    /**
     * code是否存在
     * @param code
     * @return
     */
    private boolean codeIsExist(Integer code) {
        return Blade.create(Dict.class).isExist("select * from tfw_dict d where d.code = #{code} limit 1",Record.create().set("code",code));
    }

    private int findLastCode() {
        Blade blade = Blade.create(Dict.class);
        List<Dict> dicts = blade.findTop(1,"select * from tfw_dict order by code desc");
        if(dicts != null && dicts.size() > 0) {
            return dicts.get(0).getCode() + 1;
        }
        return 101;
    }

    private int findLastNum(Integer code) {
        try {
            Blade blade = Blade.create(Dict.class);
            Dict dict = blade.findFirstBy("code = #{code} order by num desc", Record.create().set("code", code));
            return dict.getNum() + 1;
        } catch (Exception ex) {
            return 1;
        }
    }

    /**
     * 根据PID获取list
     */
    @RequestMapping(value = "/findChild")
    @ResponseBody
    public List findChild(Integer id){
        List list = dictService.findChild(id);
        return list;
    }
}
