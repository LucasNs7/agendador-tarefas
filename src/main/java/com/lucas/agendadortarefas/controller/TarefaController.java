package com.lucas.agendadortarefas.controller;

import com.lucas.agendadortarefas.business.dto.TarefaDTO;
import com.lucas.agendadortarefas.business.service.TarefaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TarefaController {

    private final TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<TarefaDTO> criarTarefa(@RequestBody TarefaDTO tarefaDTO,
                                                 @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(tarefaService.criarTarefa(token, tarefaDTO));
    }
}
