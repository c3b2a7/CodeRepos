package me.lolico.learning.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据表主题，用于通知所有注册的观察者。
 *
 * @author lolico
 */
public class DatatableSubject implements Subject {

    private final List<Observer> observers = new ArrayList<>(); //观察者列表

    /**
     * @param observer 注册的观察者
     */
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * @param observer 删除的观察者
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * 通知观察者
     */
    @Override
    public void notifyObserver() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    /**
     * 通知观察者
     *
     * @param args 附加参数
     */
    @Override
    public void notifyObserver(Object args) {
        for (Observer observer : observers) {
            observer.update(this, args);
        }
    }
}
