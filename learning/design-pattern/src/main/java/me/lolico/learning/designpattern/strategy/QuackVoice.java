package me.lolico.learning.designpattern.strategy;

/**
 * @author lolico
 */
public class QuackVoice implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("quack quack");
    }
}
