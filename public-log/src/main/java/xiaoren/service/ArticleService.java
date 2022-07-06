package xiaoren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xiaoren.domain.entity.Article;
import xiaoren.domain.entity.ResponseResult;


public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail( Long id);

    ResponseResult updateViewCount(Long id);
}
