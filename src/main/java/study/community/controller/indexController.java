package study.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Create by Khoing.
 */
@Controller
public class indexController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
