package me.lolico.learning.designpattern.decorator;

import org.junit.Before;
import org.junit.Test;

public class BeverageTest {

    private Beverage beverage;
    private Beverage beverage1;

    @Before
    public void init() {
        beverage = new Coconut(new Pudding(new ClassicMilkTea()));
        beverage1 = new Pudding(new BitterCoffee());
    }

    @Test
    public void getDescription() {
        System.out.println(beverage.getDescription());
        System.out.println(beverage1.getDescription());
    }

    @Test
    public void cost() {
        System.out.println(beverage.cost());
        System.out.println(beverage1.cost());
    }
}
