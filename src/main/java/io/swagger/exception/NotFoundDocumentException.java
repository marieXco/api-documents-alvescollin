package io.swagger.exception;

import io.swagger.model.ErrorDefinition;
import org.springframework.http.HttpStatus;

public class NotFoundDocumentException extends AbstractDocumentException {
    public static final NotFoundDocumentException DEFAULT = new NotFoundDocumentException();

    public static final String NOT_FOUND_CODE = "err.func.wired.notfound";
    public static final String NOT_FOUND_MESSAGE = "The Ressource is not foud";

    public NotFoundDocumentException() {
        super(HttpStatus.NOT_FOUND,
                ErrorDefinition.builder()
                        .errorCode(NOT_FOUND_CODE)
                        .errorMessage(NOT_FOUND_MESSAGE)
                        .build());
    }
}
