package com.lucas.agendadortarefas.business.service;

import com.lucas.agendadortarefas.business.dto.TarefaDTO;
import com.lucas.agendadortarefas.business.helper.ServiceHelper;
import com.lucas.agendadortarefas.business.mapper.TarefaMapper;
import com.lucas.agendadortarefas.infrastructure.entity.Tarefa;
import com.lucas.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.lucas.agendadortarefas.infrastructure.repository.TarefaRepository;
import com.lucas.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final ServiceHelper serviceHelper;
    private final TarefaMapper tarefaMapper;
    private final JwtUtil jwtUtil;

    public TarefaDTO criarTarefa(String token, TarefaDTO tarefaDTO) {
        String usuarioEmail = jwtUtil.extractUsername(token.substring(7));
        tarefaDTO.setUsuarioEmail(usuarioEmail);
        tarefaDTO.setDataCriacao(LocalDateTime.now());
        tarefaDTO.setDataAlteracao(LocalDateTime.now());
        tarefaDTO.setStatusNotificacao(StatusNotificacaoEnum.PENDENTE);
        Tarefa entity = tarefaMapper.paraTarefa(tarefaDTO);

        return tarefaMapper.paraTarefaDTO(tarefaRepository.save(entity));
    }

    @Transactional
    public List<TarefaDTO> buscarTodasAsTarefasPorEmail(String token) {
        String usuarioEmail = jwtUtil.extractUsername(token.substring(7));
        return serviceHelper.buscarTodasAsTarefasPorEmail(usuarioEmail);
    }

    @Transactional
    public TarefaDTO buscarTarefaPorId(String id) {
        return serviceHelper.buscarTarefaPorId(id);
    }

    @Transactional
    public TarefaDTO deletarTarefaPorId(String id) {
        return serviceHelper.deletaTarefaPorId(id);
    }
}
