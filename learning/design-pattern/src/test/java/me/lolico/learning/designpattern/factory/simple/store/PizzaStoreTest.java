package me.lolico.learning.designpattern.factory.simple.store;

import org.junit.Test;

public class PizzaStoreTest {

    @Test
    public void orderPizza() throws Exception {
        PizzaStore store = new PizzaStore();
        System.out.println(store.orderPizza("KFC"));
        System.out.println(store.orderPizza("Dicos"));
        System.out.println(store.orderPizza("McDonald"));
    }
}
