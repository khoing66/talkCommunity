package study.community.dto;

import lombok.Data;
import study.community.model.User;

/**
 * @ClassName QuestionDto
 * @Description TODO
 * @Author khoing
 * @Date 2019/7/28 23:30
 * @Version 1.0
 **/
@Data
public class QuestionDto {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModify;
    private String creatorId;
    private Integer commentCount;
    private Integer likeCount;
    private Integer viewCount;
    private User user;

}
