package xiaoren.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListVo {
    private Long id;
    //标题
    private String title;
    private String summary;
    //所属分类id
    private String  categoryName;
    private String  thumbnail;
    //访问量
    private Long viewCount;
    private Date createTime;



}
