package com.smallchill.api.function.controller;

import com.smallchill.api.common.model.Result;
import com.smallchill.api.function.meta.consts.ConstShouPage;
import com.smallchill.api.function.modal.vo.Groupvo;
import com.smallchill.api.function.modal.vo.ShouPageVo;
import com.smallchill.api.function.modal.vo.UserVo;
import com.smallchill.api.function.service.ShoupageService;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.interfaces.ILoader;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.kit.CacheKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.web.service.UserInfoService;
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
public class ShouPageyApi extends BaseController implements ConstCache, ConstShouPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShouPageyApi.class);

    @Autowired
    private ShoupageService shoupageService;
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 手页录首页
     *
     * @return result
     */
    @RequestMapping(value = "/index")
    @ResponseBody
    public String index(Integer userId, Integer domain, Integer city, Integer grouping, String keyWord) {
        final Integer userid = userId;
        final Integer domainid = domain;
        final Integer cityid = city;
        final Integer groupingid = grouping;
//        ShouPageVo vo = CacheKit.get(DIY_CACHE, INDEX + userId + "_" + domainId + "_" + city + "_grouping" + grouping, new ILoader() {
//            @Override
//            public Object load() {
//                return shoupageService.index(userid, domainid, cityid, groupingid);
//            }
//        });

//        boolean hasDefaultGrouping = userInfoService.isHaveDefaultGrouping(userId);
//        if (!hasDefaultGrouping) {
//            userInfoService.createDefaultGrouping(userId);
//        }

        ShouPageVo vo = shoupageService.index(userid, domainid, cityid, groupingid, keyWord);
        List<Record> list = userInfoService.findIndexGrouping(userId);
        vo.getGroupings().addAll(list);
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
//        resultMap = CacheKit.get(DIY_CACHE, SHOUPAGE_NEW + userId, new ILoader() {
//            @Override
//            public Object load() {
//                return shoupageService.listNew(userid);
//            }
//        });
        resultMap = shoupageService.listNew(userid);
        return success(resultMap, "newFriend");
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
//        Map<String, Object> resultMap = CacheKit.get(DIY_CACHE, SHOUPAGE_INTERESTE + userId, new ILoader() {
//            @Override
//            public Object load() {
//                return shoupageService.listIntereste(userid);
//            }
//        });
        Map<String, Object> resultMap = shoupageService.listIntereste(userid);
        return success(resultMap, "intereste");
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
//        List<UserVo> list = CacheKit.get(DIY_CACHE, SHOUPAGE_INTERESTED + userid, new ILoader() {
//            @Override
//            public Object load() {
//                return shoupageService.listInterested(userid);
//            }
//        });
        List<UserVo> list = shoupageService.listInterested(userid);
        return success(list);
    }

    /**
     * 我的熟人
     *
     * @param userId 用户ID
     * @return result
     */
    @RequestMapping(value = "/acquaintances")
    @ResponseBody
    public String acquaintances(Integer userId) {
        final Integer userid = userId;
//        List<UserVo> list = CacheKit.get(DIY_CACHE, SHOUPAGE_ACQUAINTANCES + userId, new ILoader() {
//            @Override
//            public Object load() {
//                return shoupageService.listAcquaintances(userid);
//            }
//        });
        List<UserVo> list =  shoupageService.listAcquaintances(userid, null);
        return success(list);
    }

    /**
     * 我的组织
     *
     * @param userId 用户ID
     * @return result
     */
    @RequestMapping(value = "/groups")
    @ResponseBody
    public String groups(Integer userId) {
        final Integer userid = userId;
//        List<Groupvo> list = CacheKit.get(DIY_CACHE, SHOUPAGE_PAGE + userId, new ILoader() {
//            @Override
//            public Object load() {
//                return shoupageService.listGroup(userid);
//            }
//        });
        List<Groupvo> list = shoupageService.listGroup(userid);
        return success(list);
    }
}
