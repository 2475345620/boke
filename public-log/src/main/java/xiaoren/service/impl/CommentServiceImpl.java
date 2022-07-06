package xiaoren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xiaoren.constants.SystemConstants;
import xiaoren.domain.entity.Comment;
import xiaoren.domain.entity.ResponseResult;
import xiaoren.domain.vo.CommentVo;
import xiaoren.domain.vo.PageVo;
import xiaoren.enums.AppHttpCodeEnum;
import xiaoren.exception.SystemException;
import xiaoren.mapper.CommentMapper;
import xiaoren.service.CommentService;
import xiaoren.service.UserService;
import xiaoren.utils.BeanCopyUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-06-17 16:01:55
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
//        查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();

//       对articleId判断
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId, articleId);
//        根评论 rootId为-1
        queryWrapper.eq(Comment::getRootId, "-1");

//        判断评论类型
        queryWrapper.eq(Comment::getType,commentType);

//        分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);


        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

//        查询所有根评论的子评论集合，并且赋值给对应的属性
        for (CommentVo commentVo : commentVoList) {
//            查询对应的自子评论
            List<CommentVo> children =getChildren(commentVo.getId());
//            赋值
            commentVo.setChildren(children);
        }



        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
//        评论内容不能为空
        if (!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.COMMENT_NO_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    /**
 * 根据根评论的id查询对应的子评论的集合
 */

    private List<CommentVo> getChildren(Long id) {
    LambdaQueryWrapper<Comment> queryWrapper=new LambdaQueryWrapper<>();
    queryWrapper.eq(Comment::getRootId,id);
//    按照时间来排序
    queryWrapper.orderByAsc(Comment::getCreateTime);


    List<Comment> comments =list(queryWrapper);
        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;
    }

    private List<CommentVo> toCommentVoList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
//        遍历vo集合
        for (CommentVo commentVo : commentVos) {
            //        通过creatyBy查询用户的昵称并赋值
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
//        通过toCommentVoList查询用户的昵称并赋值
//            如果toCommentVoList不为-1才进行查询
            if (commentVo.getToCommentId() != -1) {
                String tocommentusername = userService.getById(commentVo.getToCommentId()).getNickName();
                commentVo.setToCommentUserName(tocommentusername);
            }
        }
        return commentVos;

    }
}
