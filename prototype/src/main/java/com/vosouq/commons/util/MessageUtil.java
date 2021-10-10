package com.vosouq.commons.util;

import com.vosouq.commons.config.LocaleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;

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

}
