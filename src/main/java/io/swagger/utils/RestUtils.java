package io.swagger.utils;

import lombok.experimental.UtilityClass;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@UtilityClass
public class RestUtils {

    public static LocalDateTime convertToLocalDateTime(ZonedDateTime zonedDateTime) {
        return zonedDateTime == null ? null : zonedDateTime.toLocalDateTime();
    }

    public static ZonedDateTime convertToZoneDateTime(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atZone(ZoneId.systemDefault());
    }

    public String getAcceptParameter(HttpServletRequest request) {
        return request.getHeader("Accept");
    }

    public boolean isJson(HttpServletRequest request) {
        String accept = getAcceptParameter(request);
        return (accept != null && accept.contains("application/json"));
    }

}
