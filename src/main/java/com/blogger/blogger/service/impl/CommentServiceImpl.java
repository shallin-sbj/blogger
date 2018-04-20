package com.blogger.blogger.service.impl;

import com.blogger.blogger.domain.Comment;
import com.blogger.blogger.repository.CommentReposityory;
import com.blogger.blogger.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentReposityory commentReposityory;

    @Override
    public Comment getCommentById(Long id) {
        Optional<Comment> comment = commentReposityory.findById(id);
        if (comment.isPresent()){
            return comment.get();
        }
        return null;
    }

    @Override
    public void removeComment(Long id) {
        commentReposityory.deleteById(id);
    }
}
