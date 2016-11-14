package com.smallchill.api.function.controller;

import com.smallchill.api.common.model.Result;
import com.smallchill.api.function.modal.vo.Groupvo;
import com.smallchill.api.function.modal.vo.ShouPageVo;
import com.smallchill.api.function.modal.vo.UserVo;
import com.smallchill.api.function.service.ShoupageService;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.interfaces.ILoader;
import com.smallchill.core.toolbox.kit.CacheKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 社区（手页录）api
 * Created by yesong on 2016/11/1 0001.
 */
@RequestMapping(value = "/api/shou")
@Controller
public class ShouPageyApi extends BaseController implements ConstCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShouPageyApi.class);

    @Autowired
    private ShoupageService shoupageService;

    /**
     * 手页录首页
     *
     * @return result
     */
    @RequestMapping(value = "/index")
    @ResponseBody
    public String index(Integer userId) {
        final Integer userid = userId;
        ShouPageVo vo = CacheKit.get(DIY_CACHE, "shoupage_" + userId, new ILoader() {
            @Override
            public Object load() {
                ShouPageVo vo = shoupageService.index(userid);
                return vo;
            }
        });
        return success(vo, "shoupage");
    }

    /**
     * 新结识
     *
     * @return result
     */
    @RequestMapping(value = "/new")
    @ResponseBody
    public String newFriend(Integer userId) {
        final Integer userid = userId;
        Map<String, List<UserVo>> resultMap;
        resultMap = CacheKit.get(DIY_CACHE, "shoupage_new_" + userId, new ILoader() {
            @Override
            public Object load() {
                return shoupageService.listNew(userid);
            }
        });
        return success(resultMap);
    }

    /**
     * 我感兴趣的用户和组织
     *
     * @return result
     */
    @RequestMapping(value = "/intereste")
    @ResponseBody
    public String intereste(Integer userId) {
        final Integer userid = userId;
        Map<String, Object> resultMap = CacheKit.get(DIY_CACHE, "shoupage_intereste_" + userId, new ILoader() {
            @Override
            public Object load() {
                return shoupageService.listIntereste(userid);
            }
        });
        return success(resultMap);
    }

    /**
     * 被谁感兴趣
     *
     * @return result
     */
    @RequestMapping(value = "/interested")
    @ResponseBody
    public String interested(Integer userId) {
        final Integer userid = userId;
        List<UserVo> list = CacheKit.get(DIY_CACHE, "shoupage_interested_" + userid, new ILoader() {
            @Override
            public Object load() {
                return shoupageService.listInterested(userid);
            }
        });
        return success(list);
    }

    /**
     * 我的熟人
     * @param userId 用户ID
     * @return result
     */
    @RequestMapping(value = "/acquaintances")
    @ResponseBody
    public String acquaintances(Integer userId) {
        final Integer userid = userId;
        List<UserVo> list = CacheKit.get(DIY_CACHE, "shoupage_acquaintances_" + userId, new ILoader() {
            @Override
            public Object load() {
                return shoupageService.listAcquaintances(userid);
            }
        });
        return success(list);
    }

    /**
     * 我的组织
     * @param userId 用户ID
     * @return result
     */
    @RequestMapping(value = "/groups")
    @ResponseBody
    public String groups(Integer userId) {
        final Integer userid = userId;
        List<Groupvo> list = CacheKit.get(DIY_CACHE, "shoupage_acquaintances_" + userId, new ILoader() {
            @Override
            public Object load() {
                return shoupageService.listGroup(userid);
            }
        });
        return success(list);
    }
}
