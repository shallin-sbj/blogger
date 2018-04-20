package com.blogger.blogger.service.impl;

import com.blogger.blogger.domain.Blog;
import com.blogger.blogger.domain.Comment;
import com.blogger.blogger.domain.User;
import com.blogger.blogger.repository.BlogRepository;
import com.blogger.blogger.repository.CommentReposityory;
import com.blogger.blogger.service.BlogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 博客sercviceImpl
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentReposityory commentReposityory;

    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public void removeBlog(Long id) {
        blogRepository.deleteById(id);
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
        if (StringUtils.isEmpty(title)){
            return blogRepository.findBlogByUser(user,pageable);
        }
        title = "%" + title + "%";
        Page<Blog> blogs = blogRepository.findByUserAndTitleLikeOrderByCreateTimeDesc(user, title, pageable);
        return blogs;
    }

    @Override
    public Page<Blog> listBlogsByTitleLikeAndSort(User user, String title, Pageable pageable) {
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
            Comment comment = new Comment(commentContent, user);
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
}
