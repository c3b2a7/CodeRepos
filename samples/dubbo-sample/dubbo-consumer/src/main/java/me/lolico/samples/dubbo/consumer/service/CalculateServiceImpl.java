package me.lolico.samples.dubbo.consumer.service;

import me.lolico.samples.dubbo.common.CalculateService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author Lolico Li
 */
@DubboService
public class CalculateServiceImpl<T extends Number> implements CalculateService {

    @Override
    public double add(double l, double r) {
        return l + r;
    }

    @Override
    public double sub(double l, double r) {
        return l - r;
    }

    @Override
    public double multiply(double l, double r) {
        return l * r;
    }

    @Override
    public double divide(double l, double r) {
        if (r == 0) {
            throw new ArithmeticException("/ by zero");
        }
        return l / r;
    }
}
