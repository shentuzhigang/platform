package club.zstuca.platform.service;

import club.zstuca.platform.dto.PaginationDTO;
import club.zstuca.platform.dto.QuestionDTO;
import club.zstuca.platform.exception.CustomizeException;
import club.zstuca.platform.mapper.QuestionMapper;
import club.zstuca.platform.mapper.UserMapper;
import club.zstuca.platform.model.Question;
import club.zstuca.platform.model.QuestionExample;
import club.zstuca.platform.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    /**
     *
     * @return
     */
    public List<QuestionDTO> list() {
       return findByQuestionCreator(questionMapper.selectByExample(new QuestionExample()));
    }

    /**
     *
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO listAndLimit(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
        paginationDTO.setPagination(totalCount,page,size);
        page = paginationDTO.getNowPage();
        Integer offset = size * (page - 1);
        paginationDTO.setQuestionDTOList(findByQuestionCreator(questionMapper
                .selectByExampleWithRowbounds(new QuestionExample(),
                new RowBounds(offset,size))));
        return paginationDTO;
    }

    public PaginationDTO listAndLimitByCreator(Integer creatorId,Integer page, Integer size){
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
        paginationDTO.setPagination(totalCount,page,size);
        page = paginationDTO.getNowPage();
        Integer offset = size * (page - 1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(creatorId);
        paginationDTO.setQuestionDTOList(findByQuestionCreator(questionMapper
                .selectByExampleWithRowbounds(questionExample,
                new RowBounds(offset,size))));
        return paginationDTO;
    }

    /**
     *
     * @param questions
     * @return
     */

    public List<QuestionDTO> findByQuestionCreator(List<Question> questions){
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for (Question question : questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOs.add(questionDTO);
        }
        return questionDTOs;
    }

    /**
     *
     * @param id
     * @return
     */
    public QuestionDTO findById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException("查不到问题");
        }
        QuestionDTO questionDTO = new QuestionDTO();
        if(question!=null){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
        }
        return questionDTO;
    }

    public void incView(Integer id) {
    }

    public void createOrUpdate(Question question){
        if(question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else{
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria()
                    .andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(question,questionExample);
        }
    }
}
