package study.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import study.community.dto.PaginationDTO;
import study.community.enums.NotificationStatusEnum;
import study.community.mapper.UserMapper;
import study.community.model.User;
import study.community.service.NotificationService;
import study.community.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;

/**
 * @ClassName ProfileController
 * @Description TODO
 * @Author khoing
 * @Date 2019/7/29 22:29
 * @Version 1.0
 **/
@Controller
public class ProfileController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionService questionService;
    @Autowired
    NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          Model model,
                          @PathVariable(name = "action") String action,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name="size",defaultValue = "5") Integer size
                         ) {
        User user = (User)request.getSession().getAttribute("user");
        if (null == user) {
            return "redirect:/";
        }
        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的发布");

            //查询自己发布的问题
            PaginationDTO pagination = questionService.list(user.getAccountId(), page, size);
            model.addAttribute("pagination", pagination);
        }
        if ("replies".equals(action)) {
            //查询通知
            PaginationDTO pagination = notificationService.list(user.getAccountId(), page, size);
            model.addAttribute("pagination", pagination);
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");

            long unreadCount = notificationService.unreadCount(user.getAccountId());
            model.addAttribute("unreadCount", unreadCount);

        }




        return "profile";
    }
}
