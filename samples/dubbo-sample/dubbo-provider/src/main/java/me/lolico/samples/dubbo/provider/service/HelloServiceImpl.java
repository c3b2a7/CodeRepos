package me.lolico.samples.dubbo.provider.service;

import me.lolico.samples.dubbo.common.CalculateService;
import me.lolico.samples.dubbo.common.HelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Random;

/**
 * @author Lolico Li
 */
@DubboService
public class HelloServiceImpl implements HelloService {

    private final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    private final Random costTimeRandom = new Random();

    @Value("${spring.application.name}")
    private String serviceName;

    @DubboReference
    private CalculateService calculateService;

    @Override
    public String sayHello(String name) {
        long start = System.currentTimeMillis();
        await();
        long end = System.currentTimeMillis();
        double multiply = calculateService.multiply(start, end);
        return String.format("[%s] : Hello, %s, await %sms", serviceName, name, multiply);
    }

    @Override
    public String say(String msg) {
        return msg;
    }

    private void await() {
        try {
            long timeInMillisToWait = costTimeRandom.nextInt(500);
            Thread.sleep(timeInMillisToWait);
            logger.info("execution time : " + timeInMillisToWait + " ms.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
