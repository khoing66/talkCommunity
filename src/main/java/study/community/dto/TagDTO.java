package study.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @ClassName TagDTO
 * @Description TODO
 * @Author khoing
 * @Date 2019/8/3 14:53
 * @Version 1.0
 **/
@Data
public class TagDTO {
    private String categoryName;

    private List<String> tags;
}
