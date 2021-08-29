package me.lolico.learning.designpattern.strategy;

/**
 * @author lolico
 */
public class RealDuck extends Duck {
    public RealDuck(FlyBehavior flybehavior, QuackBehavior quackBehavior) {
        super(flybehavior, quackBehavior);
    }

    /**
     * 默认的会飞也会叫
     */
    public RealDuck() {
        setFlyBehavior(new FlyWithWings());
        setQuackBehavior(new QuackVoice());
    }

    @Override
    public void display() {
        System.out.println("i'm a real duck!");
    }
}
