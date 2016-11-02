package com.smallchill.api.function.service;

import com.smallchill.api.function.modal.vo.Groupvo;
import com.smallchill.api.function.modal.vo.InteresteGroupAndUser;
import com.smallchill.api.function.modal.vo.ShouPageVo;
import com.smallchill.api.function.modal.vo.UserVo;

import java.util.List;
import java.util.Map;

/**
 *
 * Created by yesong on 2016/11/1 0001.
 */
public interface ShoupageService {

    ShouPageVo index(Integer userId);

    List<UserVo> friends(Integer userId);

    int countNew(Integer userId);

    Map<String,List<UserVo>> listNew(Integer userId);

    int countIntereste(Integer userId);

    Map<String,Object> listIntereste(Integer userId);

    int countInterested(Integer userId);

    List<UserVo> listInterested(Integer userId);

    int countAcquaintances(Integer userId);

    List<UserVo> listAcquaintances(Integer userId);

    int countGroup(Integer userId);

    List<Groupvo> listGroup(Integer userId);
}
