package me.lolico.learning.designpattern.singleton.eager;

/**
 * @author lolico
 */
public class Singleton {

    private static final Singleton instance = new Singleton();

    private Singleton() {
    }

    public static Singleton getInstance() {
        return instance;
    }

    //other methods
}
