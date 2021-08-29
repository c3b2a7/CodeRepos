package me.lolico.learning.springboot.common.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lolico
 */
@Component
public class SysConfig implements InitializingBean {
    private final Map<String, String> map = new HashMap<>();
    private final JdbcTemplate jdbcTemplate;

    public SysConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getValue(String key) {
        return map.get(key);
    }

    /**
     * 填充map
     */
    @Override
    public void afterPropertiesSet() {
        jdbcTemplate.query("select * from sys_config", rs -> {
            while (rs.next()) {
                map.put(rs.getString(1), rs.getString(2));
            }
        });
    }
}
