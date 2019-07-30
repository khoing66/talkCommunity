package study.community.mapper;

import org.apache.ibatis.annotations.*;

import study.community.model.Question;
import study.community.model.User;

import java.util.List;


/**
 * Create by Khoing.
 */
@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modify,avatar_url,bio) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModify},#{avatarUrl},#{bio})")
    void insertUser(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where account_id = #{accountId}")
    User findById(@Param("accountId") String accountId);

    @Update("update user set name=#{name},gmt_modify=#{gmtModify},bio=#{bio},avatar_url=#{avatarUrl},token=#{token} where account_id = #{accountId}")
    void updateUser(User user);
    @Select("select token from user where account_id = #{accountId}")
    String getUserToken(User user);
}
