package io.swagger.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@UtilityClass
public class RestUtils {

    public String getAcceptParameter(HttpServletRequest request) {
        return request.getHeader("Accept");
    }

    public boolean isJson(HttpServletRequest request) {
        String accept = getAcceptParameter(request);
        return (accept != null && accept.contains("application/json"));
    }

    public String returnLoggedUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
