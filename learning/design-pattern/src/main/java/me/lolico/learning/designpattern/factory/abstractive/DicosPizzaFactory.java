package me.lolico.learning.designpattern.factory.abstractive;

import me.lolico.learning.designpattern.factory.abstractive.food.DicosCheesePizza;
import me.lolico.learning.designpattern.factory.abstractive.food.DicosCreamPizza;
import me.lolico.learning.designpattern.factory.abstractive.food.Pizza;

/**
 * 制作德克士的披萨的工厂
 *
 * @author lolico
 */
public class DicosPizzaFactory extends AbstractPizzaFactory {
    @Override
    public Pizza create(String type) {
        Pizza pizza;
        switch (type) {
            case "Cheese":
                pizza = new DicosCheesePizza();
                break;
            case "Cream":
                pizza = new DicosCreamPizza();
                break;
            default:
                pizza = null;
        }
        return pizza;
    }
}
