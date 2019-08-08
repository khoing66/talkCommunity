package study.community.dto;

import lombok.Data;

/**
 * @ClassName HotTagDTO
 * @Description TODO
 * @Author khoing
 * @Date 2019/8/8 12:41
 * @Version 1.0
 **/
@Data
public class HotTagDTO implements Comparable {

    private String name;
    private Integer priority;

    @Override
    public int compareTo(Object o) {
        return this.getPriority() - ((HotTagDTO) o).getPriority();
    }
}
