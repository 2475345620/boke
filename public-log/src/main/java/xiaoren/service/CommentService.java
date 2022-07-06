package xiaoren.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xiaoren.domain.entity.Comment;
import xiaoren.domain.entity.ResponseResult;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-06-17 16:01:54
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId,Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
