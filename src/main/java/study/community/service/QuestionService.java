package study.community.service;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import study.community.dto.PaginationDTO;
import study.community.dto.QuestionDto;
import study.community.exception.CustomizeErrorCode;
import study.community.exception.CustomizeException;
import study.community.mapper.QuestionExtMapper;
import study.community.mapper.QuestionMapper;
import study.community.mapper.UserMapper;
import study.community.model.Question;
import study.community.model.QuestionExample;
import study.community.model.User;
import study.community.model.UserExample;

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
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;


    public  PaginationDTO list( Integer page, Integer size) {
        Integer totalPage;
        PaginationDTO paginationDTO = new PaginationDTO();
        List<QuestionDto> questionDtos = new ArrayList<>();

        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());

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
        QuestionExample example = new QuestionExample();
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offSet, size));


        for (Question question : questions) {
            String creatorId = question.getCreatorId();
            UserExample userExample = new UserExample();
            userExample.createCriteria().andAccountIdEqualTo(question.getCreatorId());
            List<User> users = userMapper.selectByExample(userExample);
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(users.get(0));
            questionDtos.add(questionDto);

        }
        paginationDTO.setQuestions(questionDtos);

        return paginationDTO;
    }


    public PaginationDTO list(String accountId, Integer page, Integer size) {
        Integer totalPage;
        PaginationDTO paginationDTO = new PaginationDTO();
        List<QuestionDto> questionDtos = new ArrayList<>();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorIdEqualTo(accountId);
        Integer UsertotalCount = (int)questionMapper.countByExample(questionExample);

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
        QuestionExample example1 = new QuestionExample();
        example1.createCriteria().andCreatorIdEqualTo(accountId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example1, new RowBounds(offSet, size));


        for (Question question : questions) {
            UserExample example = new UserExample();
            example.createCriteria().andAccountIdEqualTo(question.getCreatorId());
            List<User> users = userMapper.selectByExample(example);
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(users.get(0));
            questionDtos.add(questionDto);

        }
        paginationDTO.setQuestions(questionDtos);

        return paginationDTO;
    }

    public QuestionDto getById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (null == question) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question, questionDto);
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(question.getCreatorId());
        List<User> users = userMapper.selectByExample(example);
        questionDto.setUser(users.get(0));
        return questionDto;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModify(question.getGmtCreate());
            question.setCommentCount(0);
            question.setViewCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        } else {
            question.setGmtModify(System.currentTimeMillis());
            Question modifyQuestion = new Question();
            modifyQuestion.setGmtModify(System.currentTimeMillis());
            modifyQuestion.setTitle(question.getTitle());
            modifyQuestion.setDescription(question.getDescription());
            modifyQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            int update = questionMapper.updateByExampleSelective(modifyQuestion, example);
            if (update != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }


        }
    }

    public void incView(Long id) {
//        Question question =questionMapper.selectByPrimaryKey(id);
//        Integer viewCount = question.getViewCount();
//        Question modifyQuestion= new Question();
//        modifyQuestion.setViewCount(viewCount + 1);
//        //updaeByExampleSelective,,,,其他为空的不更新，只更新有值的，
//
//        QuestionExample questionExample = new QuestionExample();
//        questionExample.createCriteria().andIdEqualTo(id);
//        questionMapper.updateByExampleSelective(modifyQuestion, questionExample);
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);

    }


}
