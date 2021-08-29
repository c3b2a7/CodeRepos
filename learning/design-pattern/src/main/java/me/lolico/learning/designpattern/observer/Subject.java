package me.lolico.learning.designpattern.observer;

/**
 * 主题接口（可观察者接口）。
 * 实现这个接口的类是一个主题，可用于注册、删除、通知观察者。
 *
 * @author lolico
 */
public interface Subject {
    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObserver();

    void notifyObserver(Object args);
}
