package xiaoren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xiaoren.domain.entity.ResponseResult;
import xiaoren.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-06-18 10:17:35
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);
}
