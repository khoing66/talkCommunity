package study.community.exception;

/**
 * @ClassName CustomizeException
 * @Description TODO
 * @Author khoing
 * @Date 2019/7/31 11:12
 * @Version 1.0
 **/
public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;

    public CustomizeException(ICustomizeErrorCode iCustomizeErrorCode) {
        this.code = iCustomizeErrorCode.getCode();
        this.message = iCustomizeErrorCode.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
