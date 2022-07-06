package xiaoren.handler.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xiaoren.domain.entity.ResponseResult;
import xiaoren.enums.AppHttpCodeEnum;
import xiaoren.exception.SystemException;

//@ControllerAdvice
//@ResponseBody
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({SystemException.class})
    public ResponseResult systemExceptionHandler(SystemException e){
//      打印异常信息
        log.error("出现异常了{}",e);
//        从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(e.getCode(),e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseResult ExceptionHandler(Exception e){
//      打印异常信息
        log.error("出现异常了{}",e);
//        从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }
}
