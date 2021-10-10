package com.vosouq.commons.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Configuration("localeResolver")
public class LocaleConfig extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

    @Value("${locale.default}")
    private String defaultLocale;

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String header = request.getHeader("Accept-Language");
        return new Locale(StringUtils.isEmpty(header) ? defaultLocale : header);
    }

    @Override
    public Locale getDefaultLocale() {
        return new Locale(defaultLocale);
    }

    public static Locale getRequestLocale() {
        return LocaleContextHolder.getLocale();
    }

}
