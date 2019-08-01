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
    private Long parentId;
    private Integer type;
    private String commentor;
    private Long gmt_create;
    private Long gmt_modify;
    private String content;
    private User user;
}
