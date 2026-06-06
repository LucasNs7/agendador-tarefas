package com.lucas.agendadortarefas.business.helper;

import com.lucas.agendadortarefas.business.dto.TarefaDTO;
import com.lucas.agendadortarefas.business.mapper.TarefaMapper;
import com.lucas.agendadortarefas.infrastructure.entity.Tarefa;
import com.lucas.agendadortarefas.infrastructure.exception.ResourceNotFoundException;
import com.lucas.agendadortarefas.infrastructure.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ServiceHelper {

    private final TarefaRepository tarefaRepository;
    private final TarefaMapper tarefaMapper;

    // ==> Busca Entitys
    private List<Tarefa> buscaTodasAsTarefasPorEmail(String usuarioEmail) {
        return Optional.ofNullable(tarefaRepository.findByUsuarioEmail(usuarioEmail))
                .filter(lista -> !lista.isEmpty())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Tarefas não encontradas!")
                );
    }

    // ==> TarefaDTO Section
    public List<TarefaDTO> buscarTodasAsTarefasPorEmail(String usuarioEmail) {
         return buscaTodasAsTarefasPorEmail(usuarioEmail).stream()
                 .map(tarefaMapper::paraTarefaDTO)
                 .toList();
    }
}
