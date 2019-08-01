package study.community.service;

import ch.qos.logback.core.db.dialect.DBUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.community.dto.CommentDTO;
import study.community.enums.CommentTypeEnum;
import study.community.exception.CustomizeErrorCode;
import study.community.exception.CustomizeException;
import study.community.mapper.CommentMapper;
import study.community.mapper.QuestionExtMapper;
import study.community.mapper.QuestionMapper;
import study.community.mapper.UserMapper;
import study.community.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Autowired
    private UserMapper userMapper;

    @Transactional
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


    public List<CommentDTO> listByQuestionId(Long id,CommentTypeEnum type) {
        CommentExample example = new CommentExample();
        example.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());
        List<Comment> comments = commentMapper.selectByExample(example);

        if (comments.size() == 0) {
            return new ArrayList<>();
        }

        Set<String> commentors  = comments.stream().map(comment -> comment.getCommentor()).collect(Collectors.toSet());//去重的
        List<String> userAccountIds = new ArrayList<>();
        userAccountIds.addAll(commentors);

        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdIn(userAccountIds);
        List<User> users =  userMapper.selectByExample(userExample);
        Map<String, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getAccountId(), user -> user));
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentor()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;

    }
}
