package com.forum.service.impl;

import com.forum.constants.Constants;
import com.forum.entity.dto.FileUploadDto;
import com.forum.entity.dto.SysSetting4AuditDto;
import com.forum.entity.dto.SysSettingDto;
import com.forum.entity.po.ForumArticle;
import com.forum.entity.po.ForumArticleAttachment;
import com.forum.entity.po.ForumBoard;
import com.forum.entity.query.ForumArticleAttachmentQuery;
import com.forum.entity.query.ForumArticleQuery;
import com.forum.entity.query.SimplePage;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.enums.*;
import com.forum.exception.BusinessException;
import com.forum.mappers.ForumArticleAttachmentMapper;
import com.forum.mappers.ForumArticleMapper;
import com.forum.service.ForumArticleService;
import com.forum.service.ForumBoardService;
import com.forum.service.UserInfoService;
import com.forum.utils.FileUtils;
import com.forum.utils.ImageUtils;
import com.forum.utils.StringTools;
import com.forum.utils.SysCacheUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

;

/**
 * @Description: 文章信息ForumArticleServiceImpl
 * @auther: chong
 * @date: 2023/03/27
 */
@Service("forumArticleService")
public class ForumArticleServiceImpl implements ForumArticleService {

    @Resource
    private ForumArticleMapper<ForumArticle, ForumArticleQuery> forumArticleMapper;

    @Resource
    private ForumBoardService forumBoardService;

    @Resource
    private FileUtils fileUtils;

    @Resource
    private ForumArticleAttachmentMapper<ForumArticleAttachment, ForumArticleAttachmentQuery> forumArticleAttachmentMapper;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private ImageUtils imageUtils;

    /**
     * 根据条件查询列表
     */
    public List<ForumArticle> findListByParam(ForumArticleQuery query) {
        return this.forumArticleMapper.selectList(query);
    }

    /**
     * 根据条件查询数量
     */
    public Integer findCountByParam(ForumArticleQuery query) {
        return this.forumArticleMapper.selectCount(query);
    }

    /**
     * 分页查询
     */
    public PaginationResultVO<ForumArticle> findListByPage(ForumArticleQuery query) {
        Integer count = this.findCountByParam(query);
        int pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();

        SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
        query.setSimplePage(page);
        List<ForumArticle> list = this.findListByParam(query);
        PaginationResultVO<ForumArticle> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 新增
     */
    public Integer add(ForumArticle bean) {
        return this.forumArticleMapper.insert(bean);
    }

    /**
     * 批量新增
     */
    public Integer addBatch(List<ForumArticle> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.forumArticleMapper.insertBatch(listBean);
    }

    /**
     * 批量新增或修改
     */
    public Integer addOrUpdateBatch(List<ForumArticle> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.forumArticleMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 根据ArticleId查询
     */
    public ForumArticle getForumArticleByArticleId(String articleId) {
        return this.forumArticleMapper.selectByArticleId(articleId);
    }

    /**
     * 根据ArticleId更新
     */
    public Integer updateForumArticleByArticleId(ForumArticle bean, String articleId) {
        return this.forumArticleMapper.updateByArticleId(bean, articleId);
    }

    /**
     * 根据ArticleId删除
     */
    public Integer deleteForumArticleByArticleId(String articleId) {
        return this.forumArticleMapper.deleteByArticleId(articleId);
    }

    @Override
    public ForumArticle readArticle(String articleId) throws BusinessException {
        ForumArticle forumArticle = this.forumArticleMapper.selectByArticleId(articleId);
        if (forumArticle == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        }
        if (ArticleStatusEnum.AUDIT.getStatus().equals(forumArticle.getStatus())) {
            this.forumArticleMapper.updateArticleCount(UpdateArticleCountTypeEnum.READ_COUNT.getType(), Constants.ONE, articleId);
        }
        return forumArticle;
    }

    @Override
    public void postArticle(Boolean isAdmin, ForumArticle article, ForumArticleAttachment articleAttachment, MultipartFile cover, MultipartFile attachment) throws BusinessException {
        resetBoardInfo(isAdmin, article);

        Date curDate = new Date();
        String articleId = StringTools.getRandomString(Constants.LENGTH_15);
        article.setArticleId(articleId);
        article.setPostTime(curDate);
        article.setLastUpdateTime(curDate);
        if (cover != null) {
            FileUploadDto fileUploadDto = fileUtils.uploadFile2Local(cover, Constants.FILE_FOLDER_IMAGE, FileUploadTypeEnum.ARTICLE_COVER);
            article.setCover(fileUploadDto.getLocalPath());
        }
        if (attachment != null) {
            uploadAttachment(article, articleAttachment, attachment, false);
            article.setAttachmentType(ArticleAttachmentTypeEnum.HAVE_ATTACHMENT.getType());
        } else {
            article.setAttachmentType(ArticleAttachmentTypeEnum.NO_ATTACHMENT.getType());
        }

        // 文章审核信息
        if (isAdmin) {
            article.setStatus(ArticleStatusEnum.AUDIT.getStatus());
        } else {
            SysSetting4AuditDto auditDto = SysCacheUtils.getSysSetting().getAuditSetting();
            article.setStatus(auditDto.getPostAudit() ? ArticleStatusEnum.NO_AUDIT.getStatus() : ArticleStatusEnum.AUDIT.getStatus());
        }
        // 替换图片
        String content = article.getContent();
        if (!StringTools.isEmpty(content)) {
            String month = imageUtils.resetImageHtml(content);
            String replaceMonth = "/" + month + "/";
            content = content.replace(Constants.FILE_FOLDER_TEMP, replaceMonth);
            article.setContent(content);
            String markdownContent = article.getMarkdownContent();
            if (!StringTools.isEmpty(markdownContent)) {
                markdownContent = markdownContent.replace(Constants.FILE_FOLDER_TEMP, replaceMonth);
                article.setMarkdownContent(markdownContent);
            }
        }

        this.forumArticleMapper.insert(article);

        //增加积分
        Integer postIntegral = SysCacheUtils.getSysSetting().getPostSetting().getPostIntegral();
        if (postIntegral > Constants.ZERO && ArticleStatusEnum.AUDIT.equals(article.getStatus())) {
            userInfoService.updateUserIntegral(article.getUserId(), UserIntegralOperTypeEnum.POST_ARTICLE, UserIntegralChangeTypeEnum.ADD.getChangeType(), postIntegral);
        }
    }

    private void resetBoardInfo(Boolean isAdmin, ForumArticle forumArticle) throws BusinessException {
        ForumBoard pBoard = forumBoardService.getForumBoardByBoardId(forumArticle.getPBoardId());
        if (pBoard == null || pBoard.getPostType().equals(Constants.ZERO) && !isAdmin) {
            throw new BusinessException("一级板块不存在");
        }
        forumArticle.setPBoardName(pBoard.getBoardName());
        if (forumArticle.getBoardId() != null && forumArticle.getBoardId() != 0) {
            ForumBoard board = forumBoardService.getForumBoardByBoardId(forumArticle.getBoardId());
            if (board == null || board.getPostType().equals(Constants.ZERO) && !isAdmin) {
                throw new BusinessException("二级板块不存在");
            }
            forumArticle.setBoardName(board.getBoardName());
        } else {
            forumArticle.setBoardId(0);
            forumArticle.setBoardName("");
        }
    }

    public void uploadAttachment(ForumArticle article, ForumArticleAttachment articleAttachment, MultipartFile file, Boolean isUpdate) throws BusinessException {
        Integer allowSizeMb = SysCacheUtils.getSysSetting().getPostSetting().getAttachmentSize();
        Long allowSize = Long.valueOf(allowSizeMb * Constants.FILE_SIZE_1M);
        if (file.getSize() > allowSize) {
            throw new BusinessException("附件最大只能上传" + allowSize + "MB");
        }

        // 修改
        if (isUpdate) {

        }
        FileUploadDto fileUploadDto = fileUtils.uploadFile2Local(file, Constants.FILE_FOLDER_ATTACHMENT, FileUploadTypeEnum.ARTICLE_ATTACHMENT);

        articleAttachment.setFileId(StringTools.getRandomNumber(Constants.LENGTH_15));
        articleAttachment.setArticleId(article.getArticleId());
        articleAttachment.setFileName(fileUploadDto.getOriginalFileName());
        articleAttachment.setFilePath(fileUploadDto.getLocalPath());
        articleAttachment.setFileSize(file.getSize());
        articleAttachment.setDownloadCount(Constants.ZERO);
        articleAttachment.setFileType(AttachmentFileTypeEnum.ZIP.getType());
        articleAttachment.setUserId(article.getUserId());

        forumArticleAttachmentMapper.insert(articleAttachment);
    }
}