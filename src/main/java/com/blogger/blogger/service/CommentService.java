package com.blogger.blogger.service;

import com.blogger.blogger.domain.Comment;

/**
 * 评论service
 */
public interface CommentService {

    /**
     * 根据id获取 Comment
     * @param id
     * @return
     */
    Comment getCommentById(Long id);
    /**
     * 删除评论
     * @param id
     * @return
     */
    void removeComment(Long id);
}
