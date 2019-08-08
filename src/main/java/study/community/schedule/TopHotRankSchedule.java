package study.community.schedule;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import study.community.cache.HotTagCache;
import study.community.mapper.QuestionMapper;
import study.community.model.Question;
import study.community.model.QuestionExample;

import java.util.*;

/**
 * @ClassName TopHotRankSchedule
 * @Description TODO
 * @Author khoing
 * @Date 2019/8/8 11:15
 * @Version 1.0
 **/
@Component
@Slf4j
public class TopHotRankSchedule {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private HotTagCache hotTagCache;

    @Scheduled(fixedRate = 10000)
    public void hotTagSchedule() {
        List<Question> questionList = new ArrayList<>();
        int offset = 0;
        int limit = 5;
//        当是第一页或者恒有下一页时
        log.info("hotTagSchedule start {}", new Date());
            Map<String, Integer> priorities = new HashMap<>();
        while (offset == 0 || questionList.size() == limit) {
            questionList = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
            for (Question question : questionList) {
                String[] tags = StringUtils.split(question.getTag(), "，");
                for (String tag : tags) {
                    Integer priority = priorities.get(tag);
                    if (priority != null ) {
                        priority = priority+ 5 + question.getCommentCount();
                    } else {
                        priority = 5 + question.getCommentCount();
                    }
                    priorities.put(tag, priority);
                }

            }
            offset += limit;
        }
//        遍历map打印
//        priorities.forEach(
//                (k,v)->{
//                    Syste.out.println(k);
//                    System.out.println(":");
//                    System.out.println(v);
//                }
//        );
        hotTagCache.updateTags(priorities);
        log.info("hotTagSchedule end {}", new Date());


    }

}
