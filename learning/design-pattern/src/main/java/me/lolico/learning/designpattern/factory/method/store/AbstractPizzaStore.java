package me.lolico.learning.designpattern.factory.method.store;


import me.lolico.learning.designpattern.factory.method.food.*;

/**
 * 实现这个类以定义不同披萨店
 *
 * @author lolico
 */
public abstract class AbstractPizzaStore {

    /**
     * 提供披萨,披萨具体怎么得到,由子类实现的工厂方法决定(披萨的实例化延迟到子类）
     * 可以带参数也可以不带参数,从而可以提供不同口味的披萨
     *
     * @param type 披萨的口味
     * @return 披萨
     */
    public Pizza orderPizza(String type) throws Exception {
        return create(type);
    }

    /**
     * 工厂方法：
     * 子类实现这个方法以提供的披萨，
     * 可以根据type来制造不同口味的披萨
     * 这里偷个懒,只给{@link DicosPizza}
     * 写了子类（不同口味），其他店的披萨只有一种经典口味。
     *
     * @param type 披萨的口味
     * @return 披萨
     * @see MPizza
     * @see KFCPizza
     * @see DicosPizza
     * @see DicosCreamPizza
     * @see DicosCheesePizza
     */
    public abstract Pizza create(String type) throws Exception;
}
