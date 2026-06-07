package com.lucas.agendadortarefas.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtualizacaoTarefaDTO {

    @Size(min = 1, max = 200, message = "Nome da tarefa deve ter entre 1 e 200 caracteres!")
    private String nomeTarefa;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres!")
    private String descricao;

    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @FutureOrPresent(message = "Data do evento não pode ser no passado!")
    private LocalDateTime dataEvento;
}
