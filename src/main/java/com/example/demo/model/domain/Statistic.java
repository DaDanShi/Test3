package com.example.demo.model.domain;

import javax.persistence.*;

@Entity(name = "t_statistic")
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id; // 文章统计id
    @Column(name = "article_id")
    Integer articleId; // 文章id
    Integer hits; // 文章点击量
    @Column(name = "comments_num")
    Integer commentsNum; // 文章评论量

    @Override
    public String toString() {
        return "Statistic{" +
                "id=" + id +
                ", article_id=" + articleId +
                ", hits=" + hits +
                ", comments_num=" + commentsNum +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer article_id) {
        this.articleId = article_id;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(Integer commentsNum) {
        this.commentsNum = commentsNum;
    }
}
