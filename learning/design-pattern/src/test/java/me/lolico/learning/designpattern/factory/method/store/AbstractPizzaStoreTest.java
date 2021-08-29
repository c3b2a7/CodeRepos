package me.lolico.learning.designpattern.factory.method.store;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AbstractPizzaStoreTest {

    AbstractPizzaStore pizzaStore;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void orderPizza() throws Exception {
        pizzaStore = new KFCPizzaStore();
        System.out.println(pizzaStore.orderPizza(""));

        pizzaStore = new McDonaldPizzaStore();
        System.out.println(pizzaStore.orderPizza(""));

        pizzaStore = new DicosPizzaStore();
        System.out.println(pizzaStore.orderPizza("Cheese"));
        System.out.println(pizzaStore.orderPizza("Cream"));

        //test exception
        thrown.expect(Exception.class);
        thrown.expectMessage(CoreMatchers.sameInstance("type error"));
        System.out.println(pizzaStore.orderPizza("Clam"));
    }
}
