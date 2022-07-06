package xiaoren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xiaoren.domain.entity.Link;
import xiaoren.domain.entity.ResponseResult;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-06-17 10:19:04
 */
public interface LinkService extends IService<Link> {


    ResponseResult getAllLink();
}
