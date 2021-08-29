package me.lolico.learning.designpattern.decorator;

/**
 * AbstractBeverageDecorator包含一个Beverage，它将其用作其装饰的对象。
 * AbstractBeverageDecorator类本身简单地覆盖了Beverage的所有方法，
 * 其中的版本将所有请求委托给包含的Beverage对象处理。
 * AbstractBeverageDecorator的子类可以进一步重写其中一些方法进行扩展，还可以提供其他方法和字段。
 *
 * @author lolico
 * @see Beverage
 */
public abstract class AbstractBeverageDecorator extends Beverage {
    protected Beverage beverage;

    public AbstractBeverageDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription();
    }

    @Override
    public double cost() {
        return beverage.cost();
    }
}
