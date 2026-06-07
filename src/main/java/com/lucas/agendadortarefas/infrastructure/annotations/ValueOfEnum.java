package com.lucas.agendadortarefas.infrastructure.annotations;

import com.lucas.agendadortarefas.infrastructure.validator.ValueOfEnumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValueOfEnumValidator.class)
public @interface ValueOfEnum {
    Class<? extends Enum<?>> enumClass();
    String message() default "Valor inválido para o campo";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
