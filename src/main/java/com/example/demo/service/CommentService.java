package com.example.demo.service;

import com.example.demo.model.domain.Comment;
import com.github.pagehelper.PageInfo;

public interface CommentService {
    // 获取文章下的评论
    public PageInfo<Comment> getComments(Integer aid, int page, int count);
}
