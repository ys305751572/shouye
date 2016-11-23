package com.smallchill.web.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;

/**
 * 标记
 * Generated by shilong.
 * 2016-11-22
 */
@Table(name = "tb_tag")
@BindID(name = "id")
@SuppressWarnings("serial")
public class Tag extends BaseModel {

    @Column(name = "id")
    private Integer id;

    @Column(name = "tag")
    private String tag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}