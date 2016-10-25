package com.smallchill.system.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.Table;

/**
 * Created by Administrator on 2016/10/25.
 */
@Table(name = "tfw_demo")
@BindID(name = "id")
@SuppressWarnings("serial")
public class Demo extends BaseModel {

    private Integer id;
    private String group;

    @AutoID
    @SeqID(name = "SEQ_DEMO")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
