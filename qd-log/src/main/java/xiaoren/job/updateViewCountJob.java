package xiaoren.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xiaoren.domain.entity.Article;
import xiaoren.mapper.ArticleMapper;
import xiaoren.service.ArticleService;
import xiaoren.utils.RedisCache;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class updateViewCountJob {


    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0 0/10 * * * ? ")
    public void updateViewCount(){
//        获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");
        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());

//        更新到数据库
        articleService.updateBatchById(articles);
    }


}
