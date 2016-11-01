package com.smallchill.api.function.service;

import com.smallchill.api.function.modal.vo.Groupvo;
import com.smallchill.api.function.modal.vo.InteresteGroupAndUser;
import com.smallchill.api.function.modal.vo.ShouPageVo;
import com.smallchill.api.function.modal.vo.UserVo;

import java.util.List;

/**
 *
 * Created by yesong on 2016/11/1 0001.
 */
public interface ShoupageService {

    ShouPageVo index(Integer userId);

    List<UserVo> friends(Integer userId);

    int countNew(Integer userId);

    List<UserVo> listNew(Integer userId);

    int countIntereste(Integer userId);

    InteresteGroupAndUser listIntereste(Integer userId);

    int countInterested(Integer userId);

    List<UserVo> listInterested(Integer userId);

    int countAcquaintances(Integer userId);

    List<UserVo> listAcquaintances(Integer userId);

    int countGroup(Integer userId);

    List<Groupvo> listGroup(Integer userId);
}
