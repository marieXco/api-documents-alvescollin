package io.swagger.mvc;


import io.swagger.exception.NotFoundDocumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler  {

    @ExceptionHandler(NotFoundDocumentException.class)
    public final ResponseEntity<Object> handleNotFoundException(NotFoundDocumentException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getErrorMessage(), ex.getHttpStatus());
    }
}
