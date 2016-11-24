package com.smallchill.api.function.service;

import com.smallchill.api.function.modal.vo.Groupvo;
import com.smallchill.api.function.modal.vo.InteresteGroupAndUser;
import com.smallchill.api.function.modal.vo.ShouPageVo;
import com.smallchill.api.function.modal.vo.UserVo;

import java.util.List;
import java.util.Map;

/**
 * 手页录
 * Created by yesong on 2016/11/1 0001.
 */
public interface ShoupageService {

    ShouPageVo index(Integer userId, Integer domainId, Integer city, Integer grouping,String keyWord);

    List<UserVo> friends(Integer userId, Integer domainId, Integer city, Integer grouping,String keyWord);

    int countNew(Integer userId, Long date);

    Map<String, List<UserVo>> listNew(Integer userId);

    int countIntereste(Integer userId, Long date);

    Map<String, Object> listIntereste(Integer userId);

    int countInterested(Integer userId, Long date);

    List<UserVo> listInterested(Integer userId);

    int countAcquaintances(Integer userId, Long date);

    List<UserVo> listAcquaintances(Integer userId);

    int countGroup(Integer userId, Long date);

    List<Groupvo> listGroup(Integer userId);
}
