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

    public static List<DailyVo> recordsToDailyVos(List<Record> records) {
        List<DailyVo> list = new ArrayList<>();
        DailyVo vo;
        for (Record record : records) {
            vo = new DailyVo();
            vo.setCreateTime(record.getLong("audit_time"));
            vo.setName(record.getStr("name") + "日报");
            vo.setTitle(record.getStr("title"));
            vo.setDailyId(record.getInt("daily_id"));
            vo.setArticleId(record.getInt("article_id"));
            vo.setStatus(record.getInt("status"));
            vo.setCover(record.getStr("cover"));
            list.add(vo);
        }
        return list;
    }

    public static DailyVo recordToDailyVo(Record record) {
        DailyVo vo = new DailyVo();
        vo.setCreateTime(record.getLong("audit_time"));
        vo.setTitle(record.getStr("title"));
        vo.setDailyId(record.getInt("daily_id"));
        vo.setName(record.getStr("name") + "日报");
        vo.setGroupName(record.getStr("name"));
        vo.setDesc(record.getStr("daily_desc"));
        vo.setCover(record.getStr("daily_cover"));
        return vo;
    }
}
