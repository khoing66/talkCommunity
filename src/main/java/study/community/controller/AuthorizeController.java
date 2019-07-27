package study.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import study.community.dto.accessTokenDTO;
import study.community.mapper.UserMapper;
import study.community.model.User;
import study.community.provider.githubProvider;
import study.community.provider.githubUser;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Create by Khoing.
 */
@Controller
public class AuthorizeController {
    @Autowired
    private githubProvider githubPvder;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        accessTokenDTO atdto = new accessTokenDTO();
        atdto.setClient_id(clientId);
        atdto.setClient_secret(clientSecret);
        atdto.setCode(code);
        atdto.setRedirect_uri(redirectUri);
        atdto.setState(state);
        String accessTokenDTO = githubPvder.getAcessTokenDTO(atdto);
        githubUser gitUser = githubPvder.getUser(accessTokenDTO);
        if (null != gitUser) {
            User user = new User();
            user.setName(gitUser.getName());
            user.setAccountId(String.valueOf(gitUser.getId()));
            user.setToken(UUID.randomUUID().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModify(user.getGmtCreate());
            userMapper.insertUser(user);

            String token = user.getToken();
            response.addCookie(new Cookie("token", token));

            return "redirect:/";
        } else {
            return "redirect:/";

        }

       // return "index";
    }


}
