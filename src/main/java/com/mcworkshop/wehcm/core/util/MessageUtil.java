package com.mcworkshop.wehcm.core.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * Created by markfredchen on 8/21/15.
 */
public class MessageUtil {
    public static String getMessage(String messageKey, MessageSource messageSource) {
        return messageSource.getMessage(messageKey, new String[]{}, Locale.SIMPLIFIED_CHINESE);
    }
}
