package com.forum.entity.vo.web;

public class ForumArticleDetailVO {

    private ForumArticleVO forumArticleVO;
    private ForumArticleAttachmentVO attachmentVO;
    private Boolean haveLike = false;

    public ForumArticleVO getForumArticleVO() {
        return forumArticleVO;
    }

    public void setForumArticleVO(ForumArticleVO forumArticleVO) {
        this.forumArticleVO = forumArticleVO;
    }

    public ForumArticleAttachmentVO getAttachmentVO() {
        return attachmentVO;
    }

    public void setAttachmentVO(ForumArticleAttachmentVO attachmentVO) {
        this.attachmentVO = attachmentVO;
    }

    public Boolean getHaveLike() {
        return haveLike;
    }

    public void setHaveLike(Boolean haveLike) {
        this.haveLike = haveLike;
    }
}
