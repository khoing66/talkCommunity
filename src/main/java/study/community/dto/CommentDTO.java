package study.community.dto;

import lombok.Data;
import study.community.model.User;

/**
 * @ClassName CommentDTO
 * @Description TODO
 * @Author khoing
 * @Date 2019/8/1 23:08
 * @Version 1.0
 **/
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private String commentor;
    private Long gmtCreate;
    private Long gmtModify;
    private String content;
    private Long commentCount;
    private User user;
}
