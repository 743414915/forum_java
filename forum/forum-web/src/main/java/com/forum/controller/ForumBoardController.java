package com.forum.controller;

import com.forum.controller.base.ABaseController;
import com.forum.entity.vo.ResponseVO;
import com.forum.service.ForumBoardService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/board")
public class ForumBoardController extends ABaseController {

    @Resource
    private ForumBoardService forumBoardService;

    @RequestMapping("loadBoard")
    public ResponseVO loadBoard() {
        return getSuccessResponseVO(forumBoardService.getBoardTree(null));
    }
}
