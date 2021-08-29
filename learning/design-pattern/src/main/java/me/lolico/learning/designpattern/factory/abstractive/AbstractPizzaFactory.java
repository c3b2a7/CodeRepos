package me.lolico.learning.designpattern.factory.abstractive;

import me.lolico.learning.designpattern.factory.abstractive.food.Pizza;

/**
 * @author lolico
 */
public abstract class AbstractPizzaFactory {
    public abstract Pizza create(String type);
}
