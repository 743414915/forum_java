package com.forum.service;


import java.util.List;

import com.forum.entity.vo.PaginationResultVO;
import com.forum.entity.po.Message;
import com.forum.entity.query.MessageQuery;

/**
 * @Description: 用户消息MessageService
 * @auther: chong
 * @date: 2023/03/26
 */
public interface MessageService {

	/**
	 * 根据条件查询列表
 	 */
	List<Message> findListByParam(MessageQuery query);

	/**
	 * 根据条件查询数量
 	 */
	Integer findCountByParam(MessageQuery query);

	/**
	 * 分页查询
 	 */
	PaginationResultVO<Message> findListByPage(MessageQuery query);

	/**
	 * 新增
 	 */
	Integer add(Message bean);

	/**
	 * 批量新增
 	 */
	Integer addBatch(List<Message> ListBean);

	/**
	 * 批量新增或修改
 	 */
	Integer addOrUpdateBatch(List<Message> ListBean);

	/**
	 * 根据MessageId查询
 	 */
	Message getMessageByMessageId(Integer messageId);

	/**
	 * 根据MessageId更新
 	 */
	Integer updateMessageByMessageId( Message bean, Integer messageId);

	/**
	 * 根据MessageId删除
 	 */
	Integer deleteMessageByMessageId(Integer messageId);

	/**
	 * 根据ArticleIdAndCommentIdAndSendUserIdAndMessageType查询
 	 */
	Message getMessageByArticleIdAndCommentIdAndSendUserIdAndMessageType(String articleId, Integer commentId, String sendUserId, Integer messageType);

	/**
	 * 根据ArticleIdAndCommentIdAndSendUserIdAndMessageType更新
 	 */
	Integer updateMessageByArticleIdAndCommentIdAndSendUserIdAndMessageType( Message bean, String articleId, Integer commentId, String sendUserId, Integer messageType);

	/**
	 * 根据ArticleIdAndCommentIdAndSendUserIdAndMessageType删除
 	 */
	Integer deleteMessageByArticleIdAndCommentIdAndSendUserIdAndMessageType(String articleId, Integer commentId, String sendUserId, Integer messageType);

}