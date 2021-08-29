package me.lolico.learning.designpattern.factory.abstractive;

import org.junit.Test;

import static org.junit.Assert.assertNull;

public class AbstractPizzaFactoryTest {

    AbstractPizzaFactory factory;

    @Test
    public void create() {
        factory = new DicosPizzaFactory();
        System.out.println(factory.create("Cream"));
        System.out.println(factory.create("Cheese"));
        assertNull(factory.create("Clam"));

        factory = new KFCPizzaFactory();
        System.out.println(factory.create("Clam"));
        System.out.println(factory.create("Veggie"));
        assertNull(factory.create("Cream"));
    }
}
