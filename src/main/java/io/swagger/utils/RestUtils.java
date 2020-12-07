package io.swagger.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.servlet.http.HttpServletRequest;

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
