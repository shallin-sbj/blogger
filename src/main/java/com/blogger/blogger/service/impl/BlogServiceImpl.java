package com.blogger.blogger.service.impl;

import com.blogger.blogger.domain.*;
import com.blogger.blogger.domain.es.EsBlog;
import com.blogger.blogger.repository.BlogRepository;
import com.blogger.blogger.service.BlogService;
import com.blogger.blogger.service.EsBlogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

/**
 * 博客sercviceImpl
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private EsBlogService esBlogService;

    @Override
    public Blog saveBlog(Blog blog) throws Exception {
        boolean isNew = (blog.getId() == null);
        EsBlog esBlog = null;
        Blog repositoryBlog = blogRepository.save(blog);
        if (isNew) {
            esBlog = new EsBlog(repositoryBlog);
        } else {
            try {
                esBlog = esBlogService.getEsBlogByBlogId(blog.getId());
                esBlog.update(repositoryBlog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        esBlogService.updateEsBlog(esBlog);
        return repositoryBlog;
    }

    @Override
    public void removeBlog(Long id) throws Exception {
        blogRepository.deleteById(id);
        EsBlog blog = esBlogService.getEsBlogByBlogId(id);
        esBlogService.removeEsBlog(blog.getId());
    }

    @Override
    public Blog updateBlog(Blog blog) {
        return blogRepository.saveAndFlush(blog);
    }

    @Override
    public Blog getBlogById(Long id) {
        Optional<Blog> blogOptional = blogRepository.findById(id);
        if (blogOptional.isPresent()) {
            return blogOptional.get();
        }
        return new Blog();
    }

    @Override
    public Page<Blog> listBlogsByTitleLike(User user, String title, Pageable pageable) {
        if (StringUtils.isEmpty(title)) {
            return blogRepository.findBlogByUser(user, pageable);
        }
        title = "%" + title + "%";
        Page<Blog> blogs = blogRepository.findByUserAndTitleLikeOrderByCreateTimeDesc(user, title, pageable);
        return blogs;
    }

    @Override
    public Page<Blog> listBlogsByTitleLikeAndSort(User user, String title, Pageable pageable) {
        if (StringUtils.isEmpty(title)) {
            return blogRepository.findBlogByUser(user, pageable);
        }
        title = "%" + title + "%";
        Page<Blog> blogs = blogRepository.findByUserAndTitleLike(user, title, pageable);
        return blogs;
    }

    @Override
    public void readingIncrease(Long id) {
        Optional<Blog> blogOptional = blogRepository.findById(id);
        if (blogOptional.isPresent()) {
            Blog blog = blogOptional.get();
            blog.setReadSize(blog.getReadSize() + 1);
            blogRepository.save(blog);
        }
    }

    @Override
    public Blog createComment(Long blogId, String commentContent) {
        Optional<Blog> blogs = blogRepository.findById(blogId);
        if (blogs.isPresent()) {
            Blog originalBlog = blogs.get();
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Comment comment = new Comment(user, commentContent);
            comment.setCreateTime(new Timestamp(new Date().getTime()));
            originalBlog.addComment(comment);
            return blogRepository.save(originalBlog);
        }
        return new Blog();
    }

    @Override
    public void removeComment(Long blogId, Long commentId) {
        Optional<Blog> blogs = blogRepository.findById(blogId);
        if (blogs.isPresent()) {
            Blog originalBlog = blogs.get();
            originalBlog.removeComment(commentId);
            blogRepository.save(originalBlog);
        }
    }

    @Override
    public Blog createVote(Long blogId) {
        Optional<Blog> blogs = blogRepository.findById(blogId);
        if (blogs.isPresent()) {
            Blog originalBlog = blogRepository.findById(blogId).get();
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Vote vote = new Vote(user);
            boolean isExist = originalBlog.addVote(vote);
            if (isExist) {
                throw new IllegalArgumentException("该用户已经点过赞了");
            }
            return blogRepository.save(originalBlog);
        }
        return new Blog();
    }

    @Override
    public void removeVote(Long blogId, Long voteId) {
        Optional<Blog> blogs = blogRepository.findById(blogId);
        if (blogs.isPresent()) {
            Blog originalBlog = blogRepository.findById(blogId).get();
            originalBlog.removeVote(voteId);
            blogRepository.save(originalBlog);
        }
    }

    @Override
    public Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable) {
        return blogRepository.findBlogByCatalog(catalog, pageable);
    }
}
