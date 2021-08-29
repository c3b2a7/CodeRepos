package me.lolico.learning.designpattern.adapter;

/**
 * @author lolico
 */
public class ChickenAdapter implements Duck {

    Chicken chicken;

    public ChickenAdapter(Chicken chicken) {
        this.chicken = chicken;
    }

    @Override
    public void quack() {
        chicken.gobble();
    }

    @Override
    public void fly() {
        chicken.fly();
    }
}
