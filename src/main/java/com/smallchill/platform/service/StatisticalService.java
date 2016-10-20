package com.smallchill.platform.service;

import com.smallchill.platform.model.StatisticalResult;

import java.util.List;

/**
 * 统计
 * Created by yesong on 2016/10/19 0019.
 */
public interface StatisticalService {

    /**
     * 分类统计
     * @param clazz
     * @param startTime
     * @param endTime
     * @param top
     * @param isPer
     * @return
     */
    StatisticalResult classificationStatistics(Class<?> clazz, long startTime, long endTime, int top, boolean isPer);

    /**
     * 排序统计
     * @return
     */
    StatisticalResult sortStatistical();

    /**
     * 平均数统计
     * @return
     */
    StatisticalResult avgStatistical();
}
