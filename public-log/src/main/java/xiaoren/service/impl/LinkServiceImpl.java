package xiaoren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xiaoren.constants.SystemConstants;
import xiaoren.domain.entity.Link;
import xiaoren.domain.entity.ResponseResult;
import xiaoren.domain.vo.LinkVo;
import xiaoren.mapper.LinkMapper;
import xiaoren.service.LinkService;
import xiaoren.utils.BeanCopyUtils;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2022-06-17 10:19:05
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {


    @Override
    public ResponseResult getAllLink() {
//        查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);

//        转换成vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);

//        封装返回

        return ResponseResult.okResult(linkVos);
    }
}
