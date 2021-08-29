package me.lolico.learning.springboot.common.aspect;

import me.lolico.learning.springboot.common.annotation.CheckParam;
import me.lolico.learning.springboot.common.exception.NullParamException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author lolico
 */
@Component
@Aspect
public class CheckParamAspect {

    @Pointcut(value = "@annotation(checkParam)", argNames = "checkParam")
    public void pointcut(CheckParam checkParam) {
    }

    @Around(value = "pointcut(checkParam)", argNames = "pjp,checkParam")
    public Object checkParam(ProceedingJoinPoint pjp, CheckParam checkParam) throws Throwable {
        int[] index = checkParam.index();
        Object[] args = pjp.getArgs();

        for (int i : index) {
            if (args[i] == null && i <= args.length - 1) {
                String parameterName = ((MethodSignature) pjp.getSignature()).getParameterNames()[i];
                Class<?> parameterType = ((MethodSignature) pjp.getSignature()).getParameterTypes()[i];
                throw new NullParamException(parameterName, parameterType);
            }
        }
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        return pjp.proceed();
    }
}
