package study.community.dto;

import lombok.Data;
import study.community.model.User;

/**
 * @ClassName NotificationDTO
 * @Description TODO
 * @Author khoing
 * @Date 2019/8/4 10:35
 * @Version 1.0
 **/
@Data
public class NotificationDTO {
    private Long id;
    private Integer status;
    private String typeName;//notification对应type的name
    private Long outerId;
    private Integer type;
    private String notifier;
    private String notifierName;
    private Long gmtCreate;
    private String outerTitle;
}
