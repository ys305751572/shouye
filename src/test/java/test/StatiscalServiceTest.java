package test;

import com.smallchill.platform.model.Conditions;
import com.smallchill.platform.model.StatisticalResult;
import com.smallchill.platform.service.impl.StatisticalServiceImpl;
import com.smallchill.system.model.Attach;
import com.smallchill.system.model.Dept;
import com.smallchill.system.model.Dict;
import com.smallchill.system.model.Role;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 统计service 测试
 * Created by yesong on 2016/10/20 0020.
 */
public class StatiscalServiceTest extends BaseJunit4Test{

    @Autowired
    private StatisticalServiceImpl service;

    @Transactional
    @Test
    public void testclassificationStatistics() {
        Conditions conditions = Conditions.create().setGroupByName("DEPTID").setSort(Conditions.Sort.DESC).setForeignClass(Dept.class).setForeignCol("SIMPLENAME");
        StatisticalResult result = service.classificationStatistics(Role.class,conditions);
        result.toString();
    }

    @Test
    public void testTimeStatistics() {
        Conditions conditions = Conditions.create().setGroupByName("CREATETIME").setTimeTypeIsLong(false).setTimeType(Conditions.Time.DAY).setBeforDays(20);
        StatisticalResult result = service.timeStatistics(Attach.class,conditions);
        result.toString();
    }

    /**
     * 增长趋势
     * 报错日统计月统计
     */
    @Test
    public void testBrokenLineOfDay() {
//        int beforeDays = 7; // 一周
//        int beforeDays = 14; // 半个月
        int beforeDays = 30; // 一个月
        Conditions conditions = Conditions.create().setGroupByName("CREATETIME").setTimeTypeIsLong(false).setTimeType(Conditions.Time.DAY).setBeforDays(beforeDays);
        StatisticalResult result = service.timeStatistics(Attach.class,conditions);
        result.toString();
    }

    @Test
    @Deprecated
    public void testBrokenLineOfDay2() {
//        int beforeDays = 7; // 一周
//        int beforeDays = 14; // 半个月
        int beforeDays = 30; // 一个月
        Conditions conditions = Conditions.create().setGroupByName("CREATETIME").setTimeTypeIsLong(false).setTimeType(Conditions.Time.DAY).setBeforDays(beforeDays);
        StatisticalResult result = service.timeStatistics(Attach.class,conditions);
        result.toString();
    }

    @Test
    public void testclassification() {
        // 根据附件状态统计
        Conditions conditions = Conditions.create().setGroupByName("STATUS").setForeignClass(Dict.class).setForeignCol("NAME");
        StatisticalResult result = service.classificationStatistics(Attach.class,conditions);
        result.toString();
    }

    @Test
    public void testclassificationBySort() {
        // 根据附件状态统计
        Conditions conditions = Conditions.create().setGroupByName("STATUS").setForeignClass(Dict.class).setForeignCol("NAME").setSort(Conditions.Sort.DESC).setLimit(1);
        StatisticalResult result = service.classificationStatistics(Attach.class,conditions);
        result.toString();
    }
}
