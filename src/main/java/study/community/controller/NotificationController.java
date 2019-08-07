package study.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import study.community.dto.NotificationDTO;
import study.community.enums.NotificationTypeEnum;
import study.community.model.Notification;
import study.community.model.User;
import study.community.service.NotificationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName NotificationController
 * @Description TODO
 * @Author khoing
 * @Date 2019/8/4 14:10
 * @Version 1.0
 **/
@Controller
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String  notificaiton(@PathVariable(name = "id") Long id, HttpServletRequest request) {

        User user  = (User)request.getSession().getAttribute("user");
        if (null == user) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id,user);
        if (NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType() || NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterId();
        }else{
            return "redirect:/";
        }

    }
}
