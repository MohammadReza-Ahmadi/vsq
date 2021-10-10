package com.vosouq.commons.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vosouq.commons.config.LocaleConfig;
import com.vosouq.commons.model.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Configuration
public class MessageUtil {

    private static MessageSource messageSource;

    @Autowired
    MessageUtil(MessageSource messageSource) {
        MessageUtil.messageSource = messageSource;
    }

    public static String getMessage(String code, String... args) {
        return messageSource.getMessage(code, args, LocaleConfig.getRequestLocale());
    }

    public static String getMessage(String code, Locale locale, String... args) {
        return messageSource.getMessage(code, args, locale);
    }

    public static ErrorMessage getJsonErrorMessage(String exceptionName,
                                                   int statusCode,
                                                   String systemCode,
                                                   String... args) {

        String code = getMessage(exceptionName + ".code");
        String message = getMessage(exceptionName + ".message", args);

        return buildErrorMessage(exceptionName, statusCode, systemCode, code, message);
    }

    public static ErrorMessage getJsonErrorMessage(String exceptionName,
                                                    int statusCode,
                                                    String systemCode,
                                                    Locale locale,
                                                    String... args) {

        String code = getMessage(exceptionName + ".code", locale, args);
        String message = getMessage(exceptionName + ".message", locale, args);

        return buildErrorMessage(exceptionName, statusCode, systemCode, code, message);
    }

    public static void flushErrorMessage(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         String serviceMessageCode,
                                         HttpStatus status) throws IOException {

        String header = httpServletRequest.getHeader("Accept-Language");
        Locale locale = new Locale(StringUtils.isEmpty(header) ? "fa" : header);

        ErrorMessage errorMessage = getJsonErrorMessage(
                "SecurityException",
                status.value(),
                serviceMessageCode,
                locale);

        httpServletResponse.setStatus(status.value());
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setLocale(locale);
        httpServletResponse.setCharacterEncoding("UTF-8");

        ServletOutputStream out = httpServletResponse.getOutputStream();

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, errorMessage);

        out.flush();
    }

    private static ErrorMessage buildErrorMessage(String exceptionName,
                                                  int statusCode,
                                                  String systemCode,
                                                  String code,
                                                  String message) {

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setCode(statusCode + systemCode + code);
        errorMessage.setMessage(message);
        errorMessage.setException(exceptionName);
        return errorMessage;
    }

}
