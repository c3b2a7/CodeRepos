package me.lolico.learning.springboot.common.aspect;

import me.lolico.learning.springboot.common.DynamicDataSourceContextHolder;
import me.lolico.learning.springboot.common.annotation.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author lolico
 */
@Slf4j
@Component
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class DataSourceAspect implements BeanFactoryAware {

    private final SpelExpressionParser parser = new SpelExpressionParser();
    private BeanFactory beanFactory;

    @Pointcut(value = "@annotation(dataSource)", argNames = "dataSource")
    public void pointcut(DataSource dataSource) {
    }

    @Around(value = "pointcut(dataSource)", argNames = "pjp,dataSource")
    public Object around(ProceedingJoinPoint pjp, DataSource dataSource) throws Throwable {
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        prepareVariable(evaluationContext, pjp);
        String value = dataSource.value();
        Object parseValue = parser.parseExpression(value).getValue(evaluationContext);
        DynamicDataSourceContextHolder.setKey(value);
        log.debug("使用数据库{}", value);
        Object proceed = pjp.proceed();
        DynamicDataSourceContextHolder.remove();
        return proceed;
    }

    private void prepareVariable(StandardEvaluationContext evaluationContext, ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Object[] args = pjp.getArgs();
        String[] paraNames = methodSignature.getParameterNames();
        int argCount = args.length;
        int paramCount = paraNames != null ? paraNames.length : methodSignature.getParameterTypes().length;
        for (int i = 0; i < paramCount; i++) {
            Object value = null;
            if (argCount > paramCount && i == paramCount - 1) {
                value = Arrays.copyOfRange(args, i, argCount);
            } else if (argCount > i) {
                value = args[i];
            }
            evaluationContext.setVariable("p" + i, value);
            evaluationContext.setVariable("?" + i, value);
            if (paraNames != null) {
                evaluationContext.setVariable(paraNames[i], value);
            }
        }
        evaluationContext.setBeanResolver(new BeanFactoryResolver(beanFactory));
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
