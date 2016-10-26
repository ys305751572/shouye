package com.smallchill.system.model;

import com.smallchill.core.annotation.BindID;
import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;

import javax.persistence.Table;

/**
 * Created by Administrator on 2016/10/26.
 */
@Table(name = "tfw_role_group")
@BindID(name = "id")
@SuppressWarnings("serial")
public class RoleGroup {

    private Integer id;
    private Integer roleid;
    private Integer groupid;

    @AutoID
    @SeqID(name = "SEQ_DEMO")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }
}
