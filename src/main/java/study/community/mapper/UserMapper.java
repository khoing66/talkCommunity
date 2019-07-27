package study.community.mapper;

import org.apache.ibatis.annotations.Insert;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import study.community.model.User;


/**
 * Create by Khoing.
 */
@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modify) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModify})")
    void insertUser(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(String token);
}
