package com.forum.service.impl;

import com.forum.constants.Constants;
import com.forum.entity.dto.SessionWebUserDto;
import com.forum.entity.po.*;
import com.forum.entity.query.*;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.*;
import com.forum.exception.BusinessException;
import com.forum.mappers.ForumArticleAttachmentDownloadMapper;
import com.forum.mappers.ForumArticleAttachmentMapper;
import com.forum.mappers.UserInfoMapper;
import com.forum.mappers.UserMessageMapper;
import com.forum.service.ForumArticleAttachmentService;
import com.forum.service.ForumArticleService;
import com.forum.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

;

/**
 * @Description: 文件信息ForumArticleAttachmentServiceImpl
 * @auther: chong
 * @date: 2023/03/27
 */
@Service("forumArticleAttachmentService")
public class ForumArticleAttachmentServiceImpl implements ForumArticleAttachmentService {

    @Resource
    private ForumArticleAttachmentMapper<ForumArticleAttachment, ForumArticleAttachmentQuery> forumArticleAttachmentMapper;

    @Resource
    private ForumArticleAttachmentDownloadMapper<ForumArticleAttachmentDownload, ForumArticleAttachmentDownloadQuery> forumArticleAttachmentDownloadMapper;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private ForumArticleService forumArticleService;

    @Resource
    private UserMessageMapper<UserMessage, UserMessageQuery> userMessageMapper;

    /**
     * 根据条件查询列表
     */
    public List<ForumArticleAttachment> findListByParam(ForumArticleAttachmentQuery query) {
        return this.forumArticleAttachmentMapper.selectList(query);
    }

    /**
     * 根据条件查询数量
     */
    public Integer findCountByParam(ForumArticleAttachmentQuery query) {
        return this.forumArticleAttachmentMapper.selectCount(query);
    }

    /**
     * 分页查询
     */
    public PaginationResultVO<ForumArticleAttachment> findListByPage(ForumArticleAttachmentQuery query) {
        Integer count = this.findCountByParam(query);
        int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

        SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
        query.setSimplePage(page);
        List<ForumArticleAttachment> list = this.findListByParam(query);
        PaginationResultVO<ForumArticleAttachment> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    public Integer add(ForumArticleAttachment bean) {
        return this.forumArticleAttachmentMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    public Integer addBatch(List<ForumArticleAttachment> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.forumArticleAttachmentMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或修改
     */
    public Integer addOrUpdateBatch(List<ForumArticleAttachment> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.forumArticleAttachmentMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据FileId查询
     */
    public ForumArticleAttachment getForumArticleAttachmentByFileId(String fileId) {
        return this.forumArticleAttachmentMapper.selectByFileId(fileId);
    }

    /**
     * 根据FileId更新
     */
    public Integer updateForumArticleAttachmentByFileId(ForumArticleAttachment bean, String fileId) {
        return this.forumArticleAttachmentMapper.updateByFileId(bean, fileId);
    }

    /**
     * 根据FileId删除
     */
    public Integer deleteForumArticleAttachmentByFileId(String fileId) {
        return this.forumArticleAttachmentMapper.deleteByFileId(fileId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ForumArticleAttachment downloadAttachment(String fileId, SessionWebUserDto sessionWebUserDto) throws BusinessException {
        ForumArticleAttachment forumArticleAttachment = this.forumArticleAttachmentMapper.selectByFileId(fileId);
        if (null == forumArticleAttachment) {
            throw new BusinessException("附件不存在");
        }
        ForumArticleAttachmentDownload download = null;
        if (forumArticleAttachment.getIntegral() > 0 && !sessionWebUserDto.getUserId().equals(forumArticleAttachment.getUserId())) {
            download = this.forumArticleAttachmentDownloadMapper.selectByFileIdAndUserId(fileId, sessionWebUserDto.getUserId());
            if (download != null) {
                UserInfo userInfo = userInfoService.getUserInfoByUserId(sessionWebUserDto.getUserId());
                if (userInfo.getCurrentIntegral() - forumArticleAttachment.getIntegral() < 0) {
                    throw new BusinessException("积分不足");
                }
            }
        }

        ForumArticleAttachmentDownload updateDownload = new ForumArticleAttachmentDownload();
        updateDownload.setArticleId(forumArticleAttachment.getArticleId());
        updateDownload.setFileId(fileId);
        updateDownload.setUserId(sessionWebUserDto.getUserId());
        updateDownload.setDownloadCount(Constants.ONE);
        this.forumArticleAttachmentDownloadMapper.insertOrUpdate(updateDownload);

        //更新下载次数
        this.forumArticleAttachmentMapper.updateDownloadCount(fileId);

        // 如果是自己的附件则不扣积分  或者已经下载过一次
        if (sessionWebUserDto.getUserId().equals(forumArticleAttachment.getUserId()) || download != null) {
            return forumArticleAttachment;
        }

        // 扣除下载人积分
        userInfoService.updateUserIntegral(sessionWebUserDto.getUserId(), UserIntegralOperTypeEnum.USER_DOWNLOAD_ATTACHMENT,
                UserIntegralChangeTypeEnum.REDUCE.getChangeType(), forumArticleAttachment.getIntegral());

        // 给附件提供者增加积分
        userInfoService.updateUserIntegral(forumArticleAttachment.getUserId(), UserIntegralOperTypeEnum.DOWNLOAD_ATTACHMENT,
                UserIntegralChangeTypeEnum.ADD.getChangeType(), forumArticleAttachment.getIntegral());

        // 记录消息
        ForumArticle forumArticle = forumArticleService.getForumArticleByArticleId(forumArticleAttachment.getArticleId());
        UserMessage userMessage = new UserMessage();
        userMessage.setMessageType(MessageTypeEnum.DOWNLOAD_ATTACHMENT.getType());
        userMessage.setCreateTime(new Date());
        userMessage.setArticleId(forumArticle.getArticleId());
        userMessage.setArticleTitle(forumArticle.getTitle());
        userMessage.setReceivedUserId(forumArticle.getUserId());
        userMessage.setCommentId(Constants.ZERO);
        userMessage.setSendUserId(forumArticle.getUserId());
        userMessage.setSendNickName(forumArticle.getNickName());
        userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());

        userMessageMapper.insert(userMessage);
        return forumArticleAttachment;
    }
}