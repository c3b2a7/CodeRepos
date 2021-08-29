package me.lolico.learning.designpattern.singleton.doublecheck;

import org.junit.Test;

public class SingletonTest {

    @Test
    public void getInstance() throws InterruptedException {
        new Thread(() -> {
            Singleton singleton;
            for (int i = 0; i < 10; i++) {
                singleton = Singleton.getInstance();
                System.out.println(Thread.currentThread().getName() + ": " + singleton);
            }
        }).start();
        new Thread(() -> {
            Singleton singleton;
            for (int i = 0; i < 10; i++) {
                singleton = Singleton.getInstance();
                System.out.println(Thread.currentThread().getName() + ": " + singleton);
            }
        }).start();
        Thread.sleep(2000); //等待子线程执行完
    }
}
