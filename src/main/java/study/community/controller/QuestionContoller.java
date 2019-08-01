package study.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import study.community.dto.CommentDTO;
import study.community.dto.QuestionDTO;
import study.community.enums.CommentTypeEnum;
import study.community.exception.CustomizeErrorCode;
import study.community.exception.CustomizeException;
import study.community.service.CommentService;
import study.community.service.QuestionService;

import java.util.List;

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
    @Autowired
    CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id , Model model) {
        QuestionDTO questionDto = questionService.getById(id);
        List<CommentDTO> comments = commentService.listByQuestionId(id, CommentTypeEnum.QUSETION);
        model.addAttribute("question", questionDto);
        model.addAttribute("comments",comments);
        questionService.incView(id);
        return "question";
    }
}
