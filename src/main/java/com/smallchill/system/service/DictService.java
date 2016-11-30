package com.smallchill.system.service;

import com.smallchill.core.base.service.IService;
import com.smallchill.system.model.Dict;

import java.util.List;

/**
 * Created by Administrator on 2016/11/10.
 */
public interface DictService extends IService<Dict> {

    //行业
    List findDomains();

    //职业
    List findProfessional();

    List findChild(Integer id);

}
