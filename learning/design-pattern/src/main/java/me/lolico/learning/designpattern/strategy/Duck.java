package me.lolico.learning.designpattern.strategy;

/**
 * @author lolico
 */
public abstract class Duck {

    private FlyBehavior flybehavior; //飞行行为
    private QuackBehavior quackBehavior; //叫的行为

    public Duck(FlyBehavior flybehavior, QuackBehavior quackBehavior) {
        this.flybehavior = flybehavior;
        this.quackBehavior = quackBehavior;
    }

    public Duck() {
    }

    public void setFlyBehavior(FlyBehavior flybehavior) {
        this.flybehavior = flybehavior;
    }

    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }

    public void fly() {
        flybehavior.fly();
    }

    public void quack() {
        quackBehavior.quack();
    }

    public void display() {
        System.out.println("i'm a duck!");
    }

    public void swim() {
        System.out.println("duck swimming");
    }
}
