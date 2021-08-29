package me.lolico.learning.springboot.web;

import me.lolico.learning.springboot.common.exception.NullParamException;
import me.lolico.learning.springboot.pojo.vo.AjaxResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author lolico
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NullParamException.class)
    public AjaxResponseVO handel(NullParamException ex) {
        return AjaxResponseVO.fail("[%s]参数不能为空", ex.getParameterName());
    }

    @ExceptionHandler(AuthenticationException.class)
    public AjaxResponseVO handel(AuthenticationException ex) {
        if (ex instanceof IncorrectCredentialsException) {
            return AjaxResponseVO.fail("密码错误");
        } else if (ex instanceof UnknownAccountException) {
            return AjaxResponseVO.fail("账号不存在");
        } else if (ex instanceof ExpiredCredentialsException) {
            return AjaxResponseVO.fail("令牌过期");
        } else if (ex instanceof ExcessiveAttemptsException) {
            return AjaxResponseVO.fail("操作频繁");
        } else if (ex instanceof LockedAccountException) {
            return AjaxResponseVO.fail("账户被锁定");
        } else {
            return AjaxResponseVO.fail("未知的身份认证错误：%s", ex.getMessage());
        }
    }

}
