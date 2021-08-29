package me.lolico.learning.designpattern.decorator;

/**
 * @author lolico
 */
public class BitterCoffee extends Beverage {
    public BitterCoffee() {
        description = "苦咖啡";
    }

    @Override
    public double cost() {
        return 36.8;
    }
}
