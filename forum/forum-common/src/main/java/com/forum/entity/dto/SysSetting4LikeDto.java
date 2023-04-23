package com.forum.entity.dto;

import com.forum.annotation.VerifyParam;

/**
 * 点赞设置
 */
public class SysSetting4LikeDto {

    /**
     * 点赞数量阈值
     */
        @VerifyParam(required = true)
    private Integer likeDayCountThreshold;

    public Integer getLikeDayCountThreshold() {
        return likeDayCountThreshold;
    }

    public void setLikeDayCountThreshold(Integer likeDayCountThreshold) {
        this.likeDayCountThreshold = likeDayCountThreshold;
    }
}
