package com.smallchill.api.function.meta.other;

import com.smallchill.api.function.modal.vo.DailyVo;
import com.smallchill.core.toolbox.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * 日志转换器
 * Created by yesong on 2017/2/8 0008.
 */
public class DailyConvert {

    public static List<DailyVo> recordToDailyVo(List<Record> records) {
        List<DailyVo> list = new ArrayList<>();
        DailyVo vo;
        for (Record record : records) {
            vo = new DailyVo();
            vo.setCreateTime(record.getLong("audit_time"));
            vo.setName(record.getStr("name") + "日报");
            vo.setTitle(record.getStr("title"));
            vo.setDailyId(record.getInt("daily_id"));
            vo.setStatus(record.getInt("status"));
            list.add(vo);
        }
        return list;
    }
}
