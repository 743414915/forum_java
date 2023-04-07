package com.forum.service;


import com.forum.entity.po.LikeRecord;
import com.forum.entity.query.LikeRecordQuery;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.OperRecordOpTypeEnum;
import com.forum.exception.BusinessException;

import java.util.List;

/**
 * @Description: 点赞记录LikeRecordService
 * @auther: chong
 * @date: 2023/03/27
 */
public interface LikeRecordService {

    /**
     * 根据条件查询列表
     */
    List<LikeRecord> findListByParam(LikeRecordQuery query);

    /**
     * 根据条件查询数量
     */
    Integer findCountByParam(LikeRecordQuery query);

    /**
     * 分页查询
     */
    PaginationResultVO<LikeRecord> findListByPage(LikeRecordQuery query);

    /**
     * 新增
     */
    Integer add(LikeRecord bean);

    /**
     * 批量新增
     */
    Integer addBatch(List<LikeRecord> ListBean);

    /**
     * 批量新增或修改
     */
    Integer addOrUpdateBatch(List<LikeRecord> ListBean);

    /**
     * 根据OpId查询
     */
    LikeRecord getLikeRecordByOpId(Integer opId);

    /**
     * 根据OpId更新
     */
    Integer updateLikeRecordByOpId(LikeRecord bean, Integer opId);

    /**
     * 根据OpId删除
     */
    Integer deleteLikeRecordByOpId(Integer opId);

    /**
     * 根据ObjectIdAndUserIdAndOpType查询
     */
    LikeRecord getLikeRecordByObjectIdAndUserIdAndOpType(String objectId, String userId, Integer opType);

    /**
     * 根据ObjectIdAndUserIdAndOpType更新
     */
    Integer updateLikeRecordByObjectIdAndUserIdAndOpType(LikeRecord bean, String objectId, String userId, Integer opType);

    /**
     * 根据ObjectIdAndUserIdAndOpType删除
     */
    Integer deleteLikeRecordByObjectIdAndUserIdAndOpType(String objectId, String userId, Integer opType);

    void doLike(String objectId, String userId, String nickName, OperRecordOpTypeEnum opTypeEnum) throws BusinessException;
}