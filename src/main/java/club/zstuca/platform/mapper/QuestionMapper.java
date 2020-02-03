package club.zstuca.platform.mapper;

import club.zstuca.platform.dto.QuestionDTO;
import club.zstuca.platform.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface QuestionMapper {
    @Insert("INSERT INTO question (title,description,gmt_create,gmt_modified,creator,tag) VALUES (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);
    @Select("SELECT * FROM question")
    List<Question> list();
    @Select("SELECT * FROM question LIMIT #{offset}, #{size}")
    List<Question> listAndLimit(@Param("offset") Integer offset,@Param("size") Integer size);
    @Select("SELECT count(1) FROM question")
    Integer count();
    @Select("SELECT count(1) FROM question WHERE creator=#{creator}")
    Integer countByCreator(@Param("creator")Integer creatorId);
    @Select("SELECT * FROM question WHERE creator=#{creator} LIMIT #{offset}, #{size}")
    List<Question> listAndLimitByCreator(@Param("creator")Integer creatorId,@Param("offset") Integer offset,@Param("size") Integer size);
    @Select("SELECT * FROM question WHERE id=#{id}")
    Question findById(@Param("id")Integer id);
}
