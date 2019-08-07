package study.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.community.dto.CommentDTO;
import study.community.enums.CommentTypeEnum;
import study.community.enums.NotificationStatusEnum;
import study.community.enums.NotificationTypeEnum;
import study.community.exception.CustomizeErrorCode;
import study.community.exception.CustomizeException;
import study.community.mapper.*;
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
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment, User commentor) {

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

                dbComment.setCommentCount(1L);
                commentExtMapper.incCommentCount(dbComment);

                Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
                if (question == null) {
                    throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
                }

//                    只有两级评论不用循环
//                while (dbComment != null) {
//                    Long parentId = dbComment.getParentId();
//                    Comment parentComment = commentMapper.selectByPrimaryKey(parentId);
//                    Question question = questionMapper.selectByPrimaryKey(parentId);
//                    if (parentComment == null && question != null) {
//                        //回复评论，也增长回复数，后期完善
////                      questionExtMapper.incComment(question);
//                    }
//                }
                //创建评论下通知
                createNotify(comment, dbComment.getCommentor(), commentor.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT.getType(), question.getId());

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

            //创建问题下的通知
            //这里设置的外部id为问题的id，其实应该是问题的creatorId，后期把accountid转为int，并把外键设置为user的id
            createNotify(comment, question.getCreatorId(), commentor.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT.getType(), question.getId());
        }
    }
    //添加通知
    private void createNotify(Comment comment, String receiver, String notifierName, String outerTitle, int type, Long outerId) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setNotifier(comment.getCommentor());//commentor 为评论人
        notification.setReceiver(receiver);
        notification.setOuterId(outerId);//给每个评论都设置其问题的id
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notification.setType(type);
        notification.setStatus( NotificationStatusEnum.UNREADED.getStatus());
        notificationMapper.insert(notification);
    }


    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {

//        获得taget下的所有评论
        CommentExample example = new CommentExample();
        example.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());
        List<Comment> comments = commentMapper.selectByExample(example);

        if (comments.size() == 0) {
            return new ArrayList<>();
        }

//        获得评论人的accountId
//        通过map用于映射每个评论对应的accountId，存入set集合（去重无序）
        Set<String> commentors = comments.stream().map(comment -> comment.getCommentor()).collect(Collectors.toSet());
        List<String> userAccountIds = new ArrayList<>();
        userAccountIds.addAll(commentors);

//        找到accountId对应的用户，并将accountId作为k，user作为v存入map中
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdIn(userAccountIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<String, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getAccountId(), user -> user));

//        将comment转为commentDTO（带有user）
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentor()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;

    }
}
