package com.forum.controller;

import com.forum.annotation.GlobalInterceptor;
import com.forum.annotation.VerifyParam;
import com.forum.constants.Constants;
import com.forum.controller.base.ABaseController;
import com.forum.entity.config.AdminConfig;
import com.forum.entity.po.ForumArticle;
import com.forum.entity.po.ForumArticleAttachment;
import com.forum.entity.query.ForumArticleAttachmentQuery;
import com.forum.entity.query.ForumArticleQuery;
import com.forum.entity.query.ForumCommentQuery;
import com.forum.entity.vo.ResponseVO;
import com.forum.exception.BusinessException;
import com.forum.service.ForumArticleAttachmentService;
import com.forum.service.ForumArticleService;
import com.forum.service.ForumCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;


@RestController("forumArticleController")
@RequestMapping("/forum")
public class ForumArticleController extends ABaseController {

    private static final Logger logger = LoggerFactory.getLogger(ForumArticleController.class);

    @Resource
    private ForumArticleService forumArticleService;

    @Resource
    private ForumArticleAttachmentService forumArticleAttachmentService;

    @Resource
    private AdminConfig adminConfig;

    @Resource
    private ForumCommentService forumCommentService;

    @RequestMapping("/loadArticle")
    public ResponseVO loadArticle(ForumArticleQuery articleQuery) {
        articleQuery.setOrderBy("post_time desc");
        return getSuccessResponseVO(forumArticleService.findListByPage(articleQuery));
    }

    @RequestMapping("/deleteArticle")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO deleteArticle(@VerifyParam(required = true) String articleIds) throws BusinessException {
        forumArticleService.delArticle(articleIds);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/updateBoard")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO updateBoard(@VerifyParam(required = true) String articleId,
                                  @VerifyParam(required = true) Integer pBoardId,
                                  @VerifyParam(required = true) Integer boardId) throws BusinessException {
        boardId = boardId == null ? 0 : boardId;

        forumArticleService.updateBoard(articleId, pBoardId, boardId);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/getAttachment")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO getAttachment(@VerifyParam(required = true) String articleId) throws BusinessException {
        ForumArticleAttachmentQuery articleAttachmentQuery = new ForumArticleAttachmentQuery();
        articleAttachmentQuery.setArticleId(articleId);
        List<ForumArticleAttachment> list = forumArticleAttachmentService.findListByParam(articleAttachmentQuery);
        if (list.isEmpty()) {
            throw new BusinessException("附件不存在");
        }

        return getSuccessResponseVO(list.get(0));
    }

    @RequestMapping("/attachmentDownload")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public void attachmentDownload(HttpServletRequest request, HttpServletResponse response, @VerifyParam(required = true) String fileId) throws BusinessException {
        ForumArticleAttachment attachment = forumArticleAttachmentService.getForumArticleAttachmentByFileId(fileId);
        InputStream in = null;
        OutputStream out = null;
        String downloadFileName = attachment.getFileName();
        String filePath = adminConfig.getProjectFolder() + Constants.FILE_FOLDER_FILE + Constants.FILE_FOLDER_ATTACHMENT + attachment.getFilePath();
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

    @RequestMapping("/topArticle")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO topArticle(@VerifyParam(required = true) String articleId, Integer topType) {
        ForumArticle forumArticle = new ForumArticle();
        forumArticle.setTopType(topType);
        forumArticleService.updateForumArticleByArticleId(forumArticle, articleId);

        return getSuccessResponseVO(null);
    }

    @RequestMapping("/auditArticle")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO auditArticle(@VerifyParam(required = true) String articleIds) throws BusinessException {
        forumArticleService.auditArticle(articleIds);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/loadComment")
    public ResponseVO loadComment(ForumCommentQuery commentQuery) {
        commentQuery.setLoadChildren(true);
        commentQuery.setOrderBy("post_time desc");
        return getSuccessResponseVO(forumCommentService.findListByPage(commentQuery));
    }

    @RequestMapping("/loadComment4Article")
    public ResponseVO loadComment4Article(ForumCommentQuery commentQuery) {
        commentQuery.setLoadChildren(true);
        commentQuery.setOrderBy("post_time desc");
        commentQuery.setPCommentId(0);
        return getSuccessResponseVO(forumCommentService.findCountByParam(commentQuery));
    }

    @RequestMapping("/delComment")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO delComment(@VerifyParam(required = true) String commentIds) throws BusinessException {
        forumCommentService.delComment(commentIds);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/auditComment")
    @GlobalInterceptor(checkParams = true)
    public ResponseVO auditComment(@VerifyParam(required = true) String commentIds) throws BusinessException {
        forumCommentService.auditComment(commentIds);
        return getSuccessResponseVO(null);
    }
}
