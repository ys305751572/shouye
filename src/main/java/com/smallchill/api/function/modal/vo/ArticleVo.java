package com.smallchill.api.function.modal.vo;

/**
 * 文章VO
 * Created by yesong on 2017/2/3 0003.
 */
public class ArticleVo {

    private int id;// id
    private String typename; // 文章类型名称
    private int readingQWantity; // 阅读量
    private int interestQuantity; // 感兴趣数量
    private int forwardingQuantity; // 转发数量
    private long createTime; // 创建时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public int getReadingQWantity() {
        return readingQWantity;
    }

    public void setReadingQWantity(int readingQWantity) {
        this.readingQWantity = readingQWantity;
    }

    public int getInterestQuantity() {
        return interestQuantity;
    }

    public void setInterestQuantity(int interestQuantity) {
        this.interestQuantity = interestQuantity;
    }

    public int getForwardingQuantity() {
        return forwardingQuantity;
    }

    public void setForwardingQuantity(int forwardingQuantity) {
        this.forwardingQuantity = forwardingQuantity;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
