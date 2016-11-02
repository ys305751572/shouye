package com.smallchill.api.function.meta.other;

import com.smallchill.api.function.modal.vo.Groupvo;
import com.smallchill.api.function.modal.vo.UserVo;
import com.smallchill.core.toolbox.Record;

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
        StringBuffer domainBuffer = new StringBuffer("");
        String[] kws = domain.split("\\|");
        for (String kw : kws) {
            domainBuffer.append(kw.split(",")[3]);
            domainBuffer.append("/");
        }
        domain = domainBuffer.toString().substring(0, domainBuffer.length() - 1);
        String keyWord = (String) record.get("key_word");
        keyWord = keyWord.replaceAll("\\|", "\\/");
        String professional = (String) record.get("professional");
        String[] ps = professional.split("\\|");
        String pone = ps[0];
        professional = pone.split(",")[3];
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
        String avater = record.get("avater").toString();
        String target = record.get("targat").toString();
        Integer memebers = Integer.parseInt(record.get("member_count").toString());
        String city = record.get("province_city").toString();
        return new Groupvo(id, name, avater, target, memebers, city);
    }
}
