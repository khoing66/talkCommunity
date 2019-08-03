package study.community.dto;

import lombok.Data;
import study.community.exception.CustomizeErrorCode;
import study.community.exception.CustomizeException;

import java.util.List;

/**
 * @ClassName ResultDTO
 * @Description TODO
 * @Author khoing
 * @Date 2019/7/31 14:42
 * @Version 1.0
 **/
//泛型类的声明
// 在类名后面添加了类型参数声明部分。
@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

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
//    泛型方法声明都有一个类型参数声明部分（由尖括号分隔），该类型参数声明部分在方法返回类型之前
    public static <T>ResultDTO okOf(T t) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功，请稍等。。。。马上。。。");
        resultDTO.setData(t);
        return resultDTO;
    }
}
