package study.community.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.community.cache.TagCache;
import study.community.dto.QuestionDTO;
import study.community.mapper.UserMapper;
import study.community.model.Question;
import study.community.model.User;
import study.community.service.QuestionService;

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
    QuestionService questionService;

    @GetMapping("/publish")
    public String publish(Model model) {
        //        把所有的标签库都通过model传到前台
        model.addAttribute("tags", TagCache.get());

        return "publish";
    }

    @PostMapping("/publish")
    public String create(@RequestParam(value = "title", required = false) String title,
                         @RequestParam(value = "description", required = false)String descrition,
                         @RequestParam(value = "tag", required = false) String tag,
                         @RequestParam(value = "id",required = false) Long id,
                         HttpServletRequest request,
                         Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", descrition);
        model.addAttribute("tag", tag);

//        把所有的标签库都通过model传到前台
        model.addAttribute("tags", TagCache.get());

        User user = (User)request.getSession().getAttribute("user");
        if (null == user) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        if (null == title || "" == title) {

            model.addAttribute("error", "标题不能为空");
            return "/publish";
        }

        if (null == descrition || "" == descrition) {

            model.addAttribute("error", "描述不能为空");
            return "/publish";
        }

        if (null == tag || "" == tag) {

            model.addAttribute("error", "标签不能为空");
            return "/publish";
        }

        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)) {

            model.addAttribute("error", "输入非法标签，或者是国家法律禁止的"+invalid);
            return "/publish";
        }




        Question question = new Question();
        question.setTitle(title);
        question.setDescription(descrition);
        question.setTag(tag);
        question.setCreatorId(user.getAccountId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name="id")Long id,Model model) {
        QuestionDTO quesiton = questionService.getById(id);
        model.addAttribute("title",quesiton.getTitle());
        model.addAttribute("description", quesiton.getDescription());
        model.addAttribute("tag", quesiton.getTag());
        model.addAttribute("id", quesiton.getId());

        //        把所有的标签库都通过model传到前台
        model.addAttribute("tags", TagCache.get());



        return "publish";

    }
}
