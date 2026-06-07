package com.lucas.agendadortarefas.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lucas.agendadortarefas.infrastructure.annotations.ValueOfEnum;
import com.lucas.agendadortarefas.infrastructure.enums.StatusNotificacaoEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtualizacaoStatusDTO {

    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$",
            message = "Insira um id válido!"
    )
    private String id;

    @JsonFormat(with = JsonFormat.Feature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
    @NotNull(message = "Status inválido! Use apenas: PENDENTE, ENVIADO ou CANCELADO.")
    @ValueOfEnum(enumClass = StatusNotificacaoEnum.class)
    private StatusNotificacaoEnum statusNotificacao;
}
