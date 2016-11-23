package com.smallchill.web.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;

/**
 * 分类
 * Generated by shilong.
 * 2016-11-22
 */
@Table(name = "tb_classification")
@BindID(name = "id")
@SuppressWarnings("serial")
public class Classification extends BaseModel {

    @Column(name = "id")
    private Integer id;

    @Column(name = "classification")
    private String classification;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}