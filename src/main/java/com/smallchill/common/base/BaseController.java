package com.smallchill.common.base;

import com.smallchill.api.common.model.ErrorType;
import com.smallchill.api.common.model.Result;
import com.smallchill.common.pay.util.XMLUtil;
import com.smallchill.core.base.controller.BladeController;
import com.smallchill.core.toolbox.kit.JsonKit;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * 用于拓展controller类
 */
public class BaseController extends BladeController {

    /**
     * 返回结果转换为json
     *
     * @param result 结果
     * @return string类型的结果
     */
    public String toJson(Result result) {
        return JsonKit.toJson(result);
    }

    /**
     * 返回不带信息的成功相应信息
     *
     * @return result
     */
    public String success() {
        return toJson(Result.success());
    }

    /**
     * 返货带结果的
     *
     * @param data 结果
     * @return result
     */
    public String success(Object data) {
        return toJson(Result.success(data));
    }

    /**
     * 返回默认错误信息
     *
     * @return
     */
    public String fail() {
        return toJson(Result.fail());
    }

    /**
     * 返回自定义错误信息
     *
     * @param errorType 错误码
     * @return result
     */
    public String fail(ErrorType errorType) {
        return toJson(Result.fail(errorType));
    }

    public Map<String, Object> parse(HttpServletRequest request) {
        Map<String, Object> resultMap = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line;
            String result = "";
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            System.out.println("result:" + result);
            resultMap = XMLUtil.doXMLParse(result);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }
}
