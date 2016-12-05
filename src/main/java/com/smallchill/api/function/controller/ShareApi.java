package com.smallchill.api.function.controller;

import com.smallchill.api.function.meta.other.Convert;
import com.smallchill.api.function.modal.vo.UserVo;
import com.smallchill.core.toolbox.Record;
import com.smallchill.web.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * Created by yesong on 2016/12/5 0005.
 */
@RequestMapping(value = "/api/share")
@Controller
public class ShareApi {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 跳转分享页
     * @param userId 当前用户ID
     * @param model model
     * @return 分享页地址
     */
    @RequestMapping(value = "/userinfindex")
    public String toShare(Integer userId, Model model) {
        Record record = userInfoService.findUserInfoDetail(userId);
        try {
            UserVo vo = Convert.recordToVo(record);
            vo.setUserExtendVo(userInfoService.findUserExtendVo(record));
            model.addAttribute("userVo", vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/web/share/index.html";
    }
}
