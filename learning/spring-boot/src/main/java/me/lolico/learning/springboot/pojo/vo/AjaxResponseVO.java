package me.lolico.learning.springboot.pojo.vo;

import lombok.Data;

/**
 * @author lolico
 */
@Data
public class AjaxResponseVO {
    private String msg;
    private Boolean success;

    private AjaxResponseVO(String msg, Boolean success) {
        this.msg = msg;
        this.success = success;
    }

    public static AjaxResponseVO fail(String msg) {
        return new AjaxResponseVO(msg, false);
    }

    public static AjaxResponseVO fail(String format,Object... args) {
        return new AjaxResponseVO(String.format(format, args), false);
    }

    public static AjaxResponseVO success(String msg) {
        return new AjaxResponseVO(msg, true);
    }

    public static AjaxResponseVO success(String format, Object... args) {
        return new AjaxResponseVO(String.format(format, args), true);
    }
}
