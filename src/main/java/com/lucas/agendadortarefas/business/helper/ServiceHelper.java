package com.lucas.agendadortarefas.business.helper;

import com.lucas.agendadortarefas.business.dto.TarefaDTO;
import com.lucas.agendadortarefas.business.mapper.TarefaMapper;
import com.lucas.agendadortarefas.infrastructure.entity.Tarefa;
import com.lucas.agendadortarefas.infrastructure.exception.ResourceNotFoundException;
import com.lucas.agendadortarefas.infrastructure.repository.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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

    private Tarefa buscaTarefaPorId(String id) {
        return tarefaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tarefa com id: " + id + " não encontrada!")
        );
    }

    private List<Tarefa> buscaTarefasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return Optional.ofNullable(tarefaRepository.findByDataEventoBetween(inicio, fim))
                .filter(lista -> !lista.isEmpty())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Tarefas não  encontradas!")
                );
    }

    public List<Tarefa> buscaTarefasPorDataEvento(LocalDateTime dataEvento) {
        return Optional.ofNullable(tarefaRepository.findByDataEvento(dataEvento))
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

    public TarefaDTO buscarTarefaPorId(String id) {
        return tarefaMapper.paraTarefaDTO(buscaTarefaPorId(id));
    }

    public List<TarefaDTO> buscarTarefasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return buscaTarefasPorPeriodo(inicio, fim).stream()
                .map(tarefaMapper::paraTarefaDTO)
                .toList();
    }

    public List<TarefaDTO> buscarTarefasPorDataEvento(LocalDateTime dataEvento) {
        return buscaTarefasPorDataEvento(dataEvento).stream()
                .map(tarefaMapper::paraTarefaDTO)
                .toList();
    }

    public TarefaDTO deletaTarefaPorId(String id) {
        TarefaDTO dto = buscarTarefaPorId(id);
        tarefaRepository.deleteById(id);
        return dto;
    }
}
