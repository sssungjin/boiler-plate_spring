package com.sontcamp.homework.constant;

import com.sontcamp.homework.annotation.Date;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
@Slf4j
public class LocalDateValidator implements ConstraintValidator<Date, String> {
    static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        try {
            LocalDate.parse(value, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            log.error("Invalid Date Format. (yyyy-MM-dd)");
            return false;
        }
        return true;
    }
}