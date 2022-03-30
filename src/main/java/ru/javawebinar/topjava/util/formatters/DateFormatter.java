package ru.javawebinar.topjava.util.formatters;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class DateFormatter implements Formatter<LocalDate> {

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        if (text.length() == 0) {
            return null;
        }
        return LocalDate.parse(text, DateTimeFormatter.ISO_DATE.withLocale(locale));
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return object == null ? null : object.format(DateTimeFormatter.ISO_DATE.withLocale(locale));
    }
}
