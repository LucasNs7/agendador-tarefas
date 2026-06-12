package com.lucas.agendadortarefas.business.mapper;

import com.lucas.agendadortarefas.business.dto.TarefaDTO;
import com.lucas.agendadortarefas.infrastructure.entity.Tarefa;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefaMapper {

    Tarefa paraTarefa(TarefaDTO dto);
    TarefaDTO paraTarefaDTO(Tarefa tarefa);
    List<TarefaDTO> paraListaTarefaDTO(List<Tarefa> tarefas);
}
