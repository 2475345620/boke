package xiaoren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xiaoren.domain.entity.Category;
import xiaoren.domain.entity.ResponseResult;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-06-15 18:50:18
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}
