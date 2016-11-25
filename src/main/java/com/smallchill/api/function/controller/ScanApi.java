package com.smallchill.api.function.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 扫一扫API
 * Created by yesong on 2016/11/24 0024.
 */
@Controller
@RequestMapping(value = "/api/scan")
public class ScanApi {

    /**
     * 上传坐标
     *
     * @return result
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public String upload(Integer userId, Double lon, Double lat) {

        return null;
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public String list() {
        return null;
    }
}
