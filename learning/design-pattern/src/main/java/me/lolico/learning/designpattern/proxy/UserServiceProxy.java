package me.lolico.learning.designpattern.proxy;

import me.lolico.learning.designpattern.observer.Subject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 用户服务的代理类。
 * 方法：{@link UserServiceProxy#getProxy}用于获得代理对象
 *
 * @author lolico
 */
public class UserServiceProxy {
    private final Object target;
    private final Subject subject;

    /**
     * @param target  需要代理的对象
     * @param subject 主题，用来通知观察者
     */
    public UserServiceProxy(Object target, Subject subject) {
        this.target = target;
        this.subject = subject;
    }

    /**
     * @return 代理对象
     */
    public Object getProxy() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new UserServiceInvocationHandler());
    }

    class UserServiceInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object result = method.invoke(target, args); //调用方法
            if (method.getName().equals("save")) //如果调用的是save方法，则通知观察者
                subject.notifyObserver(target);
            return result;
        }
    }
}
