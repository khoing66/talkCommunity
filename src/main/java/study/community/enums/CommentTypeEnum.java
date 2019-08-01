package study.community.enums;

import study.community.model.Question;

/**
 * @ClassName CommentTypeEnum
 * @Description TODO
 * @Author khoing
 * @Date 2019/7/31 14:49
 * @Version 1.0
 **/
public enum  CommentTypeEnum {

    QUSETION(1),
    COMMENT(2);

    private Integer type;

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if (value.type.equals(type)) {
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
