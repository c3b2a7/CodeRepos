package me.lolico.learning.designpattern.decorator;

/**
 * @author lolico
 */
public abstract class Beverage {
    String description = "unknown beverage";

    public String getDescription() {
        return description;
    }

    public abstract double cost();
}
