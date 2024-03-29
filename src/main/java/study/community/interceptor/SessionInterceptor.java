package study.community.interceptor;

import com.sun.xml.internal.ws.runtime.config.TubelineFeatureReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import study.community.mapper.UserMapper;
import study.community.model.User;
import study.community.model.UserExample;
import study.community.service.NotificationService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.handler.Handler;
import java.util.List;

/**
 * @ClassName SessionInterceptor
 * @Description TODO
 * @Author khoing
 * @Date 2019/7/30 12:33
 * @Version 1.0
 **/
@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    UserMapper userMapper;
    @Autowired
    NotificationService notificationService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();

        if (null != cookies && 0 != cookies.length){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria().andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    if (users.size()!=0) {
                        request.getSession().setAttribute("user", users.get(0));
                        long unreadCount = notificationService.unreadCount(users.get(0).getAccountId());
                        request.getSession().setAttribute("unreadCount", unreadCount);
                        break;
                    }
                }
            }
        }

        return true;
    }
}
