package com.smallchill.api.function.controller;

import com.smallchill.api.function.meta.validate.UserIdValidate;
import com.smallchill.api.function.service.ShieldingService;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.grid.JqGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 屏蔽API
 * Created by yesong on 2017/2/6 0006.
 */
@Controller
@RequestMapping(value = "/api/as")
public class ArticleShieldingApi extends BaseController {

    @Autowired
    private ShieldingService shieldingService;

    /**
     * 屏蔽发布者
     *
     * @param id     数据主键ID
     * @param userId 当前用户ID
     * @return result
     */
    @PostMapping(value = "/shielding")
    @ResponseBody
    @Before(UserIdValidate.class)
    public String shielding(Integer id, Integer userId) {
        try {
            shieldingService.shieldingByArticleShowId(id, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success();
    }

    /**
     * 取消屏蔽
     *
     * @param ids 屏蔽列表主键ID
     * @return result
     */
    @PostMapping(value = "/canel/shielding")
    @ResponseBody
    @Before(UserIdValidate.class)
    public String canelShielding(String ids) {
        try {
            shieldingService.deleteByIds(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return fail();
        }
        return success();
    }

    /**
     * 我的屏蔽列表
     *
     * @param userId 当前用户ID
     * @return result
     */
    @PostMapping(value = "/list/shielding")
    @ResponseBody
    public String listShielding(Integer userId) {
        JqGrid jqGrid = apiPaginate("Shielding.listByUserId");
        List<Record> list = shieldingService.listByUserId(jqGrid.getRows());
        jqGrid.setRows(list);
        return success(jqGrid);
    }
}
