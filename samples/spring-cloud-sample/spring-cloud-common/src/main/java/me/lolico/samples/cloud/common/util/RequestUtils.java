package me.lolico.samples.cloud.common.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lolico
 */
public class RequestUtils {
    private static final String[] headers = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};

    public static String getIp(HttpServletRequest request) {
        String ip;
        for (String header : headers) {
            ip = request.getHeader(header);
            if (!isUnknown(ip)) {
                if (ip.indexOf(',') > 0) {
                    for (String sub : ip.trim().split(",")) {
                        if (!isUnknown(sub)) {
                            ip = sub;
                            break;
                        }
                    }
                }
                return ip;
            }
        }
        ip = request.getRemoteAddr();
        return ip;
    }

    public static boolean isUnknown(String ip) {
        return !StringUtils.hasText(ip) || "unknown".equalsIgnoreCase(ip);
    }

}
