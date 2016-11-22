package com.smallchill.web.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;

/**
 * 用户分类
 * Generated by shilong.
 * 2016-11-22
 */
@Table(name = "tb_user_classification")
@BindID(name = "id")
@SuppressWarnings("serial")
public class UserClassification extends BaseModel {

    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "classification_id")
    private Integer classificationId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(Integer classificationId) {
        this.classificationId = classificationId;
    }
}
