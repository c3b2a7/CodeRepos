package me.lolico.learning.designpattern.decorator;

/**
 * @author lolico
 */
public class ClassicMilkTea extends Beverage {

    public ClassicMilkTea() {
        description = "经典奶茶";
    }

    @Override
    public double cost() {
        return 26.5;
    }
}
