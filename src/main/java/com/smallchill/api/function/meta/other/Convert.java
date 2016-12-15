package com.smallchill.api.function.meta.other;

import com.smallchill.api.function.meta.consts.TextConst;
import com.smallchill.api.function.modal.Button;
import com.smallchill.api.function.modal.vo.Groupvo;
import com.smallchill.api.function.modal.vo.IntroduceUserVo;
import com.smallchill.api.function.modal.vo.SearchResult;
import com.smallchill.api.function.modal.vo.UserVo;
import com.smallchill.core.toolbox.Record;
import com.smallchill.core.toolbox.kit.MapKit;
import com.smallchill.web.model.Refund;
import com.smallchill.web.model.UserInfo;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.CaseInsensitiveMap;
import org.apache.commons.lang3.StringUtils;
import org.beetl.sql.core.kit.CaseInsensitiveHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * record转换对应的vo
 * Created by yesong on 2016/11/2 0002.
 */
public class Convert implements TextConst{


    public static UserVo recordToVo(Record record) {
        Integer id = Integer.parseInt(record.get("userId").toString());
        String username = (String) record.get("username");
        String city = (String) record.get("provinceCity");
        String domain = (String) record.get("domain");
        String keyWord = (String) record.get("keyWord");
        keyWord = keyWord.replaceAll("\\|", "\\/");
        String professional = (String) record.get("professional");
        if (StringUtils.isNotBlank(professional)) {
            professional = professional.split("/")[0];
        }
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

    public static List<IntroduceUserVo> recordToVoOfIntroduces(List<Record> recordList) {
        List<IntroduceUserVo> introduceUserVos = new ArrayList<>();
        for (Record record : recordList) {
            introduceUserVos.add(recordToVoOfIntroduce(record));
        }
        return introduceUserVos;
    }

    /**
     * 引荐人数据字段 to vo
     *
     * @param record record
     * @return IntroduceUserVo
     */
    public static IntroduceUserVo recordToVoOfIntroduce(Record record) {
        IntroduceUserVo introduceUserVo = new IntroduceUserVo();
        Integer id = Integer.parseInt(record.get("userId1").toString());
        String username = (String) record.get("username1");
        String city = (String) record.get("provinceCity1");
        String domain = (String) record.get("domain1");
        String keyWord = (String) record.get("keyWord1");
        keyWord = keyWord.replaceAll("\\|", "\\/");
        String professional = (String) record.get("professional1");
        String organization = (String) record.get("organization1");
        Integer per = record.getInt("per1");
        UserVo fromUserVo = new UserVo(id, username, city, domain, keyWord, organization, professional, "");
        fromUserVo.setPer(per);
        fromUserVo.setDesc(record.getStr("desc1"));
        fromUserVo.setAvater(record.getStr("avater1"));
        fromUserVo.setCarrer(record.getStr("career1"));
        fromUserVo.setSchool(record.getStr("school1"));
        fromUserVo.setValidateInfo(record.getStr("validateInfo1"));

        Integer id2 = Integer.parseInt(record.get("userId2").toString());
        String username2 = (String) record.get("username2");
        String city2 = (String) record.get("provinceCity2");
        String domain2 = (String) record.get("domain2");
        String keyWord2 = (String) record.get("keyWord2");
        keyWord2 = keyWord2.replaceAll("\\|", "\\/");
        String professional2 = (String) record.get("professional2");
        String organization2 = (String) record.get("organization2");
        Integer per2 = record.getInt("per2");
        UserVo toUserVo = new UserVo(id2, username2, city2, domain2, keyWord2, organization2, professional2, "");
        toUserVo.setPer(per2);
        toUserVo.setDesc(record.getStr("desc2"));
        toUserVo.setAvater(record.getStr("avater2"));
        toUserVo.setCarrer(record.getStr("career2"));
        toUserVo.setSchool(record.getStr("school2"));
        toUserVo.setValidateInfo(record.getStr("validateInfo2"));

        introduceUserVo.setFromUserVo(fromUserVo);
        introduceUserVo.setToUserVo(toUserVo);
        introduceUserVo.setId(record.getInt("id"));
        return introduceUserVo;
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

        int isJoin = 0;
        if (record.get("status") != null && Integer.parseInt(record.get("status").toString()) == 2) {
            isJoin = 1;
        }
        Groupvo groupvo =  new Groupvo(id, name, avater, target, memebers, city);
        groupvo.setIsjoin(isJoin);
        return groupvo;
    }

    /**
     * @param record record
     * @return list
     */
    public static List<Record> recordToGroupDetail(Record record, Integer gaStatus) {
        List<Record> list = new ArrayList<>();
        int[] index = new int[]{1, 2, 3};
        for (int _i : index) {
            Object isOpen = record.get("is_open" + _i);
            Record record1 = Record.create();
            String content;
            if (isOpen != null && Integer.parseInt(isOpen.toString()) == 2 && (gaStatus == null || gaStatus != 2)) {
                content = "加入组织成为会员才可查看该信息";
            } else {
                content = record.getStr("content" + _i);
            }
            list.add(record1.set("title", record.get("title" + _i)).set("content", content));
        }
        list.add(Record.create().set("title", "最近活动").set("content", "敬请期待"));
        list.add(Record.create().set("title", "组织日报").set("content", "敬请期待"));
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
        Integer type = vo.getStatus();
        int orgIsOpen = record.getInt("org_is_open");
        if (record.get("status") == null) {
            if (vo.getStatus() == null) {
                type = NOT_FRINED;
                if (orgIsOpen == 2) {
                    vo.setOrganization(ORG_TEXT);
                }
            }
            vo.setUsername(hiddenRealUsername(record.getStr("username")));
        } else {
            int status = Integer.parseInt(record.get("status").toString());
            if (status == 1) {
                int fromUserId = record.getInt("from_user_id");
                int toUserId = record.getInt("to_user_id");
                if (userId == fromUserId) {
                    type = NOT_PROCESS_TO_USER_ID;
                } else if (userId == toUserId) {
                    type = NOT_PROCESS_FROM_USER_ID;
                }
                if (orgIsOpen == 2) {
                    vo.setOrganization(ORG_TEXT);
                }
                if (record.getInt("type") != 2) {
                    vo.setUsername(hiddenRealUsername(record.getStr("username")));
                }
            } else if (status == 2) {
                type = FRIEND;
            } else if (status == 3) {
                type = PASS;
                vo.setUsername(hiddenRealUsername(record.getStr("username")));
                if (orgIsOpen == 2) {
                    vo.setOrganization(ORG_TEXT);
                }
            } else {
                vo.setUsername(hiddenRealUsername(record.getStr("username")));
                if (orgIsOpen == 2) {
                    vo.setOrganization(ORG_TEXT);
                }
            }
        }
        vo.setStatus(type == null ? NOT_FRINED : type);
    }

    /**
     * 退款返回map,转换为refund实体类
     *
     * @param resultMap
     * @return
     */
    public static Refund resultMapToRefund(Map<String, String> resultMap) {
        Refund refund = new Refund();
        if ("SUCCESS".equals(resultMap.get("return_code")) && "SUCCESS".equals(resultMap.get("return_msg"))) {
            refund.setResultCode(resultMap.get("result_code"));
            refund.setOrderNo(resultMap.get("out_trade_no"));
            refund.setOutTradeNo(resultMap.get("out_trade_no"));
            refund.setTransactionId(resultMap.get("transaction_id"));
            refund.setOut_refund_no(resultMap.get("out_refund_no"));
            refund.setRefundId(resultMap.get("refund_id"));
            refund.setRefundChannel(resultMap.get("refund_channel"));
            refund.setRefundFee(Integer.parseInt(resultMap.get("refund_fee")));
            refund.setSettlementRefundFee(Integer.parseInt(resultMap.get("settlement_refund_fee")));
            refund.setTotalFee(Integer.parseInt(resultMap.get("total_fee")));
            refund.setSettlementTotalFee(Integer.parseInt(resultMap.get("settlement_total_fee")));
            refund.setCashFee(Integer.parseInt(resultMap.get("cash_fee")));
        } else {
            refund.setErrCode(resultMap.get("err_code"));
            refund.setErrCodeDes(resultMap.get("err_code_des"));
        }
        return refund;
    }

    /**
     * 非好友隐藏真实名字
     *
     * @param username 用户名
     * @return hiddenUsername
     */
    public static String hiddenRealUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return "";
        }
        if (username.contains("·")) {
            return username.split("·")[0] + "**";
        }
        return username.substring(0, 1) + "**";
    }

    public static int NOT_FRINED = 2000; // 未结识
    public static int FRIEND = 2001; // 已结识
    public static int NOT_PROCESS_FROM_USER_ID = 2002; // 显示忽略 结识按钮
    public static int NOT_PROCESS_TO_USER_ID = 2003; // 等待对方确认
    public static int PASS = 2004;                   // 已忽略
    public static int UN_INTEREST = 2005;                   // 未感兴趣
    public static int INTEREST = 2006;                   // 已感兴趣
}
