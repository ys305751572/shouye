package com.smallchill.web.service;

import com.smallchill.core.base.service.IService;
import com.smallchill.web.model.FriendExpand;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shilong
 * on 2016/12/01.
 */
public interface FriendExpandService extends IService<FriendExpand> {

    FriendExpand findInterestConfig();

    FriendExpand findAcquaintanceConfig();
}
