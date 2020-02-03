package club.zstuca.platform.service;

import club.zstuca.platform.dto.PaginationDTO;
import club.zstuca.platform.dto.QuestionDTO;
import club.zstuca.platform.mapper.QuestionMapper;
import club.zstuca.platform.mapper.UserMapper;
import club.zstuca.platform.model.Question;
import club.zstuca.platform.model.User;
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
       return findByQuestionCreator(questionMapper.list());
    }

    /**
     *
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO listAndLimit(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);
        page = paginationDTO.getNowPage();
        Integer offset = size * (page - 1);
        paginationDTO.setQuestionDTOList(findByQuestionCreator(questionMapper.listAndLimit(offset,size)));
        return paginationDTO;
    }

    public PaginationDTO listAndLimitByCreator(Integer creatorId,Integer page, Integer size){
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByCreator(creatorId);
        paginationDTO.setPagination(totalCount,page,size);
        page = paginationDTO.getNowPage();
        Integer offset = size * (page - 1);
        paginationDTO.setQuestionDTOList(findByQuestionCreator(questionMapper.listAndLimitByCreator(creatorId,offset,size)));
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
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOs.add(questionDTO);
        }
        return questionDTOs;
    }


}
