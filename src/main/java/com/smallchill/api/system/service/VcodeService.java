package com.smallchill.api.system.service;

/**
 * Created by Administrator on 2016/6/13.
 */
public interface VcodeService {

    public Boolean sendKX(String code, String... mobiles);

    public Boolean sendYP(String code, String... mobiles);

    public Boolean validate(String mobile, String code);
}
