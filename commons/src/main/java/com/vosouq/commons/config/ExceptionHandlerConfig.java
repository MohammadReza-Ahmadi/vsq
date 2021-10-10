package com.vosouq.commons.config;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.vosouq.commons.config.feign.FeignDecodeException;
import com.vosouq.commons.exception.AlreadyExistException;
import com.vosouq.commons.exception.VosouqBaseException;
import com.vosouq.commons.model.ErrorMessage;
import com.vosouq.commons.util.MessageUtil;
import feign.codec.DecodeException;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerConfig {

    private static final String GENERAL_EXCEPTION = "GeneralException";
    private static final String VALIDATION_EXCEPTION = "ValidationException";
    private static final String COMMONS_SYSTEM_CODE = "000";
    @Value("${service.message.code}")
    private String serviceMessageCode;

    @ExceptionHandler(VosouqBaseException.class)
    public ResponseEntity<ErrorMessage> vosouqBaseExceptionHandler(VosouqBaseException exception, WebRequest request) {

        HttpStatus status;

        if (exception instanceof AlreadyExistException)
            status = HttpStatus.CONFLICT;
        else
            status = HttpStatus.NOT_ACCEPTABLE;

        ErrorMessage errorMessage = MessageUtil.getJsonErrorMessage(
                exception.getClass().getSimpleName(),
                status.value(),
                serviceMessageCode,
                exception.getParameters());

        for (String key : exception.getExtraParams().keySet()) {
            errorMessage.getExtraParams().add(
                    new ErrorMessage.ExtraParams(key, exception.getExtraParams().get(key)));
        }

        logError(exception, errorMessage, request);

        return new ResponseEntity<>(errorMessage, status);
    }

    @ExceptionHandler(DecodeException.class)
    public ResponseEntity<ErrorMessage> decodeExceptionHandler(DecodeException decodeException, WebRequest request) {

        ErrorMessage errorMessage;

        if (decodeException instanceof FeignDecodeException) {
            errorMessage = ((FeignDecodeException) decodeException).getErrorMessage();
        } else {
            errorMessage = MessageUtil.getJsonErrorMessage(
                decodeException.getClass().getSimpleName(),
                decodeException.status(),
                COMMONS_SYSTEM_CODE);
        }

        return new ResponseEntity<>(errorMessage, HttpStatus.valueOf(decodeException.status()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorMessage> httpMediaTypeHandler(HttpMediaTypeNotSupportedException exception, WebRequest request) {

        ErrorMessage errorMessage = MessageUtil.getJsonErrorMessage(
                exception.getClass().getSimpleName(),
                HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
                COMMONS_SYSTEM_CODE);

        logError(exception, errorMessage, request);

        return new ResponseEntity<>(errorMessage, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorMessage> httpNotFoundHandler(NoHandlerFoundException exception, WebRequest request) {

        ErrorMessage errorMessage = MessageUtil.getJsonErrorMessage(
                exception.getClass().getSimpleName(),
                HttpStatus.NOT_FOUND.value(),
                COMMONS_SYSTEM_CODE);

        logError(exception, errorMessage, request);

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorMessage> httpRequestMethodHandler(HttpRequestMethodNotSupportedException exception, WebRequest request) {

        ErrorMessage errorMessage = MessageUtil.getJsonErrorMessage(
                exception.getClass().getSimpleName(),
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                COMMONS_SYSTEM_CODE);

        logError(exception, errorMessage, request);

        return new ResponseEntity<>(errorMessage, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> httpRequestReadableHandler(HttpMessageNotReadableException exception, WebRequest request) {

        Throwable throwable = exception.getCause();
        if (throwable instanceof InvalidFormatException) {
            return methodArgumentNotValidHandler((Exception) throwable, request);
        }

        ErrorMessage errorMessage = MessageUtil.getJsonErrorMessage(
                exception.getClass().getSimpleName(),
                HttpStatus.NOT_ACCEPTABLE.value(),
                COMMONS_SYSTEM_CODE);

        logError(exception, errorMessage, request);

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({
        MethodArgumentNotValidException.class,
        MethodArgumentTypeMismatchException.class,
        MethodArgumentConversionNotSupportedException.class,
        InvalidFormatException.class})
    public ResponseEntity<ErrorMessage> methodArgumentNotValidHandler(Exception exception, WebRequest request) {

        ErrorMessage errorMessage = MessageUtil.getJsonErrorMessage(
            VALIDATION_EXCEPTION,
            HttpStatus.BAD_REQUEST.value(),
            COMMONS_SYSTEM_CODE);

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

        logError(exception, errorMessage, request);

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorMessage> generalExceptionHandler(Throwable exception, WebRequest request) {

        ErrorMessage errorMessage = MessageUtil.getJsonErrorMessage(
                GENERAL_EXCEPTION,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                COMMONS_SYSTEM_CODE);

        logError(exception, errorMessage, request);

        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void logError(Throwable throwable,
                          ErrorMessage errorMessage,
                          WebRequest request) {
        log.error(
                "ExceptionHandlerConfig(RestControllerAdvice) - " +
                        "request: [{} {}], exception class: {}, error code: {}, error message: {} ",
                getMethod(request),
                getURI(request),
                errorMessage.getException(),
                errorMessage.getCode(),
                errorMessage.getMessage());

        log.error(errorMessage.getException() + " - cause: ", throwable);
    }

    private String getMethod(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getMethod();
    }

    private String getURI(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }

}
