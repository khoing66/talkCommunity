package study.community.mapper;

import study.community.model.Question;

/**
 * @ClassName QuestionExtMapper
 * @Description TODO
 * @Author khoing
 * @Date 2019/7/31 22:26
 * @Version 1.0
 **/
public interface QuestionExtMapper {
    int incView(Question question);

    int incComment(Question question);
}
