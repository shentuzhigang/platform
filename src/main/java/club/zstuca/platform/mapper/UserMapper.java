package club.zstuca.platform.mapper;

import club.zstuca.platform.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    @Insert("INSERT into user (name,account_id,token,gmt_create,gmt_modified,bio,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl})")
    void insert(User user);
    @Select("SELECT * FROM user WHERE token=#{token}")
    User findByToken(@Param("token") String token);
    @Select("SELECT * FROM user WHERE id=#{id}")
    User findById(@Param("id") Integer creator);
    @Select("SELECT * FROM user WHERE account_id=#{accountId}")
    User findByAccountId(@Param("accountId")String accountId);
    @Update("UPDATE user SET token = #{token},gmt_modified = #{gmtModified} WHERE account_id=#{accountId}")
    void update(User user);
}
