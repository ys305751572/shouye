package com.smallchill.api.function.modal;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;

/**
 * 用户-领域
 * Created by yesong on 2016/10/24 0024.
 */
@Table(name = "tb_userinfo_domain")
@BindID(name = "id")
@SuppressWarnings("serial")
public class UserDomain extends BaseModel{

    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "domain_id")
    private Integer domainId;
    @Column(name = "p_id")
    private Integer pid;
    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}