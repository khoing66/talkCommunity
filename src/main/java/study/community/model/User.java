package study.community.model;

import lombok.Data;

/**
 * Create by Khoing.
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModify;
    private String avatarUrl;
    private String bio;

}
