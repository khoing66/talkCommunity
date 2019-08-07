package study.community.exception;


import com.sun.org.apache.bcel.internal.classfile.Code;

/**
 * @ClassName CustomizeErrorCode
 * @Description TODO
 * @Author khoing
 * @Date 2019/7/31 13:12
 * @Version 1.0
 **/
public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"你找的问题不存在哦，或国家规定禁止发布访问，小臣无能为力哦~~~~"),
    TARGET_PARAM_NOT_FOUND(2001,"你找的问题未选择，或国家规定禁止发布访问，小臣无能为力哦~~~~"),
    NO_LOGIN(2003,"还没登录，不知道你谁，怎么评论成功呢？？？"),
    SYS_ERROR(2004, "服务器炸了，我的天！！！！"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或者你评论的问题不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在，要不要换个试试？"),
    NOTIFICATION_NOT_FOUND(2007, "不存在，已经删了吧。再想想。"),
    DIFFERENT_IDENTITY(2008, "你是怎么进来的，读的信息与本人不符。。");
    
    private String message;
    private Integer code;
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
