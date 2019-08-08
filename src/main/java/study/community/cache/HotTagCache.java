package study.community.cache;

import lombok.Data;
import org.apache.tomcat.jni.Poll;
import org.springframework.stereotype.Component;
import study.community.dto.HotTagDTO;

import java.util.*;

/**
 * @ClassName HotTagCache
 * @Description TODO
 * @Author khoing
 * @Date 2019/8/8 11:55
 * @Version 1.0
 **/
@Component
@Data
public class HotTagCache {
    private List<String> hots = new ArrayList<>();

    public void updateTags(Map<String, Integer> tags) {
        int max = 10;
        PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>(max);
//        遍历map
        tags.forEach((name, priority) ->{
            HotTagDTO hotTagDTO = new HotTagDTO();
            hotTagDTO.setName(name);
            hotTagDTO.setPriority(priority);
            if (priorityQueue.size() < max) {
                priorityQueue.add(hotTagDTO);
            } else {
//                小顶堆中最小的，与下个比较，若比最小的还小，就不要了，就把堆中最小的拿出来，这个替换进去。
                HotTagDTO minSpot = priorityQueue.peek();
                if ((hotTagDTO.compareTo(minSpot) > 0)) {
                    priorityQueue.poll();
                    priorityQueue.add(hotTagDTO);
                }
            }
        });

        List<String> sortTags = new ArrayList<>();
        HotTagDTO poll = priorityQueue.poll();
        while (null != poll) {
            sortTags.add(0, poll.getName());
            poll = priorityQueue.poll();
        }

        hots = sortTags;
    }

}
