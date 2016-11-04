package com.smallchill.core.meta;

import com.smallchill.core.aop.AopContext;
import com.smallchill.core.interfaces.IQuery;
import com.smallchill.core.toolbox.Record;

/**
 *
 * Created by yesong on 2016/11/4 0004.
 */
public class ApiQueryIntercept implements IQuery{

    private Record record;

    public Record getRecord() {
        return record;
    }
    @Override
    public void queryBefore(AopContext ac) {

    }

    @Override
    public void queryAfter(AopContext ac) {

    }

    public ApiQueryIntercept addRecord(Record record) {
        this.record = record;
        return this;
    }

    public void setParma(String paramKey,AopContext ac) {
        ac.getParam().put(paramKey,this.getRecord().get(paramKey));
    }
}
