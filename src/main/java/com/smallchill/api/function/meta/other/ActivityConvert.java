package com.smallchill.api.function.meta.other;

import com.smallchill.api.function.modal.vo.ActivityVo;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.kit.MapKit;
import org.beetl.sql.core.kit.CaseInsensitiveHashMap;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动转换器
 * Created by yesong on 2017/2/10 0010.
 */
public class ActivityConvert {

    public List<ActivityVo> pageToVos(List<CaseInsensitiveHashMap> maps) {
        List<ActivityVo> list = new ArrayList<>();
        for (CaseInsensitiveHashMap map : maps) {
            list.add(mapToVo(map));
        }
        return list;
    }

    public ActivityVo mapToVo(CaseInsensitiveHashMap map) {
        ActivityVo vo = new ActivityVo();
        vo.setCover(MapKit.getStr(map, "cover"));
        vo.setTitle(MapKit.getStr(map, "title"));
        vo.setCreateTime(MapKit.getLong(map, "pushTime"));
        vo.setActivityId(MapKit.getInt(map, "activityId"));
        vo.setGroupId(MapKit.getInt(map, "groupId"));
        vo.setGroupname(MapKit.getStr(map, "groupname"));
        return vo;
    }
    public static ActivityVo recordToVo(Record map) {
        ActivityVo vo = new ActivityVo();
        vo.setCover(MapKit.getStr(map, "cover"));
        vo.setTitle(MapKit.getStr(map, "title"));
        vo.setCreateTime(MapKit.getLong(map, "pushTime"));
        vo.setActivityId(MapKit.getInt(map, "activityId"));
        vo.setAddress(MapKit.getStr(map, "address"));
        vo.setApplyEndTime(MapKit.getLong(map, "applyEndTime"));
        vo.setCeiling(MapKit.getInt(map, "ceiling"));
        vo.setContent(MapKit.getStr(map, "content"));
        vo.setCost(MapKit.getInt(map, "cost"));
        vo.setEndTime(MapKit.getLong(map, "endTime"));
        vo.setStartTime(MapKit.getLong(map, "startTime"));
        vo.setLimit(MapKit.getInt(map, "limit"));
        vo.setGroupId(MapKit.getInt(map, "groupId"));
        vo.setGroupname(MapKit.getStr(map, "groupname"));
        vo.setBtnStatus(map.getInt("btnStatus"));
        vo.setIsInterest(map.getInt("isInterest"));
        return vo;
    }
}
