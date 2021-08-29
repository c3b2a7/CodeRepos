package me.lolico.samples.dubbo.common.impl;

import me.lolico.samples.dubbo.common.HelloService;

/**
 * @author lolico
 */
public class DefaultHelloService implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }

    @Override
    public String say(String msg) {
        return msg;
    }
}
