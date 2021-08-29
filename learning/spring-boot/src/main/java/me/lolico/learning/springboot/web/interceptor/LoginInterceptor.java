package me.lolico.learning.springboot.web.interceptor;

import me.lolico.learning.springboot.common.Constants;
import me.lolico.learning.springboot.pojo.entity.User;
import me.lolico.learning.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * @author lolico
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    final UserService service;

    public LoginInterceptor(UserService service) {
        this.service = service;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.Web.USER_SESSION);
        if (user == null || service.findUserForLogin(user.getUsername(), user.getPassword()) == null) {
            response.sendRedirect("/index");
            log.info("拦截请求路径：" + request.getServletPath() + '?' + Optional.ofNullable(request.getQueryString()).orElse(""));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
