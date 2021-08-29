package me.lolico.learning.designpattern.decorator;

/**
 * @author lolico
 */
public class Coconut extends AbstractBeverageDecorator {

    public Coconut(Beverage beverage) {
        super(beverage);
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + "+椰粒";
    }

    @Override
    public double cost() {
        return 0.8 + beverage.cost();
    }
}
