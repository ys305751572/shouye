package com.smallchill.api.function.controller;

import com.smallchill.api.common.model.Result;
import com.smallchill.api.function.modal.vo.ShouPageVo;
import com.smallchill.api.function.service.ShoupageService;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.ConstCache;
import com.smallchill.core.interfaces.ILoader;
import com.smallchill.core.toolbox.kit.CacheKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 社区（手页录）api
 * Created by yesong on 2016/11/1 0001.
 */
@RequestMapping(value = "/api/shou")
@Controller
public class ShouPageyApi extends BaseController implements ConstCache{

    private static final Logger LOGGER = LoggerFactory.getLogger(ShouPageyApi.class);

    @Autowired
    private ShoupageService shoupageService;

    /**
     * 手页录首页
     * @return result
     */
    @RequestMapping(value = "/index")
    @ResponseBody
    public String index(Integer userId) {
        final Integer userid = userId;
        ShouPageVo vo = CacheKit.get(DIY_CACHE , "shoupage_" + userId, new ILoader() {
            @Override
            public Object load() {
                ShouPageVo vo = shoupageService.index(userid);
                LOGGER.info("json:" + JsonKit.toJson(Result.success(vo, "shoupage")));
                return vo;
            }
        });

        return success(vo, "shoupage");
    }

    /**
     * 新结识
     * @return
     */
    @RequestMapping(value = "/new")
    @ResponseBody
    public String newFriend() {
        return null;
    }

    /**
     * 我感兴趣的用户和组织
     * @return result
     */
    @RequestMapping(value = "/intereste")
    @ResponseBody
    public String intereste() {
        return null;
    }

    /**
     * 被谁感兴趣
     * @return result
     */
    @RequestMapping(value = "/interested")
    @ResponseBody
    public String interested() {
        return null;
    }

    public String acquaintances() {
        return null;
    }
}
