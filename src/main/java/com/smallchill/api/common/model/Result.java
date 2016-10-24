package com.smallchill.api.common.model;

import com.alibaba.druid.support.json.JSONUtils;
import com.smallchill.core.base.model.BaseModel;
import com.smallchill.core.toolbox.grid.JqGrid;
import com.smallchill.core.toolbox.kit.JsonKit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API接口返回值
 * Created by yesong on 2016/10/24 0024.
 */
public class Result {

    /**
     * 状态
     * 0:正常
     * 1:异常
     */
    private int status;

    /**
     * 异常信息
     * 正常时则为空
     */
    private String msg;

    /**
     * 返回数据
     */
    private Map<String, Object> data = new HashMap<>();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    /**
     * 返回不带数据的成功信息
     *
     * @return Result
     */
    public static Result success() {
        Result result = new Result();
        result.status = 0;
        result.msg = "";
        return result;
    }

    /**
     * 返回带参数的成功信息
     * @param data 返回参数
     * @return result
     */
    public static Result success(Object data) {
        Result result = new Result();
        result.status = 0;
        result.msg = "";
        if (data instanceof List) {
            result.data.put("list", (List) data);
        } else if (data instanceof Map) {
            result.data.put("object", data);
        } else if (data instanceof BaseModel) {
            String objName = data.getClass().getSimpleName().toLowerCase();
            result.data.put(objName, data);
        } else if (data instanceof JqGrid) {
            JqGrid jqGrid = (JqGrid) data;
            List list = jqGrid.getRows();
            Map<String, Object> pagemap = new HashMap<String, Object>();
            pagemap.put("totalNum", jqGrid.getRecords());
            pagemap.put("totalPage", jqGrid.getTotal());
            pagemap.put("currentPage", jqGrid.getPage());
            result.data.put("page", pagemap);
            result.data.put("list", list);
        }
        return result;
    }

    /**
     * 默认错误返回码
     * @return result
     */
    public static Result fail() {
        Result result = new Result();
        result.status = ErrorType.ERROR_CODE_SERVER_EXCEPTION.getCode();
        result.msg = ErrorType.ERROR_CODE_SERVER_EXCEPTION.getName();
        return result;
    }

    /**
     * 返回自定义错误信息
     * @param errorType 错误码
     * @return result
     */
    public static Result fail(ErrorType errorType) {
        Result result = new Result();
        result.status = errorType.getCode();
        result.msg = errorType.getName();
        return result;
    }

    public static void main(String[] args) {
//        Role role = new Role();
//        role.setPid(0);
//
//        Role role2 = new Role();
//        role2.setPid(1);
//
//        List list = new ArrayList();
//        list.add(role);
//        list.add(role2);
//
//        JqGrid jqGrid = new JqGrid(list,10,1,100);
//        Result result = Result.success(jqGrid);
//        System.out.println(JsonKit.toJson(result));

        Result result = Result.fail(ErrorType.ERROR_CODE_USERHASLOCK);
        System.out.println(JsonKit.toJson(result));
    }
}
