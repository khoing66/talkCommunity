package study.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName PublishController
 * @Description TODO
 * @Author khoing
 * @Date 2019/7/28 10:03
 * @Version 1.0
 **/
@Controller
public class PublishController {
    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }
}
