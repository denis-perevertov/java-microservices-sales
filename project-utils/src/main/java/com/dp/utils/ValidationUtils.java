package com.dp.utils;

import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public interface ValidationUtils {

    static Map<String, String> getErrorMessages(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }

    static boolean fieldIsNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }
    static boolean fieldIsNotEmpty(Object object) { return object != null; }

    static boolean doubleValueNonNegative(Double d) { return fieldIsNotEmpty(d) && d >= 0; }
}
