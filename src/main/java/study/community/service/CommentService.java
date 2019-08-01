package study.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.community.enums.CommentTypeEnum;
import study.community.exception.CustomizeErrorCode;
import study.community.exception.CustomizeException;
import study.community.mapper.CommentMapper;
import study.community.mapper.QuestionExtMapper;
import study.community.mapper.QuestionMapper;
import study.community.model.Comment;
import study.community.model.Question;

/**
 * @ClassName CommentService
 * @Description TODO
 * @Author khoing
 * @Date 2019/7/31 15:00
 * @Version 1.0
 **/
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;


    public void insert(Comment comment) {

        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
            //统一由CustomizeExceptionHandler处理，
            //异常也需要给页面传个JSON的对象，而不是像IDEA中返回错误信息。而不是没有信息，直接跳转，对用户不太友好，而是直接在页面返回个对象，然后用户直接用过按钮点，跳转页面
        }

        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if (comment.getType().equals(CommentTypeEnum.COMMENT.getType())) {
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            } else {
                commentMapper.insert(comment);
                while (dbComment != null) {
                    Long parentId = dbComment.getParentId();
                    dbComment = commentMapper.selectByPrimaryKey(parentId);
                    Question question = questionMapper.selectByPrimaryKey(parentId);
                    if (dbComment == null && question != null) {
                        questionExtMapper.incComment(question);
                    }
                }
            }
        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);

            question.setCommentCount(1);
            questionExtMapper.incComment(question);
        }
    }
}
