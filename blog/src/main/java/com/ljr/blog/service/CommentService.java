package com.ljr.blog.service;

import com.ljr.blog.po.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);
    Comment saveComment(Comment comment);
    void deleteComment(Long id);
    Long findBlogId(Long id);
}
