package com.vosouq.scoringcommunicator.infrastructures.configs;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

import static com.vosouq.scoringcommunicator.infrastructures.Constants.TEN_INT;
import static com.vosouq.scoringcommunicator.infrastructures.Constants.UTF8;

@Configuration
public class Internationalization /*implements WebMvcConfigurer*/ {

    @Bean
    public MessageSource creditMessageSource() {
        ReloadableResourceBundleMessageSource creditMessageSource = new ReloadableResourceBundleMessageSource();
        creditMessageSource.setBasenames("classpath:messages/messages","classpath:fields/fields");
        creditMessageSource.setDefaultEncoding(UTF8);
        creditMessageSource.setCacheSeconds(TEN_INT); //reload messages every 10 seconds
        return creditMessageSource;
    }

    //@Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH);
        return slr;
    }

    //@Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    //@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
