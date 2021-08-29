package me.lolico.learning.designpattern.adapter;

/**
 * @author lolico
 */
public class MallardDuck implements Duck {
    @Override
    public void quack() {
        System.out.println("Quack.");
    }

    @Override
    public void fly() {
        System.out.println("I'm flying.");
    }
}
