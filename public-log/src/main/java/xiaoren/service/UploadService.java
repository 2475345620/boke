package xiaoren.service;

import org.springframework.web.multipart.MultipartFile;
import xiaoren.domain.entity.ResponseResult;

public interface UploadService {
    ResponseResult uploadImg(MultipartFile img);
}
