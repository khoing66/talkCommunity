package study.community.dto;

import lombok.Data;

/**
 * @ClassName Q
 * @Description TODO
 * @Author khoing
 * @Date 2019/8/7 18:34
 * @Version 1.0
 **/
@Data
public class QuestionSearchDTO {
    private String search;
    private Integer size;
    private Integer page;
    private String tag;
}

