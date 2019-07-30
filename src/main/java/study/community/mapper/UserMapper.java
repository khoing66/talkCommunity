package study.community.mapper;

import org.apache.ibatis.annotations.Insert;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import study.community.model.Question;
import study.community.model.User;

import java.util.List;


/**
 * Create by Khoing.
 */
@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modify,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModify},#{avatarUrl})")
    void insertUser(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(String token);

    @Select("select * from user where account_id = #{accountId)}")
    User findById(String accountId);

}
