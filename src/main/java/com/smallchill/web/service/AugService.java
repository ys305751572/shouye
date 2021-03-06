package com.smallchill.web.service;

import com.smallchill.core.base.service.IService;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.web.model.Aug;

import java.util.List;

/**
 * Generated by Blade.
 * 2016-11-08 17:59:49
 */
public interface AugService extends IService<Aug>{

    /**
     * 修改审核状态
     * @param id
     * @param status
     */
    void updateStatus(Integer id, Integer status);

    List loadOne(Integer id);


}
