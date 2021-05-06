package com.example.demo.service;

import com.example.demo.model.domain.Article;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ArticleService {
    // 分页查询文章列表
    public PageInfo<Article> selectArticleWithPage(Integer page, Integer count);

    // 统计前10的热度文章信息
    public List<Article> getHeatArticles(Integer count);

    // 查看文章详情
    public Article selectArticleWithId(Integer id);
}
