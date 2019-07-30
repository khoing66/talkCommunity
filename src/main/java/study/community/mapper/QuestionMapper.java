package study.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import study.community.model.Question;

import java.util.List;

/**
 * @ClassName QuestionMapper
 * @Description TODO
 * @Author khoing
 * @Date 2019/7/28 13:23
 * @Version 1.0
 **/
@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,tag,GMT_CREATE,GMT_MODIFY,CREATOR_ID,COMMENT_COUNT,LIKE_COUNT,VIEW_COUNT) values(#{title},#{description},#{tag},#{gmtCreate},#{gmtModify},#{creatorId},#{commentCount},#{likeCount},#{viewCount})")
    void create(Question question);

    @Select("select * from question limit #{offSet}, #{size}")
    List<Question> list(@Param("offSet") Integer offSet, @Param("size") Integer size);

    @Select("select * from question where creator_id=#{accountId} limit #{offSet}, #{size}")
    List<Question> listByAccountId(@Param("accountId") String accountId, @Param("offSet") Integer offSet, @Param("size") Integer size);


    @Select("select count(1) from question")
    Integer count();

    @Select("select count(1) from question where creator_id = #{accountId}")
    Integer countByAccountId(@Param("accountId")String accountId);
}
