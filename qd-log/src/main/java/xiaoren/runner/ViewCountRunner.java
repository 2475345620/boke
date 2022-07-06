package xiaoren.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import xiaoren.domain.entity.Article;
import xiaoren.mapper.ArticleMapper;
import xiaoren.utils.RedisCache;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisCache redisCache;
    @Override
    public void run(String... args) throws Exception {
//        查询博客信息  id  viewCount
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article1 -> article1.getId().toString(), article -> {
                    return article.getViewCount().intValue();
        /*articles.stream()
                .collect(Collectors.toMap(new Function<Article, Long>() {
                    @Override
                    public Long apply(Article article) {
                        return article.getId();
                    }
                }, new Function<Article, Integer>() {
                    @Override
                    public Integer apply(Article article) {
                        return article.getViewCount().intValue();
                    }
                }));*/
                }));
//        存入redis
        redisCache.setCacheMap("article:viewCount",viewCountMap);
    }
}
