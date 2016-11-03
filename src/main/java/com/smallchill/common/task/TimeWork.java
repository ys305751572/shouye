package com.smallchill.common.task;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时执行器
 * Created by yesong on 2016/11/3 0003.
 */
public abstract class TimeWork {

    private Timer timer = null;
    private Long time = null;

    public TimeWork() {
        timer = new Timer();

    }
    public TimeWork addTime(long time) {
        this.time = time;
        return this;
    }

    public void bulid() {
        this.timer.schedule(new Mywork(), new Date(this.time));
    }

    public void close() {
        this.timer.cancel();
    }

    protected abstract void doExecute();

    class Mywork extends TimerTask {
        @Override
        public void run() {
            try {
                doExecute();
                timer.cancel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
