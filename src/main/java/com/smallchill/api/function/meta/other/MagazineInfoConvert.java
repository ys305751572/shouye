package com.smallchill.api.function.meta.other;

import com.smallchill.api.function.modal.vo.MaganizeInfoVo;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.kit.MapKit;
import com.smallchill.web.model.MagazineInfo;
import org.beetl.sql.core.kit.CaseInsensitiveHashMap;

import java.util.ArrayList;
import java.util.List;

/**
 * 杂志信息转化器
 * Created by yesong on 2017/2/8 0008.
 */
public class MagazineInfoConvert {

    public static MaganizeInfoVo recordToVo(CaseInsensitiveHashMap record) {
        MaganizeInfoVo vo = new MaganizeInfoVo();
        vo.setGroupname(MapKit.getStr(record, "groupname"));
        vo.setMaganizeId(MapKit.getInt(record, "id"));
        vo.setName(MapKit.getStr(record, "name"));
        vo.setCover(MapKit.getStr(record, "cover"));

        int status = 1;
        int mi = MapKit.getInt(record, "magazine_id");
        if (MapKit.getInt(record, "magazine_id") != 0) {
            status = 2;
        }
        if (MapKit.getInt(record, "status") == 4) {
            status = 3;
        }
        vo.setStatus(status);
        return vo;
    }

    public static MaganizeInfoVo recordToVo(Record record) {
        MaganizeInfoVo vo = new MaganizeInfoVo();
        vo.setGroupname(MapKit.getStr(record, "groupname"));
        vo.setMaganizeId(MapKit.getInt(record, "id"));
        vo.setName(MapKit.getStr(record, "name"));
        vo.setCover(MapKit.getStr(record, "cover"));

        int status = 1;
        if (MapKit.getInt(record, "magazine_id") != 0) {
            status = 2;
        }
        if (MapKit.getInt(record, "status") == 4) {
            status = 3;
        }
        vo.setStatus(status);
        return vo;
    }

    public static List<MaganizeInfoVo> recordToVos(List<CaseInsensitiveHashMap> records) {
        List<MaganizeInfoVo> list = new ArrayList<>();
        for (CaseInsensitiveHashMap record : records) {
            list.add(recordToVo(record));
        }
        return list;
    }

    public static List<MaganizeInfoVo> myRecordToVos(List<Record> records) {
        List<MaganizeInfoVo> vos = new ArrayList<>();
        MaganizeInfoVo vo;
        for (Record record : records) {
            vo = new MaganizeInfoVo();
            vo.setMaganizeId(record.getInt("maganize_id"));
            vo.setCover(record.getStr("cover"));
            vo.setStatus(record.getInt("status"));
            vo.setName(record.getStr("name"));
            vo.setTitle(record.getStr("title"));
            vo.setCreateTime(record.getLong("audit_time"));
            vos.add(vo);
        }
        return vos;
    }
}
