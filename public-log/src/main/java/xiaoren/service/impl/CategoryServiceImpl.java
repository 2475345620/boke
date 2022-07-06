package xiaoren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xiaoren.constants.SystemConstants;
import xiaoren.domain.entity.Article;
import xiaoren.domain.entity.Category;
import xiaoren.domain.entity.ResponseResult;
import xiaoren.domain.vo.CategoryVo;
import xiaoren.mapper.ArticleMapper;
import xiaoren.mapper.CategoryMapper;
import xiaoren.service.ArticleService;
import xiaoren.service.CategoryService;
import xiaoren.utils.BeanCopyUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-06-15 18:50:19
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
//        查询文章表  状态已发布的
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(queryWrapper);
//        获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
//        查询分类表
        List<Category> categories = listByIds(categoryIds);

       categories= categories.stream()
                .filter(category ->SystemConstants.STATUS_NORMAL.equals(category.getStatus()) )
                .collect(Collectors.toList());
//        封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }
}
