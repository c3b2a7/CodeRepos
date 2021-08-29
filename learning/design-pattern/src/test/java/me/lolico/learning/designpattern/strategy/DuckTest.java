package me.lolico.learning.designpattern.strategy;

import org.junit.Before;
import org.junit.Test;

public class DuckTest {

    Duck toyDuck;
    Duck realDuck;

    @Before
    public void init() {
        toyDuck = new ToyDuck();
        realDuck = new RealDuck();
    }

    @Test
    public void setFlyBehavior() {
        toyDuck.setFlyBehavior(new FlyWithWings()); //让玩具鸭会飞
        toyDuck.fly();
    }

    @Test
    public void setQuackBehavior() {
        toyDuck.setQuackBehavior(new QuackVoice()); //让玩具鸭会嘎嘎叫
        toyDuck.quack();
    }

    @Test
    public void fly() {
        toyDuck.fly();
        realDuck.fly();
    }

    @Test
    public void quack() {
        toyDuck.quack();
        realDuck.quack();
    }

    @Test
    public void display() {
        toyDuck.display();
        realDuck.display();
    }

    @Test
    public void swim() {
        toyDuck.swim();
        realDuck.swim();
    }
}
