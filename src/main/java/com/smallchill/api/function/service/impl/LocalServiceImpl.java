package com.smallchill.api.function.service.impl;

import com.smallchill.api.function.service.LocalSerivce;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.Record;
import org.springframework.stereotype.Service;

/**
 * 用户坐标
 * Created by yesong on 2016/11/24 0024.
 */
@Service
public class LocalServiceImpl implements LocalSerivce {

    @Override
    public void upload(Integer userId, Double lat, Double lon) {
        String sql = "select * from tb_user_local ul where ul.user_id = #{userId}";
        Record record = Db.init().selectOne(sql, Record.create().set("userId", userId));
        if (record == null) {
            save(userId, lat, lon);
        } else {
            update(userId, lat, lon);
        }
    }

    private void update(Integer userId, Double lat, Double lon) {
        Db.init().update("update tb_user_local set lat = #{lat}, lon = #{lon} where user_id = #{userId}",
                Record.create().set("userId", userId).set("lat", lat).set("lon", lon));
    }

    private void save(Integer userId, Double lat, Double lon) {
        String sql = "insert into tb_user_local (user_id, lat, lon) VALUES (#{userId}, #{lat}, #{lon})";
        Db.init().insert(sql, Record.create().set("userId", userId).set("lat", lat).set("lon", lon));
    }
}
