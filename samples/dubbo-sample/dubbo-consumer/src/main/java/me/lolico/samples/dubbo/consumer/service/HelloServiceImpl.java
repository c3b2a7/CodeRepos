package me.lolico.samples.dubbo.consumer.service;

import me.lolico.samples.dubbo.common.HelloService;

/**
 * @author lolico
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name + "I am impl";
    }

    @Override
    public String say(String msg) {
        return msg;
    }
}
