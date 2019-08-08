package study.community.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.community.dto.accessTokenDTO;
import study.community.mapper.UserMapper;
import study.community.model.User;
import study.community.model.UserExample;
import study.community.provider.githubProvider;
import study.community.dto.githubUser;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * Create by Khoing.
 * @author khoing
 */
@Controller
@Slf4j
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
            user.setAccountId(gitUser.getId());
            user.setBio(gitUser.getBio());
            user.setAvatarUrl(gitUser.getAvatarUrl());
            user.setToken(UUID.randomUUID().toString());
            UserExample userExample = new UserExample();
            userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
            List<User> dbUsers = userMapper.selectByExample(userExample);

            if ( dbUsers.size()==0) {
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModify(user.getGmtCreate());
                userMapper.insert(user);

            } else {
                User modifyUser = new User();
                modifyUser.setGmtModify(System.currentTimeMillis());
                modifyUser.setToken(user.getToken());
                modifyUser.setAvatarUrl(user.getAvatarUrl());
                modifyUser.setBio(user.getBio());
                modifyUser.setName(user.getName());
                user.setGmtModify(System.currentTimeMillis());
                UserExample example = new UserExample();
                example.createCriteria().andAccountIdEqualTo(user.getAccountId());
                userMapper.updateByExampleSelective(modifyUser, example);


            }
            String token = user.getToken();
            response.addCookie(new Cookie("token", token));

            return "redirect:/";
        } else {
            log.error("callback get github error,{}", gitUser);
            return "redirect:/";

        }

       // return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";

    }


}
