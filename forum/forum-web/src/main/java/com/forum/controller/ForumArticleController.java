package com.forum.controller;

import com.forum.controller.base.ABaseController;
import com.forum.entity.dto.SessionWebUserDto;
import com.forum.entity.query.ForumArticleQuery;
import com.forum.entity.vo.PaginationResultVO;
import com.forum.entity.vo.ResponseVO;
import com.forum.entity.vo.web.ForumArticleVO;
import com.forum.enums.ArticleOrderTypeEnum;
import com.forum.enums.ArticleStatusEnum;
import com.forum.service.ForumArticleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/forum")
public class ForumArticleController extends ABaseController {

    @Resource
    private ForumArticleService forumArticleService;

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
        return getSuccessResponseVO(convert2PaginationVO(resultVO, ForumArticleVO.class));
    }
}
