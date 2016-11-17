package com.smallchill.common.pay.util;

import java.util.Calendar;

public class TimeUtil {

    //获得本周一0点时间
    public static Long getTimesWeekmorning(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return (Long) (cal.getTimeInMillis());
    }
    //获得本周日24点时间
    public static Long getTimesWeeknight(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return (Long) ((cal.getTime().getTime()+ (7*24*60*60*1000)));
    }
    //获得本月第一天0点时间
    public static Long getTimesMonthmorning(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return (Long) (cal.getTimeInMillis());
    }
    //获得本月最后一天24点时间
    public static Long getTimesMonthnight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return (Long) (cal.getTimeInMillis());
    }

    //获得当天0点时间
    public static Long getTimesmorning(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (Long) (cal.getTimeInMillis());
    }
    //获得当天24点时间
    public static Long getTimesnight(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (Long) (cal.getTimeInMillis());
    }
//    public static void main(String[] args) throws ParseException {
//        //当前0点
//        System.out.println(new TestUtil().getTimesmorning());
//        //当前24点
//        System.out.println(new TestUtil().getTimesnight());
//        // 当前时间
//        System.out.println(System.currentTimeMillis());
//        //当前0点
//        System.out.println(new TestUtil().getDayBegin());
//        // 当天0点
//        System.out.println(new TestUtil().getDayBeginTimestamp());
//        // 当前时间
//        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
//                .format(new Date()));
//        System.out.println(Calendar.getInstance().getTime());
//        // 当前时间
//        System.out.println(new Timestamp(System.currentTimeMillis()));
//
//    }

}