package me.lolico.learning.springboot.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lolico
 */
@Data
public class TestVO {
    private String msg;

    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    // @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    // @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime localDateTime;

}
