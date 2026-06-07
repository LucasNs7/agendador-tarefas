package com.lucas.agendadortarefas.business.mapper;

import com.lucas.agendadortarefas.business.dto.AtualizacaoTarefaDTO;
import com.lucas.agendadortarefas.infrastructure.entity.Tarefa;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TarefaAtualizadaMapper {

    void mapeiaCamposAtualizaveis(AtualizacaoTarefaDTO dto, @MappingTarget Tarefa entity);
}
