package study.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import study.community.dto.CommentDTO;
import study.community.dto.QuestionDTO;
import study.community.enums.CommentTypeEnum;
import study.community.model.Question;
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

//        找到指定用户的所有问题，将question转为questionDto（带有user），并以model传到前台
        QuestionDTO questionDto = questionService.getById(id);
        model.addAttribute("question", questionDto);

//        找到tag相似的相关问题,将question转为questionDto（带有user），并以model传到前台
        List<QuestionDTO> relatedQuestions = questionService.selectRelatedQuestion(questionDto);
        model.addAttribute("relatedQuestions", relatedQuestions);

//        找到指定问题的所有评论，将comment转为commentDTO（带有user），并以model传到前台
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUSETION);
        model.addAttribute("comments",comments);

//        访问自增浏览数
        questionService.incView(id);

        return "question";
    }
}
