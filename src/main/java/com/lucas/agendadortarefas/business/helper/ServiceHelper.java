package com.lucas.agendadortarefas.business.helper;

import com.lucas.agendadortarefas.business.dto.AtualizacaoTarefaDTO;
import com.lucas.agendadortarefas.business.dto.TarefaDTO;
import com.lucas.agendadortarefas.business.mapper.TarefaAtualizadaMapper;
import com.lucas.agendadortarefas.business.mapper.TarefaMapper;
import com.lucas.agendadortarefas.infrastructure.entity.Tarefa;
import com.lucas.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.lucas.agendadortarefas.infrastructure.exception.ConflictException;
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
    private final TarefaAtualizadaMapper tarefaAtualizadaMapper;

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

    public void verificaTarefaExistente(TarefaDTO tarefaDTO, String usuarioEmail) {
        if (
            tarefaRepository.existsByNomeTarefa(tarefaDTO.getNomeTarefa()) &&
            tarefaRepository.existsByDataEvento(tarefaDTO.getDataEvento()) &&
            tarefaRepository.existsByUsarioEmail(usuarioEmail)
        ) {
                throw new ConflictException("Tarefa já cadastrada!");
        }
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

    private Tarefa atualizaTarefa(String id, AtualizacaoTarefaDTO dto) {
        Tarefa entity = buscaTarefaPorId(id);
        entity.setDataAlteracao(LocalDateTime.now());
        tarefaAtualizadaMapper.mapeiaCamposAtualizaveis(dto, entity);
        return tarefaRepository.save(entity);
    }

    private Tarefa atualizaStatus(String id, StatusNotificacaoEnum statusNotificacao) {
        Tarefa entity = buscaTarefaPorId(id);
        entity.setStatusNotificacao(statusNotificacao);
        return tarefaRepository.save(entity);
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

    public TarefaDTO atualizarTarefa(String id, AtualizacaoTarefaDTO dto) {
        return tarefaMapper.paraTarefaDTO(atualizaTarefa(id, dto));
    }

    public TarefaDTO atualizarStatus(String id, StatusNotificacaoEnum statusNotificacao) {
        return tarefaMapper.paraTarefaDTO(atualizaStatus(id, statusNotificacao));
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
