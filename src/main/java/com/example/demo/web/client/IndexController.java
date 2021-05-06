package com.example.demo.web.client;

import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Comment;
import com.example.demo.service.ArticleService;
import com.example.demo.service.CommentService;
import com.example.demo.service.ISiteService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Resource
    private ArticleService articleService;
    @Resource
    private CommentService commentService;
    @Resource
    private ISiteService siteService;
    private int count = 5; // 分页查询，每页条数/每页品论数
    private int number = 10; // 文章热度前十

    @GetMapping("/index")
    public String index(Model model) {
        return this.index(model, 1, count);
    }

    // 文章页
    @GetMapping(value = "/page/{p}")
    public String index(Model model, @PathVariable("p") int page, @RequestParam(value = "count", defaultValue = "5") int count) {
        PageInfo<Article> articles = articleService.selectArticleWithPage(page, count);
        List<Article> articleList = articleService.getHeatArticles(number);
        model.addAttribute("articles", articles);
        model.addAttribute("articleList", articleList);
        return "client/index";
    }

    // 文章详情
    @GetMapping(value = "article/{id}")
    public String article(HttpServletRequest request, @PathVariable("id") int id) {
        Article article = articleService.selectArticleWithId(id);
        if (article != null) {
            getArticleComments(request, article);
            // 文章被点击，跟新文章热度信息
            siteService.updateStatistics(article);
            request.setAttribute("article", article);
            return "client/articleDetails";
        } else {
            return "comm/error_404";
        }
    }

    private void getArticleComments(HttpServletRequest request, Article article) {
        String cp = request.getParameter("cp");
        // 如果 cp 为空则为 1
        cp = StringUtils.isBlank(cp) ? "1" : cp;
        PageInfo<Comment> pageInfo = commentService.getComments(article.getId(), Integer.parseInt(cp), count);
        request.setAttribute("cp", cp);
        request.setAttribute("comments", pageInfo);
    }
}
