package study.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import study.community.dto.CommentDTO;
import study.community.dto.ResultDTO;
import study.community.exception.CustomizeErrorCode;
import study.community.model.Comment;
import study.community.model.User;
import study.community.service.CommentService;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName CommentController
 * @Description TODO
 * @Author khoing
 * @Date 2019/7/31 14:06
 * @Version 1.0
 **/
@Controller
public class CommentController {


    @Autowired
    private CommentService commentService;


    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
//            return new CustomizeException(CustomizeErrorCode.NO_LOGIN); 是不是因为你上传的是个Object，要通过ResulDTO返回个带有信息的对象。
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModify(System.currentTimeMillis());
        comment.setCommentor("19");
        comment.setLikeCount(0L);
        commentService.insert(comment);


        return ResultDTO.okOf();
    }

}
