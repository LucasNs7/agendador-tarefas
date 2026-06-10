package com.lucas.agendadortarefas.infrastructure.repository;

import com.lucas.agendadortarefas.infrastructure.entity.Tarefa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TarefaRepository extends MongoRepository<Tarefa, String> {

    List<Tarefa> findByUsuarioEmail(String usuarioEmail);

    Optional<Tarefa> findById(String id);

    boolean existsByDataEvento(LocalDateTime dataEvento);

    boolean existsByUsarioEmail(String usarioEmail);

    boolean existsByNomeTarefa(String nomeTarefa);

    List<Tarefa> findByDataEventoBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Tarefa> findByDataEvento(LocalDateTime dataEvento);

    void deleteById(String id);

    void deleteByDataEventoBetween(LocalDateTime inicio, LocalDateTime fim);

    void deleteByDataEvento(LocalDateTime dataEvento);
}
