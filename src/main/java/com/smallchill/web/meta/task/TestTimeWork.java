package com.smallchill.web.meta.task;

import com.smallchill.common.task.TimeWork;

/**
 * 测试定时器
 * Created by yesong on 2016/11/3 0003.
 */
public class TestTimeWork extends TimeWork{

    @Override
    protected void doExecute() {
        System.out.println("=======================执行定时任务.TestTimeWork=======================");
    }
}
