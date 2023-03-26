package com.forum.entity.po;

import java.io.Serializable;
import java.util.Date;

import com.forum.utils.DateUtils;
import com.forum.enums.DateTimePatternEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * @Description: 点赞记录
 * @auther: chong
 * @date: 2023/03/26
 */
public class Record implements Serializable {
	/**
	 * 自增ID
 	 */
	private Integer opId;

	/**
	 * 操作类型0:文章点赞 1:评论点赞
 	 */
	private Integer opType;

	/**
	 * 主体ID
 	 */
	private String objectId;

	/**
	 * 用户ID
 	 */
	private String userId;

	/**
	 * 发布时间
 	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 主体作者ID
 	 */
	private String authorUserId;

	public void setOpId(Integer opId) {
		this.opId = opId;
	}

	public Integer getOpId() {
		return this.opId;
	}

	public void setOpType(Integer opType) {
		this.opType = opType;
	}

	public Integer getOpType() {
		return this.opType;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectId() {
		return this.objectId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setAuthorUserId(String authorUserId) {
		this.authorUserId = authorUserId;
	}

	public String getAuthorUserId() {
		return this.authorUserId;
	}

	@Override
	public String toString() {
		return "自增ID: " + (opId == null ? "空" : opId) + "; 操作类型0:文章点赞 1:评论点赞: " + (opType == null ? "空" : opType) + "; 主体ID: " + (objectId == null ? "空" : objectId) + "; 用户ID: " + (userId == null ? "空" : userId) + "; 发布时间: " + (createTime == null ? "空" : DateUtils.formal(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + "; 主体作者ID: " + (authorUserId == null ? "空" : authorUserId);
	}
}