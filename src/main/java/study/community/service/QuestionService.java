package study.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import study.community.dto.PaginationDTO;
import study.community.dto.QuestionDto;
import study.community.mapper.QuestionMapper;
import study.community.mapper.UserMapper;
import study.community.model.Question;
import study.community.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName QuestionService
 * @Description TODO
 * @Author khoing
 * @Date 2019/7/28 23:31
 * @Version 1.0
 **/
@Service
public class QuestionService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;


    public  PaginationDTO list( Integer page, Integer size) {
        Integer totalPage;
        PaginationDTO paginationDTO = new PaginationDTO();
        List<QuestionDto> questionDtos = new ArrayList<>();

        Integer totalCount = questionMapper.count();

        if (totalCount % size == 0 || totalCount == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if(page <1){
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPaginnation(page, totalPage);

        Integer offSet = size * (page - 1);
        List<Question> questions = questionMapper.list(offSet,size);


        for (Question question : questions) {
            String creatorId = question.getCreatorId();
            User user = userMapper.findById(question.getCreatorId ());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtos.add(questionDto);

        }
        paginationDTO.setQuestions(questionDtos);

        return paginationDTO;
    }


    public PaginationDTO list(String accountId, Integer page, Integer size) {
        Integer totalPage;
        PaginationDTO paginationDTO = new PaginationDTO();
        List<QuestionDto> questionDtos = new ArrayList<>();

        Integer UsertotalCount = questionMapper.countByAccountId(accountId);

        if (UsertotalCount % size == 0 || UsertotalCount == 0) {
            totalPage = UsertotalCount / size;
        } else {
            totalPage = UsertotalCount / size + 1;
        }

        if(page <1){
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPaginnation(page, totalPage);

        Integer offSet = size * (page - 1);
        List<Question> questions = questionMapper.listByAccountId(accountId,offSet,size);


        for (Question question : questions) {
            User user = userMapper.findById(question.getCreatorId  ());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtos.add(questionDto);

        }
        paginationDTO.setQuestions(questionDtos);

        return paginationDTO;
    }

    public QuestionDto getById(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question, questionDto);
        User user = userMapper.findById(question.getCreatorId());
        questionDto.setUser(user);
        return questionDto;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModify(question.getGmtCreate());
            questionMapper.create(question);
        } else {
            question.setGmtModify(System.currentTimeMillis());
            questionMapper.update(question);

        }
    }
}
