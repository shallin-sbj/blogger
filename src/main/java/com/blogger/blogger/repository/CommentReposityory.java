package com.blogger.blogger.repository;

import com.blogger.blogger.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  Comment Reposityory
 */
public interface CommentReposityory extends JpaRepository<Comment,Long> {
}
