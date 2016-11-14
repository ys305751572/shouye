package com.smallchill.api.function.modal;

import java.util.Map;

/**
 * 操作按钮
 * Created by yesong on 2016/11/14 0014.
 */
public class Button {

    private String name;
    private String url;
    private Map<String,Object> params;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
