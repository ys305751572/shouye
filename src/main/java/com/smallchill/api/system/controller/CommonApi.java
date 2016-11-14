package com.smallchill.api.system.controller;

import com.qiniu.util.StringMap;
import com.smallchill.api.common.model.ErrorType;
import com.smallchill.api.common.model.Result;
import com.smallchill.api.system.model.ValidateCode;
import com.smallchill.api.system.service.VcodeService;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.constant.Cst;
import com.smallchill.core.toolbox.LeomanKit;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.file.BladeFile;
import com.smallchill.core.toolbox.file.UploadFileUtils;
import com.smallchill.core.toolbox.kit.UploadKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.Map;


/**
 * 系统方法controller
 * Created by yesong on 2016/10/24 0024.
 */
@RequestMapping("/api/common")
@Controller
public class CommonApi extends BaseController {

    @Autowired
    private VcodeService vcodeService;

    /**
     * 发送验证码
     *
     * @param mobile 手机号
     * @return result
     */
    @RequestMapping(value = "/code/send")
    @ResponseBody
    public String sendVerificationCode(String mobile) {
        String code = LeomanKit.generateCode();
        vcodeService.sendKX(code, mobile);
        ValidateCode code2 = new ValidateCode();
        code2.setCode(code);
        return success(code,"code");
    }

    /**
     * 上传头像
     * @param request 头像文件
     * @return result
     */
    @ResponseBody
    @RequestMapping("/upload")
    public String upload(MultipartHttpServletRequest request) {
        MultipartFile file = request.getFile("avater");
        if (null == file) {
            return fail(ErrorType.ERROR_CODE_AVATER_EXCEPTION);
        }
        StringMap map = UploadKit.upload(UploadKit.changeFile(file));
        return success(UploadKit.domain + map.get("key"),"url");
    }
}
