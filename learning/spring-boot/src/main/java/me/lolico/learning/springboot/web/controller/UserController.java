package me.lolico.learning.springboot.web.controller;

import me.lolico.learning.springboot.common.Constants;
import me.lolico.learning.springboot.pojo.entity.User;
import me.lolico.learning.springboot.pojo.vo.AjaxResponseVO;
import me.lolico.learning.springboot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author lolico
 */
@RestController
public class UserController {
    @Autowired
    UserService service;

    @PostMapping("/register")
    public AjaxResponseVO register(User user, HttpSession session) {
        User user1 = service.addUser(user);
        session.setAttribute(Constants.Web.USER_SESSION, user1);
        return AjaxResponseVO.success("注册成功");
    }

    @PostMapping("/login")
    public AjaxResponseVO login(User user, HttpSession session) throws Exception {
        // User userForLogin = service.findUserForLogin(user.getUsername(), user.getPassword());
        // if (userForLogin != null) {
        //     session.setAttribute(Constants.Web.USER_SESSION, userForLogin);
        //     return AjaxResponseVO.success("登录成功");
        // }
        // return AjaxResponseVO.fail("用户名或密码错误");

        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        SecurityUtils.getSubject().login(token);
        return AjaxResponseVO.success("登录成功");
    }

    @GetMapping("/logout")
    public AjaxResponseVO logout() {
        SecurityUtils.getSubject().logout();
        return AjaxResponseVO.success("登出成功");
    }
}
