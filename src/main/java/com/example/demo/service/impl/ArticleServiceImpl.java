package com.example.demo.service.impl;

import com.example.demo.dao.ArticleDao;
import com.example.demo.dao.StatisticDao;
import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Statistic;
import com.example.demo.service.ArticleService;
import com.example.demo.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleDao articleDao;
    @Resource
    private StatisticDao statisticDao;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageInfo<Article> selectArticleWithPage(Integer page, Integer count) {
        PageHelper.startPage(page, count);
        List<Article> articleList = articleDao.selectArticleWithPage();
        return new PageInfo<>(articleList);
    }

    @Override
    public List<Article> getHeatArticles(Integer count) {
        // 获取文章热度信息
        List<Statistic> statisticList = statisticDao.getStatistic();
        List<Article> articleList = new ArrayList<>();
        // 获取热度前十文章信息
        for (int i = 0; i < 10; i++) {
            Article article = articleDao.selectArticleWithId(statisticList.get(i).getArticleId());
            article.setHits(statisticList.get(i).getHits());
            articleList.add(article);
        }
        return articleList;
    }

    @Override
    public Article selectArticleWithId(Integer id) {
        Article article = null;
        Object o = redisTemplate.opsForValue().get("article_" + id);
        if (o != null) {
            article = (Article) o;
        } else {
            article = articleDao.selectArticleWithId(id);
            if (article != null) {
                redisTemplate.opsForValue().set("article_" + id, article);
            }
        }
        return article;
    }
}
