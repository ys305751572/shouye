package com.smallchill.api.function.modal.vo;

import com.smallchill.api.common.model.BaseVo;

import java.util.List;

/**
 * 文章VO
 * Created by yesong on 2017/2/3 0003.
 */
public class ArticleVo extends BaseVo{

    private Integer id;// 主键
    private Integer articleId; // 文章ID
    private String title; // 标题
    private String content; // 内容
    private String cover; // 封面
    private String fromName; // 来源名字
    private Integer authorId; // 作者ID
    private Integer authorType; // 作者类型
    private String author; // 作者
    private String typename; // 文章类型名称
    private Integer readingQWantity; // 阅读量
    private Integer interestQuantity; // 感兴趣数量
    private Integer forwardingQuantity; // 转发数量
    private Long createTime; // 创建时间
    private Long pushTime; // 最后时间
    private Integer type; // 类型
    private Integer mine; // 是否本人
    private Integer groupId; // 组织ID
    private String groupname; // 组织名字

    private Integer magazineId;  // 杂志ID
    private String maganzinename; // 杂志名字

    private Integer isInterest; // 是否感兴趣

    private List<String> urls;

    public Integer getIsInterest() {
        return isInterest;
    }

    public void setIsInterest(Integer isInterest) {
        this.isInterest = isInterest;
    }

    public Integer getMagazineId() {
        return magazineId;
    }

    public void setMagazineId(Integer magazineId) {
        this.magazineId = magazineId;
    }

    public String getMaganzinename() {
        return maganzinename;
    }

    public void setMaganzinename(String maganzinename) {
        this.maganzinename = maganzinename;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public Integer getAuthorType() {
        return authorType;
    }

    public void setAuthorType(Integer authorType) {
        this.authorType = authorType;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getMine() {
        return mine;
    }

    public void setMine(Integer mine) {
        this.mine = mine;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public Integer getReadingQWantity() {
        return readingQWantity;
    }

    public void setReadingQWantity(Integer readingQWantity) {
        this.readingQWantity = readingQWantity;
    }

    public Integer getInterestQuantity() {
        return interestQuantity;
    }

    public void setInterestQuantity(Integer interestQuantity) {
        this.interestQuantity = interestQuantity;
    }

    public Integer getForwardingQuantity() {
        return forwardingQuantity;
    }

    public void setForwardingQuantity(Integer forwardingQuantity) {
        this.forwardingQuantity = forwardingQuantity;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getPushTime() {
        return pushTime;
    }

    public void setPushTime(Long pushTime) {
        this.pushTime = pushTime;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
