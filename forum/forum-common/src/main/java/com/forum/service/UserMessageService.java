package com.forum.service;


import com.forum.entity.po.UserMessage;
import com.forum.entity.query.UserMessageQuery;
import com.forum.entity.vo.PaginationResultVO;

import java.util.List;

/**
 * @Description: 用户消息UserMessageService
 * @auther: chong
 * @date: 2023/03/27
 */
public interface UserMessageService {

	/**
	 * 根据条件查询列表
 	 */
	List<UserMessage> findListByParam(UserMessageQuery query);

	/**
	 * 根据条件查询数量
 	 */
	Integer findCountByParam(UserMessageQuery query);

	/**
	 * 分页查询
 	 */
	PaginationResultVO<UserMessage> findListByPage(UserMessageQuery query);

	/**
	 * 新增
 	 */
	Integer add(UserMessage bean);

	/**
	 * 批量新增
 	 */
	Integer addBatch(List<UserMessage> ListBean);

	/**
	 * 批量新增或修改
 	 */
	Integer addOrUpdateBatch(List<UserMessage> ListBean);

	/**
	 * 根据MessageId查询
 	 */
	UserMessage getUserMessageByMessageId(Integer messageId);

	/**
	 * 根据MessageId更新
 	 */
	Integer updateUserMessageByMessageId( UserMessage bean, Integer messageId);

	/**
	 * 根据MessageId删除
 	 */
	Integer deleteUserMessageByMessageId(Integer messageId);

	/**
	 * 根据ArticleIdAndCommentIdAndSendUserIdAndMessageType查询
 	 */
	UserMessage getUserMessageByArticleIdAndCommentIdAndSendUserIdAndMessageType(String articleId, Integer commentId, String sendUserId, Integer messageType);

	/**
	 * 根据ArticleIdAndCommentIdAndSendUserIdAndMessageType更新
 	 */
	Integer updateUserMessageByArticleIdAndCommentIdAndSendUserIdAndMessageType( UserMessage bean, String articleId, Integer commentId, String sendUserId, Integer messageType);

	/**
	 * 根据ArticleIdAndCommentIdAndSendUserIdAndMessageType删除
 	 */
	Integer deleteUserMessageByArticleIdAndCommentIdAndSendUserIdAndMessageType(String articleId, Integer commentId, String sendUserId, Integer messageType);

}