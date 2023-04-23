package com.forum.controller;

import com.forum.annotation.GlobalInterceptor;
import com.forum.annotation.VerifyParam;
import com.forum.constants.Constants;
import com.forum.controller.base.ABaseController;
import com.forum.entity.dto.FileUploadDto;
import com.forum.entity.po.ForumBoard;
import com.forum.entity.vo.ResponseVO;
import com.forum.enums.FileUploadTypeEnum;
import com.forum.exception.BusinessException;
import com.forum.service.ForumBoardService;
import com.forum.utils.FileUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController("forumBoardController")
@RequestMapping("/board")
public class ForumBoardController extends ABaseController {

    @Resource
    private ForumBoardService forumBoardService;

    @Resource
    private FileUtils fileUtils;

    @RequestMapping("/loadBoard")
    private ResponseVO loadBoard() {
        return getSuccessResponseVO(forumBoardService.getBoardTree(null));
    }

    @RequestMapping("/saveBoard")
    @GlobalInterceptor(checkParams = true)
    private ResponseVO saveBoard(Integer boardId,
                                 @VerifyParam(required = true) Integer pBoardId,
                                 @VerifyParam(required = true) String boardName,
                                 String boardDesc,
                                 Integer postType,
                                 MultipartFile cover) throws BusinessException {
        ForumBoard forumBoard = new ForumBoard();
        forumBoard.setBoardId(boardId);
        forumBoard.setPBoardId(pBoardId);
        forumBoard.setBoardName(boardName);
        forumBoard.setBoardDesc(boardDesc);
        forumBoard.setPostType(postType);
        if (cover != null) {
            FileUploadDto fileUploadDto = fileUtils.uploadFile2Local(cover, Constants.FILE_FOLDER_IMAGE, FileUploadTypeEnum.ARTICLE_COVER);
            forumBoard.setCover(fileUploadDto.getLocalPath());
        }

        forumBoardService.saveForumBoard(forumBoard);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/delBoard")
    @GlobalInterceptor(checkParams = true)
    private ResponseVO delBoard(@VerifyParam(required = true) Integer boardId) {
        forumBoardService.deleteForumBoardByBoardId(boardId);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/changeBoardSort")
    @GlobalInterceptor(checkParams = true)
    private ResponseVO changeBoardSort(@VerifyParam(required = true) String boardIds) {
        forumBoardService.changeBoardSort(boardIds);
        return getSuccessResponseVO(null);
    }
}
