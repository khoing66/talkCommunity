package study.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import study.community.model.Question;

/**
 * @ClassName QuestionMapper
 * @Description TODO
 * @Author khoing
 * @Date 2019/7/28 13:23
 * @Version 1.0
 **/
@Mapper
public interface QuestionMapper {
    @Insert("insert into question (gmt_Create,gmt_Modify,creator_Id,comment_Count,like_Count,view_Count)" +
            "values(#{gmtCreate},#{gmtModify},#{creatorId},#{commentCount},#{likeCount},#{viewCount})")
    void create(Question question);
}
