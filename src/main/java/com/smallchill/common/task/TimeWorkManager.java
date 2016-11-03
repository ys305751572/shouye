package com.smallchill.common.task;

import com.smallchill.web.meta.task.TestTimeWork;
import com.smallchill.web.meta.task.TestTimeWork2;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * 定时任务管理器
 * Created by yesong on 2016/11/3 0003.
 */
public class TimeWorkManager {

    private static ConcurrentHashMap<String,TimeWork> timewordMap = new ConcurrentHashMap<>();

    private static TimeWorkManager timeWorkManager = new TimeWorkManager();

    public static TimeWorkManager create() {
        return timeWorkManager;
    }

    public void addTimeWork(String key, long time,Class<?> timeWorkclass) {
        try {
            TimeWork timeWork = (TimeWork) timeWorkclass.newInstance();
            timewordMap.put(key, timeWork);
            timeWork.addTime(time).bulid();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭定时器
     * @param key
     */
    public void close(String key) {
        TimeWork timeWork = timewordMap.get(key);
        timeWork.close();
    }

    public static void main(String[] args) {
        TimeWorkManager.create().addTimeWork("test", System.currentTimeMillis() + 5000,TestTimeWork.class);
        TimeWorkManager.create().addTimeWork("test2", System.currentTimeMillis() + 10000,TestTimeWork2.class);
    }
}
