package com.smallchill.web.model;

import com.smallchill.core.annotation.BindID;
import com.smallchill.core.base.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * Created
 * by shilong on 2016/11/11.
 */
@Table(name = "tb_report_reasons")
@BindID(name = "id")
@SuppressWarnings("serial")
public class ReportReasons extends BaseModel{

    @Column(name = "id")
    private Integer id;

    @Column(name = "report_id")
    private Integer reportId;

    @Column(name = "reasons")
    private Integer reasons;

    @Column(name = "create_time")
    private Long createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Integer getReasons() {
        return reasons;
    }

    public void setReasons(Integer reasons) {
        this.reasons = reasons;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
