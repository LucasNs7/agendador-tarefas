package com.lucas.agendadortarefas.infrastructure.validator;

import com.lucas.agendadortarefas.infrastructure.annotations.ValueOfEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Stream;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, Object> {
    private List<String> acceptedValues;

    @Override
    public void initialize(ValueOfEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true;
        String name = (value instanceof Enum<?>) ? ((Enum<?>) value).name() : value.toString();
        return acceptedValues.contains(name.toUpperCase());
    }
}
