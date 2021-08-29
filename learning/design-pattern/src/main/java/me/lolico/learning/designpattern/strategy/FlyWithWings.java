package me.lolico.learning.designpattern.strategy;

/**
 * @author lolico
 */
public class FlyWithWings implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("flying with wings!");

    }
}
