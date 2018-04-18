package com.blogger.blogger.service;

import com.blogger.blogger.domain.Blog;
import com.blogger.blogger.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 博客Service
 */
public interface BlogService {

    /**
     * 保存Blog
     * @param 
     * @return
     */
    Blog saveBlog(Blog blog);

    /**
     * 删除Blog
     * @param id
     * @return
     */
    void removeBlog(Long id);

    /**
     * 更新Blog
     * @param
     * @return
     */
    Blog updateBlog(Blog blog);

    /**
     * 根据id获取Blog
     * @param id
     * @return
     */
    Blog getBlogById(Long id);

    /**
     * 根据用户名进行分页模糊查询（最新）
     * @param user
     * @return
     */
    Page<Blog> listBlogsByTitleLike(User user, String title, Pageable pageable);

    /**
     * 根据用户名进行分页模糊查询（最热）
     * @param
     * @return
     */
    Page<Blog> listBlogsByTitleLikeAndSort(User suser, String title, Pageable pageable);

    /**
     * 阅读量递增
     * @param id
     */
    void readingIncrease(Long id);

}
