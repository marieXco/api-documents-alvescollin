package io.swagger.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class RestUtils {

    public String returnLoggedUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
