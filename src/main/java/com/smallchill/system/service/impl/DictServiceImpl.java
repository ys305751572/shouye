package com.smallchill.system.service.impl;

import com.smallchill.core.base.service.BaseService;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import com.smallchill.system.model.Dict;
import com.smallchill.system.service.DictService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/11/10.
 */
@Service
public class DictServiceImpl extends BaseService<Dict> implements DictService {

    @Override
    public List findDomains() {
        List list= Db.init().selectList("SELECT * FROM tfw_dict WHERE CODE = '906' AND NUM <> '0' ");
        return list;
    }

    @Override
    public List findChild(Integer id) {
        List list= Db.init().selectList("SELECT * FROM tfw_dict WHERE PID = #{id}", Record.create().set("id",id));
        return list;
    }

    @Override
    public List findProfessional() {
        List list = Db.init().selectList("SELECT * FROM tfw_dict WHERE CODE = '910' AND NUM <> '0' ");
        return list;
    }
}
