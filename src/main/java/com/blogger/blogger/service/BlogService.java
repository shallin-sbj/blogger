package com.blogger.blogger.service;

import com.blogger.blogger.domain.Blog;
import com.blogger.blogger.domain.Catalog;
import com.blogger.blogger.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/**
 * 博客Service
 */
public interface BlogService {

    /**
     * 保存Blog
     * @param 
     * @return
     */
    @Transactional
    Blog saveBlog(Blog blog) throws Exception;

    /**
     * 删除Blog
     * @param id
     * @return
     */
    @Transactional
    void removeBlog(Long id) throws Exception;

    /**
     * 更新Blog
     * @param
     * @return
     */
    @Transactional
    Blog updateBlog(Blog blog);

    /**
     * 根据id获取Blog
     * @param id
     * @return
     */
    @Transactional
    Blog getBlogById(Long id);

    /**
     * 根据用户名进行分页模糊查询（最新）
     * @param user
     * @return
     */
    @Transactional
    Page<Blog> listBlogsByTitleLike(User user, String title, Pageable pageable);

    /**
     * 根据用户名进行分页模糊查询（最热）
     * @param
     * @return
     */
    @Transactional
    Page<Blog> listBlogsByTitleLikeAndSort(User suser, String title, Pageable pageable);

    /**
     * 阅读量递增
     * @param id
     */
    void readingIncrease(Long id);

    /**
     * 发表评论
     * @param blogId
     * @param commentContent
     * @return
     */
    @Transactional
    Blog createComment(Long blogId, String commentContent);

    /**
     * 删除评论
     * @param blogId
     * @param commentId
     * @return
     */
    @Transactional
    void removeComment(Long blogId, Long commentId);

    /**
     * 点赞
     * @param blogId
     * @return
     */
    @Transactional
    Blog createVote(Long blogId);

    /**
     * 取消点赞
     * @param blogId
     * @param voteId
     * @return
     */
    void removeVote(Long blogId, Long voteId);

    /**
     * 根据分类查询
     */
    Page<Blog> listBlogsByCatalog(Catalog catalog,Pageable pageable);

}
