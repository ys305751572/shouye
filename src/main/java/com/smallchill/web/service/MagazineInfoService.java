package com.smallchill.web.service;

import com.smallchill.core.base.service.IService;
import com.smallchill.web.model.MagazineInfo;

import java.util.List;

/**
 * 杂志信息
 * Generated by yesong.
 * 2017-01-12 16:37:27
 */
public interface MagazineInfoService extends IService<MagazineInfo>{
    List<MagazineInfo> findAll2();
    List<MagazineInfo> findByUserId(int userId);
}
