package com.smallchill.api.function.modal.vo;

/**
 * 圈脉搜索结果
 * Created by yesong on 2016/11/18 0018.
 */
public class SearchResult {

    // 对象类别 1:组织 2:用户
    private Integer type;
    // 组织/用户名字
    private String name;
    // 组织/用户头像
    private String avater;
    // 组织/用户地址
    private String city;
    // 组织/用户关键字
    private String keyWord;
    // 组织用户数
    private Integer memberCount;
    // 用户领域行业
    private String domain;
    // 用户信息完成度
    private Integer per;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Integer getPer() {
        return per;
    }

    public void setPer(Integer per) {
        this.per = per;
    }
}
