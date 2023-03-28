package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(@Nullable String text, Locale locale) {
        return StringUtils.hasLength(text) ? LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null;
    }

    @Override
    public String print(LocalDate localDate, Locale locale) {

        return localDate.toString();
    }
}