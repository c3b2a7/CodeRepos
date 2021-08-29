package me.lolico.learning.designpattern.factory.simple;

import me.lolico.learning.designpattern.factory.simple.food.DicosPizza;
import me.lolico.learning.designpattern.factory.simple.food.KFCPizza;
import me.lolico.learning.designpattern.factory.simple.food.MPizza;
import me.lolico.learning.designpattern.factory.simple.food.Pizza;

/**
 * @author lolico
 */
public class PizzaSimpleFactory {

    public Pizza create(String type) throws Exception {
        Pizza pizza;
        switch (type) {
            case "KFC":
                pizza = new KFCPizza();
                break;
            case "Dicos":
                pizza = new DicosPizza();
                break;
            case "McDonald":
                pizza = new MPizza();
                break;
            default:
                throw new Exception("type error");
        }
        return pizza;
    }
}
