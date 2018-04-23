package com.blogger.blogger.repository;

import com.blogger.blogger.domain.Blog;
import com.blogger.blogger.domain.Catalog;
import com.blogger.blogger.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 博客dao
 */

public interface BlogRepository extends JpaRepository<Blog,Long> {
    /**
     * 根据用户名分页查询用户列表
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    Page<Blog> findByUserAndTitleLikeOrderByCreateTimeDesc(User user, String title, Pageable pageable);

    /**
     * 根据用户名分页查询用户列表
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    Page<Blog> findByUserAndTitleLike(User user, String title, Pageable pageable);

    /**
     * 通过user查询列表
     */
    Page<Blog> findBlogByUser(User user,Pageable pageable);


    /**
     * 根据分类查询博客列表
     */
    Page<Blog> findBlogByCatalog(Catalog catalog,Pageable pageable);
}
