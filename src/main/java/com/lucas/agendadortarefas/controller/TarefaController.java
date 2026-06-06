package com.lucas.agendadortarefas.controller;

import com.lucas.agendadortarefas.business.dto.TarefaDTO;
import com.lucas.agendadortarefas.business.helper.ControllerHelper;
import com.lucas.agendadortarefas.business.service.TarefaService;
import com.lucas.agendadortarefas.infrastructure.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TarefaController {

    private final TarefaService tarefaService;
    private final ControllerHelper controllerHelper;

    @PostMapping
    public ResponseEntity<TarefaDTO> criarTarefa(@RequestBody @Valid TarefaDTO tarefaDTO,
                                                 @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(tarefaService.criarTarefa(token, tarefaDTO));
    }

    @GetMapping()
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

    @DeleteMapping()
    public ResponseEntity<?> deletarTarefaPorId(@RequestParam @Valid String id){
        return controllerHelper.tryCatchFunction(
                () -> tarefaService.deletarTarefaPorId(id),
                HttpStatus.OK,
                ResourceNotFoundException.class,
                HttpStatus.NOT_FOUND
        );
    }
}
