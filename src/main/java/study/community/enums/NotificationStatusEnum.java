package study.community.enums;

/**
 * @ClassName NotificationEnum
 * @Description TODO
 * @Author khoing
 * @Date 2019/8/4 8:50
 * @Version 1.0
 **/
public enum NotificationStatusEnum {
    READED(1),
    UNREADED(2);

    private int status;

    NotificationStatusEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
