package com.smallchill.api.function.meta.other;

import com.smallchill.api.function.modal.vo.Groupvo;
import com.smallchill.api.function.modal.vo.UserVo;
import com.smallchill.core.toolbox.Record;
import com.smallchill.web.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * record转换对应的vo
 * Created by yesong on 2016/11/2 0002.
 */
public class Convert {


    public static UserVo recordToVo(Record record) {
        Integer id = Integer.parseInt(record.get("user_id").toString());
        String username = (String) record.get("username");
        String city = (String) record.get("province_city");
        String domain = (String) record.get("domain");
        String keyWord = (String) record.get("key_word");
        keyWord = keyWord.replaceAll("\\|", "\\/");
        String professional = (String) record.get("professional");
        String organization = (String) record.get("organization");
        return new UserVo(id, username, city, domain, keyWord, organization, professional);
    }


    /**
     * 返回对象拼装成groupvo
     *
     * @param record 返回值
     * @return groupvo
     */
    public static Groupvo recordToGroupVo(Record record) {
        Integer id = Integer.parseInt(record.get("id").toString());
        String name = record.get("name").toString();
        String avater = record.get("avater") == null ? "" : record.get("avater").toString();
        String target = record.get("targat") == null ? "" : record.get("targat").toString();
        Integer memebers = Integer.parseInt(record.get("memberCount").toString());
        String city = record.get("provinceCity") == null ? "" : record.get("provinceCity").toString();
        return new Groupvo(id, name, avater, target, memebers, city);
    }

    /**
     * @param record record
     * @return list
     */
    public static List<Record> recordToGroupDetail(Record record) {
        List<Record> list = new ArrayList<>();
        int[] index = new int[]{1,2,3};
        for (int _i : index){
            Object isOpen = record.get("is_open" + _i);
            Record record1 = Record.create();
            list.add(record1.set("title",record.get("title" + _i)).set("content" ,(isOpen != null && Integer.parseInt(isOpen.toString()) == 1) ? record.get("content" + _i) : "加入组织成为会员才可查看该信息"));
        }
        return list;
    }

    /**
     * 登录之后返回用户基本信息
     * @param userInfo 用户基本信息
     * @return record
     */
    public static Record userInfoToRecord(UserInfo userInfo) {
        Record record = Record.create().set("username",userInfo.getUsername())
                .set("userId",userInfo.getUserId()).set("avater",userInfo.getAvater())
                .set("per",userInfo.getPer());
        return record;
    }
}
