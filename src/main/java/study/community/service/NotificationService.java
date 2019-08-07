package study.community.service;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.community.dto.NotificationDTO;
import study.community.dto.PaginationDTO;
import study.community.dto.QuestionDTO;
import study.community.enums.NotificationStatusEnum;
import study.community.enums.NotificationTypeEnum;
import study.community.exception.CustomizeErrorCode;
import study.community.exception.CustomizeException;
import study.community.mapper.NotificationMapper;
import study.community.mapper.QuestionMapper;
import study.community.mapper.UserMapper;
import study.community.model.*;

import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName NotificationService
 * @Description TODO
 * @Author khoing
 * @Date 2019/8/4 10:39
 * @Version 1.0
 **/
@Service
public class NotificationService {

    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    UserMapper userMapper;

    public PaginationDTO list(String accountId, Integer page, Integer size) {

        Integer totalPage;
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        List<QuestionDTO> questionDtos = new ArrayList<>();

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(accountId);
        Integer UsertotalCount = (int)notificationMapper.countByExample(notificationExample);

        if (UsertotalCount % size == 0 || UsertotalCount == 0) {
            totalPage = UsertotalCount / size;
        } else {
            totalPage = UsertotalCount / size + 1;
        }

        if(page <1){
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPaginnation(page, totalPage);

        Integer offSet = size * (page - 1);
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(accountId);
        example.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example, new RowBounds(offSet, size));
        if (notifications.size() == 0) {
            return paginationDTO;
        }
//        //获取通知人的accountId，并去重
//        Set<String> disNotifiers = notifications.stream().map(notification -> notification.getNotifier()).collect(Collectors.toSet());
//        //转成list,并通过UserMapper获取用户
//        ArrayList<String> notifiers = new ArrayList<>(disNotifiers);
//        UserExample userExample = new UserExample();
//        userExample.createCriteria().andAccountIdIn(notifiers);
//        List<User> notifyUsers = userMapper.selectByExample(userExample);
//        //转成map容易输入
//        notifyUsers.stream().collect(Collectors.toMap(u -> u.getAccountId(), u -> u));
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    public long unreadCount(String accountId) {
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(accountId)
                .andStatusEqualTo(NotificationStatusEnum.UNREADED.getStatus());

        return  notificationMapper.countByExample(example);

    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (null == notification) {
            throw  new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!notification.getReceiver().equals(user.getAccountId())) {
            throw new CustomizeException(CustomizeErrorCode.DIFFERENT_IDENTITY);
        }
        //设置已读
        notification.setStatus(NotificationStatusEnum.READED.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
