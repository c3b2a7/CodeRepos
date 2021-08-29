package me.lolico.learning.designpattern.factory.method.store;

import me.lolico.learning.designpattern.factory.method.food.MPizza;
import me.lolico.learning.designpattern.factory.method.food.Pizza;

/**
 * @author lolico
 */
public class McDonaldPizzaStore extends AbstractPizzaStore {
    /**
     * 只有一种经典口味的披萨
     *
     * @param type 披萨的口味
     * @return 披萨
     */
    @Override
    public Pizza create(String type) {
        return new MPizza();
    }
}
