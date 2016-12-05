package com.smallchill.web.meta.intercept;

import com.smallchill.core.aop.AopContext;
import com.smallchill.core.meta.PageIntercept;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.web.model.Group;

/**
 * Created by 史龙 on 2016/10/28.
 */
public class OrderIntercept extends PageIntercept {

    public void queryBefore(AopContext ac) {
        String condition = "and orderType = '1' OR orderType = '2'";
        ac.setCondition(condition);
    }

}
