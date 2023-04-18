package com.forum.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.forum.entity.query.UserIntegralRecordQuery;
import com.forum.enums.DateTimePatternEnum;
import com.forum.enums.UserIntegralOperTypeEnum;
import com.forum.utils.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * @Description: 用户积分记录表
 * @auther: chong
 * @date: 2023/03/27
 */
public class UserIntegralRecord implements Serializable {
    /**
     * 记录ID
     */
    private Integer recordId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 操作类型
     */
    private Integer operType;

    /**
     * 积分
     */
    private Integer integral;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String operTypeName;

    public String getOperTypeName() {
        UserIntegralOperTypeEnum operTypeEnum = UserIntegralOperTypeEnum.getByType(operType);
        return operTypeName == null ? "" : operTypeEnum.getDesc();
    }

    public void setOperTypeName(String operTypeName) {
        this.operTypeName = operTypeName;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getRecordId() {
        return this.recordId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setOperType(Integer operType) {
        this.operType = operType;
    }

    public Integer getOperType() {
        return this.operType;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getIntegral() {
        return this.integral;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    @Override
    public String toString() {
        return "记录ID: " + (recordId == null ? "空" : recordId) + "; 用户ID: " + (userId == null ? "空" : userId) + "; 操作类型: " + (operType == null ? "空" : operType) + "; 积分: " + (integral == null ? "空" : integral) + "; 创建时间: " + (createTime == null ? "空" : DateUtils.formal(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()));
    }
}