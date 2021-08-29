package me.lolico.learning.designpattern.factory.abstractive;

import me.lolico.learning.designpattern.factory.abstractive.food.KFCClamPizza;
import me.lolico.learning.designpattern.factory.abstractive.food.Pizza;
import me.lolico.learning.designpattern.factory.abstractive.food.KFCVeggiePizza;

/**
 * 制作KFC的披萨的工厂
 *
 * @author lolico
 */
public class KFCPizzaFactory extends AbstractPizzaFactory {

    @Override
    public Pizza create(String type) {
        Pizza pizza;
        switch (type) {
            case "Clam":
                pizza = new KFCClamPizza();
                break;
            case "Veggie":
                pizza = new KFCVeggiePizza();
                break;
            default:
                pizza = null;
        }
        return pizza;
    }
}
