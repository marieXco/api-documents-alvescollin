package io.swagger.errors;

import java.util.ArrayList;
import java.util.List;

import io.swagger.api.NotFoundException;
import io.swagger.model.ErrorDefinition;
import io.swagger.model.ErrorDefinitionErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<ErrorDefinitionErrors> errors = new ArrayList<>();
        errors.add(new ErrorDefinitionErrors("404",ex.getMessage()));
        ErrorDefinition error = new ErrorDefinition(ErrorDefinition.ErrorTypeEnum.FUNCTIONAL, errors);
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

}
