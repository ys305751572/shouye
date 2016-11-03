package test;

import com.smallchill.common.task.TimeWorkManager;
import com.smallchill.web.meta.task.TestTimeWork2;
import org.junit.Test;

/**
 * 定时器测试
 * Created by yesong on 2016/11/3 0003.
 */
public class TimeWorkTest extends  BaseJunit4Test{

    @Test
    public void test() {
        System.out.println("==============test()================");
        TimeWorkManager.create().addTimeWork("test2", System.currentTimeMillis() + 5000,TestTimeWork2.class);
    }
}
