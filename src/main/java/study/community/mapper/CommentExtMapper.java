package study.community.mapper;

import study.community.model.Comment;

/**
 * @ClassName QuestionExtMapper
 * @Description TODO
 * @Author khoing
 * @Date 2019/7/31 22:26
 * @Version 1.0
 **/
public interface CommentExtMapper {
    long incCommentCount(Comment comment);

}
