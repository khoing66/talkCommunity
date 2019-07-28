package study.community.model;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @ClassName Question
 * @Description TODO
 * @Author khoing
 * @Date 2019/7/28 13:24
 * @Version 1.0
 **/
@Component
@Data
public class Question {
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

}
