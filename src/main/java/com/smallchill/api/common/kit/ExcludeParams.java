package com.smallchill.api.common.kit;

import java.util.ArrayList;

/**
 * api剔除参数
 * Created by yesong on 2016/11/3 0003.
 */
public class ExcludeParams extends ArrayList<String>{

    private ExcludeParams() {}

    public static ExcludeParams create() {
        return new ExcludeParams();
    }

    public ExcludeParams set(String param) {
        this.add(param);
        return this;
    }
}
