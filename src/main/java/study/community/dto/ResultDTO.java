package study.community.dto;

import lombok.Data;
import study.community.exception.CustomizeErrorCode;
import study.community.exception.CustomizeException;

/**
 * @ClassName ResultDTO
 * @Description TODO
 * @Author khoing
 * @Date 2019/7/31 14:42
 * @Version 1.0
 **/
@Data
public class ResultDTO {
    private Integer code;
    private String message;

    public static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功，请稍等。。。。马上。。。");
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(), e.getMessage());

    }
}
