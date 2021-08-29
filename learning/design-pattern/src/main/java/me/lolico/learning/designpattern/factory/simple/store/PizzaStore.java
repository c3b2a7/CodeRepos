package me.lolico.learning.designpattern.factory.simple.store;

import me.lolico.learning.designpattern.factory.simple.PizzaSimpleFactory;
import me.lolico.learning.designpattern.factory.simple.food.Pizza;

/**
 * @author lolico
 */
public class PizzaStore {
    public Pizza orderPizza(String type) throws Exception {
        return new PizzaSimpleFactory().create(type);
    }
}
