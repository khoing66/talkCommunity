package study.community.dto;

import lombok.Data;

/**
 * Create by Khoing.
 */
@Data
public class accessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;


}
