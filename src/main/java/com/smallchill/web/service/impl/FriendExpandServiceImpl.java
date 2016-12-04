package com.smallchill.web.service.impl;

import com.smallchill.api.function.meta.consts.StatusConst;
import com.smallchill.core.base.service.BaseService;
import com.smallchill.core.toolbox.Record;
import com.smallchill.web.model.FriendExpand;
import com.smallchill.web.service.FriendExpandService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator
 * on 2016/12/1.
 */
@Service
public class FriendExpandServiceImpl extends BaseService<FriendExpand> implements FriendExpandService, StatusConst {

    @Override
    public FriendExpand findInterestConfig() {
        return findByType(VALUE_ADD_SERVICE_TYPE_INTEREST);
    }

    @Override
    public FriendExpand findAcquaintanceConfig() {
        return findByType(VALUE_ADD_SERVICE_TYPE_ACQUAINTANCE);
    }

    public FriendExpand findByType(int type) {
        return this.findFirstBy("type = #{type}", Record.create().set("type", type));
    }
}
