package io.swagger.exception;

import java.util.ArrayList;
import java.util.List;

import io.swagger.model.ErrorDefinition;
import io.swagger.model.ErrorDefinitionErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request) {
        List<ErrorDefinitionErrors> errors = new ArrayList<>();
        errors.add(new ErrorDefinitionErrors("404",ex.getMessage()));
        ErrorDefinition error = new ErrorDefinition(ErrorDefinition.ErrorTypeEnum.FUNCTIONAL, errors);
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleBadRequestException(Exception ex, WebRequest request) {
        List<ErrorDefinitionErrors> errors = new ArrayList<>();
        errors.add(new ErrorDefinitionErrors("400",ex.getMessage()));
        ErrorDefinition error = new ErrorDefinition(ErrorDefinition.ErrorTypeEnum.TECHNICAL, errors);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LockedException.class)
    public final ResponseEntity<Object> handleLockedException(Exception ex, WebRequest request) {
        List<ErrorDefinitionErrors> errors = new ArrayList<>();
        errors.add(new ErrorDefinitionErrors("423",ex.getMessage()));
        ErrorDefinition error = new ErrorDefinition(ErrorDefinition.ErrorTypeEnum.FUNCTIONAL, errors);
        return new ResponseEntity(error, HttpStatus.LOCKED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public final ResponseEntity<Object> handleForbiddenException(Exception ex, WebRequest request) {
        List<ErrorDefinitionErrors> errors = new ArrayList<>();
        errors.add(new ErrorDefinitionErrors("403",ex.getMessage()));
        ErrorDefinition error = new ErrorDefinition(ErrorDefinition.ErrorTypeEnum.TECHNICAL, errors);
        return new ResponseEntity(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<Object> handleConflictException(Exception ex, WebRequest request) {
        List<ErrorDefinitionErrors> errors = new ArrayList<>();
        errors.add(new ErrorDefinitionErrors("409",ex.getMessage()));
        ErrorDefinition error = new ErrorDefinition(ErrorDefinition.ErrorTypeEnum.FUNCTIONAL, errors);
        return new ResponseEntity(error, HttpStatus.CONFLICT);
    }





}
