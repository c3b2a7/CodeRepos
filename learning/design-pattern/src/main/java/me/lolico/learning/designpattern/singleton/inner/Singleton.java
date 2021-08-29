package me.lolico.learning.designpattern.singleton.inner;

/**
 * @author lolico
 */
public class Singleton {

    private Singleton() {
    }

    public static Singleton getInstance() {
        return Inner.instance;
    }

    static class Inner {
        static Singleton instance = new Singleton();
    }

    //other methods
}
