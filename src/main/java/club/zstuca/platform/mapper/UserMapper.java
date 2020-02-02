package club.zstuca.platform.mapper;

import club.zstuca.platform.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
}