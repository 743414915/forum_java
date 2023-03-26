package com.forum.service.impl;

import java.util.List;

import com.forum.entity.query.SimplePage;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.PageSize;;
import com.forum.mappers.MessageMapper;
import com.forum.service.MessageService;
import org.springframework.stereotype.Service;
import com.forum.entity.po.Message;
import com.forum.entity.query.MessageQuery;

import javax.annotation.Resource;

/**
 * @Description: 用户消息MessageServiceImpl
 * @auther: chong
 * @date: 2023/03/26
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {

	@Resource
	private MessageMapper<Message, MessageQuery> messageMapper;

	/**
	 * 根据条件查询列表
 	 */
	public List<Message> findListByParam(MessageQuery query) {
		return this.messageMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
 	 */
	public Integer findCountByParam(MessageQuery query) {
		return this.messageMapper.selectCount(query);
	}

	/**
	 * 分页查询
 	 */
	public PaginationResultVO<Message> findListByPage(MessageQuery query) {
		Integer count = this.findCountByParam(query);
		int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<Message> list = this.findListByParam(query);
		PaginationResultVO<Message> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
 	 */
	public Integer add(Message bean) {
		return this.messageMapper.insert(bean);
	}

	/**
	 * 批量新增
 	 */
	public Integer addBatch(List<Message> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.messageMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或修改
 	 */
	public Integer addOrUpdateBatch(List<Message> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.messageMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据MessageId查询
 	 */
	public Message getMessageByMessageId(Integer messageId) {
		return this.messageMapper.selectByMessageId(messageId);
	}

	/**
	 * 根据MessageId更新
 	 */
	public Integer updateMessageByMessageId(Message bean, Integer messageId) {
		return this.messageMapper.updateByMessageId(bean, messageId);
	}

	/**
	 * 根据MessageId删除
 	 */
	public Integer deleteMessageByMessageId(Integer messageId) {
		return this.messageMapper.deleteByMessageId(messageId);
	}

	/**
	 * 根据ArticleIdAndCommentIdAndSendUserIdAndMessageType查询
 	 */
	public Message getMessageByArticleIdAndCommentIdAndSendUserIdAndMessageType(String articleId, Integer commentId, String sendUserId, Integer messageType) {
		return this.messageMapper.selectByArticleIdAndCommentIdAndSendUserIdAndMessageType(articleId, commentId, sendUserId, messageType);
	}

	/**
	 * 根据ArticleIdAndCommentIdAndSendUserIdAndMessageType更新
 	 */
	public Integer updateMessageByArticleIdAndCommentIdAndSendUserIdAndMessageType(Message bean, String articleId, Integer commentId, String sendUserId, Integer messageType) {
		return this.messageMapper.updateByArticleIdAndCommentIdAndSendUserIdAndMessageType(bean, articleId, commentId, sendUserId, messageType);
	}

	/**
	 * 根据ArticleIdAndCommentIdAndSendUserIdAndMessageType删除
 	 */
	public Integer deleteMessageByArticleIdAndCommentIdAndSendUserIdAndMessageType(String articleId, Integer commentId, String sendUserId, Integer messageType) {
		return this.messageMapper.deleteByArticleIdAndCommentIdAndSendUserIdAndMessageType(articleId, commentId, sendUserId, messageType);
	}

}