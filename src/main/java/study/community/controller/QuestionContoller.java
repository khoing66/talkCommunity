package study.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import study.community.dto.QuestionDto;
import study.community.mapper.QuestionMapper;
import study.community.model.Question;
import study.community.service.QuestionService;

/**
 * @ClassName QuestionContoller
 * @Description TODO
 * @Author khoing
 * @Date 2019/7/30 13:12
 * @Version 1.0
 **/
@Controller
public class QuestionContoller {
    @Autowired
    QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id , Model model) {
        QuestionDto questionDto = questionService.getById(id);
        model.addAttribute("question", questionDto);
        questionService.incView(id);
        return "question";
    }
}
