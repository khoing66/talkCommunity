package study.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.community.mapper.QuestionMapper;
import study.community.mapper.UserMapper;
import study.community.model.Question;
import study.community.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName PublishController
 * @Description TODO
 * @Author khoing
 * @Date 2019/7/28 10:03
 * @Version 1.0
 **/
@Controller
public class PublishController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    Question question;
    @Autowired
    QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String create(@RequestParam(name = "title") String title,
                         @RequestParam(name = "description") String descrition,
                         @RequestParam(name = "tag") String tag,
                         HttpServletRequest request,
                         Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", descrition);
        model.addAttribute("tag", tag);
        if (null == title) {
            model.addAttribute("error", "标题不能为空");
            return "/publish";
        }
        if (null == descrition) {
            model.addAttribute("error", "描述不能为空");
            return "/publish";
        }
        if (null == tag) {
            model.addAttribute("error", "标签不能为空");
            return "/publish";
        }
        Cookie[] cookies = request.getCookies();
        User user = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                user = userMapper.findByToken(cookie.getValue());
                if (null != user) {
                    request.getSession().setAttribute("user", user);
                    break;
                }
            }
        }
        if (null == user) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        question.setTitle(title);
        question.setDescription(descrition);
        question.setTag(tag);
        question.setGmtCreate(user.getGmtCreate());
        question.setGmtModify(user.getGmtModify());
        question.setCreatorId(user.getAccountId());
        question.setGmtModify(question.getGmtModify());
        questionMapper.create(question);
        return "redirect:/";
    }
}
