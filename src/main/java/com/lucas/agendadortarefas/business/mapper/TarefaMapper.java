package com.lucas.agendadortarefas.business.mapper;

import com.lucas.agendadortarefas.business.dto.TarefaDTO;
import com.lucas.agendadortarefas.infrastructure.entity.Tarefa;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefaMapper {

    Tarefa paraTarefa(TarefaDTO dto);
    TarefaDTO paraTarefaDTO(Tarefa tarefa);
}
