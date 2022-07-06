package xiaoren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import xiaoren.domain.entity.LoginUser;
import xiaoren.domain.entity.ResponseResult;
import xiaoren.domain.entity.User;
import xiaoren.domain.vo.BlogUserLoginVo;
import xiaoren.domain.vo.UserInfoVo;
import xiaoren.service.BlogLoginService;
import xiaoren.utils.BeanCopyUtils;
import xiaoren.utils.JwtUtil;
import xiaoren.utils.RedisCache;

import java.net.Authenticator;
import java.util.Objects;
@Service
public class BlogLoginServiceImpl implements BlogLoginService {
    @Autowired
    private AuthenticationManager  authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
//        判断是否认证通过
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }

//        获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
//        把用户信息 存入redis
        redisCache.setCacheObject("bloglogin:"+userId,loginUser);
//        把token和userinfo封装 返回
//        把user转换成userinfo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt,userInfoVo);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {

//        获取token 解析userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

//        获取userid
        Long userId = loginUser.getUser().getId();

//        删除redis的用户信息
        redisCache.deleteObject("bloglogin"+userId);

        return ResponseResult.okResult();
    }
}
