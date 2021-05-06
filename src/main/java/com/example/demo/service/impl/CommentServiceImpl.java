package com.example.demo.service.impl;

import com.example.demo.dao.CommentDao;
import com.example.demo.model.domain.Comment;
import com.example.demo.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("commentService")
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentDao commentDao;

    @Override
    public PageInfo<Comment> getComments(Integer aid, int page, int count) {
        PageHelper.startPage(page, count);
        List<Comment> commentList = commentDao.selectCommentWithPage(aid);
        return new PageInfo<>(commentList);
    }
}
