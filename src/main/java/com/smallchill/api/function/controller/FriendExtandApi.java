package com.smallchill.api.function.controller;

import com.smallchill.api.function.meta.consts.SystemConst;
import com.smallchill.api.function.meta.validate.UserIdValidate;
import com.smallchill.api.function.modal.vo.UserInfoExtendVo;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.web.model.FriendExpand;
import com.smallchill.web.model.UserInfoExtend;
import com.smallchill.web.service.FriendExpandService;
import com.smallchill.web.service.UserInfoExtendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 用户拓展信息
 * 感兴趣人数
 * 熟人人数
 * Created by yesong on 2016/12/5 0005.
 */
@RequestMapping(value = "/api/fe")
@Controller
public class FriendExtandApi extends BaseController implements SystemConst{

    @Autowired
    private FriendExpandService friendExpandService;
    @Autowired
    private UserInfoExtendService userInfoExtendService;

    /**
     *
     * @param userId 当前用户ID
     * @return result
     */
    @PostMapping(value = "/config")
    @ResponseBody
    @Before(UserIdValidate.class)
    public String config(Integer userId) {
        // 返回该用户的已购买人数、剩余人数、以及系统默认人数上限
        FriendExpand sysConfigI = friendExpandService.findInterestConfig();
        FriendExpand sysConfigA = friendExpandService.findAcquaintanceConfig();
        UserInfoExtend userInfoExtend = userInfoExtendService.findByUserId(userId);

        UserInfoExtendVo vo = new UserInfoExtendVo();
        int userInterestCount = 0;
        int userAcquaintanceCount = 0;
        if (userInfoExtend == null || userInfoExtend.getId() == null) {
            userInterestCount = INTEREST_COUNTS;
            userAcquaintanceCount = ACQUAINTANCE_COUNTS;
        }
        else {
            userInterestCount = userInfoExtend.getInterestCount() + INTEREST_COUNTS;
            userAcquaintanceCount = userInfoExtend.getAcquaintanceCount() + ACQUAINTANCE_COUNTS;
        }
        vo.setUserInterestCount(userInterestCount >= sysConfigI.getNum() ? sysConfigI.getNum() : userInterestCount);
        vo.setUserAcquaintanceCount(userAcquaintanceCount >= sysConfigA.getNum() ? sysConfigA.getNum() : userAcquaintanceCount);
        vo.setSystemInterestCount(sysConfigI.getNum() + INTEREST_COUNTS);
        vo.setSystemAcquaintanceCount(sysConfigA.getNum() + ACQUAINTANCE_COUNTS);
        vo.setUserRemainInterestCount(sysConfigI.getNum() + INTEREST_COUNTS - userInterestCount);
        vo.setUserRemainAcquaintanceCount(sysConfigA.getNum() + ACQUAINTANCE_COUNTS - userAcquaintanceCount);
        vo.setSystemInterestPrice(sysConfigI.getAmount());
        vo.setSystemAcquaintancePrice(sysConfigA.getAmount());
        return success(vo);
    }
}
