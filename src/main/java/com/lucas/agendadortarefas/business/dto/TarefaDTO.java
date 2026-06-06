package com.lucas.agendadortarefas.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lucas.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarefaDTO {

    private String id;

    @Size(min = 1, max = 200, message = "Nome da tarefa deve ter entre 1 e 200 caracteres!")
    @NotBlank(message = "Nome da tarefa é obrigatório!")
    private String nomeTarefa;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres!")
    private String descricao;


    private LocalDateTime dataCriacao;

    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @NotNull(message = "Data do evento é obrigatória!")
    @FutureOrPresent(message = "Data do evento não pode ser no passado!")
    private LocalDateTime dataEvento;

    private String usuarioEmail;

    private LocalDateTime dataAlteracao;

    private StatusNotificacaoEnum statusNotificacao;
}
