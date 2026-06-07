package com.lucas.agendadortarefas.business.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class ControllerHelper {

    public <T> ResponseEntity<?> tryCatchFunction(Supplier<T> action, HttpStatus actionStatusCode, Class<?
            extends RuntimeException> exceptionClass, HttpStatus exceptionStatusCode) {
        try {
            return ResponseEntity.status(actionStatusCode).body(action.get());
        } catch (Exception e) {
            if (exceptionClass.isInstance(e)) {
                return ResponseEntity.status(exceptionStatusCode).body(e.getMessage());
            }
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro no servidor!");
        }
    }
}
