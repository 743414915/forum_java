package com.forum.controller;

import com.forum.annotation.GlobalInterceptor;
import com.forum.annotation.VerifyParam;
import com.forum.constants.Constants;
import com.forum.controller.base.ABaseController;
import com.forum.entity.config.WebConfig;
import com.forum.entity.dto.SessionWebUserDto;
import com.forum.entity.po.*;
import com.forum.entity.query.ForumArticleAttachmentQuery;
import com.forum.entity.query.ForumArticleQuery;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.entity.vo.ResponseVO;
import com.forum.entity.vo.UserDownloadInfoVO;
import com.forum.entity.vo.web.ForumArticleAttachmentVO;
import com.forum.entity.vo.web.ForumArticleDetailVO;
import com.forum.entity.vo.web.ForumArticleVO;
import com.forum.enums.*;
import com.forum.exception.BusinessException;
import com.forum.service.*;
import com.forum.utils.CopyTools;
import com.forum.utils.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/forum")
public class ForumArticleController extends ABaseController {
    private static final Logger logger = LoggerFactory.getLogger(ForumArticleController.class);

    @Resource
    private ForumArticleService forumArticleService;

    @Resource
    private ForumArticleAttachmentService forumArticleAttachmentService;

    @Resource
    private LikeRecordService likeRecordService;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private ForumArticleAttachmentDownloadService forumArticleAttachmentDownloadService;

    @Resource
    private WebConfig webConfig;

    @Resource
    private ForumBoardService forumBoardService;

    @RequestMapping("/loadArticle")
    public ResponseVO loadArticle(HttpSession session, Integer boardId, Integer pBoardId, Integer orderType, Integer pageNo) {
        ForumArticleQuery articleQuery = new ForumArticleQuery();
        articleQuery.setBoardId(boardId == null || boardId == 0 ? null : boardId);
        articleQuery.setPBoardId(pBoardId);
        articleQuery.setPageNo(pageNo);

        SessionWebUserDto userDto = getUserInfoFromSession(session);
        if (userDto != null) {
            articleQuery.setCurrentUserId(userDto.getUserId());
        } else {
            articleQuery.setStatus(ArticleStatusEnum.AUDIT.getStatus());
        }

        ArticleOrderTypeEnum orderTypeEnum = ArticleOrderTypeEnum.getByType(orderType);
        orderTypeEnum = orderTypeEnum == null ? ArticleOrderTypeEnum.HOT : orderTypeEnum;
        articleQuery.setOrderBy(orderTypeEnum.getOrderSql());

        PaginationResultVO resultVO = forumArticleService.findListByPage(articleQuery);
//        try {
//            // 模拟网速慢的时候，等待二秒，为了可以在前端展示loading
//            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return getSuccessResponseVO(convert2PaginationVO(resultVO, ForumArticleVO.class));
    }

    @RequestMapping("/getArticleDetail")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO getArticleDetail(HttpSession session, @VerifyParam(required = true) String articleId) throws BusinessException {

        SessionWebUserDto sessionWebUserDto = getUserInfoFromSession(session);

        ForumArticle forumArticle = forumArticleService.readArticle(articleId);

        /**
         * forumArticle没有
         * session里面没有值说明没有登录   或者   登录了  但登录的不是待审核的帖子的号主    或者   不是管理员   管理员是可以看待审核的
         * 帖子被删除了
         */
        if (null == forumArticle ||
                (ArticleStatusEnum.NO_AUDIT.getStatus().equals(forumArticle.getStatus())
                        && (sessionWebUserDto == null || !sessionWebUserDto.getUserId().equals(forumArticle.getUserId()) || !sessionWebUserDto.getAdmin()))
                || ArticleStatusEnum.DEL.getStatus().equals(forumArticle.getStatus())) {
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        }

        ForumArticleDetailVO detailVO = new ForumArticleDetailVO();
        detailVO.setForumArticleVO(CopyTools.copy(forumArticle, ForumArticleVO.class));

        // 有附件
        if (forumArticle.getAttachmentType().equals(Constants.ONE)) {
            ForumArticleAttachmentQuery articleAttachmentQuery = new ForumArticleAttachmentQuery();
            articleAttachmentQuery.setArticleId(articleId);
            List<ForumArticleAttachment> forumArticleAttachmentList = forumArticleAttachmentService.findListByParam(articleAttachmentQuery);
            if (!forumArticleAttachmentList.isEmpty()) {
                detailVO.setAttachmentVO(CopyTools.copy(forumArticleAttachmentList.get(0), ForumArticleAttachmentVO.class));
            }
        }

        // 是否已经点赞
        if (sessionWebUserDto != null) {
            LikeRecord likeRecord = likeRecordService.getLikeRecordByObjectIdAndUserIdAndOpType(articleId, sessionWebUserDto.getUserId(), OperRecordOpTypeEnum.ARTICLE_LIKE.getType());
            if (likeRecord != null) {
                detailVO.setHaveLike(true);
            }
        }

        return getSuccessResponseVO(detailVO);
    }

    // 点赞
    @RequestMapping("/doLike")
    @GlobalInterceptor(checkLogin = true, checkParams = true, frequencyType = UserOperFrequencyTypeEnum.DO_LIKE)
    public ResponseVO doLike(HttpSession session, @VerifyParam(required = true) String articleId) throws BusinessException {
        SessionWebUserDto sessionWebUserDto = getUserInfoFromSession(session);

        likeRecordService.doLike(articleId, sessionWebUserDto.getUserId(), sessionWebUserDto.getNickName(), OperRecordOpTypeEnum.ARTICLE_LIKE);
        return getSuccessResponseVO(null);
    }

    // 获取附件下载信息
    @RequestMapping("/getUserDownloadInfo")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public ResponseVO getUserDownloadInfo(HttpSession session, @VerifyParam(required = true) String fileId) throws BusinessException {
        SessionWebUserDto sessionWebUserDto = getUserInfoFromSession(session);

        UserInfo userInfo = userInfoService.getUserInfoByUserId(sessionWebUserDto.getUserId());

        UserDownloadInfoVO userDownloadInfoVO = new UserDownloadInfoVO();
        userDownloadInfoVO.setUserIntegral(userInfo.getCurrentIntegral());

        ForumArticleAttachmentDownload articleAttachmentDownload = forumArticleAttachmentDownloadService.getForumArticleAttachmentDownloadByFileIdAndUserId(fileId, sessionWebUserDto.getUserId());
        if (null != articleAttachmentDownload) {
            userDownloadInfoVO.setHaveDownload(true);
        }

        return getSuccessResponseVO(userDownloadInfoVO);
    }

    @RequestMapping("/attachmentDownload")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public void attachmentDownload(HttpSession session, HttpServletRequest request, HttpServletResponse response, @VerifyParam(required = true) String fileId) throws BusinessException {
        ForumArticleAttachment attachment = forumArticleAttachmentService.downloadAttachment(fileId, getUserInfoFromSession(session));
        InputStream in = null;
        OutputStream out = null;
        String downloadFileName = attachment.getFileName();
        String filePath = webConfig.getProjectFolder() + Constants.FILE_FOLDER_FILE + Constants.FILE_FOLDER_ATTACHMENT + attachment.getFilePath();
        File file = new File(filePath);
        try {
            in = new FileInputStream(file);
            out = response.getOutputStream();
            response.setContentType("application/x-msdownload; charset=UTF-8");
            //解决中文文件乱码
            if (request.getHeader("User-Agent").toLowerCase().indexOf("msie") > 0) { //IE浏览器
                downloadFileName = URLEncoder.encode(downloadFileName, "UTF-8");
            } else {
                downloadFileName = new String(downloadFileName.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setHeader("Content-Disposition", "attachment;filename=\"" + downloadFileName + "\"");
            byte[] byteData = new byte[1024];
            int len = 0;
            while ((len = in.read(byteData)) != -1) {
                out.write(byteData, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            logger.error("下载异常");
            throw new BusinessException("下载失败");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("io异常", e);
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                logger.error("io异常", e);
            }
        }
    }

    @RequestMapping("/loadBoard4Post")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO loadBoard4Post(HttpSession session) {
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        // 是管理员 查询所有
        Integer postType = null;
        if (!userDto.getAdmin()) {
            postType = Constants.ONE;
        }
        List<ForumBoard> boardList = forumBoardService.getBoardTree(postType);
        return getSuccessResponseVO(boardList);
    }

    @RequestMapping("/postArticle")
    @GlobalInterceptor(checkLogin = true, checkParams = true, frequencyType = UserOperFrequencyTypeEnum.POST_ARTICLE)
    public ResponseVO postArticle(HttpSession session,
                                  MultipartFile cover,
                                  MultipartFile attachment,
                                  Integer integral,
                                  @VerifyParam(required = true, max = 150) String title,
                                  @VerifyParam(required = true) Integer pBoardId,
                                  Integer boardId,
                                  @VerifyParam(max = 200) String summary,
                                  @VerifyParam(required = true) Integer editorType,
                                  @VerifyParam(required = true) String content,
                                  String markdownContent) throws BusinessException {
        SessionWebUserDto webUserDto = getUserInfoFromSession(session);
        title = StringTools.ecpapeHtml(title);

        ForumArticle forumArticle = new ForumArticle();
        forumArticle.setPBoardId(pBoardId);
        forumArticle.setBoardId(boardId);
        forumArticle.setTitle(title);
        forumArticle.setSummary(summary);
        forumArticle.setContent(content);

        EditorTypeEnum typeEnum = EditorTypeEnum.getByType(editorType);
        if (typeEnum == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        if (EditorTypeEnum.MARKDOWN.getType().equals(editorType) && StringTools.isEmpty(markdownContent)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        forumArticle.setMarkdownContent(markdownContent);
        forumArticle.setEditorType(editorType);
        forumArticle.setUserId(webUserDto.getUserId());
        forumArticle.setNickName(webUserDto.getNickName());
        forumArticle.setUserIpAddress(webUserDto.getProvince());

        // 附件信息
        ForumArticleAttachment forumArticleAttachment = new ForumArticleAttachment();
        forumArticleAttachment.setIntegral(integral == null ? 0 : integral);
        forumArticleService.postArticle(webUserDto.getAdmin(), forumArticle, forumArticleAttachment, cover, attachment);

        return getSuccessResponseVO(forumArticle.getArticleId());
    }


    @RequestMapping("/articleDetail4Update")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public ResponseVO articleDetail4Update(HttpSession session, @VerifyParam(required = true) String articleId) throws BusinessException {
        SessionWebUserDto userDto = getUserInfoFromSession(session);
        ForumArticle forumArticle = forumArticleService.getForumArticleByArticleId(articleId);

        if (forumArticle == null || !forumArticle.getUserId().equals(userDto.getUserId())) {
            throw new BusinessException("文章不存在或者无权编辑该文章");
        }
        ForumArticleDetailVO detailVO = new ForumArticleDetailVO();
        detailVO.setForumArticleVO(CopyTools.copy(forumArticle, ForumArticleVO.class));

        if (forumArticle.getAttachmentType().equals(Constants.ONE)) {
            ForumArticleAttachmentQuery articleAttachmentQuery = new ForumArticleAttachmentQuery();
            articleAttachmentQuery.setArticleId(articleId);
            List<ForumArticleAttachment> forumArticleAttachmentList = forumArticleAttachmentService.findListByParam(articleAttachmentQuery);
            if (!forumArticleAttachmentList.isEmpty()) {
                detailVO.setAttachmentVO(CopyTools.copy(forumArticleAttachmentList.get(0), ForumArticleAttachmentVO.class));
            }
        }

        return getSuccessResponseVO(detailVO);
    }

    @RequestMapping("/updateArticle")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public ResponseVO updateArticle(HttpSession session,
                                    MultipartFile cover,
                                    MultipartFile attachment,
                                    Integer integral,
                                    @VerifyParam(required = true) String articleId,
                                    @VerifyParam(required = true, max = 150) String title,
                                    @VerifyParam(required = true) Integer pBoardId,
                                    Integer boardId,
                                    @VerifyParam(max = 200) String summary,
                                    @VerifyParam(required = true) Integer editorType,
                                    @VerifyParam(required = true) String content,
                                    String markdownContent,
                                    @VerifyParam Integer attachmentType) throws BusinessException {
        SessionWebUserDto webUserDto = getUserInfoFromSession(session);
        title = StringTools.ecpapeHtml(title);

        ForumArticle forumArticle = new ForumArticle();
        forumArticle.setArticleId(articleId);
        forumArticle.setPBoardId(pBoardId);
        forumArticle.setBoardId(boardId);
        forumArticle.setTitle(title);
        forumArticle.setContent(content);
        forumArticle.setMarkdownContent(markdownContent);
        forumArticle.setEditorType(editorType);
        forumArticle.setSummary(summary);
        forumArticle.setUserIpAddress(webUserDto.getProvince());
        forumArticle.setAttachmentType(attachmentType);
        forumArticle.setUserId(webUserDto.getUserId());

        // 附件信息
        ForumArticleAttachment forumArticleAttachment = new ForumArticleAttachment();
        forumArticleAttachment.setIntegral(integral == null ? 0 : integral);

        forumArticleService.updateArticle(webUserDto.getAdmin(), forumArticle, forumArticleAttachment, cover, attachment);

        return getSuccessResponseVO(forumArticle.getArticleId());
    }

    @RequestMapping("/search")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO search(HttpSession session, @VerifyParam(required = true) String keyword) {
        ForumArticleQuery query = new ForumArticleQuery();
        query.setTitleFuzzy(keyword);

        PaginationResultVO resultVO = forumArticleService.findListByPage(query);
        return getSuccessResponseVO(resultVO);
    }
}
