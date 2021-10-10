package com.vosouq.commons.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.vosouq.commons.model.ErrorMessage;
import com.vosouq.commons.util.MessageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@RestControllerAdvice
public class VosouqExceptionHandler {

    private static final String GENERAL_EXCEPTION = "GeneralException";
    private static final String VALIDATION_EXCEPTION = "ValidationException";
    private static final String GENERAL_MESSAGE_CODE = "000";
    @Value("${service.message.code}")
    private String serviceMessageCode;

    @ExceptionHandler(VosouqBaseException.class)
    public ResponseEntity<ErrorMessage> vosouqBaseExceptionHandler(VosouqBaseException exception, WebRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (exception instanceof NotFoundException)
            status = HttpStatus.NOT_FOUND;
        else if (exception instanceof AlreadyExistException)
            status = HttpStatus.CONFLICT;
        else if (exception instanceof BusinessException)
            status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorMessage errorMessage = buildErrorMessage(
                exception.getClass().getName(),
                exception.getClass().getSimpleName(),
                status.value(),
                serviceMessageCode,
                exception.getParameters());

        return new ResponseEntity<>(errorMessage, status);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorMessage> httpMediaTypeHandler(HttpMediaTypeNotSupportedException exception, WebRequest request) {

        ErrorMessage errorMessage = buildErrorMessage(
                exception.getClass().getSimpleName(),
                exception.getClass().getSimpleName(),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                GENERAL_MESSAGE_CODE);

        return new ResponseEntity<>(errorMessage, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorMessage> httpNotFoundHandler(NoHandlerFoundException exception, WebRequest request) {

        ErrorMessage errorMessage = buildErrorMessage(
                exception.getClass().getSimpleName(),
                exception.getClass().getSimpleName(),
                HttpStatus.NOT_FOUND.value(),
                GENERAL_MESSAGE_CODE);

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorMessage> httpRequestMethodHandler(HttpRequestMethodNotSupportedException exception, WebRequest request) {

        ErrorMessage errorMessage = buildErrorMessage(
                exception.getClass().getSimpleName(),
                exception.getClass().getSimpleName(),
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                GENERAL_MESSAGE_CODE);

        return new ResponseEntity<>(errorMessage, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> httpRequestReadableHandler(HttpMessageNotReadableException exception, WebRequest request) {

        Throwable throwable = exception.getCause();
        if (throwable instanceof InvalidFormatException) {
            return methodArgumentNotValidHandler((Exception) throwable, request);
        }

        ErrorMessage errorMessage = buildErrorMessage(
                exception.getClass().getSimpleName(),
                exception.getClass().getSimpleName(),
                HttpStatus.NOT_ACCEPTABLE.value(),
                GENERAL_MESSAGE_CODE);

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            MethodArgumentConversionNotSupportedException.class,
            InvalidFormatException.class})
    public ResponseEntity<ErrorMessage> methodArgumentNotValidHandler(Exception exception, WebRequest request) {

        ErrorMessage errorMessage = buildErrorMessage(
                VALIDATION_EXCEPTION,
                VALIDATION_EXCEPTION,
                HttpStatus.BAD_REQUEST.value(),
                GENERAL_MESSAGE_CODE);

        if (exception instanceof MethodArgumentNotValidException) {

            MethodArgumentNotValidException validateException = (MethodArgumentNotValidException) exception;
            List<FieldError> fieldErrors = validateException.getBindingResult().getFieldErrors();

            fieldErrors.stream()
                    .filter(fieldError ->
                            errorMessage.getFields()
                                    .stream()
                                    .noneMatch(field -> fieldError.getField().equals(field.getName())))
                    .forEach(fieldError ->
                            errorMessage.getFields().add(
                                    new ErrorMessage.Field(
                                            fieldError.getField(),
                                            MessageUtil.getMessage(fieldError.getDefaultMessage()),
                                            String.valueOf(fieldError.getRejectedValue()))));

        } else if (exception instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException mismatchException = (MethodArgumentTypeMismatchException) exception;
            errorMessage.getFields().add(
                    new ErrorMessage.Field(
                            mismatchException.getName(),
                            errorMessage.getMessage(),
                            String.valueOf(mismatchException.getValue())));

        } else if (exception instanceof MethodArgumentConversionNotSupportedException) {
            MethodArgumentConversionNotSupportedException convertException = (MethodArgumentConversionNotSupportedException) exception;
            errorMessage.getFields().add(
                    new ErrorMessage.Field(
                            convertException.getName(),
                            errorMessage.getMessage(),
                            String.valueOf(convertException.getValue())));

        } else if (exception instanceof InvalidFormatException) {
            InvalidFormatException formatException = (InvalidFormatException) exception;
            errorMessage.getFields().add(
                    new ErrorMessage.Field(
                            formatException.getPath().get(0).getFieldName(),
                            errorMessage.getMessage(),
                            String.valueOf(formatException.getValue())));
        }

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorMessage> generalExceptionHandler(Throwable exception, WebRequest request) {

        ErrorMessage errorMessage = buildErrorMessage(
                GENERAL_EXCEPTION,
                GENERAL_EXCEPTION,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                GENERAL_MESSAGE_CODE);

        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorMessage buildErrorMessage(String key,
                                           String exceptionName,
                                           int statusCode,
                                           String systemCode,
                                           String... args) {

        String code = MessageUtil.getMessage(key + ".code");
        String message = MessageUtil.getMessage(key + ".message", args);

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setCode(statusCode + systemCode + code);
        errorMessage.setMessage(message);
        errorMessage.setException(exceptionName);
        return errorMessage;
    }

}
