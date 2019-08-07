package study.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import study.community.dto.CommentCreateDTO;
import study.community.dto.CommentDTO;
import study.community.dto.ResultDTO;
import study.community.enums.CommentTypeEnum;
import study.community.exception.CustomizeErrorCode;
import study.community.model.Comment;
import study.community.model.User;
import study.community.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
//            return new CustomizeException(CustomizeErrorCode.NO_LOGIN); 是不是因为你上传的是个Object，要通过ResulDTO返回个带有信息的对象。
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModify(System.currentTimeMillis());
        comment.setCommentor(user.getAccountId());
        comment.setCommentCount(0L);
        comment.setLikeCount(0L);
        commentService.insert(comment,user);


        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Long id) {
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }



}
