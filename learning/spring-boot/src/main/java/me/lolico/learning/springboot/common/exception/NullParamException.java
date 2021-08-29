package me.lolico.learning.springboot.common.exception;

/**
 * @author lolico
 */
public class NullParamException extends RuntimeException {
    private final String parameterName;
    private final Class<?> parameterType;

    public NullParamException(String parameterName, Class<?> parameterType) {
        this.parameterName = parameterName;
        this.parameterType = parameterType;
    }

    @Override
    public String getMessage() {
        return "Required " + this.parameterType + " parameter '" + this.parameterName + "' must be not null !";
    }

    public final String getParameterName() {
        return this.parameterName;
    }

    public final Class<?> getParameterType() {
        return this.parameterType;
    }
}
