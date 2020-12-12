package io.swagger.exception;

import io.swagger.model.ErrorDefinition;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AbstractDocumentException extends RuntimeException {

    private final transient ErrorDefinition errorMessage;
    private final HttpStatus httpStatus;



    public AbstractDocumentException(HttpStatus httpStatus, ErrorDefinition errorDefinition) {
        super(errorDefinition.getErrorMessage());
        this.errorMessage = errorDefinition;
        this.httpStatus = httpStatus;
    }

    public AbstractDocumentException(ErrorDefinition errorMessage) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
    }
}
