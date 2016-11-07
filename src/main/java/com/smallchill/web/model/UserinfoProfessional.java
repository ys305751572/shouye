package com.smallchill.web.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;
import org.beetl.sql.core.annotatoin.Table;

import javax.persistence.Column;

/**
 * 用户职业（专业）关联表
 * Created by 史龙 on 2016/11/7.
 */
@Table(name = "tb_userinfo_professional")
@BindID(name = "id")
@SuppressWarnings("serial")
public class UserinfoProfessional extends BaseModel {

    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    //职业ID
    @Column(name = "pro_id")
    private Integer proId;

    //职业名称
    @Column(name = "pro_name")
    private String proName;

    //职级
    @Column(name = "level")
    private String level;

    //职业父ID，主要用于选择“其他”时，绑定父级
    @Column(name = "p_id")
    private Integer pId;

    @Column(name = "create_time")
    private Long createTime;

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

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
