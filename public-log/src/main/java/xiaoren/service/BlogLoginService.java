package xiaoren.service;

import xiaoren.domain.entity.ResponseResult;
import xiaoren.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
