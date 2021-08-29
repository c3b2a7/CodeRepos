package me.lolico.learning.springboot.common.exception;

/**
 * @author lolico
 */
public class EntityCastException extends RuntimeException {
    public EntityCastException() {
        super();
    }

    public EntityCastException(String message) {
        super(message);
    }
}
