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

    // ==> Úteis
    private List<Tarefa> verificaListaVazia(List<Tarefa> tarefaList) {
        return Optional.ofNullable(tarefaList)
                .filter(lista -> !lista.isEmpty())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Tarefas não encontradas!")
                );
    }

    private List<TarefaDTO> mapeiaListaParaTarefaDTO(List<Tarefa> tarefaList) {
        return tarefaList.stream()
                .map(tarefaMapper::paraTarefaDTO)
                .toList();
    }

    // ==> Busca Entitys
    private List<Tarefa> buscaTodasAsTarefasPorEmail(String usuarioEmail) {
        return verificaListaVazia(tarefaRepository.findByUsuarioEmail(usuarioEmail));
    }

    private Tarefa buscaTarefaPorId(String id) {
        return tarefaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tarefa com id: " + id + " não encontrada!")
        );
    }

    private List<Tarefa> buscaTarefasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return verificaListaVazia(tarefaRepository.findByDataEventoBetween(inicio, fim));
    }

    private List<Tarefa> buscaTarefasPorDataEvento(LocalDateTime dataEvento) {
        return verificaListaVazia(tarefaRepository.findByDataEvento(dataEvento));
    }

    // ==> TarefaDTO Section
    public List<TarefaDTO> buscarTodasAsTarefasPorEmail(String usuarioEmail) {
         return mapeiaListaParaTarefaDTO(buscaTodasAsTarefasPorEmail(usuarioEmail));
    }

    public TarefaDTO buscarTarefaPorId(String id) {
        return tarefaMapper.paraTarefaDTO(buscaTarefaPorId(id));
    }

    public List<TarefaDTO> buscarTarefasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return mapeiaListaParaTarefaDTO(buscaTarefasPorPeriodo(inicio, fim));
    }

    public List<TarefaDTO> buscarTarefasPorDataEvento(LocalDateTime dataEvento) {
        return mapeiaListaParaTarefaDTO(buscaTarefasPorDataEvento(dataEvento));
    }

    public TarefaDTO deletaTarefaPorId(String id) {
        TarefaDTO dto = buscarTarefaPorId(id);
        tarefaRepository.deleteById(id);
        return dto;
    }

    public List<TarefaDTO> deletaTarefasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        List<TarefaDTO> dtos = buscarTarefasPorPeriodo(inicio, fim);
        tarefaRepository.deleteByDataEventoBetween(inicio, fim);
        return dtos;
    }

    public List<TarefaDTO> deletaTarefasPorDataEvento(LocalDateTime dataEvento) {
        List<TarefaDTO> dtos = buscarTarefasPorDataEvento(dataEvento);
        tarefaRepository.deleteByDataEvento(dataEvento);
        return dtos;
    }
}
