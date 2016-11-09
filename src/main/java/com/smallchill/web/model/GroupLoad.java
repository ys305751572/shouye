package com.smallchill.web.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;

/**
 * 加载默认组织
 * Created by Administrator on 2016/11/9.
 */
@Table(name = "tb_group_load")
@BindID(name = "id")
@SuppressWarnings("serial")
public class GroupLoad extends BaseModel {

    @Column(name = "id")
    private Integer id;
    @Column(name = "group_id")
    private Integer groupId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

}
