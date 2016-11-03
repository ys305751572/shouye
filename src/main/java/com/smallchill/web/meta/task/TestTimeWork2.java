package com.smallchill.web.meta.task;

import com.smallchill.common.task.TimeWork;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;

import java.util.List;

/**
 * 定时器测试2
 * Created by yesong on 2016/11/3 0003.
 */
public class TestTimeWork2 extends TimeWork{

    @Override
    protected void doExecute() {
        System.out.println("=======================执行定时任务.TestTimeWork2=======================");
        String sql = "select * from tb_message";
        List<Record> list = Db.init().selectList(sql);
        for (Record record : list) {
            System.out.println("to_id:" + record.get("to_id") + "  title:" + record.get("title") + "  content:" + record.get("content"));

        }
    }
}
