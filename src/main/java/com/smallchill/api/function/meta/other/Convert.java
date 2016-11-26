package com.smallchill.api.function.meta.other;

import com.smallchill.api.function.modal.Button;
import com.smallchill.api.function.modal.vo.Groupvo;
import com.smallchill.api.function.modal.vo.SearchResult;
import com.smallchill.api.function.modal.vo.UserVo;
import com.smallchill.core.toolbox.Record;
import com.smallchill.web.model.UserInfo;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.CaseInsensitiveMap;
import org.apache.commons.lang3.StringUtils;
import org.beetl.sql.core.kit.CaseInsensitiveHashMap;

import java.util.ArrayList;
import java.util.List;

/**
 * record转换对应的vo
 * Created by yesong on 2016/11/2 0002.
 */
public class Convert {


    public static UserVo recordToVo(Record record) {
        Integer id = Integer.parseInt(record.get("userId").toString());
        String username = (String) record.get("username");
        String city = (String) record.get("provinceCity");
        String domain = (String) record.get("domain");
        String keyWord = (String) record.get("keyWord");
        keyWord = keyWord.replaceAll("\\|", "\\/");
        String professional = (String) record.get("professional");
        String organization = (String) record.get("organization");
        Integer per = record.getInt("per");
        UserVo userVo = new UserVo(id, username, city, domain, keyWord, organization, professional, "");
        userVo.setPer(per);
        userVo.setDesc(record.getStr("desc"));
        userVo.setAvater(record.getStr("avater"));
        userVo.setCarrer(record.getStr("career"));
        userVo.setSchool(record.getStr("school"));
        return userVo;
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
        int[] index = new int[]{1, 2, 3};
        for (int _i : index) {
            Object isOpen = record.get("is_open" + _i);
            Record record1 = Record.create();
            list.add(record1.set("title", record.get("title" + _i)).set("content", (isOpen != null && Integer.parseInt(isOpen.toString()) == 1) ? record.get("content" + _i) : "加入组织成为会员才可查看该信息"));
        }
        list.add(Record.create().set("title", "最近活动").set("content", "敬请期待"));
        list.add(Record.create().set("title", "机构日报").set("content", "敬请期待"));
        return list;
    }

    public static List<Button> groupDetailBtnList() {
        return null;
    }

    /**
     * 登录之后返回用户基本信息
     *
     * @param userInfo 用户基本信息
     * @return record
     */
    public static UserVo userInfoToRecord(UserInfo userInfo) {
        return new UserVo(userInfo.getUserId(), userInfo.getUsername(), userInfo.getProvinceCity(),
                userInfo.getDomain(), userInfo.getKeyWord(), userInfo.getOrganization(), userInfo.getProfessional(), userInfo.getAvater());
    }

    /**
     * 关键字搜索
     * 用户传换为searchresult
     *
     * @param groupList 组织列表
     */
    public static void recordToSearchResult(List<CaseInsensitiveHashMap> groupList) {
        if (groupList != null && groupList.size() > 0) {
            for (CaseInsensitiveHashMap map : groupList) {
                Object status = map.get("status");
                if (status == null) {
                    map.put("status", 1);
                } else {
                    map.put("status", 2);
                }
            }
        }
    }

    /**
     * 好友关键字 字符串转换为列表
     *
     * @param label 关键字
     * @return list
     */
    public static List<String> labelToSameKeyList(String label) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isBlank(label)) return list;

        String[] labels = label.split("\\|");
        for (String l : labels) {
            if (StringUtils.isNotBlank(l)) {
                list.add(l);
            }
        }
        return list;
    }

    /**
     * 设置当前用户与
     *
     * @param vo 好友信息
     */
    public static void setUserVoStatus(UserVo vo, Record record, Integer userId) {
        int type = NOT_FRINED;
        if (record.get("status") == null) {
            type = NOT_FRINED;
        } else {
            int status = Integer.parseInt(record.get("status").toString());
            if (status == 1) {
                type = FRIEND;
            } else if (status == 2) {
                type = PASS;
            } else if (status == 0) {
                int fromUserId = Integer.parseInt(record.get("from_user_id").toString());
                int toUserId = Integer.parseInt(record.get("to_user_id").toString());
                if (userId == fromUserId) {
                    type = NOT_PROCESS_TO_USER_ID;
                } else if (userId == toUserId) {
                    type = NOT_PROCESS_FROM_USER_ID;
                }
            }
        }
        vo.setStatus(type);
    }

    private static int NOT_FRINED = 2000; // 未结识
    private static int FRIEND = 2001; // 已结识
    private static int NOT_PROCESS_FROM_USER_ID = 2002; // 显示忽略 结识按钮
    private static int NOT_PROCESS_TO_USER_ID = 2003; // 等待对方确认
    private static int PASS = 2004;                   // 已忽略
}
