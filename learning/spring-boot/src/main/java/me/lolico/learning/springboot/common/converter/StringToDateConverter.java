package me.lolico.learning.springboot.common.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lolico
 */
@Slf4j
public class StringToDateConverter implements Converter<String, Date> {

    public static Date parse(String source, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(source);
    }

    public static String format(Date source, String pattern) {
        return new SimpleDateFormat(pattern).format(source);
    }

    @Override
    public Date convert(String source) {
        return convert(source, null);
    }

    protected Date convert(String source, String pattern) {
        source = source.trim();
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        if (!StringUtils.isEmpty(pattern)) {
            try {
                return parse(source, pattern);
            } catch (ParseException e) {
                log.warn("Can't using '{}' to convert '{}', will try convert it by default pattern.", pattern, source);
            }
        }
        try {
            if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
                return parse(source, "yyyy-MM-dd");
            } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
                return parse(source, "yyyy-MM-dd HH:mm:ss");
            } else if (source.matches("^\\d{4}/\\d{1,2}/\\d{1,2}$")) {
                return parse(source, "yyyy/MM/dd");
            } else if (source.matches("^\\d{4}/\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                return parse(source, "yyyy/MM/dd HH:mm:ss");
            } else {
                return new Date(Long.parseLong(source));
            }
        } catch (Exception e) {
            log.error("Can not convert '{}'", source);
            throw new RuntimeException(e);
        }
    }
}
