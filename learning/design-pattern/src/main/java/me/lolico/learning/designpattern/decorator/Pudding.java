package me.lolico.learning.designpattern.decorator;

/**
 * @author lolico
 */
public class Pudding extends AbstractBeverageDecorator {

    public Pudding(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + "+布丁";
    }

    @Override
    public double cost() {
        return 1.2 + beverage.cost();
    }
}
