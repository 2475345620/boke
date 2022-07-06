package xiaoren.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xiaoren.constants.SystemConstants;
import xiaoren.domain.entity.Comment;
import xiaoren.domain.entity.ResponseResult;
import xiaoren.service.CommentService;

@RestController
@RequestMapping("/comment")
@Api(tags = "评论")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
           return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){
      return   commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
    @ApiOperation(value = "友链评论列表")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }
}
