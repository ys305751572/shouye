package com.smallchill.api.function.meta.intercept;

import com.smallchill.core.aop.AopContext;
import com.smallchill.core.meta.ApiQueryIntercept;
import org.apache.commons.lang3.StringUtils;

/**
 * 杂志信息拦截器
 * Created by yesong on 2017/2/8 0008.
 */
public class MagazineInfoIntercept extends ApiQueryIntercept {

    @Override
    public void queryBefore(AopContext ac) {
        setParma("userId", ac);
        setParma("pid", ac);
        setParma("domainId", ac);
        StringBuilder buffer = new StringBuilder(" AND (status = 2 OR status = 4)");
        Object status = this.getRecord().get("status");
        Object pid = this.getRecord().get("pid");
        Object domainId = this.getRecord().get("domainId");
        if (pid != null && StringUtils.isNotBlank(pid.toString())) {
            buffer.append(" AND domain_pid = #{pid}");
        }
        if (domainId != null && StringUtils.isNotBlank(domainId.toString())) {
            buffer.append(" AND domain_id = #{domainId}");
        }
        if (status != null && StringUtils.isNotBlank(status.toString())) {
            if (Integer.parseInt(status.toString()) == 1) {
                buffer.append(" AND magazine_id IS NOT NULL");
            } else {
                buffer.append(" AND magazine_id IS NULL");
            }
        }
        ac.setCondition(buffer.toString());
    }

    @Override
    public void queryAfter(AopContext ac) {
        super.queryAfter(ac);
    }
}
