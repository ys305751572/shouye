package com.smallchill.api.function.service;

import com.smallchill.api.function.modal.vo.UserVo;

import java.util.List;

/**
 *
 * Created by yesong on 2016/11/24 0024.
 */
public interface LocalSerivce {

    void upload(Integer userId, Double lat, Double lon);
}
