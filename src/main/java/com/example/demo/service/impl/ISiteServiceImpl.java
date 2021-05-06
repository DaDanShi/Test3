package com.example.demo.service.impl;

import com.example.demo.dao.StatisticDao;
import com.example.demo.model.ResponseData.StaticticsBo;
import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Comment;
import com.example.demo.model.domain.Statistic;
import com.example.demo.service.ISiteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ISiteServiceImpl implements ISiteService {
    @Resource
    private StatisticDao statisticDao;

    @Override
    public List<Comment> recentComments(int count) {
        return null;
    }

    @Override
    public List<Article> recentArticles(int count) {
        return null;
    }

    @Override
    public StaticticsBo getStatistics() {
        return null;
    }

    @Override
    public void updateStatistics(Article article) {
        Statistic statistic = statisticDao.selectStatisticWithArticleId(article.getId());
        if (null == statistic) {
            // 如果文章没有热度信息则新增文章对应的统计信息
            addStatistics(article);
        }
        statistic.setHits(statistic.getHits() + 1);
        statisticDao.updateArticleHitsWithId(statistic);
        article.setHits(statistic.getHits());
    }

    private void addStatistics(Article article) {
        statisticDao.addStatistic(article);
    }
}
