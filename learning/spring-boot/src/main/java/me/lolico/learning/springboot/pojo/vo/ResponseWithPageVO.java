package me.lolico.learning.springboot.pojo.vo;

import lombok.Data;

/**
 * @author lolico
 */
@Data
public class ResponseWithPageVO {
    private Integer code;
    private String message;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private Integer numberOfElements;
    private Object content;
}
