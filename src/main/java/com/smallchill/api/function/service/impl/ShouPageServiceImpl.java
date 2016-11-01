package com.smallchill.api.function.service.impl;

import com.smallchill.api.function.modal.vo.Groupvo;
import com.smallchill.api.function.modal.vo.InteresteGroupAndUser;
import com.smallchill.api.function.modal.vo.ShouPageVo;
import com.smallchill.api.function.modal.vo.UserVo;
import com.smallchill.api.function.service.ShoupageService;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 手页录api
 * Created by yesong on 2016/11/1 0001.
 */
@Service
public class ShouPageServiceImpl implements ShoupageService{

    @Override
    public ShouPageVo index(Integer userId) {
        return null;
    }

    @Override
    public List<UserVo> friends(Integer userId) {
        String sql = Blade.dao().getScript("UserFriend.list").getSql();
        System.out.println("sql:" + sql);
        return null;
    }

    @Override
    public int countNew(Integer userId) {
        return 0;
    }

    @Override
    public List<UserVo> listNew(Integer userId) {
        return null;
    }

    @Override
    public int countIntereste(Integer userId) {
        return 0;
    }

    @Override
    public InteresteGroupAndUser listIntereste(Integer userId) {
        return null;
    }

    @Override
    public int countInterested(Integer userId) {
        return 0;
    }

    @Override
    public List<UserVo> listInterested(Integer userId) {
        return null;
    }

    @Override
    public int countAcquaintances(Integer userId) {
        return 0;
    }

    @Override
    public List<UserVo> listAcquaintances(Integer userId) {
        return null;
    }

    @Override
    public int countGroup(Integer userId) {
        return 0;
    }

    @Override
    public List<Groupvo> listGroup(Integer userId) {
        return null;
    }
}
