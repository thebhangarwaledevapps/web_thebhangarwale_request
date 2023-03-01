package com.app.request.util.internalization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class Translator {

    @Autowired
    private ResourceBundleMessageSource messageSource;

    public String toLocale(String msg) {
        return messageSource.getMessage(msg, null, LocaleContextHolder.getLocale());
    }

}
