package com.lucas.agendadortarefas.controller;

import com.lucas.agendadortarefas.business.dto.AtualizacaoStatusDTO;
import com.lucas.agendadortarefas.business.dto.AtualizacaoTarefaDTO;
import com.lucas.agendadortarefas.business.dto.TarefaDTO;
import com.lucas.agendadortarefas.business.helper.ControllerHelper;
import com.lucas.agendadortarefas.business.service.TarefaService;
import com.lucas.agendadortarefas.infrastructure.exception.ConflictException;
import com.lucas.agendadortarefas.infrastructure.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TarefaController {

    private final TarefaService tarefaService;
    private final ControllerHelper controllerHelper;

    @PostMapping
    public ResponseEntity<?> criarTarefa(@RequestBody @Valid TarefaDTO tarefaDTO,
                                                 @RequestHeader("Authorization") String token) {
        return controllerHelper.tryCatchFunction(
                () -> tarefaService.criarTarefa(token, tarefaDTO),
                HttpStatus.OK,
                ConflictException.class,
                HttpStatus.CONFLICT
        );
    }

    @GetMapping
    public ResponseEntity<?> buscarTodasAsTarefasPorEmail(@RequestHeader("Authorization") String token) {
        return controllerHelper.tryCatchFunction(
                () -> tarefaService.buscarTodasAsTarefasPorEmail(token),
                HttpStatus.OK,
                ResourceNotFoundException.class,
                HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/event")
    public ResponseEntity<?> buscarTarefaPorId(@RequestParam @Valid String id){
        return controllerHelper.tryCatchFunction(
                () -> tarefaService.buscarTarefaPorId(id),
                HttpStatus.OK,
                ResourceNotFoundException.class,
                HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/period")
    public ResponseEntity<?> buscarTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim
    ) {
        return controllerHelper.tryCatchFunction(
                () -> tarefaService.buscarTarefasPorPeriodo(inicio, fim),
                HttpStatus.OK,
                ResourceNotFoundException.class,
                HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/date")
    public ResponseEntity<?> buscarTarefasPorDataEvento(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataEvento
    ){
        return controllerHelper.tryCatchFunction(
                () -> tarefaService.buscarTarefasPorDataEvento(dataEvento),
                HttpStatus.OK,
                ResourceNotFoundException.class,
                HttpStatus.NOT_FOUND
        );
    }

    @PutMapping
    public ResponseEntity<?> atualizarTarefa(@RequestParam @Valid String id,
                                             @RequestBody @Valid AtualizacaoTarefaDTO dto){
        return controllerHelper.tryCatchFunction(
                () -> tarefaService.atualizarTarefa(id, dto),
                HttpStatus.OK,
                ResourceNotFoundException.class,
                HttpStatus.NOT_FOUND
        );
    }

    @PatchMapping("/status")
    public ResponseEntity<?> atualizarStatus(@RequestBody @Valid AtualizacaoStatusDTO dto) {
        return controllerHelper.tryCatchFunction(
                () -> tarefaService.atualizarStatus(dto.getId(), dto.getStatusNotificacao()),
                HttpStatus.OK,
                ResourceNotFoundException.class,
                HttpStatus.NOT_FOUND
        );
    }

    @DeleteMapping
    public ResponseEntity<?> deletarTarefaPorId(@RequestParam @Valid String id){
        return controllerHelper.tryCatchFunction(
                () -> tarefaService.deletarTarefaPorId(id),
                HttpStatus.OK,
                ResourceNotFoundException.class,
                HttpStatus.NOT_FOUND
        );
    }

    @DeleteMapping("/period")
    public ResponseEntity<?> deletarTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim
    ){
        return controllerHelper.tryCatchFunction(
                () -> tarefaService.deletarTarefasPorPeriodo(inicio, fim),
                HttpStatus.OK,
                ResourceNotFoundException.class,
                HttpStatus.NOT_FOUND
        );
    }

    @DeleteMapping("/date")
    public ResponseEntity<?> deletarTarefasPorDataEvento(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataEvento
    ){
        return controllerHelper.tryCatchFunction(
                () -> tarefaService.deletarTarefasPorDataEvento(dataEvento),
                HttpStatus.OK,
                ResourceNotFoundException.class,
                HttpStatus.NOT_FOUND
        );
    }
}
