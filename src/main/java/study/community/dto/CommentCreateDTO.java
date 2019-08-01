package study.community.dto;

import lombok.Data;

/**
 * @ClassName CommentDTO
 * @Description TODO
 * @Author khoing
 * @Date 2019/7/31 14:11
 * @Version 1.0
 **/
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;


}
