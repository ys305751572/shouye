package com.smallchill.web.meta.intercept;

import com.smallchill.core.aop.AopContext;
import com.smallchill.core.meta.PageIntercept;

/**
 *
 * Created by yesong on 2017/2/23 0023.
 */
public class DailyAndMagazineMgrIntercept extends PageIntercept{

    private Integer status;

    @Override
    public void queryBefore(AopContext ac) {
        ac.setCondition(" AND status = " + status);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
