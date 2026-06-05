package com.lucas.agendadortarefas.business.service;

import com.lucas.agendadortarefas.business.dto.TarefaDTO;
import com.lucas.agendadortarefas.business.mapper.TarefaMapper;
import com.lucas.agendadortarefas.infrastructure.entity.Tarefa;
import com.lucas.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.lucas.agendadortarefas.infrastructure.repository.TarefaRepository;
import com.lucas.agendadortarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final TarefaMapper tarefaMapper;
    private final JwtUtil jwtUtil;

    public TarefaDTO criarTarefa(String token, TarefaDTO tarefaDTO) {
        String email = jwtUtil.extractUsername(token.substring(7));
        tarefaDTO.setUsuarioEmail(email);
        tarefaDTO.setDataCriacao(LocalDateTime.now());
        tarefaDTO.setDataAlteracao(LocalDateTime.now());
        tarefaDTO.setStatusNotificacao(StatusNotificacaoEnum.PENDENTE);
        Tarefa entity = tarefaMapper.paraTarefa(tarefaDTO);

        return tarefaMapper.paraTarefaDTO(tarefaRepository.save(entity));
    }
}
