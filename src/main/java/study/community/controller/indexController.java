package study.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.community.cache.HotTagCache;
import study.community.dto.PaginationDTO;
import study.community.mapper.QuestionMapper;
import study.community.mapper.UserMapper;
import study.community.schedule.TopHotRankSchedule;
import study.community.service.QuestionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Create by Khoing.
 */

@Controller
public class indexController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionService questionService;
    @Autowired
    HotTagCache hotTagCache;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "tag", required = false) String tag) {

        PaginationDTO pagination = questionService.list(page,tag, size,search);
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);

        List<String> hots = hotTagCache.getHots();
        model.addAttribute("hots", hots);

        return "index";
    }
}
