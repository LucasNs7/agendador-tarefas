package com.lucas.agendadortarefas.business.service;

import com.lucas.agendadortarefas.business.dto.AtualizacaoTarefaDTO;
import com.lucas.agendadortarefas.business.dto.TarefaDTO;
import com.lucas.agendadortarefas.business.mapper.TarefaAtualizadaMapper;
import com.lucas.agendadortarefas.business.mapper.TarefaMapper;
import com.lucas.agendadortarefas.infrastructure.entity.Tarefa;
import com.lucas.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.lucas.agendadortarefas.infrastructure.exception.ConflictException;
import com.lucas.agendadortarefas.infrastructure.exception.ResourceNotFoundException;
import com.lucas.agendadortarefas.infrastructure.repository.TarefaRepository;
import com.lucas.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaAtualizadaMapper tarefaAtualizadaMapper;
    private final TarefaRepository tarefaRepository;
    private final TarefaMapper tarefaMapper;
    private final JwtUtil jwtUtil;

    // ==> Úteis
    private String pegaEmail(String token){
        return jwtUtil.extractUsername(token.substring(7));
    }

    @Transactional
    private void verificaTarefaExistente(TarefaDTO tarefaDTO, String usuarioEmail) {
        if (
            tarefaRepository.existsByNomeTarefa(tarefaDTO.getNomeTarefa()) &&
            tarefaRepository.existsByDataEvento(tarefaDTO.getDataEvento()) &&
            tarefaRepository.existsByUsuarioEmail(usuarioEmail)
        ) {
                throw new ConflictException("Tarefa já cadastrada!");
        }
    }

    private List<Tarefa> verificaListaVazia(List<Tarefa> tarefaList) {
        return Optional.ofNullable(tarefaList)
                .filter(lista -> !lista.isEmpty())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Tarefas não encontradas!")
                );
    }

    @Transactional(readOnly = true)
    private List<Tarefa> buscaTodasAsTarefasPorEmail(String usuarioEmail) {
        return verificaListaVazia(tarefaRepository.findByUsuarioEmail(usuarioEmail));
    }

    @Transactional(readOnly = true)
    private Tarefa buscaTarefaPorId(String id) {
        return tarefaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Tarefa com id: " + id + " não encontrada!")
        );
    }

    @Transactional
    private List<Tarefa> buscaTarefasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return verificaListaVazia(
                tarefaRepository.findByDataEventoBetweenAndStatusNotificacao(
                        inicio, fim, StatusNotificacaoEnum.PENDENTE
                )
        );
    }

    @Transactional
    private List<Tarefa> buscaTarefasPorDataEvento(LocalDateTime dataEvento) {
        return verificaListaVazia(tarefaRepository.findByDataEvento(dataEvento));
    }

    @Transactional
    public TarefaDTO criarTarefa(String token, TarefaDTO tarefaDTO) {
        String usuarioEmail = pegaEmail(token);
        verificaTarefaExistente(tarefaDTO, usuarioEmail);

        tarefaDTO.setUsuarioEmail(usuarioEmail);
        tarefaDTO.setDataCriacao(LocalDateTime.now());
        tarefaDTO.setDataAlteracao(LocalDateTime.now());
        tarefaDTO.setStatusNotificacao(StatusNotificacaoEnum.PENDENTE);
        Tarefa entity = tarefaMapper.paraTarefa(tarefaDTO);

        return tarefaMapper.paraTarefaDTO(tarefaRepository.save(entity));
    }

    @Transactional
    public List<TarefaDTO> buscarTodasAsTarefasPorEmail(String token) {
        return tarefaMapper.paraListaTarefaDTO(buscaTodasAsTarefasPorEmail(pegaEmail(token)));
    }

    @Transactional
    public TarefaDTO buscarTarefaPorId(String id) {
        return tarefaMapper.paraTarefaDTO(buscaTarefaPorId(id));
    }

    @Transactional
    public List<TarefaDTO> buscarTarefasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return tarefaMapper.paraListaTarefaDTO(buscaTarefasPorPeriodo(inicio, fim));
    }

    @Transactional
    public List<TarefaDTO> buscarTarefasPorDataEvento(LocalDateTime dataEvento) {
        return tarefaMapper.paraListaTarefaDTO(buscaTarefasPorDataEvento(dataEvento));
    }

    @Transactional
    public TarefaDTO atualizarTarefa(String id, AtualizacaoTarefaDTO dto) {
        Tarefa entity = buscaTarefaPorId(id);
        entity.setDataAlteracao(LocalDateTime.now());
        tarefaAtualizadaMapper.mapeiaCamposAtualizaveis(dto, entity);
        return tarefaMapper.paraTarefaDTO(tarefaRepository.save(entity));
    }

    @Transactional
    public TarefaDTO atualizarStatus(String id, StatusNotificacaoEnum statusNotificacao) {
        Tarefa entity = buscaTarefaPorId(id);
        entity.setStatusNotificacao(statusNotificacao);
        return tarefaMapper.paraTarefaDTO(tarefaRepository.save(entity));
    }

    @Transactional
    public TarefaDTO deletarTarefaPorId(String id) {
        TarefaDTO dto = buscarTarefaPorId(id);
        tarefaRepository.deleteById(id);
        return dto;
    }

    @Transactional
    public List<TarefaDTO> deletarTarefasPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        List<TarefaDTO> dtos = buscarTarefasPorPeriodo(inicio, fim);
        tarefaRepository.deleteByDataEventoBetween(inicio, fim);
        return dtos;
    }

    @Transactional
    public List<TarefaDTO> deletarTarefasPorDataEvento(LocalDateTime dataEvento) {
        List<TarefaDTO> dtos = buscarTarefasPorDataEvento(dataEvento);
        tarefaRepository.deleteByDataEvento(dataEvento);
        return dtos;
    }
}
